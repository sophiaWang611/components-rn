/**
 * Created by sophia on 16/7/7.
 */

import it from '../helpers/appium';

describe("Main page", () => {
    it("show webView of material page", function* (driver, done) {

        yield driver.elementById("indexLogin").click();

        yield driver.elementById('userName').sendKeys("15088655203");
        yield driver.elementById('password').sendKeys("wxy1313");
        yield driver.elementById('smsCaptcha').sendKeys("1111");
        yield driver.elementById('getToken').click();
        yield driver.elementById('doLogin').click();


        yield driver.elementById('购买物料').click();

        yield driver.elementById('物料列表');

        yield driver.elementById('关闭').click();

        yield driver.elementById('购买物料');

        done();
    });

    it("show setting page", function* (driver, done) {
        yield driver.elementById('设置').click();

        yield driver.elementById('设置');

        done();

    });

});