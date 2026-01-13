package com.qa.apitest;/*
 * @Project PlayWrightUIAutomation
 * @author  pritipradhan
 */

import com.api.data.User;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.RequestOptions;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class APITestingwithPOJO {

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
    public void postToaddwithPOJO() throws IOException {

        User userData = new User("test_1a", "test_ojo3@gmail","male","active");

        APIResponse postResponse = requestContext.post("https://gorest.co.in/public/v2/users",
                RequestOptions.create()
                        .setHeader("Content-Type","application/json")
                        .setHeader("Authorization", "Bearer ca8fdae18afe6ee73b94668571e4cbf89fa32ea2679811fa2576e51b5baabc65")
                        .setData(userData));

        System.out.println(postResponse.status());
        System.out.println(postResponse.statusText());

        String responseText = postResponse.text();
        System.out.println(responseText);

        // convert response text/json to pojo
        ObjectMapper objectMapper = new ObjectMapper();
        User responseUser = objectMapper.readValue(responseText, User.class);

        System.out.println("Email returned "+responseUser.getEmail());
        Assert.assertEquals(userData.getEmail(), responseUser.getEmail());
        Assert.assertEquals(userData.getGender(),responseUser.getGender());
        Assert.assertEquals(userData.getName(),responseUser.getName());
        Assert.assertNotNull(responseUser.getId());
    }
}
