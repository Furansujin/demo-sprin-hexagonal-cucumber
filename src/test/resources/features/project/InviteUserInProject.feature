Feature: Invite User In Project

  Background:
    Given user with ID "550e8400-e29b-41d4-a716-446655440000" is logged in

  Scenario Outline: Invite users to a project
    Given I am the admin of the project with ID "<project_id>"
    And user with email "<user_email>" exists
    When I invite a user with email "<user_email>" to the project with ID "<project_id>"
    Then the user with email "<user_email>" should be invited to the project with ID "<project_id>"

    Examples:
      | project_id                           | user_email        |
      | 9f233522-5e0c-11ee-8c99-0242ac120003 | user1@example.com |
      | 8f233522-5e0c-11ee-8c99-0242ac120002 | user2@example.com |

  Scenario Outline: Invite a non-existent user to a project
    Given I am the admin of the project with ID "<project_id>"
    When I invite a non-existent user with email "<invalid_email>" to the project  with ID "<project_id>"
    Then the invitation should fail due to an invalid email address in the project  with ID "<project_id>"

    Examples:
      | project_id                           | invalid_email        |
      | 9f233522-5e0c-11ee-8c99-0242ac120003 | nonuser1@example.com |
      | 8f233522-5e0c-11ee-8c99-0242ac120002 | nonuser2@example.com |
