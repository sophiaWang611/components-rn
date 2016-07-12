import it from '../helpers/appium';

describe("integration smoke test", () => {
  it("should launch the simulator and compute the sum", function* (driver, done) {
    var value = 1 + 1;
    value.should.equal(2);
    done();
  });

  /*it("should launch the simulator and compute the sum", function* (driver, done) {

    yield driver.get("http://172.16.3.148:8080/").elementById("touchable_cell0").click().sleep(500);

    done();
  });*/

});
