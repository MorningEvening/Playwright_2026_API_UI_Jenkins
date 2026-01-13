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

public class APITestingDelete {

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

    public static String getRandomEmail() {
        emailID = "testAutomation" + System.currentTimeMillis() + "@gmail.com";
        return emailID;
    }

    @Test
    public void deleteWithPOJO() throws IOException {

        UsersLombok usersLombok = UsersLombok.builder()
                .name("test_delete")
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
        System.out.println("After post - the responseText is " + responseText);

        ObjectMapper mapper = new ObjectMapper();
        User responseUser = mapper.readValue(responseText, User.class);
        String id = responseUser.getId();
        System.out.println("Id is "+id);

        // 2. To delete

        APIResponse deleteResponse = requestContext.delete("https://gorest.co.in/public/v2/users/"+id,
                RequestOptions.create()
                        .setHeader("Content-Type", "application/json")
                        .setHeader("Authorization", "Bearer ca8fdae18afe6ee73b94668571e4cbf89fa32ea2679811fa2576e51b5baabc65")
                        .setData(usersLombok));

        System.out.println(deleteResponse.status());
        System.out.println(deleteResponse.statusText());
        Assert.assertEquals(deleteResponse.status(),204);

        // 3 Verify with get

        APIResponse getResponse = requestContext.get("https://gorest.co.in/public/v2/users/"+id,
                RequestOptions.create()
                        .setHeader("Content-Type", "application/json")
                        .setHeader("Authorization", "Bearer ca8fdae18afe6ee73b94668571e4cbf89fa32ea2679811fa2576e51b5baabc65")
                        .setData(usersLombok));

        System.out.println(getResponse.status());
        System.out.println(getResponse.statusText());
        Assert.assertEquals(getResponse.status(),404);


    }
}
