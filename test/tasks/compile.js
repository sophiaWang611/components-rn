
var child_process = require("child_process");
var Compiler, compiler;

if (process.env.PLATFORM == "ios") {
  Compiler = require("./compilerIOS");
  compiler = new Compiler(process.env.TARGET, console);
  compiler.cleanDirectory();
  compiler.build();
  compiler.zip();
} else {
  Compiler = require("./compilerAnd");
  compiler = new Compiler(process.env.TARGET, console);
  compiler.build();
}
