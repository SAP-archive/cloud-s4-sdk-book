import { BusinessPartnerAddress } from "@sap/cloud-sdk-vdm-business-partner-service";
import { createAddressRequest, generateRandomName, retrieveAddressRequest } from "../util/request";
import { getUrl, startServer, stopServer } from "../util/server";

describe('modify business partner address', () => {
  const BUPA_ID = "1003764";

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
    const streetName = generateRandomName();
    const address = await createAddressRequest(BUPA_ID, streetName).execute(destination);
    const retrievedAddress = await retrieveAddressRequest(BUPA_ID, address.addressId).execute(destination);

    expect(retrievedAddress).not.toBeNil();
    expect(retrievedAddress.streetName).toEqual(streetName);
  });

  it('create for non exisitng business partner', async () => {
    const create = createAddressRequest('DOES_NOT_EXIST', generateRandomName()).execute(destination);
    expect(create).toReject();
  });

  it('update', async () => {
    let originalAddress = await createAddressRequest(BUPA_ID, generateRandomName()).execute(destination);
    let updateDiff = BusinessPartnerAddress.builder()
      .businessPartner(originalAddress.businessPartner)
      .addressId(originalAddress.addressId)
      .streetName(generateRandomName())
      .build();
    await BusinessPartnerAddress.requestBuilder()
      .update(updateDiff)
      .execute(destination);
    const retrievedAddress = await retrieveAddressRequest(originalAddress.businessPartner, originalAddress.addressId).execute(destination);

    expect(retrievedAddress).not.toBeNil();
    expect(retrievedAddress.streetName).toEqual(updateDiff.streetName);
  });

  it('update with workaround', async () => {
    let originalAddress = await createAddressRequest(BUPA_ID, generateRandomName()).execute(destination);
    originalAddress.streetName = generateRandomName();
    await BusinessPartnerAddress.requestBuilder()
      .update(originalAddress)
      .execute(destination);
    const retrievedAddress = await retrieveAddressRequest(originalAddress.businessPartner, originalAddress.addressId).execute(destination);

    expect(retrievedAddress).not.toBeNil();
    expect(retrievedAddress.streetName).toEqual(originalAddress.streetName);
  });

  it('update non exisitng', async () => {
    const update = BusinessPartnerAddress.requestBuilder()
      .update(
        BusinessPartnerAddress.builder()
          .businessPartner(BUPA_ID)
          .addressId('DOES_NOT_EXIST')
          .streetName(generateRandomName())
          .build()
      )
      .execute(destination);

    expect(update).toReject();
  });

  it('delete', async () => {
    const streetName = generateRandomName();
    const businessPartnerAddress = await createAddressRequest(BUPA_ID, streetName).execute(destination);
    const addressToDelete = BusinessPartnerAddress.builder()
      .businessPartner(businessPartnerAddress.businessPartner)
      .addressId(businessPartnerAddress.addressId)
      .build();

    await BusinessPartnerAddress.requestBuilder()
      .delete(addressToDelete)
      .execute(destination);

    const retrieve = retrieveAddressRequest(BUPA_ID, businessPartnerAddress.addressId).execute(destination);

    expect(retrieve).toReject();
  });
});
