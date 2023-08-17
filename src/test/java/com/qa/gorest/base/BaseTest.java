package com.qa.gorest.base;

import com.qa.gorest.client.RestClient;
import com.qa.gorest.configuration.ConfigurationManager;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import java.util.Properties;

public class BaseTest {
    protected ConfigurationManager config;
    protected Properties prop;
    protected RestClient restClient;
    protected String baseURI;
    //Service URLs:
    public static final String GOREST_ENDPOINT = "/public/v2/users";
    public static final String REQRES_ENDPOINT = "/api/users";
    public static final String CIRCUIT_ENDPOINT = "/api/f1";
    public static final String AMADEUS_TOKEN_ENDPOINT = "/v1/security/oauth2/token";
    public static final String AMADEUS_FLIGHTBOKKING_ENDPOINT = "/v1/shopping/flight-destinations";

    @Parameters({"baseURI"})
    @BeforeTest
    public void setUp(String baseURI) {
        RestAssured.filters(new AllureRestAssured());
        this.baseURI = baseURI;
        config = new ConfigurationManager();
        prop = config.initProp();
       // String baseUri = prop.getProperty("baseURI");
    }

}
