Feature: : As a user, I want to view my own user information using the API
  so that I can see what information is stored about me

  @us05
  Scenario Outline: Decode User
    Given I logged Library api with credentials "<email>" and "<password>"
    And Ali-Accept header is "application/json"
    And Request Content Type header is "application/x-www-form-urlencoded"
    And I send token information as request body
    When I send POST request to "/decode" endpoint
    Then Ali- status code should be 200
    And Ali - Response Content type is "application/json; charset=utf-8"
    And the field value for "user_group_id" path should be equal to "<user_group_id>"
    And the field value for "email" path should be equal to "<email>"
    And Ali - "full_name" field should not be null
    And Ali - "id" field should not be null


    Examples:
      | email                | password    | user_group_id |
      | student5@library     | libraryUser | 3             |
      | librarian10@library  | libraryUser | 2             |
      | student10@library    | libraryUser | 3             |
      | librarian13@library | libraryUser | 2             |