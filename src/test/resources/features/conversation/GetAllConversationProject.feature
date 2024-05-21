Feature: Get all conversations for a project


  Scenario Outline: User successfully retrieves all conversations for a project
    Given user with ID "<user_id>" is logged in
    And project exist with id "<project_id>" and userId "<user_id>" have role "MEMBER"
    And conversation exist with "<conversation_id>" and project id "<project_id>" and userId "<user_id>"
    When the user requests to see all conversations for the project with ID "<project_id>"
    Then all conversations for the project with ID "<project_id>" should be returned

    Examples:
      | user_id                              | project_id                           | conversation_id                       |
      | 8f233522-5e0c-11ee-8c99-0242ac120002 | a1b2c3d4-1111-2222-3333-4444ccccbbb2 | 9f233522-5e0c-11ee-8c99-0242ac120002 |

  Scenario Outline: User tries to retrieve conversations for a project they do not have access to
    Given user with ID "<user_id>" is logged in
    And the user does not have access to the project with ID "<project_id>"
    And conversation exist with "9f233522-5e0c-11ee-8c99-0242ac120002" and project id "<project_id>"
    When the user requests to see all conversations for the project with ID "<project_id>"
    Then an error message "No project found with ID: " should be returned for project ID "<project_id>"

    Examples:
      | user_id                              | project_id                           |
      | 8f233522-5e0c-11ee-8c99-0242ac120002 | 6f233522-5e0c-11ee-8c99-0242ac120002 |

  Scenario: User is not authenticated when trying to retrieve conversations
    Given user exist with id "8f233522-5e0c-11ee-8c99-0242ac120002"
    And  the user with ID "8f233522-5e0c-11ee-8c99-0242ac120002" is not logged in
    And project exist with id "a1b2c3d4-1111-2222-3333-4444ccccbbb2" and userId "8f233522-5e0c-11ee-8c99-0242ac120002" have role "MEMBER"
    When the user requests to see all conversations for the project with ID "a1b2c3d4-1111-2222-3333-4444ccccbbb2"
    Then an error message "No authenticate user" should be returned when trying to retrieve conversations
