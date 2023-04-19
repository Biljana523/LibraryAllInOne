package com.library.steps;

import com.library.utility.ConfigurationReader;
import com.library.utility.LibraryAPI_Util;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.hamcrest.Matchers;
import org.junit.Assert;

import java.util.List;

import java.util.LinkedHashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class APIStepDefs {

    RequestSpecification givenPart;
    Response response;
    ValidatableResponse thenPart;


    /**
     * US 01 RELATED STEPS
     */
    @Given("I logged Library api as a {string}")
    public void i_logged_library_api_as_a(String userType) {

        givenPart = given().log().uri()
                .header("x-library-token", LibraryAPI_Util.getToken(userType));
    }

    @Given("Accept header is {string}")
    public void accept_header_is(String contentType) {
        givenPart.accept(contentType);
    }

    @When("I send GET request to {string} endpoint")
    public void i_send_get_request_to_endpoint(String endpoint) {
        response = givenPart.when().get(ConfigurationReader.getProperty("library.baseUri") + endpoint).prettyPeek();
        thenPart = response.then();
    }

    @Then("status code should be {int}")
    public void status_code_should_be(Integer statusCode) {
        thenPart.statusCode(statusCode);
    }

    @Then("Response Content type is {string}")
    public void response_content_type_is(String contentType) {
        thenPart.contentType(contentType);
    }

    @Then("{string} field should not be null")
    public void field_should_not_be_null(String path) {
        thenPart.body(path, everyItem(notNullValue()));
    }
    /**
     * US 02 Bota  RELATED STEPS
     */
    static String id;


    @Given("Path param is {string}")
    public void path_param_is(String id) {

    /**
     * US03_SK Scenario: Create a new book API
     */

    @Given("Request Content Type header is {string}")
    public void request_content_type_header_is(String contentType) {
        givenPart.contentType(contentType);

    }

    Map<String,Object> randomDataMap;
    @Given("I create a random {string} as request body")
    public void i_create_a_random_as_request_body(String randomData) {

        Map<String, Object> requestBody = new LinkedHashMap<>();

        switch (randomData) {
            case "user":
                requestBody = LibraryAPI_Util.getRandomUserMap();
                break;
            case "book":
                requestBody = LibraryAPI_Util.getRandomBookMap();
                break;
            default:
                throw new RuntimeException("Unexpected value: " + randomData);
        }
        System.out.println("requestBody = " + requestBody);
        randomDataMap = requestBody;
        givenPart.formParams(requestBody);

    }

    @When("I send POST request to {string} endpoint")
    public void i_send_post_request_to_endpoint(String endpoint) {
        response = givenPart.when().post(ConfigurationReader.getProperty("library.baseUri") + endpoint)
                .prettyPeek();

        thenPart=response.then();
    }
    @Then("the field value for {string} path should be equal to {string}")
    public void the_field_value_for_path_should_be_equal_to(String path, String value) {

        thenPart.body(path,is(value));

    }


        this.id = id;
        givenPart.pathParam("id", id);

    }

    @Then("{string} field should be same with path param")
    public void field_should_be_same_with_path_param(String path) {

        String path1 = thenPart.extract().jsonPath().getString(path);

        Assert.assertEquals(path1, id);

    }

    @Then("following fields should not be null")
    public void following_fields_should_not_be_null(List<String> dataList) {

        for (String eachField : dataList) {
            thenPart.body(eachField, is(notNullValue()));
        }
    }

}
