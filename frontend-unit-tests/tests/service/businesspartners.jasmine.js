"use strict";
sap.ui.define(["sdk-tutorial-frontend/service/businessPartner"], function (
  BusinessPartnersService
) {
  //Create test data used for mocking and in the assertion
  var testBusinessPartners = [
    {
      BusinessPartner: "1",
      LastName: "Doe",
    },
  ];

  function getBusinessPartnersPromise() {
    var jQueryPromise = new $.Deferred();
    return jQueryPromise.resolve(testBusinessPartners);
  }

  describe("Business Partner Service", function () {
    it("gets a business partner", function (done) {
      spyOn(jQuery, "get").and.returnValue(getBusinessPartnersPromise());
      BusinessPartnersService.getBusinessPartner('1').then(function (
        businessPartners
      ) {
        expect(businessPartners).toEqual(testBusinessPartners);
        expect(jQuery.get).toHaveBeenCalled();
        done();
      });
    });
  });
});