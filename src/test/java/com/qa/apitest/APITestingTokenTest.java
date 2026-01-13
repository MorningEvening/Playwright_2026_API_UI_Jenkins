package com.qa.apitest;/*
 * @Project PlayWrightUIAutomation
 * @author  pritipradhan
 */

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.RequestOptions;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class APITestingTokenTest {

    Playwright playwright;
    APIRequestContext requestContext;
    APIRequest request;


    @BeforeTest
    public void setup(){

        playwright = Playwright.create();
        request = playwright.request();
        requestContext = request.newContext();
    }

    @Test
    public void getTokenTest() throws IOException {

        String reqJson = "{\"username\":\"admin\",\"password\":\"password123\"}";

        APIResponse tokenResponse = requestContext.post("https://restful-booker.herokuapp.com/auth",
                RequestOptions.create()
                        .setHeader("Content-Type", "application/json")
                        .setData(reqJson));

        System.out.println(tokenResponse.status());
        System.out.println(tokenResponse.statusText());
        String responseText = tokenResponse.text();
        System.out.println("Response text is "+responseText);

        ObjectMapper mapper = new ObjectMapper();
        JsonNode postJsonResponse = mapper.readTree(tokenResponse.body());
        String token = postJsonResponse.get("token").asText();
        System.out.println("Token is " +token);
    }
}
