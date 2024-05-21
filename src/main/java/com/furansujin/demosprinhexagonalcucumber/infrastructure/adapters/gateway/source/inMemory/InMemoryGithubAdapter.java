package com.furansujin.demosprinhexagonalcucumber.infrastructure.adapters.gateway.source.inMemory;

import com.furansujin.demosprinhexagonalcucumber.domain.entities.GithubRepo;
import com.furansujin.demosprinhexagonalcucumber.domain.entities.GithubUser;
import com.furansujin.demosprinhexagonalcucumber.domain.entities.Source;
import com.furansujin.demosprinhexagonalcucumber.domain.entities.User;
import com.furansujin.demosprinhexagonalcucumber.domain.gateways.sources.GithubGateway;
import com.furansujin.demosprinhexagonalcucumber.infrastructure.adapters.gateway.source.util.FileUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

public class InMemoryGithubAdapter implements GithubGateway {
    List<GithubRepo> githubRepos = new ArrayList<>();
    String path = "src/test/resources/files";
    String pathOriginal = "src/test/resources/files/original";

    List<String> codesValide = new ArrayList<>();
    @Override
    public List<GithubRepo> fetchUserRepositories(User user, Source source) {
        return  githubRepos;
    }

    @Override
    public Stream<File> downloadRepository(Source source, String nameRepo) throws IOException {
        String newDirectory = path + "/" +UUID.randomUUID().toString();
        boolean b = FileUtil.deleteAndRecreateDirectory(newDirectory);
        FileUtil.copyDirectory(pathOriginal, newDirectory);
        return   FileUtil.getFilesInDirectory(newDirectory).stream();
    }



    @Override
    public Optional<String> exchangeCodeForAccessToken(String code) {
          if(codesValide.contains(code)) {
              return Optional.of("1234567890");
          }
          return Optional.empty();
    }

    @Override
    public Optional<GithubUser> useAccessTokenForInformation(String accessToken) {
        return Optional.of(new GithubUser("test","test","test"));
    }

    @Override
    public String generateGithubAuthorizeUrl() {
        return null;
    }

    public void addRepo(GithubRepo repo) {
        githubRepos.add(repo);
    }

    public void addCode(String codeValid) {
        this.codesValide.add(codeValid);
    }
}
