package com.furansujin.demosprinhexagonalcucumber.infrastructure.adapters.controller.source.github;


import com.furansujin.demosprinhexagonalcucumber.domain.entities.GithubRepo;
import com.furansujin.demosprinhexagonalcucumber.domain.usecases.source.github.FetchReposGithubUseCase;
import com.furansujin.demosprinhexagonalcucumber.domain.usecases.source.github.SelectRepositoryAndCreateTaskFileGithubUseCase;
import com.furansujin.demosprinhexagonalcucumber.infrastructure.adapters.controller.source.github.payload.NameRepoSaveSourceGithubRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/source")
public class SourceGithubController {

    private final FetchReposGithubUseCase fetchReposGithubUseCase;
    private final SelectRepositoryAndCreateTaskFileGithubUseCase selectRepositoryAndCreateTaskFileGithubUseCase;


    public SourceGithubController(FetchReposGithubUseCase fetchReposGithubUseCase, SelectRepositoryAndCreateTaskFileGithubUseCase selectRepositoryAndCreateTaskFileGithubUseCase) {
        this.fetchReposGithubUseCase = fetchReposGithubUseCase;
        this.selectRepositoryAndCreateTaskFileGithubUseCase = selectRepositoryAndCreateTaskFileGithubUseCase;
    }


    @GetMapping("/github/repos")
    public ResponseEntity<List<GithubRepo>> getGithubRepos() {
        try {
            return ResponseEntity.ok(this.fetchReposGithubUseCase.execute());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/github/save")
    public ResponseEntity<?> saveGithubRepo(@RequestBody NameRepoSaveSourceGithubRequest nameRepoSaveSourceGithubRequest) {
        try {
            this.selectRepositoryAndCreateTaskFileGithubUseCase.execute(new SelectRepositoryAndCreateTaskFileGithubUseCase.SelectRepositoryGithubRequest(nameRepoSaveSourceGithubRequest.getNameRepo(),
                    nameRepoSaveSourceGithubRequest.getProjectId()));
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
