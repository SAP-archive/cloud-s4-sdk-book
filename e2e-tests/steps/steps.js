const { client } = require("nightwatch-api");
const { Given, Then } = require("cucumber");

Given(/^I open the Address Manager home page$/, async () => {
  const businesspartner = client.page.businesspartner();
  console.info(businesspartner.url());
  await businesspartner.navigate().waitForElementVisible("@body");
});

Then(/^a list of business partners is shown$/, async () => {
  const businesspartner = client.page.businesspartner();
  await businesspartner.waitForElementVisible("@businessPartnerList");
  await businesspartner.assert.visible("@businessPartnerList");
});

Then(/^the business partner name John Doe exists$/, async () => {
  const businesspartner = client.page.businesspartner();
  await businesspartner.navigate().waitForElementVisible("@businessPartnerDoe");
});
