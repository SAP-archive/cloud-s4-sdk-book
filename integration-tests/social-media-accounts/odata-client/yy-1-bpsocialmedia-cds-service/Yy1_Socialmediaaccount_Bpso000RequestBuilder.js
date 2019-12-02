"use strict";
/*!
 * Copyright (c) 2019 SAP SE or an SAP affiliate company. All rights reserved.
 */
var __extends = (this && this.__extends) || (function () {
    var extendStatics = function (d, b) {
        extendStatics = Object.setPrototypeOf ||
            ({ __proto__: [] } instanceof Array && function (d, b) { d.__proto__ = b; }) ||
            function (d, b) { for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p]; };
        return extendStatics(d, b);
    };
    return function (d, b) {
        extendStatics(d, b);
        function __() { this.constructor = d; }
        d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
    };
})();
Object.defineProperty(exports, "__esModule", { value: true });
var cloud_sdk_core_1 = require("@sap/cloud-sdk-core");
var Yy1_Socialmediaaccount_Bpso000_1 = require("./Yy1_Socialmediaaccount_Bpso000");
/**
 * Request builder class for operations supported on the [[Yy1_Socialmediaaccount_Bpso000]] entity.
 */
var Yy1_Socialmediaaccount_Bpso000RequestBuilder = /** @class */ (function (_super) {
    __extends(Yy1_Socialmediaaccount_Bpso000RequestBuilder, _super);
    function Yy1_Socialmediaaccount_Bpso000RequestBuilder() {
        return _super !== null && _super.apply(this, arguments) || this;
    }
    /**
     * Returns a request builder for retrieving one `Yy1_Socialmediaaccount_Bpso000` entity based on its keys.
     * @param sapUuid Key property. See [[Yy1_Socialmediaaccount_Bpso000.sapUuid]].
     * @returns A request builder for creating requests to retrieve one `Yy1_Socialmediaaccount_Bpso000` entity based on its keys.
     */
    Yy1_Socialmediaaccount_Bpso000RequestBuilder.prototype.getByKey = function (sapUuid) {
        return new cloud_sdk_core_1.GetByKeyRequestBuilder(Yy1_Socialmediaaccount_Bpso000_1.Yy1_Socialmediaaccount_Bpso000, { SAP_UUID: sapUuid });
    };
    /**
     * Returns a request builder for querying all `Yy1_Socialmediaaccount_Bpso000` entities.
     * @returns A request builder for creating requests to retrieve all `Yy1_Socialmediaaccount_Bpso000` entities.
     */
    Yy1_Socialmediaaccount_Bpso000RequestBuilder.prototype.getAll = function () {
        return new cloud_sdk_core_1.GetAllRequestBuilder(Yy1_Socialmediaaccount_Bpso000_1.Yy1_Socialmediaaccount_Bpso000);
    };
    /**
     * Returns a request builder for creating a `Yy1_Socialmediaaccount_Bpso000` entity.
     * @param entity The entity to be created
     * @returns A request builder for creating requests that create an entity of type `Yy1_Socialmediaaccount_Bpso000`.
     */
    Yy1_Socialmediaaccount_Bpso000RequestBuilder.prototype.create = function (entity) {
        return new cloud_sdk_core_1.CreateRequestBuilder(Yy1_Socialmediaaccount_Bpso000_1.Yy1_Socialmediaaccount_Bpso000, entity);
    };
    /**
     * Returns a request builder for updating an entity of type `Yy1_Socialmediaaccount_Bpso000`.
     * @param entity The entity to be updated
     * @returns A request builder for creating requests that update an entity of type `Yy1_Socialmediaaccount_Bpso000`.
     */
    Yy1_Socialmediaaccount_Bpso000RequestBuilder.prototype.update = function (entity) {
        return new cloud_sdk_core_1.UpdateRequestBuilder(Yy1_Socialmediaaccount_Bpso000_1.Yy1_Socialmediaaccount_Bpso000, entity);
    };
    Yy1_Socialmediaaccount_Bpso000RequestBuilder.prototype.delete = function (sapUuidOrEntity) {
        return new cloud_sdk_core_1.DeleteRequestBuilder(Yy1_Socialmediaaccount_Bpso000_1.Yy1_Socialmediaaccount_Bpso000, sapUuidOrEntity instanceof Yy1_Socialmediaaccount_Bpso000_1.Yy1_Socialmediaaccount_Bpso000 ? sapUuidOrEntity : { SAP_UUID: sapUuidOrEntity });
    };
    return Yy1_Socialmediaaccount_Bpso000RequestBuilder;
}(cloud_sdk_core_1.RequestBuilder));
exports.Yy1_Socialmediaaccount_Bpso000RequestBuilder = Yy1_Socialmediaaccount_Bpso000RequestBuilder;
//# sourceMappingURL=Yy1_Socialmediaaccount_Bpso000RequestBuilder.js.map