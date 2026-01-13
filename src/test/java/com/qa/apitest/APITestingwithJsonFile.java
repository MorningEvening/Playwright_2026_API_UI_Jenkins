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
import java.util.HashMap;
import java.util.Map;

public class APITestingwithJsonFile {

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
    public void postToadd() throws IOException {


        //get json file
        byte[] fileBytes = null;
        File file = new File("./src/test/data/user.json");
        fileBytes = Files.readAllBytes(file.toPath());

        APIResponse postResponse = requestContext.post("https://gorest.co.in/public/v2/users",
                RequestOptions.create()
                        .setHeader("Content-Type","application/json")
                        .setHeader("Authorization", "Bearer ca8fdae18afe6ee73b94668571e4cbf89fa32ea2679811fa2576e51b5baabc65")
                        .setData(fileBytes));

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
}
