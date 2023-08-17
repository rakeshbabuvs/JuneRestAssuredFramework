package com.qa.gorest.client;

import com.qa.gorest.frameworkexception.APIFrameworkException;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.Map;
import java.util.Properties;

import static io.restassured.RestAssured.given;

public class RestClient {
    private static RequestSpecBuilder specBuilder;
//    private static final String BASE_URI = "https://gorest.co.in";
//    private static final String BEARER_TOKEN = "674d5028e20db9ca966d39a9a85bb4b14be2f1bab31ee9fd74401c8a9c2b4faa";
    private Properties prop;
    private String baseURI;
    private boolean isAuthorizationHeaderAdded = false;

    public RestClient(Properties prop, String baseURI) {
        specBuilder = new RequestSpecBuilder();
        this.prop = prop;
        this.baseURI = baseURI;
    }
    public void addAuthorizationHeader() {
        if (!isAuthorizationHeaderAdded){
            specBuilder.addHeader("Authorization","Bearer " + prop.getProperty("tokenId"));
            isAuthorizationHeaderAdded = true;
        }

    }
    private void setRequestContentType(String contentType) {
        switch (contentType.toLowerCase()){
            case "json":
                specBuilder.setContentType(ContentType.JSON);
                break;
            case "xml":
                specBuilder.setContentType(ContentType.XML);
                break;
            case "multipart":
                specBuilder.setContentType(ContentType.MULTIPART);
                break;
            default:
                System.out.println("plz pass the right Content type");
                throw new APIFrameworkException("INVALIDCONTENTTYPE");
        }

    }

    private RequestSpecification createRequestSpec (boolean includeAuth) {
        specBuilder.setBaseUri(baseURI);
        if(includeAuth){
            addAuthorizationHeader();
        }
        return specBuilder.build();
    }
    private RequestSpecification createRequestSpec (Map<String, String> headersMap) {
        specBuilder.setBaseUri(baseURI);
        addAuthorizationHeader();
        if (headersMap!=null){
            specBuilder.addHeaders(headersMap);

        }
        return specBuilder.build();
    }
    private RequestSpecification createRequestSpec (Map<String, String> headersMap, Map<String,String>queryParams ) {
        specBuilder.setBaseUri(baseURI);
        addAuthorizationHeader();
        if (headersMap!=null){
            specBuilder.addHeaders(headersMap);

        }
        if (queryParams !=null){
            specBuilder.addQueryParams(queryParams);
        }
        return specBuilder.build();
    }
    //POST Calls - Pass POJO and Content TYpe
    private RequestSpecification createRequestSpec (Object requestBody, String contentType) {
        specBuilder.setBaseUri(baseURI);
        addAuthorizationHeader();
        //Generic method for setting up Content type
        setRequestContentType(contentType);
        if (requestBody != null) {
            specBuilder.setBody(requestBody);
        }
        return specBuilder.build();
    }
    private RequestSpecification createRequestSpec (Object requestBody, String contentType, Map<String, String> headersMap) {
        specBuilder.setBaseUri(baseURI);
        addAuthorizationHeader();
        if (headersMap!=null) {
            specBuilder.addHeaders(headersMap);
        }
        if (requestBody != null) {
            specBuilder.setBody(requestBody);
        }

        return specBuilder.build();
    }

    // Http method Utils
    public Response getapi(String serviceUrl,boolean includeAuth, boolean log) {
        if (log) {
            return given(createRequestSpec(includeAuth)).log().all()
                    .when()
                    .get(serviceUrl);
        }
        return given(createRequestSpec(includeAuth))
                .when()
                .get(serviceUrl);
    }
    public Response getapi(String serviceUrl,Map<String,String> headersMap, boolean log) {
        if (log) {
            return given(createRequestSpec(headersMap)).log().all()
                    .when()
                    .get(serviceUrl);
        }
        return given(createRequestSpec(headersMap))
                .when()
                .get(serviceUrl);
    }
    public Response getapi(String serviceUrl,Map<String,String>queryParams, Map<String,String> headersMap, boolean log) {
        if (log) {
            return given(createRequestSpec(headersMap,queryParams)).log().all()
                    .when()
                    .get(serviceUrl);
        }
        return given(createRequestSpec(headersMap,queryParams))
                .when()
                .get(serviceUrl);
    }
    public Response postapi(String serviceUrl, String contentType, Object requestBody, boolean flag) {

        if (flag){
            return given(createRequestSpec(requestBody,contentType)).log().all()
                    .when()
                    .post(serviceUrl);
        }
        return given(createRequestSpec(requestBody,contentType))
                .when()
                .post(serviceUrl);
    }
    public Response postapi(String serviceUrl, String contentType, Object requestBody, Map<String,String> headerMap, boolean flag) {

        if (flag){
            return given(createRequestSpec(requestBody,contentType,headerMap)).log().all()
                    .when()
                    .post(serviceUrl);
        }
        return given(createRequestSpec(requestBody,contentType,headerMap))
                .when()
                .post(serviceUrl);
    }
    public Response putapi(String serviceUrl, String contentType, Object requestBody, boolean flag) {

        if (flag){
            return given(createRequestSpec(requestBody,contentType)).log().all()
                    .when()
                    .put(serviceUrl);
        }
        return given(createRequestSpec(requestBody,contentType))
                .when()
                .put(serviceUrl);
    }
    public Response putapi(String serviceUrl, String contentType, Object requestBody, Map<String,String> headerMap, boolean flag) {

        if (flag){
            return given(createRequestSpec(requestBody,contentType,headerMap)).log().all()
                    .when()
                    .put(serviceUrl);
        }
        return given(createRequestSpec(requestBody,contentType,headerMap))
                .when()
                .put(serviceUrl);
    }
    public void delete(String serviceUrl, boolean includeAuth,boolean log) {
        if (log) {
            given(createRequestSpec(includeAuth)).log().all()
                    .when()
                    .delete();
        }
        given(createRequestSpec(includeAuth))
                .when()
                .delete();
    }
    public String getAccessToken(String serviceURL, String grantType, String clientId, String clientSecret  ) {
        //1. POST - get the access token
        RestAssured.baseURI = "https://test.api.amadeus.com";

        String accessToken = given().log().all()
                .contentType(ContentType.URLENC)
                .formParam("grant_type", grantType)
                .formParam("client_id", clientId)
                .formParam("client_secret", clientSecret)
                .when()
                .post(serviceURL)
                .then().log().all()
                .assertThat()
                .statusCode(200)
                .extract().path("access_token");

        System.out.println("access token: " + accessToken);
        return 	accessToken;

    }







}
