/**
 * Created by sophia on 16/6/29.
 */

import it from '../helpers/appium';

describe("Authentication", () => {

    it("should sign up the user, show dashboard, logout", function* (driver, done) {

        yield driver.elementById("indexRegister");
        yield driver.elementById("indexLogin").click();


        yield driver.elementById('userName').sendKeys("15088655203");
        yield driver.elementById('password').sendKeys("wxy1313");
        yield driver.elementById('smsCaptcha').sendKeys("1111");

        yield driver.elementById('getToken').click();
        //yield driver.elementById('getToken').click();
        yield driver.elementById('doLogin').click();


        yield driver.elementById('shopPhoto').click();
        yield driver.elementById('logout').click();

        yield driver.elementById("indexLogin");
        yield driver.elementById("indexRegister");

        done();
    });

    it("register new user", function* (driver, done) {
        yield driver.elementById("indexRegister").click();

        yield driver.elementById('enter_phone').sendKeys("15088651200");
        yield driver.elementById('next').click();
        yield driver.elementById('next').click();

        yield driver.elementById('enterMSCode').sendKeys("1111");
        yield driver.elementById('next').click();

        yield driver.elementById('enterName').sendKeys("test name");
        yield driver.elementById('setSex').click();
        yield driver.elementById('确认').click();

        yield driver.elementById('enterPassword').sendKeys("wxy1111");
        yield driver.elementById('repeatPassword').sendKeys("wxy1111");

        yield driver.elementById('submit').click();

        yield driver.resetApp();

        done();
    });
});
