//require('./packager');

// APPIUM -----------------
var child_process = require('child_process');
/*var appiumProc    = child_process.spawn('appium', [
  '--address', '0.0.0.0',
  '--command-timeout', '7200',
  '--platform-version', '9.3',
  '--platform-name', 'iOS',
  '--browser-name', 'Safari',
  '--device-name', 'iPhone 6',
  '--pre-launch',
  '--safari',
  '--port', '4723'
]);*/

var appiumProc    = child_process.spawn('appium', [
  '-p', '4723'
]);

var Promise = require('Promise');

var server = {
  host: '0.0.0.0',
  port: 4723 // one off from normal
};

var loadedAppium = null;
var driver = null;
var current = {};

var appiumPromise = new Promise(function (resolve, reject) {
  appiumProc.stdout.on('data', function (data) {
    if (loadedAppium) return;
    console.log('APPIUM: ' + data);

    if (data.indexOf('Appium REST http interface listener started') >= 0) {
      loadedAppium = true;
      resolve(data);
    }
  });
});

appiumProc.stderr.on('data', function (data) {
  console.log('APPIUM err: ' + data);
  appiumProc.kill();
});
process.on('exit', function () {
  appiumProc.kill();
});

// WD -----------------

var realWd = require("wd");
var wd     = require("yiewd");
var color  = require('colors');

// KOA -----------------

var localServer = require("./server");

// Config for Appium

var UNLIMITED = 100000;

var caps = {
  browserName: '',
  'appium-version': '1.4.13',
  platformName: 'iOS',
  platformVersion: '9.3',
  deviceName: 'iPhone 6',
  autoLaunch: 'true',
  autoAcceptAlerts: 'true',
  newCommandTimeout: UNLIMITED,
  app: "/Users/sophia/Documents/mseller-rn副本/testbuild/test_ios/MishiBuyer_ios.zip"
};

var capsAndroid = {
  'appium-version': '1.4.13',
  platformName: "Android",
  platformVersion: "6.0",
  deviceName: "Google Nexus 5",
  autoLaunch: 'true',
  app: "/Users/sophia/Documents/mseller-rn副本/android/app/build/outputs/apk/app-release.apk",
  "app-package": "com.mishi.android.seller"
};

module.exports = function(callback) {
  console.log("DRIVER: starting it up");

  appiumPromise.then(function () {
    console.log("DRIVER: will init");
    driver = wd.remote(server);

    console.log("DRIVER: on status");
    driver.on('status', function(info) {
      console.log(info.cyan);
    });

    console.log("DRIVER: on command");
    driver.on('command', function(meth, path, data) {
      console.log(' > ' + meth.yellow, path.grey, data || '');
    });

    current = {};

    console.log("DRIVER: driver.init");

    var platformCap = process.env.PLATFORM == "ios" ? caps : capsAndroid;

    driver.init(platformCap, function(){
      console.log('driver started');
      callback({
        driver: driver,
        realWd: realWd,
        localServer: localServer,
        wd: wd,
      });
    });
  });
};
