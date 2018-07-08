"use strict";

sap.ui.define([
], function () {
    var api = {
        translate: function (sText) {
            return jQuery.get("/api/translate?input=" + sText);
        }
    };
    return api;
}, true);
