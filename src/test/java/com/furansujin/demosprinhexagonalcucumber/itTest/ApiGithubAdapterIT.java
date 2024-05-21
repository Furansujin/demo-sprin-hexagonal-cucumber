//package com.furansujin.demosprinhexagonalcucumber.itTest;
//
//import com.miraisystems.getsensebackend.domain.entities.GithubRepo;
//import com.miraisystems.getsensebackend.domain.entities.GithubUser;
//import com.miraisystems.getsensebackend.domain.entities.Source;
//import com.miraisystems.getsensebackend.domain.entities.User;
//import com.miraisystems.getsensebackend.domain.entities.commun.SourceType;
//import com.miraisystems.getsensebackend.infrastructure.adapters.gateway.source.api.ApiGithubAdapter;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.Test;
//
//import java.io.File;
//import java.net.URI;
//import java.net.http.HttpClient;
//import java.net.http.HttpRequest;
//import java.net.http.HttpResponse;
//import java.util.Base64;
//import java.util.List;
//import java.util.Optional;
//import java.util.Properties;
//import java.util.UUID;
//import java.util.stream.Stream;
//
//import static org.junit.jupiter.api.Assertions.assertFalse;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//public class ApiGithubAdapterIT {
//
//    private static ApiGithubAdapter apiGithubAdapter;
//
//    private static String githubClientId;
//    private static String githubClientSecret;
//    private static String redirectUri;
//
//    @BeforeAll
//    public static void loadProperties() throws Exception {
//
//        Properties props = new Properties();
//        props.load(ApiGithubAdapterIT.class.getResourceAsStream("/application.yml"));
//        githubClientId = props.getProperty("github.clientId");
//        githubClientSecret = props.getProperty("github.clientSecret");
//        redirectUri = props.getProperty("github.redirectUri");
//
//        apiGithubAdapter = new ApiGithubAdapter(githubClientId, githubClientSecret, redirectUri);
//
//    }
//    public String getGithubCode() throws Exception {
//        HttpClient client = HttpClient.newHttpClient();
//
//        // Encoder en base64 le client ID et le client secret
//        String basicAuth = githubClientId + ":" + githubClientSecret;
//        byte[] encodedCredentiels = Base64.getEncoder().encode(basicAuth.getBytes());
//        String encodedString = new String(encodedCredentiels);
//
//        // Créer la requête HTTP
//        HttpRequest request = HttpRequest.newBuilder()
//                .uri(new URI("https://github.com/login/oauth/authorize"))
//                .POST(HttpRequest.BodyPublishers.ofString(
//                        "client_id=" + githubClientId))
//                .header("Authorization", "Basic " + encodedString)
//                .build();
//
//        // Envoyer la requête et récupérer la réponse
//        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
//
//        // Récupérer le code OAuth dans la réponse
//        return response.body()
//                .split("code=")[1]
//                .split("&")[0];
//    }
//    @Test
//    public void testExchangeCodeForAccessToken() {
//
//        // Générer un code OAuth de test
//        getAccesToken();
//
//    }
//
//
//    @Test
//    public void testFetchUserRepositories() {
//
//        String accesToken = getAccesToken();
//
//        User user = new User();
//        user.setUsername("john");
//        Source source = new Source(SourceType.GITHUB, accesToken, UUID.randomUUID(), "john_github");
//
//        List<GithubRepo> repos = apiGithubAdapter.fetchUserRepositories(user, source);
//
//        assertNotNull(repos);
//        assertFalse(repos.isEmpty());
//
//        GithubRepo repo = repos.get(0);
//
//        assertNotNull(repo.getName());
//    }
//
//    private String getAccesToken() {
//        // Générer un code OAuth de test
//        String code = "test1234";
//
//        // Appeler la méthode à tester
//        Optional<String> accessTokenOptional =
//                apiGithubAdapter.exchangeCodeForAccessToken(code);
//
//        // Vérifier le résultat
//        assertTrue(accessTokenOptional.isPresent());
//
//        String accessToken = accessTokenOptional.get();
//
//        assertNotNull(accessToken);
//        assertFalse(accessToken.isEmpty());
//        assertTrue(accessToken.matches("^[a-zA-Z0-9\\-._~]+[=]{0,2}$"));
//
//        return  accessToken;
//    }
//
//
//    @Test
//    public void testDownloadRepository() throws Exception {
//
//        String accesToken = getAccesToken();
//
//        Source source = new Source(SourceType.GITHUB, "access_token", UUID.randomUUID(), "john_github");
//        String repoName = "test-repo";
//
//        Stream<File> files = apiGithubAdapter.downloadRepository(source, repoName);
//
//        assertNotNull(files);
//        Optional<File> optionalFile = files.findFirst();
//
//        assertTrue(optionalFile.isPresent());
//
//        File file = optionalFile.get();
//
//        assertTrue(file.exists());
//        assertTrue(file.length() > 0);
//
//    }
//    @Test
//    public void testUseAccessTokenForInformation() {
//        String accessToken = "valid_access_token";
//
//        Optional<GithubUser> optionalUser = apiGithubAdapter.useAccessTokenForInformation(accessToken);
//
//        assertTrue(optionalUser.isPresent());
//
//        GithubUser user = optionalUser.get();
//
//        assertNotNull(user.getLogin());
//        assertNotNull(user.getName());
//        assertNotNull(user.getEmail());
//    }
//
//
//
//}
