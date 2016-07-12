var child_process = require("child_process");
var path          = require("path")

var Compiler = function(environment, console) {
  this.environment = environment;
  this.console = console;

  this.calculateDirectories();
};

Compiler.prototype.log = function(toLog) {
  //this.console.log.writeln(toLog);
  this.console.log(toLog);
};

Compiler.prototype.run = function(toRun) {
  this.log('------------------')
  this.log(toRun);
  this.log('->')
  var out = child_process.execSync(toRun);
  this.log('' + out);
  this.log('------------------')
  return out;
};

Compiler.prototype.calculateDirectories = function() {
  this.testDirectory     = process.cwd();
  this.rootDirectory     = "../../";
  this.platformDirectory = this.rootDirectory + '/ios';
  this.appDirectory      = this.rootDirectory + '/testbuild/' + this.environment + '_ios';
  this.buildDirectory    = this.appDirectory  + '/build';

  switch(this.environment) {
    case 'test':
      this.configuration = "Debug";
      this.iosSdk = "iphonesimulator";
      this.iosDestination = "platform=iOS Simulator,name=iPhone 6s,OS=latest"
      break;
    case 'staging':
      this.configuration = "Staging";
      this.iosSdk = "iphoneos";
      break;
    default:
      throw("UNKNOWN ENVIRONMENT: " + this.environment);
  }

  this.compiledApp = this.buildDirectory;

  this.compiledApp += '/' + this.configuration + '-' + this.iosSdk + '/RNComponents.app';

  this.zippedApp = this.appDirectory + '/RNComponents_ios.zip';
};

Compiler.prototype.cleanDirectory = function() {
  this.log('cleaning: ' + this.appDirectory);
  this.run('rm -rf ' + this.appDirectory);
};

Compiler.prototype.buildJavascript = function() {
  var pwd = this.rootDirectory;
  var to_run = 'cd ' + pwd;
  to_run += " && react-native bundle --minify"
  this.run(to_run);
};

Compiler.prototype.buildIos = function() {
  var env, scheme, config, sdk;
  switch(this.environment) {
    case 'test':
      env = "TEST_ENVIRONMENT=1";
      scheme = "RNComponents Test";
      break;
    case 'staging':
      env = "STAGING_ENVIRONMENT=1";
      scheme = "RNComponents Staging";
      break;
    default:
      throw("UNKNOWN ENVIRONMENT: " + this.environment);
  }

  var to_run = 'xcodebuild';
  to_run += "  GCC_PREPROCESSOR_DEFINITIONS='$GCC_PREPROCESSOR_DEFINITIONS " + env + "'"
  to_run += " -workspace " + this.platformDirectory + "/RNComponents.xcworkspace";
  to_run += " -scheme \"" + scheme + "\"";
  to_run += " -sdk " + this.iosSdk;
  if (this.iosDestination) to_run += " -destination '" + this.iosDestination + "'";
  to_run += " -configuration " + this.configuration;
  to_run += " OBJROOT=" + this.buildDirectory;
  to_run += " SYMROOT=" + this.buildDirectory;
  to_run += " ONLY_ACTIVE_ARCH=NO";
  to_run += " | xcpretty -c && exit ${PIPESTATUS[0]}";
  this.run(to_run)
};

Compiler.prototype.build = function() {
  this.run('mkdir -p ' + this.buildDirectory);

  if (this.environment !== 'test') {
    this.buildJavascript();
  }

  var bundle = "cd " + this.rootDirectory;
  bundle += " && react-native bundle --dev true --assets-dest ./app/img --entry-file index.ios.js --platform ios --bundle-output ios/main.jsbundle";
  this.run(bundle);
  this.run("cd " + this.testDirectory);
  this.buildIos();
};

Compiler.prototype.zipIos = function() {
  var parent_dir = path.dirname(this.compiledApp);
  var pwd = this.rootDirectory;
  var to_run = "cd " + parent_dir;
  to_run += " && zip -r " + this.zippedApp + " " + this.compiledApp;
  to_run += " && cd " + pwd;
  this.run(to_run);
};

Compiler.prototype.zip = function() {
  this.zipIos();
};

Compiler.prototype.phoneInstall = function() {
  // ios-deploy    testbuild/staging_ios/build/Staging-iphoneos/觅食-家厨版.app
  var to_run = "ios-deploy";
  //to_run += " --justlaunch";
  //to_run += " --debug";
  to_run += " --bundle \"" + this.compiledApp + "\"";

  this.run(to_run);
};

// TODO: copySauce

module.exports = Compiler;
