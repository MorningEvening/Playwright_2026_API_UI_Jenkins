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

import java.io.IOException;

public class APTestingUsingTokenPut {

    Playwright playwright;
    APIRequestContext requestContext;
    APIRequest request;
    public static String emailID;
    private static String token_val;


    @BeforeTest
    public void setup() throws IOException {

        playwright = Playwright.create();
        request = playwright.request();
        requestContext = request.newContext();

        // Generating token
        String reqJson = "{\"username\":\"admin\",\"password\":\"password123\"}";

        APIResponse tokenResponse = requestContext.post("https://restful-booker.herokuapp.com/auth",
                RequestOptions.create()
                        .setHeader("Content-Type", "application/json")
                        .setData(reqJson));

        System.out.println("Token status" +tokenResponse.status());
        System.out.println(tokenResponse.statusText());
        String responseText = tokenResponse.text();
        System.out.println("Response text is "+responseText);

        ObjectMapper mapper = new ObjectMapper();
        JsonNode postJsonResponse = mapper.readTree(tokenResponse.body());
        token_val = postJsonResponse.get("token").asText();

    }

    public static String getRandomEmail(){
        emailID="testAutomation"+ System.currentTimeMillis()+"@gmail.com";
        return emailID;
    }

    @Test
    public void putWithToken() throws IOException {


        String bookingJson = "{\"firstname\":\"James\"," +
                              "\"lastname\":\"Brown\"," +
                              "\"totalprice\":111," +
                              "\"depositpaid\":true," +
                              "\"bookingdates\":" +
                              "{\"checkin\":\"2018-01-01\",\"checkout\":\"2019-01-01\"}," +
                              "\"additionalneeds\":\"Lunch\"}";

        System.out.println("Token value is "+token_val);
        APIResponse putResponse = requestContext.put("https://restful-booker.herokuapp.com/booking/1",
                RequestOptions.create()
                        .setHeader("Content-Type", "application/json")
                        .setHeader("Cookie", "token="+token_val)
                        .setData(bookingJson));

        System.out.println("Put status" +putResponse.status());
        System.out.println(putResponse.statusText());
        String responseText = putResponse.text();
        System.out.println("After put - the responseText is "+responseText);

    }
}
