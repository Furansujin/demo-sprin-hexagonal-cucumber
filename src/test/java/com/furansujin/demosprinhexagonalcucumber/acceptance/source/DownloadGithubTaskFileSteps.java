package com.furansujin.demosprinhexagonalcucumber.acceptance.source;

import com.furansujin.demosprinhexagonalcucumber.domain.entities.Project;
import com.furansujin.demosprinhexagonalcucumber.domain.entities.Source;
import com.furansujin.demosprinhexagonalcucumber.domain.entities.SourceDownloaderProcess;
import com.furansujin.demosprinhexagonalcucumber.domain.entities.User;
import com.furansujin.demosprinhexagonalcucumber.domain.entities.commun.StateDownloaderProcess;
import com.furansujin.demosprinhexagonalcucumber.domain.gateways.sources.GithubGateway;
import com.furansujin.demosprinhexagonalcucumber.domain.persistence.OriginalRessourcePersistencePort;
import com.furansujin.demosprinhexagonalcucumber.domain.persistence.ProjectPersistencePort;
import com.furansujin.demosprinhexagonalcucumber.domain.persistence.SourceDownloaderPersistencePort;
import com.furansujin.demosprinhexagonalcucumber.domain.persistence.SourcePersistencePort;
import com.furansujin.demosprinhexagonalcucumber.domain.persistence.UserPersistencePort;
import com.furansujin.demosprinhexagonalcucumber.domain.usecases.source.github.DownloadTaskFileGithubUseCase;
import io.cucumber.java.en.And;
import io.cucumber.java.en.But;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class DownloadGithubTaskFileSteps {

    private final SourceDownloaderPersistencePort sourceDownloaderPersistencePort;
    private final UserPersistencePort userPersistencePort;
    private final SourcePersistencePort sourcePersistencePort;
    private final ProjectPersistencePort projectPersistencePort;
    private final DownloadTaskFileGithubUseCase downloadTaskFileGithubUseCase;
    private final OriginalRessourcePersistencePort originalRessourcePersistencePort;
    private final GithubGateway githubGateway;

private String messageError;

    public DownloadGithubTaskFileSteps(SourceDownloaderPersistencePort sourceDownloaderPersistencePort, UserPersistencePort userPersistencePort,
                                       SourcePersistencePort sourcePersistencePort, ProjectPersistencePort projectPersistencePort, GithubGateway githubGateway, OriginalRessourcePersistencePort originalRessourcePersistencePort) {
        this.sourceDownloaderPersistencePort = sourceDownloaderPersistencePort;
        this.userPersistencePort = userPersistencePort;
        this.sourcePersistencePort = sourcePersistencePort;
        this.projectPersistencePort = projectPersistencePort;
        this.githubGateway = githubGateway;
        this.originalRessourcePersistencePort = originalRessourcePersistencePort;
        this.downloadTaskFileGithubUseCase = new DownloadTaskFileGithubUseCase(sourceDownloaderPersistencePort, githubGateway, userPersistencePort, projectPersistencePort, sourcePersistencePort, originalRessourcePersistencePort);
        this.messageError = "";
    }

    @Given("there are PENDING source downloader processes")
    public void there_are_PENDING_source_downloader_processes() {
//        SourceDownloaderProcess sourceDownloaderProcess = SourceDownloaderProcess.builder()
//                .sourceId()
//                .build();
        throw new io.cucumber.java.PendingException();
    }


    @Then("the task files should be downloaded and stored")
    public void theTaskFilesShouldBeDownloadedAndStored() {
        PageRequest pageRequest = PageRequest.of(0, 100);

        Page<SourceDownloaderProcess> byStateDownloaderProcess1 = this.sourceDownloaderPersistencePort.findAll( pageRequest);
        assertTrue(byStateDownloaderProcess1.getContent().size() > 0);
    }

    @And("the state of the downloader processes should be set to DOWNLOADED")
    public void theStateOfTheDownloaderProcessesShouldBeSetToDOWNLOADED() {
        PageRequest pageRequest = PageRequest.of(0, 100);
        Page<SourceDownloaderProcess> byStateDownloaderProcess = this.sourceDownloaderPersistencePort.findByStateDownloaderProcess(StateDownloaderProcess.DOWNLOADED, pageRequest);
        assertTrue(byStateDownloaderProcess.getContent().size() > 0);
    }



    @But("the corresponding project with projectId {string} for a userid {string} does not exist")
    public void theCorrespondingProjectWithProjectIdForAUseridDoesNotExist(String idProject, String userid) {
        Optional<Project> byIdAndUserId = this.projectPersistencePort.findByIdAndUserId(UUID.fromString(idProject), UUID.fromString(userid));
        assertTrue(byIdAndUserId.isEmpty());
    }



    @Then("an error {string} should be logged for id {string}")
    public void anErrorShouldBeLoggedForProjectId(String message, String id) {

        assertTrue(this.messageError.equals(message + id));
    }

    @And("the state of the downloader processes should be set to ERROR")
    public void theStateOfTheDownloaderProcessesShouldBeSetToERROR() {
        PageRequest pageRequest = PageRequest.of(0, 100);
        Page<SourceDownloaderProcess> all = this.sourceDownloaderPersistencePort.findAll(pageRequest);

        assertTrue(all.getContent().stream().allMatch(s -> s.getStateDownloader().equals(StateDownloaderProcess.ERROR)));

    }


    @When("the scheduler downloads the task files")
    public void theSchedulerDownloadsTheTaskFiles()  {

        try {
            this.downloadTaskFileGithubUseCase.execute();
        } catch (Exception e) {
            System.out.println("Caught exception: " + e.getMessage());
            this.messageError = e.getMessage();
        }


    }






    @And("there are PENDING source downloader processes for a userid {string} and sourceId {string} and projectId {string}")
    public void thereArePENDINGSourceDownloaderProcessesForAUseridAndSourceIdAndProjectId(String userId, String sourceId, String projectId) {

        SourceDownloaderProcess sourceDownloaderProcess = SourceDownloaderProcess.builder()
                .sourceId(UUID.fromString(sourceId))
                .projectId(UUID.fromString(projectId))
                .userId(UUID.fromString(userId))
                .stateDownloader(StateDownloaderProcess.PENDING)
                .build();

        this.sourceDownloaderPersistencePort.save(sourceDownloaderProcess);
        PageRequest pageRequest = PageRequest.of(0, 100);
        Page<SourceDownloaderProcess> byStateDownloaderProcess = this.sourceDownloaderPersistencePort.findByStateDownloaderProcess(StateDownloaderProcess.PENDING, pageRequest);
        assertTrue(byStateDownloaderProcess.getContent().size() > 0);

    }




    @But("the corresponding user with userId {string} does not exist")
    public void theCorrespondingUserWithUserIdDoesNotExist(String userId) {
        Optional<User> byId = this.userPersistencePort.findById(UUID.fromString(userId));
        assertTrue(byId.isEmpty());
    }

    @But("the corresponding source with sourceId {string} for a userid {string} does not exist")
    public void theCorrespondingSourceWithSourceIdForAUseridDoesNotExist(String isSource, String userId) {
        Optional<Source> byIdAndUserId = this.sourcePersistencePort.findByIdAndUserId(UUID.fromString(isSource), UUID.fromString(userId));
        assertTrue(byIdAndUserId.isEmpty());
    }
}
