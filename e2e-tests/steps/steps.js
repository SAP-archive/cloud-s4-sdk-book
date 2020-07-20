const { client } = require("nightwatch-api");
const { Given, Then } = require("cucumber");

Given(/^I open the Address Manager home page$/, async () => {
  const businesspartner = client.page.businesspartner()
  console.info (businesspartner.url())
  await businesspartner.navigate()
})

Then('a list of business partners is shown', function () {
  console.log("dbg>> e2e test")
})
