const fs = require('fs');
const path = require('path');
const { setDefaultTimeout,  AfterAll, BeforeAll } = require('cucumber');
const { createSession, closeSession, startWebDriver, stopWebDriver, client } = require('nightwatch-api');
const reporter = require('cucumber-html-reporter');
const argv = require("yargs").argv;

setDefaultTimeout(60000);

const reportsDirectory = path.resolve(__dirname, '../s4hana_pipeline/reports/e2e')
if (!fs.existsSync(reportsDirectory)){
  fs.mkdirSync(reportsDirectory, { recursive: true });
}

BeforeAll(async () => {
  const options = {
    configFile: __dirname + "/nightwatch.conf.js",
    env: argv.NIGHTWATCH_ENV || 'firefox'
  }
  await startWebDriver(options);
  await createSession(options);
});

AfterAll(async () => {
  await closeSession();
  await stopWebDriver();

  setTimeout(() => {
    reporter.generate({
      theme: 'bootstrap',
      jsonFile: reportsDirectory + "/cucumber_report.json",
      output: reportsDirectory + "/cucumber_report.html",
      reportSuiteAsScenarios: true,
      launchReport: false
    });
  }, 0);
});
