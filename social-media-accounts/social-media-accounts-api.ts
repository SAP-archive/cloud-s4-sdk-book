import * as express from 'express';
const odata = require('../odata-helpers.js');
import {socialMediaAccountStore} from './social-media-accounts-model';

export const router = express.Router();

const socMediaModel = socialMediaAccountStore;

const retrieveAllBusinessPartnerSocialMedia = function(req: express.Request, res: express.Response, next: express.NextFunction) {
    console.log('Reading business partner social media entity set');
    (<any>res).result = socMediaModel.getBusinessPartnerSocialMedia();
    next();
};

const retrieveSingleBusinessPartnerSocialMedia = function(req: express.Request, res: express.Response, next: express.NextFunction) {
    console.log(`Reading business partner social media ${req.params.uuid}`);
    (<any>res).result = socMediaModel.findBusinessPartnerSocialMedia(req.params.uuid);
    next();
};

const retrieveAllSocialMediaAccounts = function(req: express.Request, res: express.Response, next: express.NextFunction) {
    console.log('Reading social media accounts entity set');
    (<any>res).result = socMediaModel.getSocialMediaAccounts();
    next();
};

const retrieveSingleSocialMediaAccount = function(req: express.Request, res: express.Response, next: express.NextFunction) {
    console.log(`Reading social media account ${req.params.uuid}`);
    (<any>res).result = socMediaModel.findSocialMediaAccount(req.params.uuid);
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
