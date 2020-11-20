"use strict";

const loginCommands = {
  loginWithForm: function () {
    const user = this.api.globals.user;
    const pass = this.api.globals.pass;
    delete this.api.globals.user;
    delete this.api.globals.pass;
    this.waitForElementVisible("@usernameField").setValue(
      "@usernameField",
      user
    );
    this.waitForElementVisible("@passwordField").setValue(
      "@passwordField",
      pass
    );
    this.waitForElementVisible("@submitButton").click("@submitButton");

    this.expect.element("@usernameField").to.not.be.present;
    return this;
  },
};

module.exports = {
  url: function () {
    return this.api.launchUrl;
  },
  elements: {
    usernameField: "input[name=username]",
    passwordField: "input[name=password]",
    submitButton: "input[type=submit]",
  },
  commands: [loginCommands],
};
