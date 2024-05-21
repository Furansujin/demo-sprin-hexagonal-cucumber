Feature: Authentication GitHub

  Scenario Outline: Failure in GitHub Authentication
    Given user with ID "550e8400-e29b-41d4-a716-446655440000" is logged in
    And I am the admin of the project with ID "660e8400-e29b-41d4-a716-446655440000"
    And "1234567890" is valid GitHub credentials
    When I attempt to authenticate with "1111111111" invalid credentials
    Then I should receive an error message "<error_message>"

    Examples:
    | error_message               |
    | Error GitHub credentials    |

  Scenario: Authenticating with GitHub
    Given user with ID "550e8400-e29b-41d4-a716-446655440000" is logged in
    And I am the admin of the project with ID "660e8400-e29b-41d4-a716-446655440000"
    And "850e8400e29b41d4a71446655440000" is valid GitHub credentials
    When I authenticate and give a code "850e8400e29b41d4a71446655440000" to the system in my Sources
    Then I should be have source github in my Sources with token

