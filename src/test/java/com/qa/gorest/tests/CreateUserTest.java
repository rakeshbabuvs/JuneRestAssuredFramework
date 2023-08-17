package com.qa.gorest.tests;

import com.qa.gorest.base.BaseTest;
import com.qa.gorest.client.RestClient;
import com.qa.gorest.constants.APIHttpStatus;
import com.qa.gorest.pojo.User;
import com.qa.gorest.utils.StringUtils;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class CreateUserTest extends BaseTest {

    @BeforeMethod
    public void getUserSetup(){
        restClient = new RestClient(prop,baseURI);
    }

    @DataProvider
    public Object[][] getUserTestData(){

        return new Object[][] {
                {"Rakesh","male","active"},
                {"Ishani","female","active"},
                {"Veena","female","active"}
        };
    }
    @Test(dataProvider = "getUserTestData")
    public void createUser(String name, String gender, String status) {
        User user = new User(name,StringUtils.getRandomEmailId(),gender,status);

        Integer userId = restClient.postapi("public/v2/users", "json", user, true)
                .then().log().all()
                .assertThat().statusCode(APIHttpStatus.CREATED_201.getCode())
                .extract().path("id");

        System.out.println("Created user id is " + userId);
    //RestClient restClient1 = new RestClient(prop,baseURI);
        restClient.getapi("/public/v2/users/" + userId, true,true)
                .then()
                .assertThat()
                .statusCode(APIHttpStatus.OK_200.getCode());
    }



    }


