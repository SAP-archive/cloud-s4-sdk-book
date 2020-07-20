const chromedriver = require('chromedriver');
const geckodriver = require('geckodriver');
const argv = require("yargs").argv;

module.exports = {
  output_folder: 's4hana_pipeline/reports/e2e',
  page_objects_path: __dirname + '/page_objects',
  silent: !process.env.NIGHTWATCH_VERBOSE,
  test_settings: {
    default: {
      launch_url : argv.launchUrl,
      webdriver: {
        start_process: true,
        port: 4444
      },
      globals: {
        abortOnAssertionFailure : true,
        retryAssertionTimeout: 10000,
        waitForConditionTimeout: 10000,
        asyncHookTimeout : 10000
      },
      screenshots: {
        enabled: true,
        path: 's4hana_pipeline/reports/e2e/screenshots'
      }
    },
    chromeHeadless: {
      webdriver: {
        server_path: chromedriver.path,
        cli_args: ['--port=4444']
      },
      desiredCapabilities: {
        browserName: 'chrome',
        javascriptEnabled: true,
        acceptSslCerts: true,
        chromeOptions: {
          args: ['headless', 'window-size=1280,800', 'disable-gpu', 'no-sandbox']
        }
      }
    },
    chrome: {
      webdriver: {
        server_path: chromedriver.path,
        cli_args: ['--port=4444']
      },
      desiredCapabilities: {
        browserName: 'chrome',
        javascriptEnabled: true,
        acceptSslCerts: true,
        chromeOptions: {
          args: ['window-size=1280,900', 'disable-gpu', 'no-sandbox']
        }
      }
    },
    firefox: {
      webdriver: {
        server_path: geckodriver.path,
        cli_args: ['--port', '4444', '--log', 'debug']
      },
      desiredCapabilities: {
        browserName: 'firefox',
        javascriptEnabled: true,
        acceptSslCerts: true,
        marionette: true
      }
    }
  }
};
