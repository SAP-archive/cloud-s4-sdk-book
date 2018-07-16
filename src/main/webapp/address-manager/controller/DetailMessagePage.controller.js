"use strict";

/* global history */
sap.ui.define([
    "sap/ui/demo/addressmgr/controller/BaseController",
    "sap/ui/model/json/JSONModel",
    "sap/ui/demo/addressmgr/model/MessageType"
], function (BaseController, JSONModel, MessageType) {
    return BaseController.extend("sap.ui.demo.addressmgr.controller.DetailMessagePage", {
        viewModelName: "detailMessageView",

        onInit: function () {
            var that = this;
            this.setViewModel(new JSONModel());

            this.getRouter().getTargets().getTarget("detailMessagePage").attachDisplay(function (oEvent) {
                that.getViewModel().setProperty("/messageType", oEvent.getParameters().data.messageType);
            });
        },

        messageText: function (messageType) {
            var i18n = this.getResourceBundle();
            switch (messageType) {
                case MessageType.NOTHING_SELECTED:
                    return i18n.getText("nothingSelectedText");
                case MessageType.NOTHING_AVAILABLE:
                    return i18n.getText("nothingAvailableText");
                case MessageType.NOTHING_FOUND:
                    return i18n.getText("nothingFoundText");
                default:
                    return "";
            }
        }
    });
});
