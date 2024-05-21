Feature: Get all projects for a user

  Scenario Outline: User successfully retrieves all projects
    Given user with ID "<user_id>" is logged in
    And project exist with id "<project_id>" and userId "<user_id>" have role "MEMBER"
    When the user requests to see all their projects
    Then all projects associated with user ID "<user_id>" should be returned

    Examples:
      | user_id                              | project_id                           |
      | 8f233522-5e0c-11ee-8c99-0242ac120002 | 5f233522-5e0c-11ee-8c99-0242ac120002 |
      | 94150b6e-5e0c-11ee-8c99-0242ac120002 | 44150b6e-5e0c-11ee-8c99-0242ac120002 |

  Scenario Outline: User tries to retrieve projects without being logged in
    Given user exist with id "<user_id>"
    And project exist with id "<project_id>" and userId "<user_id>" have role "MEMBER"
    When the user requests to see all their projects
    Then an error message "No authenticate user" should be returned

    Examples:
      | user_id                              | project_id                           |
      | 8f233522-5e0c-11ee-8c99-0242ac120002 | 5f233522-5e0c-11ee-8c99-0242ac120002 |
      | 94150b6e-5e0c-11ee-8c99-0242ac120002 | 44150b6e-5e0c-11ee-8c99-0242ac120002 |

  Scenario Outline: User has no projects
    Given user with ID "<user_id>" is logged in
    But the user with ID "<user_id>" has no projects
    When the user requests to see all their projects
    Then an empty project list should be returned for user ID "<user_id>"

    Examples:
      | user_id                              |
      | ddd50b6e-5e0c-11ee-8c99-0242ac130003 |
      | eee20b7f-5e0c-11ee-8c99-0242ac130004 |
