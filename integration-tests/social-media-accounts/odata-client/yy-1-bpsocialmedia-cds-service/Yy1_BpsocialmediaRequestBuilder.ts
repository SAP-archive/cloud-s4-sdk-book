/*!
 * Copyright (c) 2019 SAP SE or an SAP affiliate company. All rights reserved.
 */

import { RequestBuilder, GetAllRequestBuilder, GetByKeyRequestBuilder, CreateRequestBuilder, UpdateRequestBuilder, DeleteRequestBuilder } from '@sap/cloud-sdk-core';
import { Yy1_Bpsocialmedia } from './Yy1_Bpsocialmedia';

/**
 * Request builder class for operations supported on the [[Yy1_Bpsocialmedia]] entity.
 */
export class Yy1_BpsocialmediaRequestBuilder extends RequestBuilder<Yy1_Bpsocialmedia> {
  /**
   * Returns a request builder for retrieving one `Yy1_Bpsocialmedia` entity based on its keys.
   * @param sapUuid Key property. See [[Yy1_Bpsocialmedia.sapUuid]].
   * @returns A request builder for creating requests to retrieve one `Yy1_Bpsocialmedia` entity based on its keys.
   */
  getByKey(sapUuid: string): GetByKeyRequestBuilder<Yy1_Bpsocialmedia> {
    return new GetByKeyRequestBuilder(Yy1_Bpsocialmedia, { SAP_UUID: sapUuid });
  }

  /**
   * Returns a request builder for querying all `Yy1_Bpsocialmedia` entities.
   * @returns A request builder for creating requests to retrieve all `Yy1_Bpsocialmedia` entities.
   */
  getAll(): GetAllRequestBuilder<Yy1_Bpsocialmedia> {
    return new GetAllRequestBuilder(Yy1_Bpsocialmedia);
  }

  /**
   * Returns a request builder for creating a `Yy1_Bpsocialmedia` entity.
   * @param entity The entity to be created
   * @returns A request builder for creating requests that create an entity of type `Yy1_Bpsocialmedia`.
   */
  create(entity: Yy1_Bpsocialmedia): CreateRequestBuilder<Yy1_Bpsocialmedia> {
    return new CreateRequestBuilder(Yy1_Bpsocialmedia, entity);
  }

  /**
   * Returns a request builder for updating an entity of type `Yy1_Bpsocialmedia`.
   * @param entity The entity to be updated
   * @returns A request builder for creating requests that update an entity of type `Yy1_Bpsocialmedia`.
   */
  update(entity: Yy1_Bpsocialmedia): UpdateRequestBuilder<Yy1_Bpsocialmedia> {
    return new UpdateRequestBuilder(Yy1_Bpsocialmedia, entity);
  }

  /**
   * Returns a request builder for deleting an entity of type `Yy1_Bpsocialmedia`.
   * @param sapUuid Key property. See [[Yy1_Bpsocialmedia.sapUuid]].
   * @returns A request builder for creating requests that delete an entity of type `Yy1_Bpsocialmedia`.
   */
  delete(sapUuid: string): DeleteRequestBuilder<Yy1_Bpsocialmedia>;
  /**
   * Returns a request builder for deleting an entity of type `Yy1_Bpsocialmedia`.
   * @param entity Pass the entity to be deleted.
   * @returns A request builder for creating requests that delete an entity of type `Yy1_Bpsocialmedia` by taking the entity as a parameter.
   */
  delete(entity: Yy1_Bpsocialmedia): DeleteRequestBuilder<Yy1_Bpsocialmedia>;
  delete(sapUuidOrEntity: any): DeleteRequestBuilder<Yy1_Bpsocialmedia> {
    return new DeleteRequestBuilder(Yy1_Bpsocialmedia, sapUuidOrEntity instanceof Yy1_Bpsocialmedia ? sapUuidOrEntity : { SAP_UUID: sapUuidOrEntity! });
  }
}
