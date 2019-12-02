import { ReadResponse, WriteResponses } from "@sap/cloud-sdk-core";
import { batch, BusinessPartner, BusinessPartnerAddress, changeset } from "@sap/cloud-sdk-vdm-business-partner-service";
import { createAddressRequest, createBusinessPartnerRequest, generateRandomName, retrieveAddressRequest, retrieveBusinessPartnerRequest } from "../util/request";
import { getUrl, startServer, stopServer } from "../util/server";

describe('batch business partner', () => {
  const BUPA_ID = "1003764";
  const ADDRESS_ID = "28238";

  let server;
  let destination;
  beforeEach(async () => {
    server = await startServer();
    destination = { url: getUrl(server) };
  });

  afterEach(async () => {
    return stopServer(server);
  });

  it('one retrieve', async () => {
    const req = retrieveBusinessPartnerRequest(BUPA_ID);
    const batchResponse = await batch(req).execute(destination);

    const batchRetrievedBusinessPartner = (batchResponse[0] as ReadResponse).as(BusinessPartner)[0];
    const retrievedBusinessPartner = await req.execute(destination);

    expect(batchResponse).toHaveLength(1);
    expect(batchRetrievedBusinessPartner).toEqual(retrievedBusinessPartner);
  });

  it('multi retrieve', async () => {
    const req1 = retrieveBusinessPartnerRequest(BUPA_ID);
    const req2 = retrieveAddressRequest(BUPA_ID, ADDRESS_ID);
    const req3 = BusinessPartner.requestBuilder().getAll();
    const batchResponse = await batch(req1, req2, req3).execute(destination);

    const batchRetrievedBusinessPartner = (batchResponse[0] as ReadResponse).as(BusinessPartner)[0];
    const batchRetrievedAddress = (batchResponse[1] as ReadResponse).as(BusinessPartnerAddress)[0];
    const batchRetrievedBusinessPartners = (batchResponse[2] as ReadResponse).as(BusinessPartner);
    const retrievedBusinessPartner = await req1.execute(destination);
    const retrievedAddress = await req2.execute(destination);
    const retrievedBusinessPartners = await req3.execute(destination);

    expect(batchResponse).toHaveLength(3);
    expect(batchRetrievedBusinessPartner).toEqual(retrievedBusinessPartner);
    expect(batchRetrievedAddress).toEqual(retrievedAddress);
    expect(batchRetrievedBusinessPartners).toEqual(retrievedBusinessPartners);
  });

  it('one changeset', async () => {
    const req = createBusinessPartnerRequest(generateRandomName());
    const batchResponse = await batch(
      changeset(req)
    ).execute(destination);

    const batchCreatedBusinessPartner = (batchResponse[0] as WriteResponses).responses[0].as(BusinessPartner);
    const retrievedBusinessPartner = await retrieveBusinessPartnerRequest(batchCreatedBusinessPartner.businessPartner).execute(destination);

    expect(batchResponse).toHaveLength(1);
    expect((batchResponse[0] as WriteResponses).responses).toHaveLength(1);
    expect(batchCreatedBusinessPartner).toEqual(retrievedBusinessPartner);
  });

  it('multi changeset', async () => {
    const addressToDelete = await createAddressRequest(BUPA_ID, generateRandomName()).execute(destination);
    const req1 = createBusinessPartnerRequest(generateRandomName());
    const req2 = BusinessPartnerAddress.requestBuilder().delete(addressToDelete.businessPartner, addressToDelete.addressId);
    const batchResponse = await batch(
      changeset(req1, req2)
    ).execute(destination);

    const batchCreatedBusinessPartner = (batchResponse[0] as WriteResponses).responses[0].as(BusinessPartner);
    const retrievedBusinessPartner = await retrieveBusinessPartnerRequest(batchCreatedBusinessPartner.businessPartner).execute(destination);
    const retrieveAddress = retrieveAddressRequest(BUPA_ID, ADDRESS_ID).execute(destination);

    expect(batchResponse).toHaveLength(1);
    expect((batchResponse[0] as WriteResponses).responses).toHaveLength(2);
    expect(batchCreatedBusinessPartner).toEqual(retrievedBusinessPartner);
    expect(retrieveAddress).toReject();
  });

  it('mixed batch', async () => {
    const addressToDelete = await createAddressRequest(BUPA_ID, generateRandomName()).execute(destination);
    const req1 = retrieveBusinessPartnerRequest(BUPA_ID);
    const req2 = createBusinessPartnerRequest(generateRandomName());
    const req3 = BusinessPartnerAddress.requestBuilder().delete(addressToDelete.businessPartner, addressToDelete.addressId);
    const req4 = retrieveAddressRequest(addressToDelete.businessPartner, addressToDelete.addressId);
    const req5 = createBusinessPartnerRequest(generateRandomName());

    const batchResponse = await batch(
      req1,
      changeset(req2, req3),
      req4,
      changeset(req3, req5)
    )
    .execute(destination);

    const batchRetrievedBusinessPartner = (batchResponse[0] as ReadResponse).as(BusinessPartner)[0];
    const batchCreatedBusinessPartner = (batchResponse[1] as WriteResponses).responses[0].as(BusinessPartner);
    const retrievedBusinessPartner = await req1.execute(destination);
    const retrievedCreatedBusinessPartner = await retrieveBusinessPartnerRequest(batchCreatedBusinessPartner.businessPartner).execute(destination)

    expect(batchResponse).toHaveLength(4);
    expect(batchResponse[0].isSuccess()).toBeTruthy();
    expect(batchResponse[1].isSuccess()).toBeTruthy();
    expect(batchResponse[2].isSuccess()).toBeFalsy();
    expect(batchResponse[3].isSuccess()).toBeFalsy();

    expect(batchRetrievedBusinessPartner).toEqual(retrievedBusinessPartner);
    expect(batchCreatedBusinessPartner).toEqual(retrievedCreatedBusinessPartner);
  });
});

