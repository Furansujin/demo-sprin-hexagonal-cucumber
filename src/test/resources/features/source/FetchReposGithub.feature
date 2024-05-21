Feature: Fetching Repositories github

  Scenario Outline: Fetching Repositories github
    Given user with ID "550e8400-e29b-41d4-a716-446655440000" is logged in
    And I am the admin of the project with ID "660e8400-e29b-41d4-a716-446655440000"
    And I have source github with repository "<github_repo_name>"
    When fetching Repositories github
    Then I should to retrieve the list of my repositories
    And I should see the repository "<github_repo_name>" in the list

    Examples:
      | project_id                           | github_repo_name                            | github_token                         |
      | 750e8400-e29b-41d4-a716-446655440000 | user/repo_one;user/repo_one2;user/repo_one3 | 850e8400-e29b-41d4-a716-446655440000 |
      | 850e8400-e29b-41d4-a716-446655440000 | user/repo_two                               | 950e8400-e29b-41d4-a716-446655440000 |

