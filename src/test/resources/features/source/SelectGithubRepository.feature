Feature: Select a GitHub repository and create a task file

  Scenario: Successfully select a repository and create a task
    Given user with ID "550e8400-e29b-41d4-a716-446655440000" is logged in
    And source exist with id "650e8400-e29b-41d4-a716-446655440000" and source type "GITHUB" for userId "550e8400-e29b-41d4-a716-446655440000"
    When the user selects a GitHub repository named "TestRepo" for project with ID "660e8400-e29b-41d4-a716-446655440000"
    Then a new source downloader should be created for "TestRepo"

  Scenario: Fail to select a repository due to no logged in user
    Given user exist with id "550e8400-e29b-41d4-a716-446655440000"
    And source exist with id "650e8400-e29b-41d4-a716-446655440000" and source type "GITHUB" for userId "550e8400-e29b-41d4-a716-446655440000"
    When the user selects a GitHub repository named "TestRepo" for project with ID "660e8400-e29b-41d4-a716-446655440000"
    Then an error "No authenticate user" should be thrown


  Scenario: Fail to select a repository due to no GitHub source type
    Given user with ID "550e8400-e29b-41d4-a716-446655440000" is logged in
    But the user does not have a GitHub source type
    When the user selects a GitHub repository named "TestRepo"
    Then an error "No source found with type: GITHUB for user ID:" should be thrown



  Scenario Outline: Selecting for Download a Repository
    Given user with ID "550e8400-e29b-41d4-a716-446655440000" is logged in
    And I am the admin of the project with ID "660e8400-e29b-41d4-a716-446655440000"
    And source exist with id "750e8400-e29b-41d4-a716-446655440000" and source type "GITHUB" for userId "550e8400-e29b-41d4-a716-446655440000"
    When I select the repository "<github_repo_name>" to link with my project
    Then the system should create task download of all files from the repository

    Examples:
      | github_repo_name |
      | user/repo_one    |
      | user/repo_three  |

