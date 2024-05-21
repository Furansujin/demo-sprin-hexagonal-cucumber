Feature: Create message

  Scenario Outline: Create a new conversation in a project with title 25 first charactere of first message
    Given user with ID "550e8400-e29b-41d4-a716-446655440000" is logged in
    And I am an admin of the project with ID "<project_id>"
    And the project with ID "<project_id>" has been linked to a GitHub repository
    When I create a new conversation with first message "<message>" in project with ID "<project_id>"
    Then the conversation should be created successfully with title "<conversation_title>" 25 first charactere of first message in project with ID "<project_id>"

    Examples:
      | project_id                           | conversation_title        | message                                                                                                                             |
      | 8f233522-5e0c-11ee-8c99-0242ac120002 | Lorem Ipsum is simply dum | Lorem Ipsum is simply dummy text of the printing and typesetting industry                                                           |
      | 9f233522-5e0c-11ee-8c99-0242ac120003 | Contrary to popular belie | Contrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a piece of classical Latin literature from 45 BC |




  Scenario Outline: Create a new conversation without linking to GitHub first
    Given user with ID "550e8400-e29b-41d4-a716-446655440000" is logged in
    And I am an admin of the project with ID "<project_id>"
    And the project with ID "<project_id>" has not been linked to a GitHub repository
    When I create a new conversation with first message "<message>" in project with ID "<project_id>"
    Then the creation should fail due to missing GitHub linkage in project with ID "<project_id>"

    Examples:
      | project_id                           | message       |
      | 8f233522-5e0c-11ee-8c99-0242ac120002 | ConversationA |
      | 9f233522-5e0c-11ee-8c99-0242ac120003 | ConversationB |


  Scenario Outline: Sending a message and enriching with vectorized resources
    Given user with ID "550e8400-e29b-41d4-a716-446655440000" is logged in
    And project exist with id "<project_id>" and userId "550e8400-e29b-41d4-a716-446655440000" have role "MEMBER"
    And In project with ID "<project_id>" you have "<similar_ressource>"
    When I send a message with content "<original_message>" in the conversation in the project with ID "<project_id>"
    Then the system should perform an embedding on the message in the conversation in the project with ID "<project_id>"
    And enrich the message with retrieved resources to form "<similar_ressource>"
    And check if ChatGPT answer

    Examples:
      | project_id                           | conversation_id                      | original_message | similar_ressource                  |
      | 750e8400-e29b-41d4-a716-446655440000 | 650e8400-e29b-41d4-a716-446655440000 | What's a loop?   | Similar resource: For loop in JS   |
      | 850e8400-e29b-41d4-a716-446655440000 | 950e8400-e29b-41d4-a716-446655440000 | Define function? | Similar resource: Python Functions |



  Scenario Outline: Sending a message with no similar vectorized resources
    Given user with ID "550e8400-e29b-41d4-a716-446655440000" is logged in
    And project exist with id "<project_id>" and userId "550e8400-e29b-41d4-a716-446655440000" have role "MEMBER"
    And In project with ID "<project_id>" you have "<ressource>"
    And no similar resources are found in the vector database
    When I send a message with content "<original_message>" in the conversation in the project with ID "<project_id>"
    Then the system should perform an embedding on the message in the conversation in the project with ID "<project_id>"
    Then the message remains unchanged as "<original_message>"
    And check if ChatGPT answer

    Examples:
      | project_id                           | conversation_id                      | original_message   | ressource                  |
      | 740e8400-e29b-41d4-a716-446655440000 | 651e8400-e29b-41d4-a716-446655440000 | How's the weather? | resource: For loop in JS   |
      | 450e8400-e29b-41d4-a716-446655440000 | 930e8400-e29b-41d4-a716-446655440000 | What's for lunch?  | resource: Python Functions |

  Scenario Outline: Attempt to enrich a message in an unlinked project
    Given user with ID "550e8400-e29b-41d4-a716-446655440000" is logged in
    And project exist with id "<project_id>" and userId "550e8400-e29b-41d4-a716-446655440000" have role "MEMBER"
    And the project with ID "<project_id>" has not been linked to a GitHub repository
    When I send a message with content "<original_message>" in the conversation in the project with ID "<project_id>"
    Then No conversation created

    Examples:
      | project_id                           | original_message   |
      | 740e8400-e29b-41d4-a716-446655440000 | How's the weather? |
      | 450e8400-e29b-41d4-a716-446655440000 | What's for lunch?  |

  Scenario Outline: Enriching a message with multiple similar vectorized resources
    Given user with ID "550e8400-e29b-41d4-a716-446655440000" is logged in
    And project exist with id "<project_id>" and userId "550e8400-e29b-41d4-a716-446655440000" have role "MEMBER"
    And In project with ID "<project_id>" you have "<ressource_text>"
    When I send a message with content "<original_message>" in the conversation in the project with ID "<project_id>"
    Then the system should perform an embedding on the message in the conversation in the project with ID "<project_id>"
    And enrich the message with retrieved resources to form "<ressource_text>"
    And check if ChatGPT answer

    Examples:
      | project_id                           | conversation_id                      | original_message        | ressource_text                |
      | 150e8400-e29b-41d4-a716-446655440000 | 250e8400-e29b-41d4-a716-446655440000 | Tell me about variables | Java; JS; Python; c++; csharp |
