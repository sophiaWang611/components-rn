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
    this.platformDirectory = this.rootDirectory + '/android';
    this.appDirectory      = this.platformDirectory + '/app/build/outputs/apk/';
};

Compiler.prototype.build = function() {
    this.run("cd " + this.platformDirectory + " && ./gradlew assembleRelease");
    this.run("cd " + this.testDirectory);
};


module.exports = Compiler;
