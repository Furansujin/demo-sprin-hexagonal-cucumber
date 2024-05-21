Feature: Process Original Resources

  Scenario: Successfully process PENDING resources
    Given there are PENDING original resources in the database
    When I execute the ProcessOriginalRessourceUseCase
    Then each resource should be segmented based on the base segment size
    And each segment should be vectorized
    And the original resource status should be updated to PROCESSED

  Scenario: There are no PENDING resources
    Given there are no PENDING original resources in the database
    When I execute the ProcessOriginalRessourceUseCase
    Then no new split resources should be created
    And no original resource status should be updated

  Scenario: Error occurs while vectorizing a segment
    Given there are PENDING original resources in the database
    And an error occurs during segment vectorization
    When I execute the ProcessOriginalRessourceUseCase
    Then the problematic resource should remain in PENDING status
    And an error log should be generated

  Scenario: Error occurs while saving split resources
    Given there are PENDING original resources in the database
    And an error occurs while saving split resources
    When I execute the ProcessOriginalRessourceUseCase
    Then the original resource status should not be updated to PROCESSED
    And an error log should be generated
#
#  Scenario: Text segmentation results in more segments than expected
#    Given there are PENDING original resources in the database
#    And the text of a resource is such that its segmentation results in more segments than expected
#    When I execute the ProcessOriginalRessourceUseCase
#    Then the original resource should be processed normally
#    And extra segments should be handled appropriately
#    And the original resource status should be updated to PROCESSED

#  Scenario: Text segmentation results in fewer segments than expected
#    Given there are PENDING original resources in the database
#    And the text of a resource is such that its segmentation results in fewer segments than expected
#    When I execute the ProcessOriginalRessourceUseCase
#    Then the original resource should be processed normally
#    And missing segments should be handled appropriately
#    And the original resource status should be updated to PROCESSED

#  Scenario Outline: Failure in File Processing
#    Given the system attempts to download files from the repository
#    When there is an error while processing the file "<file_name>"
#    Then the system should log the error
#    And notify the admin with the message "<notification_message>"
#
#    Examples:
#      | file_name | notification_message                |
#      | main.js   | Error processing the file main.js.  |
#      | guide.py  | Error processing the file guide.py. |
#
#
#
#  Scenario Outline: Failure in Vectorizing Resources
#    Given the system processes the file and splits it into resources
#    When there is an error while vectorizing the split resource from the file "<file_name>"
#    Then the system should log the error
#    And notify the admin with the message "<notification_message>"
#
#    Examples:
#      | file_name | notification_message                       |
#      | main.js   | Error vectorizing the content of main.js.  |
#      | guide.py  | Error vectorizing the content of guide.py. |
#
#
