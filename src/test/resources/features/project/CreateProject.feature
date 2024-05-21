Feature: Create project

  Scenario Outline: Create a new project
    Given user with ID "<user_id>" is logged in
    When I create a project with name "<project_name>" and description "<project_description>"
    Then the project should be created successfully with name "<project_name>" and description "<project_description>"
    And I should be assigned the admin role in project for user with ID "<user_id>"

    Examples:
      | user_id                              | project_name | project_description    |
      | 8f233522-5e0c-11ee-8c99-0242ac120002 | ProjectA     | A test project for A   |
      | 94150b6e-5e0c-11ee-8c99-0242ac120002 | ProjectB     | Another test project B |

