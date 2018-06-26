const express = require('express');
const bodyParser = require('body-parser');
const router = express.Router();

const odata = require('../odata-helpers.js');
const socMediaModel = require('./social-media-accounts-model.js');

const retrieveAllBusinessPartnerSocialMedia = function(req, res, next) {
    console.log('Reading business partner social media entity set');
    res.result = socMediaModel.getBusinessPartnerSocialMedia();
    next();
};

const retrieveSingleBusinessPartnerSocialMedia = function(req, res, next) {
    console.log(`Reading business partner social media ${req.params.uuid}`);
    res.result = socMediaModel.findBusinessPartnerSocialMedia(req.params.uuid);
    next();
};

const retrieveAllSocialMediaAccounts = function(req, res, next) {
    console.log('Reading social media accounts entity set');
    res.result = socMediaModel.getSocialMediaAccounts();
    next();
};

const retrieveSingleSocialMediaAccount = function(req, res, next) {
    console.log(`Reading social media account ${req.params.uuid}`);
    res.result = socMediaModel.findSocialMediaAccount(req.params.uuid);
    next();
};

// Serve EDMX file for /$metadata
router.get('/([$])metadata', function(req, res) {
    const options = {
        root: __dirname + '/',
        headers: {
            'Content-Type': 'application/xml'
        }
    };
    console.log('Serving metadata for Business Partner Social Media custom API');
    const EDMX_FILE_NAME = 'YY1_BPSOCIALMEDIA_CDS.edmx';
    res.sendFile(EDMX_FILE_NAME, options, function(err) {
        if(err) {
            console.error(`No metadata file found at social-media-accounts/${EDMX_FILE_NAME}.`)
            res.sendStatus(404);
        }
    });
});

router.route('/YY1_BPSOCIALMEDIA')
.get(retrieveAllBusinessPartnerSocialMedia, odata.middlewareForSet());

router.route('/YY1_BPSOCIALMEDIA\\((SAP_UUID=)?(guid\':uuid\'|%27:uuid%27)\\)')
.get(retrieveSingleBusinessPartnerSocialMedia, odata.middlewareForEntity());

router.route('/YY1_SOCIALMEDIAACCOUNT_BPSO000')
.get(retrieveAllSocialMediaAccounts, odata.middlewareForSet());

router.route('/YY1_SOCIALMEDIAACCOUNT_BPSO000\\((SAP_UUID=)?(guid\':uuid\'|%27:uuid%27)\\)')
.get(retrieveSingleSocialMediaAccount, odata.middlewareForEntity());

router.get('/', function(req, res) {
    res.json({
        "d": {
            "EntitySets": [
                "YY1_BPSOCIALMEDIA",
                "YY1_SOCIALMEDIAACCOUNT_BPSO000"
            ]
        }
    });
});

module.exports = router;