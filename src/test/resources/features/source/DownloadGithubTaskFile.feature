Feature: Download task files from GitHub

  Scenario Outline: Successfully download task files
    Given user exist with id "<userId>"
    And source exist with id "<sourceId>" and source type "GITHUB" for userId "<userId>"
    And project exist with id "<projectId>" and userId "<userId>" have role "ADMIN"
    And there are PENDING source downloader processes for a userid "<userId>" and sourceId "<sourceId>" and projectId "<projectId>"
    When the scheduler downloads the task files
    Then the task files should be downloaded and stored
    And the state of the downloader processes should be set to DOWNLOADED

    Examples:
      | userId                               | sourceId                             | projectId                            |
      | 750e8400-e29b-41d4-a716-446655440000 | 650e8400-e29b-41d4-a716-446655440000 | 250e8400-e29b-41d4-a716-446655440000 |
      | 850e8400-e29b-41d4-a716-446655440000 | 950e8400-e29b-41d4-a716-446655440000 | 450e8400-e29b-41d4-a716-446655440000 |

  Scenario Outline: Fail to download task files due to missing project
    Given user exist with id "<userId>"
    And source exist with id "<sourceId>" and source type "GITHUB" for userId "<userId>"
    And there are PENDING source downloader processes for a userid "<userId>" and sourceId "<sourceId>" and projectId "<projectId>"
    But the corresponding project with projectId "<projectId>" for a userid "<userId>" does not exist
    When the scheduler downloads the task files
    Then an error "No project found with ID: " should be logged for id "<projectId>"
    And the state of the downloader processes should be set to ERROR

    Examples:
      | userId                               | sourceId                             | projectId                            |
      | 750e8400-e29b-41d4-a716-446655440000 | 650e8400-e29b-41d4-a716-446655440000 | 550e8400-e29b-41d4-a716-446655440000 |
      | 850e8400-e29b-41d4-a716-446655440000 | 950e8400-e29b-41d4-a716-446655440000 | 450e8400-e29b-41d4-a716-446655440000 |

  Scenario Outline: Fail to download task files due to missing user
    And source exist with id "<sourceId>" and source type "GITHUB" for userId "<userId>"
    And the user does not have access to the project with ID "<projectId>"
    And there are PENDING source downloader processes for a userid "<userId>" and sourceId "<sourceId>" and projectId "<projectId>"
    But the corresponding user with userId "<userId>" does not exist
    When the scheduler downloads the task files
    Then an error "No user found with ID: " should be logged for id "<userId>"

    And the state of the downloader processes should be set to ERROR

    Examples:
      | userId                               | sourceId                             | projectId                            |
      | 750e8400-e29b-41d4-a716-446655440000 | 650e8400-e29b-41d4-a716-446655440000 | 550e8400-e29b-41d4-a716-446655440000 |
      | 850e8400-e29b-41d4-a716-446655440000 | 950e8400-e29b-41d4-a716-446655440000 | 450e8400-e29b-41d4-a716-446655440000 |

  Scenario Outline: Fail to download task files due to missing source
    Given user exist with id "<userId>"
    And project exist with id "<projectId>" and userId "<userId>" have role "ADMIN"
    And there are PENDING source downloader processes for a userid "<userId>" and sourceId "<sourceId>" and projectId "<projectId>"
    But the corresponding source with sourceId "<sourceId>" for a userid "<userId>" does not exist
    When the scheduler downloads the task files
    Then an error "No source found with ID: " should be logged for id "<sourceId>"
    And the state of the downloader processes should be set to ERROR

    Examples:
      | userId                               | sourceId                             | projectId                            |
      | 750e8400-e29b-41d4-a716-446655440000 | 650e8400-e29b-41d4-a716-446655440000 | 550e8400-e29b-41d4-a716-446655440000 |
      | 850e8400-e29b-41d4-a716-446655440000 | 950e8400-e29b-41d4-a716-446655440000 | 450e8400-e29b-41d4-a716-446655440000 |