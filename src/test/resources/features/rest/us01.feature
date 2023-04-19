Feature: As a librarian, I want to retrieve all users

@us01
  Scenario: Retrieve all users from the API endpoint

    Given I logged Library api as a "librarian" EK
    And Accept header is "application/json" EK
    When I send GET request to "/get_all_users" endpoint EK
    Then status code should be 200 EK
    And Response Content type is "application/json; charset=utf-8" EK
    And "id" field should not be null EK
    And "name" field should not be null EK




