import { BusinessPartnerAddress } from "@sap/cloud-sdk-vdm-business-partner-service";
import { expectPropertiesDefinedForAll, expectPropertiesUndefinedForAll, expectProperyEqualsForAll } from "../util/expect";
import { getUrl, startServer, stopServer } from "../util/server";

describe('read business partner addresses', () => {
  const BUPA_ID = "1003764";
  const ADDRESS_ID = "28238";

  let server;
  let destination;
  beforeAll(async () => {
    server = await startServer();
    destination = { url: getUrl(server) };
  });

  afterAll(async () => {
    return stopServer(server);
  });

  it('get all', async () => {
    const businessPartnerAddresses = await BusinessPartnerAddress.requestBuilder()
      .getAll()
      .execute(destination);

    expect(businessPartnerAddresses).not.toBeEmpty();
    expectPropertiesDefinedForAll(businessPartnerAddresses, 'addressId', 'businessPartner');
  });

  it('get all select', async () => {
    const businessPartnerAddresses = await BusinessPartnerAddress.requestBuilder()
      .getAll()
      .select(BusinessPartnerAddress.STREET_NAME, BusinessPartnerAddress.CITY_NAME)
      .execute(destination);

    expect(businessPartnerAddresses).not.toBeEmpty();
    expectPropertiesDefinedForAll(businessPartnerAddresses, 'streetName', 'cityName');
    expectPropertiesUndefinedForAll(businessPartnerAddresses, 'addressId');
  });

  it('get all filter', async () => {
    const businessPartnerAddresses = await BusinessPartnerAddress.requestBuilder()
      .getAll()
      .filter(BusinessPartnerAddress.BUSINESS_PARTNER.equals(BUPA_ID))
      .execute(destination);

    expect(businessPartnerAddresses).not.toBeEmpty();
    expectProperyEqualsForAll(businessPartnerAddresses, 'businessPartner', BUPA_ID);
  });

  it('get by key', async () => {
    const businessPartnerAddress = await BusinessPartnerAddress.requestBuilder()
      .getByKey(BUPA_ID, ADDRESS_ID)
      .execute(destination);

    expect(businessPartnerAddress).not.toBeNil();
    expect(businessPartnerAddress.businessPartner).toEqual(BUPA_ID);
    expect(businessPartnerAddress.addressId).toEqual(ADDRESS_ID);
    expect(businessPartnerAddress.streetName).toBeDefined();
  });

  it('get by key select', async () => {
    const businessPartnerAddress = await BusinessPartnerAddress.requestBuilder()
      .getByKey(BUPA_ID, ADDRESS_ID)
      .select(BusinessPartnerAddress.STREET_NAME, BusinessPartnerAddress.CITY_NAME)
      .execute(destination);

    expect(businessPartnerAddress).not.toBeNil();
    expect(businessPartnerAddress.businessPartner).toBeUndefined();
    expect(businessPartnerAddress.addressId).toBeUndefined();
    expect(businessPartnerAddress.streetName).toBeDefined();
    expect(businessPartnerAddress.cityName).toBeDefined();
  });
});
