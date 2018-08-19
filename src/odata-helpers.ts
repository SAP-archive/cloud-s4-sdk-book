import * as express from 'express';
import * as bodyParser from 'body-parser';

/** Returns true if the property name refers to a navigation property (i.e., begins with "to_") */
const isNavigationProperty = function(propertyName: string) {
    return propertyName.startsWith('to_');
};

/**
 * Removes the first segment of the navigation property path
 * @param {string} propertyName Navigation property path
 */
const removeFirstNavigationPath = function(propertyName: string) {
    return propertyName.substr(propertyName.split('/', 1)[0].length + 1);
};

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
const handleEntityNavPropertiesForExpand = function(entity: any, expandedProperties: string[] = []) {
    return Object.entries(entity).reduce(function(result: any, [key, value]: [string, any]) {
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
const handleEntitySetNavPropertiesForExpand = function(entityArray: any[], expandedProperties: string[]) {
    return entityArray.map( (item) => handleEntityNavPropertiesForExpand(item, expandedProperties) );
}

/**
 * Reduces the entity to only contain the selected properties (and meta properties).
 * @param {Object} entity Entity to reduce
 * @param {string[]} selectedProperties Properties to keep, or all, if empty
 */
const reduceEntityToSelect = function(entity: any, selectedProperties: string[] = []) {
    if(0 === selectedProperties.length) {
        return entity;
    }
    return Object.entries(entity).reduce(function(result: any, [key, value]: [string, any]) {
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
const reduceEntitySetToSelect = function(entityArray: any[], selectedProperties: string[]) {
    return entityArray.map( (item) => reduceEntityToSelect(item, selectedProperties) );
};

const insertHostIntoBody = function(body: string, req: express.Request) {
    const urlPrefix = `${req.protocol}://${req.get('host')}`;
    return body.replace(/https:\/\/{host}:{port}/g, urlPrefix);
};

export = {
    /** Send the result as an OData response */
    sendAsODataResult: function(req: express.Request, res: express.Response, next: express.NextFunction) {
        const result = (<any>res).result;
        const arrayWrapped = Array.isArray(result) ? { results: result } : result;
        const bodyAsString = JSON.stringify({ d: arrayWrapped });
        const bodyWithHost = insertHostIntoBody(bodyAsString, req);

        res.set('Content-Type', 'application/json');
        res.send(bodyWithHost);
    },

    /** Send 404 response if result is undefined */
    send404IfNotFound: function (req: express.Request, res: express.Response, next: express.NextFunction) {
        if((<any>res).result) {
            next();
        } else {
            console.log("No result, responding with 404")
            res.sendStatus(404);
        }
    },

    /** Send 204 response for no content */
    send204NoContent: function(req: express.Request, res: express.Response, next: express.NextFunction) {
        res.sendStatus(204);
    },

    /** Expand each result entity (and replace not queried content with __deferred) */
    expand: function(req: express.Request, res: express.Response, next: express.NextFunction) {
        const expandQuery = req.query.$expand;
        const propertiesToExpand = expandQuery? expandQuery.split(',') : [];

        const resultRef = (<any>res).result;
        (<any>res).result = Array.isArray(resultRef)?
            handleEntitySetNavPropertiesForExpand(resultRef, propertiesToExpand) :
            handleEntityNavPropertiesForExpand(resultRef, propertiesToExpand);

        next();
    },

    /** Filter the result set per the $filter query option */
    filter: function(req: express.Request, res: express.Response, next: express.NextFunction) {
        const filterQuery = req.query.$filter;

        if(filterQuery) {
            // RegExp that matches filters such as "FirstName eq 'John'" and groups property and value
            const filterRegex = /^(\w+) eq '(.*)'$/;
            const regexMatch = filterRegex.exec(filterQuery)
            if(regexMatch !== null) {
                const [, filterProperty, filterValue] = regexMatch;
    
                (<any>res).result = (<any>res).result.filter((item: any) => {
                    return item[filterProperty] == filterValue;
                });
            }
        }

        next();
    },

    /** Sort the result set per the $orderby query option (not yet implemented) */
    sort: function(req: express.Request, res: express.Response, next: express.NextFunction) {
        const orderbyQuery = req.query.$orderby;

        if(orderbyQuery) {
            const [sortByProperty,sortOrder] = orderbyQuery.split(' ');
            (<any>res).result.sort(function(a: any, b: any) {
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
    select: function(req: express.Request, res: express.Response, next: express.NextFunction) {
        const selectQuery = req.query.$select;

        if(selectQuery) {
            const selectedProperties = selectQuery.split(',');
            const resultRef = (<any>res).result;
            (<any>res).result = Array.isArray(resultRef) ?
                reduceEntitySetToSelect(resultRef, selectedProperties) :
                reduceEntityToSelect(resultRef, selectedProperties);
        }

        next();
    },

    /** Limits the result set per the $top and $skip query options (not yet implemented) */
    limit: function(req: express.Request, res: express.Response, next: express.NextFunction) {
        next();
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
    middlewareForUpdate: function(retrieveFunction: express.RequestHandler, modifyFunction: express.RequestHandler) {
        return [retrieveFunction, this.send404IfNotFound, bodyParser.json(), modifyFunction, this.send204NoContent];
    }
};