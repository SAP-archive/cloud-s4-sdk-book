const express = require('express');
const bodyParser = require('body-parser');
const router = express.Router();

const odata = require('../odata-helpers.js');
const timeOffModel = require('./model.js');

const retrieveAllEmployeeTime = function(req, res, next) {
  console.log('Reading EmployeeTime entity set');
  res.result = timeOffModel.getEmployeeTime();
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
  console.log('Serving metadata for ECTimeOff service');
  res.sendFile('ECTimeOff.edmx', options, function(err) {
      if(err) {
        console.error('No metadata file found at services/time-off/ECTimeOff.edmx. Please check the documentation on how to retrieve and where to store this file.')
        res.sendStatus(404);
      }
  });
});

router.route('/EmployeeTime')
  .get(retrieveAllEmployeeTime, odata.middlewareForSet());

module.exports = router;
