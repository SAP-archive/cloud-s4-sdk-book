"use strict";

sap.ui.define([
    "sap/ui/demo/addressmgr/controller/BaseController",
    "sap/ui/model/json/JSONModel",
    "sap/ui/demo/addressmgr/model/MessageType",
    "sap/ui/demo/addressmgr/service/businessPartner"
], function (BaseController, JSONModel, MessageType, businessPartnerService) {
    return BaseController.extend("sap.ui.demo.addressmgr.controller.App", {
        viewModelName: "appView",

        onInit: function () {
            this.setViewModel(new JSONModel({
                busy: true
            }));

            this._loadMainModel();

            // apply content density mode to root view
            this.getView().addStyleClass(this.getOwnerComponent().getContentDensityClass());
        },

        _loadMainModel: function () {
            var that = this;
            var oViewModel = this.getViewModel();
            var oModel = new JSONModel();
            this.setModel(oModel);

            oModel.attachRequestCompleted(function fnSetAppNotBusy() {
                oViewModel.setProperty("/busy", false);
            });

            oModel.attachRequestFailed(function () {
                that.getRouter().getTargets().display("detailMessagePage", { messageType: MessageType.NOTHING_AVAILABLE });
            });

            oModel.loadData(businessPartnerService.getBusinessPartnerUrl());
        },

        onLogout: function () {
            window.location = "/logout";
        }
    });
});
