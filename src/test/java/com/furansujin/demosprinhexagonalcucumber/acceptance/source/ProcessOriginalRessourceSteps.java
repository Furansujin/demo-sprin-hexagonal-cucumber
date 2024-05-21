package com.furansujin.demosprinhexagonalcucumber.acceptance.source;
import com.furansujin.demosprinhexagonalcucumber.domain.entities.commun.StateProcessSource;
import com.furansujin.demosprinhexagonalcucumber.domain.gateways.authentication.AuthenticationGateway;
import com.furansujin.demosprinhexagonalcucumber.domain.gateways.languageModel.LanguageModelGateway;
import com.furansujin.demosprinhexagonalcucumber.domain.persistence.OriginalRessourcePersistencePort;
import com.furansujin.demosprinhexagonalcucumber.domain.persistence.ProjectPersistencePort;
import com.furansujin.demosprinhexagonalcucumber.domain.persistence.SourceDownloaderPersistencePort;
import com.furansujin.demosprinhexagonalcucumber.domain.persistence.SourcePersistencePort;
import com.furansujin.demosprinhexagonalcucumber.domain.persistence.SplitRessourcePersistencePort;
import com.furansujin.demosprinhexagonalcucumber.domain.usecases.source.github.ProcessOriginalRessourceUseCase;
import com.furansujin.demosprinhexagonalcucumber.infrastructure.adapters.gateway.languageModel.inMemory.InMemoryLanguageModelGateway;
import com.furansujin.demosprinhexagonalcucumber.infrastructure.adapters.persistence.inMemory.InMemoryOriginalRessourcePersistenceAdapter;
import com.furansujin.demosprinhexagonalcucumber.infrastructure.adapters.persistence.inMemory.InMemorySplitRessourcePersistenceAdapter;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.Assert.assertTrue;

public class ProcessOriginalRessourceSteps {

    private ProcessOriginalRessourceUseCase processOriginalRessourceUseCase;
    private InMemorySplitRessourcePersistenceAdapter splitRessourcePersistencePort;

    private InMemoryOriginalRessourcePersistenceAdapter originalRessourcePersistencePort;

    private InMemoryLanguageModelGateway languageModelGateway;
    private String errorMessage;

    public ProcessOriginalRessourceSteps(AuthenticationGateway authenticationGateway, SourcePersistencePort sourcePersistencePort,
                                         SourceDownloaderPersistencePort sourceDownloaderPersistencePort, OriginalRessourcePersistencePort originalRessourcePersistencePort,
                                         LanguageModelGateway languageModelGateway, SplitRessourcePersistencePort splitRessourcePersistencePort, ProjectPersistencePort projectPersistencePort) {
        this.originalRessourcePersistencePort = (InMemoryOriginalRessourcePersistenceAdapter) originalRessourcePersistencePort;
        this.splitRessourcePersistencePort = (InMemorySplitRessourcePersistenceAdapter) splitRessourcePersistencePort;
        this.languageModelGateway = (InMemoryLanguageModelGateway) languageModelGateway;
        this.processOriginalRessourceUseCase = new ProcessOriginalRessourceUseCase(authenticationGateway, projectPersistencePort, sourcePersistencePort, sourceDownloaderPersistencePort, originalRessourcePersistencePort, languageModelGateway, splitRessourcePersistencePort);
        this.errorMessage = "";
    }
    @Given("there are PENDING original resources in the database")
    public void there_are_PENDING_original_resources_in_the_database() {
        // Assuming you have a mocked database or method that fetches these resources.
        String text = "@Then(\"the creation should fail due to missing GitHub linkage in project with ID {string}\")public void theCreationShouldFailDueToMissingGitHubLinkageInProjectWithID(String idProject){User user=this.authenticationGateway.currentUser().get();assertTrue(this.messageFailed);Project project";
        String name = "somename.java";
//        OriginalRessource originalRessource = new OriginalRessource(text, name, SourceType.GITHUB, FileType.JAVA);
//        this.originalRessourcePersistencePort.save(originalRessource);

    }

    @When("I execute the ProcessOriginalRessourceUseCase")
    public void iExecuteTheProcessOriginalRessourceUseCase() {
        try {
            this.processOriginalRessourceUseCase.execute();
        } catch (Exception e) {
            this.errorMessage = e.getMessage();
        }

    }

    @Then("each resource should be segmented based on the base segment size")
    public void eachResourceShouldBeSegmentedBasedOnTheBaseSegmentSize() {

        int size = ProcessOriginalRessourceUseCase.baseSegmentSize;
        this.splitRessourcePersistencePort.findAll().forEach(splitRessource -> {
            if (splitRessource.getText().length() > size) {
                throw new RuntimeException("Segment size is greater than base segment size");
            }
        });
    }

    @And("each segment should be vectorized")
    public void eachSegmentShouldBeVectorized() {

        this.splitRessourcePersistencePort.findAll().forEach(splitRessource -> {
            if (splitRessource.getVector() == null) {
                throw new RuntimeException("Segment is not vectorized");
            }
        });
    }


    @And("the original resource status should be updated to PROCESSED")
    public void theOriginalResourceStatusShouldBeUpdatedToPROCESSED() {
        this.originalRessourcePersistencePort.findAll().forEach(originalRessource -> {
            if (originalRessource.getStateProcessSource() != StateProcessSource.PROCESSED) {
                throw new RuntimeException("Original resource status is not updated to PROCESSED");
            }
        });
    }

    @Given("there are no PENDING original resources in the database")
    public void thereAreNoPENDINGOriginalResourcesInTheDatabase() {
        String text = "@Then(\"the creation should fail due to missing GitHub linkage in project with ID {string}\")public void theCreationShouldFailDueToMissingGitHubLinkageInProjectWithID(String idProject){User user=this.authenticationGateway.currentUser().get();assertTrue(this.messageFailed);Project project";
        String name = "somename.java";
//        OriginalRessource originalRessource = new OriginalRessource(text, name, SourceType.GITHUB, FileType.JAVA);
//        originalRessource.setStateProcessSource(StateProcessSource.PROCESSED);
//        this.originalRessourcePersistencePort.save(originalRessource);
    }




    @Then("no new split resources should be created")
    public void noNewSplitResourcesShouldBeCreated() {

        assertTrue(this.splitRessourcePersistencePort.findAll().isEmpty());
    }

    @And("no original resource status should be updated")
    public void noOriginalResourceStatusShouldBeUpdated() {
       this.originalRessourcePersistencePort.findAll().forEach(originalRessource -> {
           // because one original resource is already processed
           if(originalRessource.getStateProcessSource() != StateProcessSource.PROCESSED){
               throw new RuntimeException("Original resource status is updated");
           }
       });
    }

    @And("an error occurs during segment vectorization")
    public void anErrorOccursDuringSegmentVectorization() {
        this.languageModelGateway.setMessage(null);
    }

    @Then("the problematic resource should remain in PENDING status")
    public void theProblematicResourceShouldRemainInPENDINGStatus() {
        this.originalRessourcePersistencePort.findAll().forEach(originalRessource -> {
            if(originalRessource.getStateProcessSource() != StateProcessSource.PENDING){
                throw new RuntimeException("Original resource status is updated");
            }
        });
    }

    @And("an error log should be generated")
    public void anErrorLogShouldBeGenerated() {
        assertTrue(!this.errorMessage.isEmpty());
    }

    @And("an error occurs while saving split resources")
    public void anErrorOccursWhileSavingSplitResources() {
        // null for create error when saving
        this.splitRessourcePersistencePort.setSplitRessources(null);
    }

    @Then("the original resource status should not be updated to PROCESSED")
    public void theOriginalResourceStatusShouldNotBeUpdatedToPROCESSED() {
        this.originalRessourcePersistencePort.findAll().forEach(originalRessource -> {
            if(originalRessource.getStateProcessSource() == StateProcessSource.PROCESSED){
                throw new RuntimeException("Original resource status is updated");
            }
        });
    }
 
}
