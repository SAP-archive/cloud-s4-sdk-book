"use strict";

/* global history */
sap.ui.define([
    "sap/ui/demo/addressmgr/controller/BaseController",
    "sap/ui/model/json/JSONModel",
    "sap/ui/model/Filter",
    "sap/ui/model/FilterOperator",
    "sap/ui/model/Sorter",
    "sap/m/GroupHeaderListItem",
    "sap/ui/demo/addressmgr/model/formatter",
    "sap/ui/demo/addressmgr/model/MessageType"
], function (BaseController, JSONModel, Filter, FilterOperator, Sorter, GroupHeaderListItem, formatter, MessageType) {
    return BaseController.extend("sap.ui.demo.addressmgr.controller.Master", {
        viewModelName: "masterView",
        formatter: formatter,

        /* =========================================================== */
        /* lifecycle methods                                           */
        /* =========================================================== */

        /**
         * Called when the master list controller is instantiated. It sets up the event handling for the master/detail communication and other lifecycle tasks.
         * @public
         */
        onInit: function () {
            var that = this;
            this._oList = this.byId("list");
            this._setupViewModel();
            this._oListFilterState = {
                aFilter: [],
                aSearch: []
            };

            this.getRouter().getRoute("master").attachPatternMatched(this._onMasterMatched, this);
            this.getRouter().getRoute("businessPartner").attachPatternMatched(this._onBusinessPartnerMatched, this);
            this.getRouter().attachBypassed(this._onBypassed, this);
            this.getRouter().getTargets().getTarget("detailMessagePage").attachDisplay(function () {
                that.getViewModel().setProperty("/selectedBusinessPartner", null);
            });
        },

        /**
         * After list data is available, this handler method updates the
         * master list counter and hides the pull to refresh control, if
         * necessary.
         * @param {sap.ui.base.Event} oEvent the update finished event
         * @public
         */
        onUpdateFinished: function () {
            this.byId("pullToRefresh").hide();
        },

        /**
         * Event handler for the master search field. Applies current
         * filter value and triggers a new search. If the search field's
         * 'refresh' button has been pressed, no new search is triggered
         * and the list binding is refresh instead.
         * @param {sap.ui.base.Event} oEvent the search event
         * @public
         */
        onSearch: function (oEvent) {
            if (oEvent.getParameters().refreshButtonPressed) {
                this.onRefresh();
                return;
            }

            var sQuery = oEvent.getParameter("query");

            if (sQuery) {
                this._oListFilterState.aSearch = [
                    new Filter({
                        filters: [
                            new Filter("BusinessPartner", FilterOperator.Contains, sQuery),
                            new Filter("FirstName", FilterOperator.Contains, sQuery),
                            new Filter("LastName", FilterOperator.Contains, sQuery)
                        ],
                        and: false
                    })
                ];
            } else {
                this._oListFilterState.aSearch = [];
            }
            this._applyFilterSearch();
        },

        /**
         * Event handler for refresh event. Keeps filter, sort
         * and group settings and refreshes the list binding.
         * @public
         */
        onRefresh: function () {
            this._oList.getBinding("items").refresh();
        },

        /**
         * Event handler for the sorter selection.
         * @param {sap.ui.base.Event} oEvent the select event
         * @public
         */
        onSort: function (oEvent) {
            var sKey = oEvent.getSource().getSelectedItem().getKey();
            this._applyGroupSort([new Sorter(sKey, false)]);
        },

        /**
         * Event handler for the list selection event
         * @param {sap.ui.base.Event} oEvent the list selectionChange event
         * @public
         */
        onSelectionChange: function (oEvent) {
            var oList = oEvent.getSource();
            var bSelected = oEvent.getParameter("selected");

            // get the list item, either from the listItem parameter or from the event's source itself (will depend on the device-dependent mode).
            if (!(oList.getMode() === "MultiSelect" && !bSelected)) {
                this._showDetail(oEvent.getParameter("listItem") || oEvent.getSource());
            }
        },

        /**
         * Event handler for the bypassed event, which is fired when no routing pattern matched.
         * If there was an object selected in the master list, that selection is removed.
         * @private
         */
        _onBypassed: function () {
            this._oList.removeSelections(true);
        },

        /* =========================================================== */
        /* begin: internal methods                                     */
        /* =========================================================== */

        _setupViewModel: function () {
            this.setViewModel(new JSONModel({
                noDataText: this.getResourceBundle().getText("masterListNoDataText"),
                sortBy: "BusinessPartner",
                selectedBusinessPartner: null
            }));
        },

        /**
         * If the master route was hit (empty hash) we have to set
         * the hash to to the first item in the list as soon as the
         * listLoading is done and the first item in the list is known
         * @private
         */
        _onMasterMatched: function () {
            this.getRouter().getTargets().display("detailMessagePage", { messageType: MessageType.NOTHING_SELECTED });
        },

        _onBusinessPartnerMatched: function (oEvent) {
            var that = this;
            var sBusinessPartnerId = oEvent.getParameters().arguments.businessPartnerId;
            that.getModel("masterView").setProperty("/selectedBusinessPartner", sBusinessPartnerId);
        },

        /**
         * Shows the selected item on the detail page
         * On phones a additional history entry is created
         * @param {sap.m.ObjectListItem} oItem selected Item
         * @private
         */
        _showDetail: function (oItem) {
            this.getRouter().navTo("businessPartner", {
                businessPartnerId: oItem.getBindingContext().getProperty("BusinessPartner")
            });
        },

        /**
         * Internal helper method to apply both filter and search state together on the list binding
         * @private
         */
        _applyFilterSearch: function () {
            var aFilters = this._oListFilterState.aSearch;
            var oViewModel = this.getModel("masterView");
            this._oList.getBinding("items").filter(aFilters, "Application");
            // changes the noDataText of the list in case there are no filter results
            if (aFilters.length !== 0) {
                oViewModel.setProperty("/noDataText", this.getResourceBundle().getText("masterListNoDataWithFilterOrSearchText"));
            } else if (this._oListFilterState.aSearch.length > 0) {
                // only reset the no data text to default when no new search was triggered
                oViewModel.setProperty("/noDataText", this.getResourceBundle().getText("masterListNoDataText"));
            }
        },

        /**
         * Internal helper method to apply both group and sort state together on the list binding
         * @param {sap.ui.model.Sorter[]} aSorters an array of sorters
         * @private
         */
        _applyGroupSort: function (aSorters) {
            this._oList.getBinding("items").sort(aSorters);
        }
    });
});
