package org.example;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.BrowserChannel;

import java.nio.file.Paths;

class App {
    public static void main(String[] args) {
        try (Playwright playwright = Playwright.create()) { // start playwright server

            BrowserType.LaunchOptions lp = new BrowserType.LaunchOptions();
            lp.setChannel("chrome");
            lp.setHeadless(false);
            Browser browser  = playwright.chromium().launch(lp);
            //Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
            Page page = browser.newPage();

            page.navigate("https://google.com");
            page.locator("[aria-label='Search']").click();
            page.locator("[aria-label=\"Search\"]").fill("meditation");
            page.locator("[aria-label=\"Search\"]").press("Enter");
            page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("example.png")));
            //expect(page).toHaveURL('https://www.google.com/search?q=meditation&source=hp&ei=Jb-PZPzaLJWxqtsP8aqBwAk&iflsig=AOEireoAAAAAZI_NNUorXGwiV0sQCZIT7MQ-fXaeiSIa&ved=0ahUKEwi8gbazps7_AhWVmGoFHXFVAJgQ4dUDCAw&uact=5&oq=meditation&gs_lcp=Cgdnd3Mtd2l6EAMyCAgAEIAEELEDMg4ILhCABBCxAxCDARDUAjIICC4QgAQQsQMyCwgAEIAEELEDEIMBMggIABCABBCxAzIICC4QgAQQsQMyCAgAEIAEEMkDMggIABCKBRCSAzIICAAQigUQkgMyCwgAEIAEELEDEIMBOhAILhADEI8BEOoCEIwDEOUCOhAIABADEI8BEOoCEIwDEOUCOhEILhCABBCxAxCDARDHARDRAzoLCAAQigUQsQMQgwE6BQgAEIAEOhEILhCKBRCxAxCDARDHARDRAzoLCC4QgAQQsQMQgwE6CwguEIoFELEDEIMBOg4ILhCDARDUAhCxAxCKBToLCC4QgAQQxwEQrwE6CwgAEIAEELEDEMkDOg4ILhCABBCSAxDHARCvAToLCC4Q1AIQsQMQgARQlLgHWKbFB2C6yAdoAnAAeACAAV6IAY4GkgECMTCYAQCgAQGwAQo&sclient=gws-wiz');

        }
    }
}