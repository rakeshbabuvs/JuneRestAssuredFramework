package com.qa.gorest.tests;

import com.qa.gorest.base.BaseTest;
import com.qa.gorest.client.RestClient;
import com.qa.gorest.constants.APIHttpStatus;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.*;

public class GetUserTests extends BaseTest {

    @BeforeMethod
    public void getUserSetup(){
        restClient = new RestClient(prop,baseURI);
    }

    @Test (enabled = true, priority = 1)
    public void getAllUsers() {
        restClient.getapi(GOREST_ENDPOINT, true,true)
                .then().log().all()
                .assertThat()
                .statusCode(APIHttpStatus.OK_200.getCode());
    }
    @Test (priority = 2)
    public void getUser() {
        restClient.getapi(GOREST_ENDPOINT+"/4269499",true,true)
                .then().log().all()
                .assertThat()
                .statusCode(APIHttpStatus.OK_200.getCode())
                .and().body("id",equalTo(4269499));
    }
    @Test (priority = 3)
    public void getUserWithQueryParamsTest() {
      //  restClient = new RestClient(prop, baseURI);
        Map<String,String> queryParams = new HashMap<String,String>();
        queryParams.put("name","Rakesh");
        queryParams.put("status","active");
        restClient.getapi(GOREST_ENDPOINT+"/4269499",queryParams,null,true)
                .then().log().all()
                .assertThat()
                .statusCode(APIHttpStatus.OK_200.getCode())
                .and().body("id",equalTo(4269499));
    }

}
