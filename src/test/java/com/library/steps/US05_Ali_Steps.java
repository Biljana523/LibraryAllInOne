package com.library.steps;

import com.library.utility.ConfigurationReader;
import com.library.utility.LibraryAPI_Util;
import io.cucumber.java.en.*;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;



public class US05_Ali_Steps {

        private RequestSpecification request;
        private Response response;
        String token;

        @Given("I logged Library api with credentials {string} and {string}")
        public void i_logged_Library_api_with_credentials_and(String email, String password) {
            token = LibraryAPI_Util.getToken(email, password);

            request = given().log().uri()
                    .header("x-library-token", LibraryAPI_Util.getToken(email, password));
        }

        @Given("Ali-Accept header is {string}")
        public void ali_accept_header_is(String acceptHeader) {
            request.accept(acceptHeader);
        }

        @Given("Request Content Type header is {string}")
        public void request_content_type_header_is(String contentType) {
            request.contentType(contentType);
        }

        @Given("I send token information as request body")
        public void i_send_token_information_as_request_body() {
            request.formParams("token", token);
        }

        @When("I send POST request to {string} endpoint")
        public void i_send_POST_request_to_endpoint(String endpoint) {
            System.out.println("Endpoint: " + endpoint);
            response = request.post(ConfigurationReader.getProperty("library.baseUri") + endpoint);
        }

    @Then("Ali- status code should be {int}")
    public void aliStatusCodeShouldBe(int statusCode) {
        assertThat(response.getStatusCode(), equalTo(statusCode));
    }


    @And("Ali - Response Content type is {string}")
    public void aliResponseContentTypeIs(String contentType) {
        assertThat(response.getContentType(), equalTo(contentType));
    }


    @Then("the field value for {string} path should be equal to {string}")
        public void the_field_value_for_path_should_be_equal_to(String fieldPath, String expectedValue) {
            assertThat(response.jsonPath().getString(fieldPath), equalTo(expectedValue));
        }

    @And("Ali - {string} field should not be null")
    public void aliFieldShouldNotBeNull(String field) {
        assertThat(response.jsonPath().getString(field), notNullValue());

    }
}



