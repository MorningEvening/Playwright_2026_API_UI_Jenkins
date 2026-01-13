package com.qa.apitest;/*
 * @Project PlayWrightUIAutomation
 * @author  pritipradhan
 */

import com.api.data.User;
import com.api.data.UsersLombok;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.RequestOptions;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;

public class APITestingPOJOLombok {

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

        // create users object : using builder pattern
        UsersLombok usersLombok = UsersLombok.builder()
                .name("test test")
                .email("sdsd@ddd")
                .gender("male")
                .status("active").build();


        APIResponse postResponse = requestContext.post("https://gorest.co.in/public/v2/users",
                RequestOptions.create()
                        .setHeader("Content-Type","application/json")
                        .setHeader("Authorization", "Bearer ca8fdae18afe6ee73b94668571e4cbf89fa32ea2679811fa2576e51b5baabc65")
                        .setData(usersLombok));

        System.out.println(postResponse.status());
        System.out.println(postResponse.statusText());

        String responseText = postResponse.text();
        System.out.println(responseText);

        // convert response text/json to pojo
        ObjectMapper objectMapper = new ObjectMapper();
        User responseUser = objectMapper.readValue(responseText, User.class);

        System.out.println("Email returned "+responseUser.getEmail());
        Assert.assertEquals(usersLombok.getEmail(), responseUser.getEmail());
        Assert.assertEquals(usersLombok.getGender(),responseUser.getGender());
        Assert.assertEquals(usersLombok.getName(),responseUser.getName());
        Assert.assertNotNull(responseUser.getId());
    }
}
