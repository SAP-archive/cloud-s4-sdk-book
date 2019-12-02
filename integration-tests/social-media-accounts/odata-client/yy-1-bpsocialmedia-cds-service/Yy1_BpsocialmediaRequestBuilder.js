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
var Yy1_Bpsocialmedia_1 = require("./Yy1_Bpsocialmedia");
/**
 * Request builder class for operations supported on the [[Yy1_Bpsocialmedia]] entity.
 */
var Yy1_BpsocialmediaRequestBuilder = /** @class */ (function (_super) {
    __extends(Yy1_BpsocialmediaRequestBuilder, _super);
    function Yy1_BpsocialmediaRequestBuilder() {
        return _super !== null && _super.apply(this, arguments) || this;
    }
    /**
     * Returns a request builder for retrieving one `Yy1_Bpsocialmedia` entity based on its keys.
     * @param sapUuid Key property. See [[Yy1_Bpsocialmedia.sapUuid]].
     * @returns A request builder for creating requests to retrieve one `Yy1_Bpsocialmedia` entity based on its keys.
     */
    Yy1_BpsocialmediaRequestBuilder.prototype.getByKey = function (sapUuid) {
        return new cloud_sdk_core_1.GetByKeyRequestBuilder(Yy1_Bpsocialmedia_1.Yy1_Bpsocialmedia, { SAP_UUID: sapUuid });
    };
    /**
     * Returns a request builder for querying all `Yy1_Bpsocialmedia` entities.
     * @returns A request builder for creating requests to retrieve all `Yy1_Bpsocialmedia` entities.
     */
    Yy1_BpsocialmediaRequestBuilder.prototype.getAll = function () {
        return new cloud_sdk_core_1.GetAllRequestBuilder(Yy1_Bpsocialmedia_1.Yy1_Bpsocialmedia);
    };
    /**
     * Returns a request builder for creating a `Yy1_Bpsocialmedia` entity.
     * @param entity The entity to be created
     * @returns A request builder for creating requests that create an entity of type `Yy1_Bpsocialmedia`.
     */
    Yy1_BpsocialmediaRequestBuilder.prototype.create = function (entity) {
        return new cloud_sdk_core_1.CreateRequestBuilder(Yy1_Bpsocialmedia_1.Yy1_Bpsocialmedia, entity);
    };
    /**
     * Returns a request builder for updating an entity of type `Yy1_Bpsocialmedia`.
     * @param entity The entity to be updated
     * @returns A request builder for creating requests that update an entity of type `Yy1_Bpsocialmedia`.
     */
    Yy1_BpsocialmediaRequestBuilder.prototype.update = function (entity) {
        return new cloud_sdk_core_1.UpdateRequestBuilder(Yy1_Bpsocialmedia_1.Yy1_Bpsocialmedia, entity);
    };
    Yy1_BpsocialmediaRequestBuilder.prototype.delete = function (sapUuidOrEntity) {
        return new cloud_sdk_core_1.DeleteRequestBuilder(Yy1_Bpsocialmedia_1.Yy1_Bpsocialmedia, sapUuidOrEntity instanceof Yy1_Bpsocialmedia_1.Yy1_Bpsocialmedia ? sapUuidOrEntity : { SAP_UUID: sapUuidOrEntity });
    };
    return Yy1_BpsocialmediaRequestBuilder;
}(cloud_sdk_core_1.RequestBuilder));
exports.Yy1_BpsocialmediaRequestBuilder = Yy1_BpsocialmediaRequestBuilder;
//# sourceMappingURL=Yy1_BpsocialmediaRequestBuilder.js.map