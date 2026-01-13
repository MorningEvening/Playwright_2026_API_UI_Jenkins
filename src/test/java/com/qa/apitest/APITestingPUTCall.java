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

public class APITestingPUTCall {

    Playwright playwright;
    APIRequestContext requestContext;
    APIRequest request;
    public static String emailID;


    @BeforeTest
    public void setup() {

        playwright = Playwright.create();
        request = playwright.request();
        requestContext = request.newContext();
    }

    public static String getRandomEmail(){
        emailID="testAutomation"+ System.currentTimeMillis()+"@gmail.com";
        return emailID;
    }

    @Test(enabled = false)
    public void putWithPOJO() throws IOException {

        //1. post call to create a new user
        //2. put call to update the newly created id
        //3. get to verify - updates

        UsersLombok usersLombok = UsersLombok.builder()
                .name("test_post4")
                .email(getRandomEmail())
                .gender("male")
                .status("active").build();


        APIResponse postResponse = requestContext.post("https://gorest.co.in/public/v2/users",
                RequestOptions.create()
                        .setHeader("Content-Type", "application/json")
                        .setHeader("Authorization", "Bearer ca8fdae18afe6ee73b94668571e4cbf89fa32ea2679811fa2576e51b5baabc65")
                        .setData(usersLombok));

        System.out.println(postResponse.status());
        System.out.println(postResponse.statusText());
        String responseText = postResponse.text();
        System.out.println("After post - the responseText is "+responseText);

        ObjectMapper mapper = new ObjectMapper();
        User responseUser = mapper.readValue(responseText, User.class);
        String id = responseUser.getId();
        System.out.println("Id is "+id);

        //2. passing this id to update the content
        // updating below contents

        usersLombok.setStatus("inactive");
        usersLombok.setEmail(getRandomEmail());

        APIResponse putResponse = requestContext.put("https://gorest.co.in/public/v2/users/"+id,
                RequestOptions.create()
                        .setHeader("Content-Type", "application/json")
                        .setHeader("Authorization", "Bearer ca8fdae18afe6ee73b94668571e4cbf89fa32ea2679811fa2576e51b5baabc65")
                        .setData(usersLombok));

        System.out.println(putResponse.status());
        System.out.println(putResponse.statusText());
        String responseText_put = putResponse.text();
        System.out.println("After put - the responseText is "+responseText_put);

        User responseUser_put = mapper.readValue(responseText_put, User.class);

        Assert.assertEquals(responseUser_put.getId(),id);
        Assert.assertEquals(responseUser_put.getEmail(),usersLombok.getEmail());
        Assert.assertEquals(responseUser_put.getStatus(),usersLombok.getStatus());
    }
}