Feature: Get all messages for a conversation


  Scenario Outline: User successfully retrieves all messages from a conversation
    Given user with ID "<user_id>" is logged in
    And project exist with id "<project_id>" and userId "<user_id>" have role "MEMBER"
    And conversation exist with "<conversation_id>" and project id "<project_id>" and userId "<user_id>"
    And message exist with id "<message_id>" and conversation id "<conversation_id>"
    When the user requests to see all messages for the conversation with ID "<conversation_id>"
    Then all messages for the conversation with ID "<conversation_id>" should be returned

    Examples:
      | user_id                              | conversation_id                     | project_id                            | message_id                            |
      | 8f233522-5e0c-11ee-8c99-0242ac120002 | bbb24b16-3333-4444-5555-c2c2d2d2d2d2 |  9f233522-5e0c-11ee-8c99-0242ac120002 | 5f233522-5e0c-11ee-8c99-0242ac120002 |

  Scenario Outline: User tries to retrieve messages from a conversation they do not have access to
    Given user with ID "<user_id>" is logged in
    And project exist with id "<project_id>" and userId "<user_id>" have role "MEMBER"
    And conversation exist with "<conversation_id>" and project id "<project_id>"
    And message exist with id "<message_id>" and conversation id "<conversation_id>"
    When the user requests to see all messages for the conversation with ID "<conversation_id>"
    Then an error message "Conversation not found" should be returned for conversation ID "<conversation_id>"

    Examples:
      | user_id                              | conversation_id                     |  project_id                            | message_id                            |
      | 8f233522-5e0c-11ee-8c99-0242ac120002 | eee24b16-7777-8888-9999-a2a2b2b2b2b2 | 9f233522-5e0c-11ee-8c99-0242ac120002 | 5f233522-5e0c-11ee-8c99-0242ac120002 |

  Scenario Outline: User is not authenticated when trying to retrieve messages
    Given user exist with id "<user_id>"
    And  the user with ID "<user_id>" is not logged in
    When the user requests to see all messages for the conversation with ID "<conversation_id>"
    Then an error message "No authenticate user" should be returned when requests to see all messages

    Examples:
      | user_id                              | conversation_id                     |
      | 8f233522-5e0c-11ee-8c99-0242ac120002 | eee24b16-7777-8888-9999-a2a2b2b2b2b2 | 
