import { asc, desc } from '@sap/cloud-sdk-core';
import { BusinessPartner, BusinessPartnerAddress } from '@sap/cloud-sdk-vdm-business-partner-service';
import { expectPropertiesDefinedForAll, expectPropertiesUndefinedForAll, expectProperyEqualsForAll, getSizeOfUnfilteredResult } from '../util/expect';
import { getUrl, startServer, stopServer } from '../util/server';

describe('read business partners', () => {
  const BUPA_ID = "1003764";
  const FIRST_NAME = "John";

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
    const businessPartners = await BusinessPartner.requestBuilder()
      .getAll()
      .execute(destination);

    expect(businessPartners).not.toBeEmpty();
    expectPropertiesDefinedForAll(businessPartners, 'businessPartnerFullName', 'createdByUser');
  });

  it('get all select', async () => {
    const businessPartners = await BusinessPartner.requestBuilder()
      .getAll()
      .select(BusinessPartner.BUSINESS_PARTNER_FULL_NAME, BusinessPartner.CREATED_BY_USER)
      .execute(destination);

    expect(businessPartners).not.toBeEmpty();
    expectPropertiesDefinedForAll(businessPartners, 'businessPartnerFullName', 'createdByUser');
    expectPropertiesUndefinedForAll(businessPartners, 'businessPartner');
  });

  it('get all select all fields', async () => {
    const businessPartners = await BusinessPartner.requestBuilder()
      .getAll()
      .select(BusinessPartner.ALL_FIELDS)
      .execute(destination);

    expect(businessPartners).not.toBeEmpty();
    expectPropertiesDefinedForAll(businessPartners, 'businessPartner', 'businessPartnerCategory');
  });

  it('get all filter', async () => {
    const businessPartners = await BusinessPartner.requestBuilder()
      .getAll()
      .filter(BusinessPartner.FIRST_NAME.equals(FIRST_NAME))
      .execute(destination);

    expect(businessPartners.length).toBeLessThanOrEqual(await getSizeOfUnfilteredResult(BusinessPartner, destination));
    expectPropertiesDefinedForAll(businessPartners, 'businessPartner', 'businessPartnerCategory');
    expectProperyEqualsForAll(businessPartners, 'firstName', FIRST_NAME);
  });

  it('get all select filter', async () => {
    const businessPartners = await BusinessPartner.requestBuilder()
      .getAll()
      .select(BusinessPartner.BUSINESS_PARTNER_FULL_NAME, BusinessPartner.FIRST_NAME, BusinessPartner.CREATED_BY_USER)
      .filter(BusinessPartner.FIRST_NAME.equals(FIRST_NAME))
      .execute(destination);

    expect(businessPartners.length).toBeLessThanOrEqual(await getSizeOfUnfilteredResult(BusinessPartner, destination));
    expectPropertiesDefinedForAll(businessPartners, 'businessPartnerFullName', 'firstName', 'createdByUser');
    expectPropertiesUndefinedForAll(businessPartners, 'businessPartner', 'businessPartnerCategory');
    expectProperyEqualsForAll(businessPartners, 'firstName', FIRST_NAME);
  });

  it('get all select filter not part of select', async () => {
    const businessPartners = await BusinessPartner.requestBuilder()
      .getAll()
      .select(BusinessPartner.BUSINESS_PARTNER_CATEGORY, BusinessPartner.CREATED_BY_USER)
      .filter(BusinessPartner.FIRST_NAME.equals(FIRST_NAME))
      .execute(destination);

    expect(businessPartners.length).toBeLessThanOrEqual(await getSizeOfUnfilteredResult(BusinessPartner, destination));
    expectPropertiesDefinedForAll(businessPartners, 'businessPartnerCategory', 'createdByUser');
    expectPropertiesUndefinedForAll(businessPartners, 'firstName');
  });

  it('get all expand', async () => {
    const businessPartners = await BusinessPartner.requestBuilder()
      .getAll()
      .select(BusinessPartner.BUSINESS_PARTNER, BusinessPartner.TO_BUSINESS_PARTNER_ADDRESS)
      .execute(destination);

    const businessPartnersWithAddresses = businessPartners.filter(b => b.toBusinessPartnerAddress);

    expect(businessPartners).not.toBeEmpty();
    expect(businessPartnersWithAddresses).not.toBeEmpty();

    expectPropertiesDefinedForAll(businessPartners, 'businessPartner', 'toBusinessPartnerAddress');

    businessPartnersWithAddresses.forEach(b => {
      expectPropertiesDefinedForAll(b.toBusinessPartnerAddress, 'addressId', 'country');
    });
  });

  it('get all multiple expands and specific fields', async () => {
    const businessPartners = await BusinessPartner.requestBuilder()
      .getAll()
      .select(
        BusinessPartner.BUSINESS_PARTNER,
        BusinessPartner.TO_BUSINESS_PARTNER_ADDRESS.select(
          BusinessPartnerAddress.ADDRESS_ID,
          BusinessPartnerAddress.TO_EMAIL_ADDRESS
        ),
        BusinessPartner.TO_BUSINESS_PARTNER_ROLE
      )
      .execute(destination);

    const businessPartnersWithAddresses = businessPartners.filter(b => b.toBusinessPartnerAddress.length);

    expect(businessPartners).not.toBeEmpty();
    expect(businessPartnersWithAddresses).not.toBeEmpty();
    expectPropertiesDefinedForAll(businessPartners, 'businessPartner', 'toBusinessPartnerAddress', 'toBusinessPartnerRole');

    businessPartnersWithAddresses.forEach(b => {
      expectPropertiesDefinedForAll(b.toBusinessPartnerAddress, 'addressId', 'toEmailAddress');

      const addressesWithEmail = b.toBusinessPartnerAddress.filter(a => a.toEmailAddress.length);
      addressesWithEmail.forEach(a => {
        expectPropertiesDefinedForAll(a.toEmailAddress, 'emailAddress');
      })
    });
  });

  it('get all order by asc', async () => {
    const businessPartners = await BusinessPartner.requestBuilder()
      .getAll()
      .select(
        BusinessPartner.FIRST_NAME
      )
      .orderBy(asc(BusinessPartner.FIRST_NAME))
      .execute(destination);

    const names = businessPartners.map(b => b.firstName);
    const sortedNames = names.sort();

    expect(businessPartners).not.toBeEmpty();
    expect(names).toEqual(sortedNames);
  });

  it('get all order by desc', async () => {
    const businessPartners = await BusinessPartner.requestBuilder()
      .getAll()
      .select(
        BusinessPartner.FIRST_NAME
      )
      .orderBy(desc(BusinessPartner.FIRST_NAME))
      .execute(destination);

    const names = businessPartners.map(b => b.firstName);
    const sortedNames = names.sort().reverse();

    expect(businessPartners).not.toBeEmpty();
    expect(names).toEqual(sortedNames);
  });

  it('get by key', async () => {
    const businessPartner = await BusinessPartner.requestBuilder()
      .getByKey(BUPA_ID)
      .execute(destination);

    expect(businessPartner).not.toBeNil();
    expect(businessPartner.businessPartner).toEqual(BUPA_ID);
    expect(businessPartner.businessPartnerCategory).not.toBeNil();
    expect(businessPartner.businessPartnerCategory).not.toBeEmpty();
  });

  it('get by key select', async () => {
    const businessPartner = await BusinessPartner.requestBuilder()
      .getByKey(BUPA_ID)
      .select(BusinessPartner.BUSINESS_PARTNER_FULL_NAME, BusinessPartner.CREATED_BY_USER)
      .execute(destination);

    expect(businessPartner).not.toBeNil();
    expect(businessPartner.businessPartner).toBeUndefined();
    expect(businessPartner.businessPartnerFullName).toBeDefined();
    expect(businessPartner.createdByUser).toBeDefined();
  });

  it('get by key expand', async () => {
    const businessPartner = await BusinessPartner.requestBuilder()
      .getByKey(BUPA_ID)
      .select(BusinessPartner.BUSINESS_PARTNER, BusinessPartner.TO_BUSINESS_PARTNER_ADDRESS)
      .execute(destination);

    expect(businessPartner).not.toBeNil();
    expect(businessPartner.businessPartner).toEqual(BUPA_ID);
    expect(businessPartner.toBusinessPartnerAddress).toBeDefined();
  });
});
