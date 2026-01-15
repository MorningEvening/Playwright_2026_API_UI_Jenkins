package com.qa.UI;/*
 * @Project PlayWrightUIAutomation
 * @author  pritipradhan
 */

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import com.microsoft.playwright.*;
import org.testng.annotations.Test;

public class loginTest {

    Playwright playwright;
    Browser browser;
    Page page;

    @BeforeTest
    public void setup(){
        playwright = Playwright.create();

        // Launch Chromium browser (headless false for demo)
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));

        // Create a new page
        page = browser.newPage();

        // Navigate to a URL before all tests
        page.navigate("https://example.com");
        System.out.println("Opened URL: " + page.url());
    }
    @Test
    public void sampleTest() {
        System.out.println("Title of page: " + page.title());
        // Add test steps here
    }

    @AfterTest
    public void tearDown() {
        // Close browser and Playwright
        browser.close();
        playwright.close();
    }

}
