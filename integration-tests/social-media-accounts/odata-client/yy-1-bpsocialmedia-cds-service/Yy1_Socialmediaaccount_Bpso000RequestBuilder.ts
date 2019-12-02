/*!
 * Copyright (c) 2019 SAP SE or an SAP affiliate company. All rights reserved.
 */

import { RequestBuilder, GetAllRequestBuilder, GetByKeyRequestBuilder, CreateRequestBuilder, UpdateRequestBuilder, DeleteRequestBuilder } from '@sap/cloud-sdk-core';
import { Yy1_Socialmediaaccount_Bpso000 } from './Yy1_Socialmediaaccount_Bpso000';

/**
 * Request builder class for operations supported on the [[Yy1_Socialmediaaccount_Bpso000]] entity.
 */
export class Yy1_Socialmediaaccount_Bpso000RequestBuilder extends RequestBuilder<Yy1_Socialmediaaccount_Bpso000> {
  /**
   * Returns a request builder for retrieving one `Yy1_Socialmediaaccount_Bpso000` entity based on its keys.
   * @param sapUuid Key property. See [[Yy1_Socialmediaaccount_Bpso000.sapUuid]].
   * @returns A request builder for creating requests to retrieve one `Yy1_Socialmediaaccount_Bpso000` entity based on its keys.
   */
  getByKey(sapUuid: string): GetByKeyRequestBuilder<Yy1_Socialmediaaccount_Bpso000> {
    return new GetByKeyRequestBuilder(Yy1_Socialmediaaccount_Bpso000, { SAP_UUID: sapUuid });
  }

  /**
   * Returns a request builder for querying all `Yy1_Socialmediaaccount_Bpso000` entities.
   * @returns A request builder for creating requests to retrieve all `Yy1_Socialmediaaccount_Bpso000` entities.
   */
  getAll(): GetAllRequestBuilder<Yy1_Socialmediaaccount_Bpso000> {
    return new GetAllRequestBuilder(Yy1_Socialmediaaccount_Bpso000);
  }

  /**
   * Returns a request builder for creating a `Yy1_Socialmediaaccount_Bpso000` entity.
   * @param entity The entity to be created
   * @returns A request builder for creating requests that create an entity of type `Yy1_Socialmediaaccount_Bpso000`.
   */
  create(entity: Yy1_Socialmediaaccount_Bpso000): CreateRequestBuilder<Yy1_Socialmediaaccount_Bpso000> {
    return new CreateRequestBuilder(Yy1_Socialmediaaccount_Bpso000, entity);
  }

  /**
   * Returns a request builder for updating an entity of type `Yy1_Socialmediaaccount_Bpso000`.
   * @param entity The entity to be updated
   * @returns A request builder for creating requests that update an entity of type `Yy1_Socialmediaaccount_Bpso000`.
   */
  update(entity: Yy1_Socialmediaaccount_Bpso000): UpdateRequestBuilder<Yy1_Socialmediaaccount_Bpso000> {
    return new UpdateRequestBuilder(Yy1_Socialmediaaccount_Bpso000, entity);
  }

  /**
   * Returns a request builder for deleting an entity of type `Yy1_Socialmediaaccount_Bpso000`.
   * @param sapUuid Key property. See [[Yy1_Socialmediaaccount_Bpso000.sapUuid]].
   * @returns A request builder for creating requests that delete an entity of type `Yy1_Socialmediaaccount_Bpso000`.
   */
  delete(sapUuid: string): DeleteRequestBuilder<Yy1_Socialmediaaccount_Bpso000>;
  /**
   * Returns a request builder for deleting an entity of type `Yy1_Socialmediaaccount_Bpso000`.
   * @param entity Pass the entity to be deleted.
   * @returns A request builder for creating requests that delete an entity of type `Yy1_Socialmediaaccount_Bpso000` by taking the entity as a parameter.
   */
  delete(entity: Yy1_Socialmediaaccount_Bpso000): DeleteRequestBuilder<Yy1_Socialmediaaccount_Bpso000>;
  delete(sapUuidOrEntity: any): DeleteRequestBuilder<Yy1_Socialmediaaccount_Bpso000> {
    return new DeleteRequestBuilder(Yy1_Socialmediaaccount_Bpso000, sapUuidOrEntity instanceof Yy1_Socialmediaaccount_Bpso000 ? sapUuidOrEntity : { SAP_UUID: sapUuidOrEntity! });
  }
}
