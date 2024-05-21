package com.furansujin.demosprinhexagonalcucumber.domain.gateways.sources;

import com.furansujin.demosprinhexagonalcucumber.domain.entities.GithubRepo;
import com.furansujin.demosprinhexagonalcucumber.domain.entities.GithubUser;
import com.furansujin.demosprinhexagonalcucumber.domain.entities.Source;
import com.furansujin.demosprinhexagonalcucumber.domain.entities.User;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public interface GithubGateway {

    public List<GithubRepo> fetchUserRepositories(User user, Source source);


    public Stream<File> downloadRepository( Source source, String nameRepo) throws IOException;

    public Optional<String> exchangeCodeForAccessToken(String code);

    public  Optional<GithubUser> useAccessTokenForInformation(String accessToken);


    public String generateGithubAuthorizeUrl();
}
