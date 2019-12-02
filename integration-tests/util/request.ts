import { BusinessPartner, BusinessPartnerAddress } from "@sap/cloud-sdk-vdm-business-partner-service";


export function createBusinessPartnerRequest(firstName: string) {
  const lastName = generateRandomName();
  return BusinessPartner.requestBuilder()
    .create(
      BusinessPartner.builder()
        .firstName(firstName)
        .lastName(lastName)
        .businessPartnerFullName(`${firstName} ${lastName}`)
        .businessPartnerCategory('1')
        .isFemale(true)
        .build()
    );
}

export function retrieveBusinessPartnerRequest(businessPartnerId: string) {
  return BusinessPartner.requestBuilder()
    .getByKey(businessPartnerId);
}

export function createAddressRequest(businessPartnerId: string, streetName: string) {
  return BusinessPartnerAddress.requestBuilder()
    .create(
      BusinessPartnerAddress.builder()
        .businessPartner(businessPartnerId)
        .streetName(streetName)
        .cityName(generateRandomName())
        .build()
    );
}

export function retrieveAddressRequest(businessPartnerId: string, addressId: string) {
  return BusinessPartnerAddress.requestBuilder()
    .getByKey(businessPartnerId, addressId);
}

export function generateRandomName() {
  return getRandomInt(10000, 1000000000).toString(36);
}

function getRandomInt(min, max) {
  min = Math.ceil(min);
  max = Math.floor(max);
  return Math.floor(Math.random() * (max - min)) + min;
}
