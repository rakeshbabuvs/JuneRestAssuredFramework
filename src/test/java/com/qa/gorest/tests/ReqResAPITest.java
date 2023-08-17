package com.qa.gorest.tests;

import com.qa.gorest.base.BaseTest;
import com.qa.gorest.client.RestClient;
import com.qa.gorest.constants.APIHttpStatus;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ReqResAPITest extends BaseTest {

    @BeforeMethod
    public void getUserSetup(){
        restClient = new RestClient(prop,baseURI);
    }

    @Test
    public void getReqResponseUserTest() {
        restClient.getapi(REQRES_ENDPOINT+"/2", true,false)
                .then().log().all()
                .assertThat()
                .statusCode(APIHttpStatus.OK_200.getCode());
    }


}
