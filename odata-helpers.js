const bodyParser = require('body-parser');
const axios = require('axios');

/** Returns true if the property name refers to a navigation property (i.e., begins with "to_") */
function isNavigationProperty(propertyName) {
    return propertyName.startsWith('to_');
};

/**
 * Removes the first segment of the navigation property path
 * @param {string} propertyName Navigation property path
 */
function removeFirstNavigationPath(propertyName) {
    return propertyName.substr(propertyName.split('/', 1)[0].length + 1);
};

/**
 * Reduce the given results based on the given filter.
 * @param {any[]} result result items to filter
 * @param {string} singleFilter filter definition
 */
function applyFilter(result, singleFilter) {
    if (singleFilter.includes(' eq \'')) {
        const filterRegex = /^\(?(\w+) eq '(.*)'\)?$/;
        const [, filterProperty, filterValue] = filterRegex.exec(singleFilter);
        return applyEqFilter(result, filterProperty, filterValue);
    } else if (singleFilter.includes(' ne \'')) {
        const filterRegex = /^\(?(\w+) ne '(.*)'\)?$/;
        const [, filterProperty, filterValue] = filterRegex.exec(singleFilter);
        return applyNeFilter(result, filterProperty, filterValue);
    } else if (singleFilter.includes(' ge datetime\'')) {
        const filterRegex = /^\(?(\w+) ge datetime'(.*)'\)?$/;
        const [, filterProperty, filterValue] = filterRegex.exec(singleFilter);
        return applyGeFilter(result, filterProperty, transformDateTime(filterValue));
    } else if (singleFilter.includes(' le datetime\'')) {
        const filterRegex = /^\(?(\w+) le datetime'(.*)'\)?$/;
        const [, filterProperty, filterValue] = filterRegex.exec(singleFilter);
        return applyLeFilter(result, filterProperty, transformDateTime(filterValue));
    }
}

/**
 * Transform datetime defintion to OData v2 date
 * @param {string} dateTime
 */
function transformDateTime(dateTime) {
    const [dateParts] = dateTime.split('T');
    const [year, month, day] = dateParts.split('-').map(p => parseInt(p));
    return `/Date(${Date.UTC(year, month-1, day)})/`;
}

function applyEqFilter(result, filterProperty, filterValue) {
    return result.filter(item => item[filterProperty] === filterValue);
}

function applyNeFilter(result, filterProperty, filterValue) {
    return result.filter(item => item[filterProperty] !== filterValue);
}

function applyGeFilter(result, filterProperty, filterValue) {
    return result.filter(item => item[filterProperty] >= filterValue);
}

function applyLeFilter(result, filterProperty, filterValue) {
    return result.filter(item => item[filterProperty] <= filterValue);
}

/**
 * Deals with navigation properties of the supplied entity per the expand specification.
 * Navigation properties that are part of the expanded properties are included in the result object.
 * The value of navigation properties that shall not be expanded is set to an object { "__deferred": ... }.
 * Non-navigation properties are left untouched.
 *
 * The transformation is applied recursively.
 * @param {*} entity Entity to process
 * @param {string[]} expandedProperties Navigation properties to include as expanded properties.
 */
const handleEntityNavPropertiesForExpand = function(entity, expandedProperties = []) {
    return Object.entries(entity).reduce(function(result, [key, value]) {
        if(isNavigationProperty(key)) {
            if(expandedProperties.includes(key) ||
                expandedProperties.find( (property) => property.startsWith(key))) {
                // TODO: handle single-valued nav properties
                if(value && value.results && value.results.length > 0) {
                    const associatedEntities = value.results;
                    // Recursively handle expansion of navigation properties
                    const expandedPropertiesForNavProperty = expandedProperties
                        .filter( (property) => property.startsWith(key + '/') )
                        .map(removeFirstNavigationPath);
                    // Construct a new object, do not modify value
                    result[key] = {
                        results: handleEntitySetNavPropertiesForExpand(value.results, expandedPropertiesForNavProperty)
                    };
                } else {
                    result[key] = value;
                }
            } else {
                result[key] = {
                    "__deferred": {
                        "uri": `${entity.__metadata.uri}/${key}`
                    }
                };
            }
        } else {
            result[key] = value;
        }

        return result;
    }, {});
};

/**
 * Deals with navigation properties of the supplied entity set per the expand specification.
 * @see handleEntityNavPropertiesForExpand
 */
const handleEntitySetNavPropertiesForExpand = function(entityArray, expandedProperties) {
    return entityArray.map( (item) => handleEntityNavPropertiesForExpand(item, expandedProperties) );
}

/**
 * Reduces the entity to only contain the selected properties (and meta properties).
 * @param {Object} entity Entity to reduce
 * @param {string[]} selectedProperties Properties to keep, or all, if empty
 */
const reduceEntityToSelect = function(entity, selectedProperties = []) {
    if(0 === selectedProperties.length) {
        return entity;
    }

    return Object.entries(entity).reduce(function(result, [key, value]) {
        const isNavProperty = isNavigationProperty(key);
        if('__metadata' === key || selectedProperties.includes(key) ||
                (selectedProperties.includes('*')) && !isNavProperty) {
            result[key] = value;
        }
        else if(isNavProperty) {
            const selectedPropertiesForNavProperty = selectedProperties
                .filter( (item) => item.startsWith(key + '/') )
                .map( removeFirstNavigationPath );
            if(selectedPropertiesForNavProperty.length > 0 && value) {
                // TODO: handle single-valued nav properties
                const associatedEntities = value.results;
                // Construct a new object, do not modify value
                result[key] = { results: reduceEntitySetToSelect(associatedEntities, selectedPropertiesForNavProperty)};
            }
        }
        return result;
    }, {});
};

/**
 * Reduces all entities of the given array to only contain the selected properties (and meta properties)
 * @param {Object[]} entityArray Array of entities to reduce
 * @param {string[]} selectedProperties Properties to keep, or all, if empty
 */
function reduceEntitySetToSelect(entityArray, selectedProperties) {
    return entityArray.map(item => reduceEntityToSelect(item, selectedProperties));
};

function replaceHost(text, req) {
    const urlPrefix = `${req.protocol}://${req.get('host')}`;
    return text.replace(/https:\/\/{host}:{port}/g, urlPrefix);
};

/**
 * Returns the method and url as an array
 * @param {string} subRequest Part of a batch request
 */
function findRequestMetadata(subRequest) {
    const [, request, payload] = subRequest.split('\n\n');
    const data = payload.trim()

    const [requestMetaData, ...requestHeaders] = request.split('\n');
    const [method, url] = requestMetaData.split(' ');

    const headers = requestHeaders
        .map(h => h.split(':'))
        .reduce((obj, [key, value]) => {
            return { ...obj, [key.trim()]: value.trim() };
        }, {});

    return {
        method,
        url,
        headers,
        ...(data ? { data: data } : {})
    };
}

/**
 * Split changeset into sub requests
 * @param {string} changeset
 */
function splitChangeset(changeset) {
    const changesetId = changeset.split('\n')[0].split('=')[1];
    return changeset.split(`--${changesetId}\n`).slice(1);
}

/**
 * Execute a rub request of batch
 * @param {string} baseUrl The baseurl of the current request
 * @param {string} batchPart The request definition in the batch request
 */
async function executeBatchSubrequest(baseUrl, batchPart) {
    const config = findRequestMetadata(batchPart);
    config.url = `${baseUrl}${config.url}`;

    return axios.request(config)
        .then(response => wrapSingleRequestResponse(response));

}

/**
 * Execute all subrequests for a changeset
 * @param {string} baseUrl Base url for the current request, to be used with the sub request
 * @param {string} changeset Definition of the changeset, containing definitions for all sub requests
 */
async function executeChangeset(baseUrl, changeset) {
    return Promise.all(
        splitChangeset(changeset).map(changesetPart => executeBatchSubrequest(baseUrl, changesetPart))
    )
    .then(result => wrapChangesetResponse(result))
    .catch(error => {
        return wrapSingleRequestResponse(error.response)
    });
}

/**
 * Create a batch response from the response for a single sub request
 * @param {AxiosResponse} response single response of a sub request
 */
function wrapSingleRequestResponse(response) {
    const body = [
        `HTTP/1.1 ${response.status} ${response.statusText}`,
        `Content-Type: ${response.headers['content-type']}`,
        `Content-Length: ${response.headers['content-length']}`,
        'dataserviceversion: 2.0',
        'cache-control: no-store, no-cache',
        '',
        JSON.stringify(response.data)
    ].join('\n');

    const header = [
        'Content-Type: application/http',
        `Content-Length: ${body.length}`,
        'content-transfer-encoding: binary'
    ].join('\n');

    return `${header}\n\n${body}`;
}

/**
 * Create a changeset response for the single responses of all requests within a batch changeset
 * @param {string[]} responses wrapped responses for the single sub requests
 */
function wrapChangesetResponse(responses) {
    const responsesBody = responses.join(`\n--changeset\n`);
    const header = [
        `Content-Type: multipart/mixed; boundary=changeset`,
        `Content-Length: ${responsesBody.length}`
    ];

    return [
        ...header,
        '',
        `--changeset`,
        responsesBody,
        `--changeset--`
    ].join('\n');
}

/**
 * Wrap all first level responses for a batch request in a batch response
 * @param {string[]} responses All responses for sub requests
 */
function wrapBatchResponse(responses) {
    return [
        `--batch`,
        responses.join(`\n--batch\n`),
        `--batch--`
    ].join('\n');
}

module.exports = {
    /** Send the result as an OData response */
    sendAsODataResult: function(req, res, next) {
        const result = res.result;
        const arrayWrapped = Array.isArray(result) ? { results: result } : result;
        const bodyAsString = JSON.stringify({ d: arrayWrapped });
        const bodyWithHost = replaceHost(bodyAsString, req);

        res.set('Content-Type', 'application/json');
        res.send(bodyWithHost);
    },

    /** Send 404 response if result is undefined */
    send404IfNotFound: function (req, res, next) {
        if(res.result) {
            next();
        } else {
            console.log("No result, responding with 404")
            res.sendStatus(404);
        }
    },

    /** Send 204 response for no content */
    send204NoContent: function(req, res, next) {
        res.sendStatus(204);
    },

    /** Set 201 response for created */
    set201Created: function(req, res, next) {
        res.status(201);
        const location = res.result && res.result.__metadata ? res.result.__metadata.uri : '';
        res.location(replaceHost(location, req));
        next();
    },

    /** Expand each result entity (and replace not queried content with __deferred) */
    expand: function(req, res, next) {
        const expandQuery = req.query.$expand;
        const propertiesToExpand = expandQuery? expandQuery.split(',') : [];

        res.result = Array.isArray(res.result)?
            handleEntitySetNavPropertiesForExpand(res.result, propertiesToExpand) :
            handleEntityNavPropertiesForExpand(res.result, propertiesToExpand);

        next();
    },

    /** Filter the result set per the $filter query option */
    filter: function(req, res, next) {
        var filterQuery = req.query.$filter;

        if(filterQuery) {
            // dirty hack to only accept the first level of and expressions
            filterQuery = filterQuery.startsWith('(') && filterQuery.endsWith(')') ? filterQuery.slice(1,-1) : filterQuery;
            const singleFilters = filterQuery.split('and').map(f => f.trim());

            singleFilters.forEach(singleFilter => {
                res.result = applyFilter(res.result, singleFilter);
            });
        }

        next();
    },

    /** Sort the result set per the $orderby query option (not yet implemented) */
    sort: function(req, res, next) {
        const orderbyQuery = req.query.$orderby;

        if(orderbyQuery) {
            const [sortByProperty,sortOrder] = orderbyQuery.split(' ');
            res.result.sort(function(a, b) {
                const valueA = a[sortByProperty];
                const valueB = b[sortByProperty];
                if(undefined === valueA || undefined === valueB) {
                    console.warn('Invalid property for sorting: ', sortByProperty);
                    return 0;
                }
                const compareResult = valueA < valueB ? -1 : (valueA == valueB ? 0: 1);
                // if no sort order has been specified, sort ascending
                return (sortOrder == 'desc' ? -1 : 1) * compareResult;
            });
        }
        next();
    },

    /** Select only the properties of each result entity specified by the $select query option */
    select: function(req, res, next) {
        const selectQuery = req.query.$select;

        if(selectQuery) {
            const selectedProperties = selectQuery.split(',');
            res.result = Array.isArray(res.result) ?
                reduceEntitySetToSelect(res.result, selectedProperties) :
                reduceEntityToSelect(res.result, selectedProperties);
        }

        next();
    },

    /** Limits the result set per the $top and $skip query options (not yet implemented) */
    limit: function(req, res, next) {
        next();
    },

    /**
     * Executes batch requests.
     * Errors for changesets will not roll back any changes.
     */
    batch: function(req, res, next) {
        const allLines = req.body.trim().split('\n');
        const batchId = allLines[0];
        const batchParts = req.body.trim().split(`${batchId}\n`).filter(line => line);
        const baseUrl = `${req.protocol}://${req.get('host')}`;

        const requests = batchParts.map(batchPart => {
            if (batchPart.startsWith('Content-Type: multipart/mixed;')) {
                return executeChangeset(baseUrl, batchPart);
            }
            return executeBatchSubrequest(baseUrl, batchPart)
                .catch(error => {
                    return wrapSingleRequestResponse(error.response)
                });
        });

        const chainedRequests = requests.reduce(async (promiseChain, req) => {
            return promiseChain.then(responses => req.then(response => [...responses, response]));
        }, Promise.resolve([]))

        chainedRequests
            .then(responses => {
                res.send(wrapBatchResponse(responses));
            })
            .catch(e => {
                console.error(e);
                res.send(e);
            });
    },

    /** All generic middlewares for entity sets */
    middlewareForSet: function() {
        return [this.expand, this.filter, this.sort, this.select, this.limit, this.sendAsODataResult];
    },

    /** All generic middlewares for single entities */
    middlewareForEntity: function() {
        return [this.send404IfNotFound, this.expand, this.select, this.sendAsODataResult];
    },

    /** Create middleware chain for update based on retrieve and modify function */
    middlewareForUpdate: function(retrieveFunction, modifyFunction) {
        return [retrieveFunction, this.send404IfNotFound, bodyParser.json(), modifyFunction, this.send204NoContent];
    },

    middlewareForCreate: function(createFunction) {
        return [bodyParser.json(), createFunction, this.set201Created, this.sendAsODataResult]
    },

    middlewareForBatch: function() {
        return [bodyParser.text({ type: () => true }), this.set201Created, this.batch];
    }
};
