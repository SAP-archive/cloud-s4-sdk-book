import { BusinessPartner } from "@sap/cloud-sdk-vdm-business-partner-service";
import { createBusinessPartnerRequest, generateRandomName, retrieveBusinessPartnerRequest } from "../util/request";
import { getUrl, startServer, stopServer } from "../util/server";

describe('modify business partner', () => {
  let server;
  let destination;
  beforeEach(async () => {
    server = await startServer();
    destination = { url: getUrl(server) };
  });

  afterEach(async () => {
    return stopServer(server);
  });

  it('create', async () => {
    const firstName = generateRandomName();
    const businessPartner = await createBusinessPartnerRequest(firstName).execute(destination);
    const retrievedBusinessPartner = await retrieveBusinessPartnerRequest(businessPartner.businessPartner).execute(destination);

    expect(retrievedBusinessPartner).not.toBeNil();
    expect(retrievedBusinessPartner.firstName).toEqual(firstName);
  });

  it('update', async () => {
    let originalBusinessPartner = await createBusinessPartnerRequest(generateRandomName()).execute(destination);
    let updateDiff = BusinessPartner.builder()
      .businessPartner(originalBusinessPartner.businessPartner)
      .firstName(generateRandomName())
      .build();
    await BusinessPartner.requestBuilder()
      .update(updateDiff)
      .execute(destination);
    const retrievedBusinessPartner = await retrieveBusinessPartnerRequest(originalBusinessPartner.businessPartner).execute(destination);

    expect(retrievedBusinessPartner).not.toBeNil();
    expect(retrievedBusinessPartner.firstName).toEqual(updateDiff.firstName);
  });

  it('update with workaround', async () => {
    let originalBusinessPartner = await createBusinessPartnerRequest(generateRandomName()).execute(destination);
    originalBusinessPartner.firstName = generateRandomName();
    await BusinessPartner.requestBuilder()
      .update(originalBusinessPartner)
      .execute(destination);
    const retrievedBusinessPartner = await retrieveBusinessPartnerRequest(originalBusinessPartner.businessPartner).execute(destination);

    expect(retrievedBusinessPartner).not.toBeNil();
    expect(retrievedBusinessPartner.firstName).toEqual(originalBusinessPartner.firstName);
  });

  it('update non exisitng', async () => {
    const update = BusinessPartner.requestBuilder()
      .update(
        BusinessPartner.builder()
          .businessPartner('DOES_NOT_EXIST')
          .firstName(generateRandomName())
          .build()
      )
      .execute(destination);

    expect(update).toReject();
  });
});


