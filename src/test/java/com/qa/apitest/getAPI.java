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
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class getAPI {

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
    public void getSpecificUser(){
        APIResponse apiResponse = requestContext.get("https://gorest.co.in/public/v2/users/",
                RequestOptions.create()
                .setQueryParam("id",8115124)
                .setQueryParam("status", "inactive"));

        System.out.println("Query parameter response " +apiResponse.statusText());
        System.out.println("Text "+apiResponse.text());
    }

    @Test(enabled = false)
    public void postToadd() throws IOException {

        Map<String,String> data = new HashMap<>();
        data.put("name","Nerrddwe2");
        data.put("email","isdyydbd@g");
        data.put("gender","male");
        data.put("status","Inactive");
        APIResponse postResponse = requestContext.post("https://gorest.co.in/public/v2/users",
                RequestOptions.create()
                        .setHeader("Content-Type","application/json")
                        .setHeader("Authorization", "Bearer ca8fdae18afe6ee73b94668571e4cbf89fa32ea2679811fa2576e51b5baabc65")
                        .setData(data));

        System.out.println(postResponse.status());
        System.out.println(postResponse.statusText());
        System.out.println(postResponse.text());
        ObjectMapper objectMapper = new ObjectMapper();

        JsonNode postJsonResponse = objectMapper.readTree(postResponse.body());
        System.out.println(postJsonResponse.toPrettyString());

        String userCreated = postJsonResponse.get("id").asText();
        System.out.println("Id is "+userCreated);
        APIResponse getResponse = requestContext.get("https://gorest.co.in/public/v2/users/",
                        RequestOptions.create()
                            .setQueryParam("id",userCreated)
                            .setHeader("Authorization","Bearer ca8fdae18afe6ee73b94668571e4cbf89fa32ea2679811fa2576e51b5baabc65"));
        System.out.println("Get Response is "+getResponse.text());
    }

    @Test
    public void getUsersTest() throws IOException {

        APIResponse apiResponse = requestContext.get("https://gorest.co.in/public/v2/users/");

        int status = apiResponse.status();
        System.out.println("Response status code "+status);
        Assert.assertEquals(status,200);
        Assert.assertEquals(apiResponse.ok(),true);

        String statusText = apiResponse.statusText();
        System.out.println(statusText);
        System.out.println(apiResponse.url());

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonResponse =  objectMapper.readTree(apiResponse.body());
        //System.out.println(jsonResponse.toPrettyString());

        Map<String,String> headerMap = apiResponse.headers();
        System.out.println(headerMap);
        Assert.assertEquals(headerMap.get("content-type"),"application/json");
        Assert.assertEquals(headerMap.get("x-download-options"),"noopen");
    }

    @AfterTest
    public void teardown(){
        playwright.close();
    }

}
