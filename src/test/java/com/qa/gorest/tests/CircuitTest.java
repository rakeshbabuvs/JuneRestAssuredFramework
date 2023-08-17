package com.qa.gorest.tests;

import com.qa.gorest.base.BaseTest;
import com.qa.gorest.client.RestClient;
import com.qa.gorest.constants.APIHttpStatus;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class CircuitTest extends BaseTest {

    @BeforeMethod
    public void getUserSetup(){
        restClient = new RestClient(prop,baseURI);
    }

    @Test
    public void getAllCircuits() {
        restClient.getapi(CIRCUIT_ENDPOINT+"/2017/circuits.json", true,false)
                .then().log().all()
                .assertThat()
                .statusCode(APIHttpStatus.OK_200.getCode());
    }

}
