module.exports = {
  url: function () {
    return this.api.launchUrl;
  },
  elements: {
    body: "body",
    businessPartnerDoe: {
      selector:
        "//li[contains(@class, 'sapMObjLItem')]//*[contains(text(), 'John Doe')]",
      locateStrategy: "xpath",
    },
    businessPartnerList: {
      selector:
        "(//div[contains(@class, 'sapMList') and contains(@class, 'sapMListBGSolid')])",
      locateStrategy: "xpath",
    },
  },
};
