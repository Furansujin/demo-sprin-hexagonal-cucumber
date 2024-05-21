package com.furansujin.demosprinhexagonalcucumber.infrastructure.adapters.gateway.source.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.furansujin.demosprinhexagonalcucumber.domain.entities.GithubRepo;
import com.furansujin.demosprinhexagonalcucumber.domain.entities.GithubUser;
import com.furansujin.demosprinhexagonalcucumber.domain.entities.Source;
import com.furansujin.demosprinhexagonalcucumber.domain.entities.User;
import com.furansujin.demosprinhexagonalcucumber.domain.gateways.sources.GithubGateway;
import com.furansujin.demosprinhexagonalcucumber.domain.usecases.source.utils.IgnoreNode;
import lombok.Setter;
import org.apache.commons.io.IOUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Enumeration;
import java.util.UUID;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;


import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@Setter
public class ApiGithubAdapter implements GithubGateway {

    @Value("${github.clientId}")
    private String githubClientId;

    @Value("${github.clientSecret}")
    private String githubClientSecret;

    @Value("${github.redirectUri}")
    private String githubRedirectUri;
    @Value("${batch.storage.path:C:\\tmp}")
    private String storagePath;
    public ApiGithubAdapter() {
    }

    @Override
    public List<GithubRepo> fetchUserRepositories(User user, Source source) {

        RestTemplate restTemplate = new RestTemplate();

        // Définir l'en-tête d'authentification
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(source.getAccessToken());

        // Appel API
        ResponseEntity<GithubRepo[]> response = restTemplate.exchange(
                "https://api.github.com/user/repos", HttpMethod.GET,
                new HttpEntity<>(headers), GithubRepo[].class);

        // Mapper la réponse
        return Arrays.asList(response.getBody());
    }

    @Override
    public Stream<File> downloadRepository(Source source, String nameRepo) throws IOException {

        RestTemplate restTemplate = new RestTemplate();

        String defaultBranchName = getDefaultBranchName(source, nameRepo);

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(source.getAccessToken());
        headers.add("Accept", "application/vnd.github+json");
        headers.add("X-GitHub-Api-Version", "2022-11-28");

        String url = String.format("https://api.github.com/repos/%s/%s/zipball/%s",
                source.getUserName(), nameRepo, defaultBranchName);

        ResponseEntity<byte[]> response = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(headers), byte[].class);

        byte[] zipFile = response.getBody();

        String repoDir = storagePath + "/"+ source.getUserName() + "/" + UUID.randomUUID() ;
        Path repoDirPath = Paths.get(repoDir);

        Path zipPath = Paths.get(repoDir, "repo.zip");

        Files.createDirectories(zipPath.getParent());

        // Ecriture du zip dans le répertoire dédié
        Files.write(zipPath, zipFile);

        // Décompression dans le répertoire dédié
        unzipFiles(zipPath.toFile(), repoDirPath.toFile());
         IgnoreNode ignoreNode = new IgnoreNode();
        try (InputStream gitIgnoreStream = getClass().getResourceAsStream("/git/.fileignore")) {
            ignoreNode.parse(gitIgnoreStream);
        }




            // Filtrer les dossiers et fichiers selon .fileignore
            return Files.walk(repoDirPath, FileVisitOption.FOLLOW_LINKS)
                    .filter(file -> !ignoreNode.isIgnored(file, repoDirPath))
                    .map(Path::toFile)
                    .filter(file -> !file.isDirectory()) // Filtre les dossiers
                    .filter(file -> file.length() < 1024 * 1024); // Filtre les fichiers par taille

    }


//    protected void readZip(ZipInputStream zis, IFileStore destination, IgnoreNode ignoreNode) throws IOException {
//
//        ZipEntry entry = zis.getNextEntry();
//
//        while (entry != null) {
//            String filePath = entry.getName();
//
//            // Vérifie si le fichier est ignoré
//            if (!ignoreNode.isIgnored(filePath)) {
//                IFileStore fileStore = destination.getChild(filePath);
//
//                if (entry.isDirectory()) {
//                    // Crée le répertoire s'il n'est pas ignoré
//                    fileStore.mkdir(EFS.NONE, null);
//                } else {
//                    // Crée le fichier s'il n'est pas ignoré
//                    OutputStream os = fileStore.openOutputStream(EFS.NONE, null);
//                    try {
//                        IOUtilities.pipe(zis, os, false, true);
//                    } finally {
//                        os.close();
//                    }
//                }
//            }
//
//            zis.closeEntry();
//            entry = zis.getNextEntry();
//        }
//    }

    private void unzipFiles(File zip, File destDir) throws IOException {

        ZipFile zipFile = new ZipFile(zip);

        destDir.mkdirs();

        Enumeration<? extends ZipEntry> entries = zipFile.entries();

        while(entries.hasMoreElements()) {
            ZipEntry entry = entries.nextElement();

            File entryDestination = new File(destDir, entry.getName());

            if (entry.isDirectory()) {
                entryDestination.mkdirs();
            } else {
                entryDestination.getParentFile().mkdirs();
                InputStream in = zipFile.getInputStream(entry);

                OutputStream out = new FileOutputStream(entryDestination);
                IOUtils.copy(in, out);
                IOUtils.closeQuietly(in);
                out.close();
            }

        }
        zipFile.close();
        zip.delete();
    }
//    }
//    @Override
//    public Stream<File> downloadRepository(Source source, String nameRepo) throws IOException {
//        RestTemplate restTemplate = new RestTemplate();
//
//        String defaultBranchName = getDefaultBranchName(source, nameRepo);
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setBearerAuth(source.getAccessToken());
//        headers.add("Accept", "application/vnd.github+json");
//        headers.add("X-GitHub-Api-Version", "2022-11-28");
//
//        String url = String.format("https://api.github.com/repos/%s/%s/zipball/%s", source.getUserName(), nameRepo, defaultBranchName);
//
//        ResponseEntity<byte[]> response = restTemplate.exchange(
//                url, HttpMethod.GET, new HttpEntity<>(headers), byte[].class);
//
//        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
//            File tempFile = new File(Paths.get(storagePath, nameRepo ).toString());
//
//            try (FileOutputStream fos = new FileOutputStream(tempFile)) {
//                fos.write(response.getBody());
//            }
//
//            return Stream.of(tempFile);
//        }
//
//        return Stream.empty();
//    }
//    public Stream<File> downloadRepository(Source source, String nameRepo) throws IOException {
//        // Créer une instance de RestTemplate
//        RestTemplate restTemplate = new RestTemplate();
//
//        String defaultBranchName = getDefaultBranchName(source, nameRepo);
//        // Définir l'en-tête d'authentification
//        HttpHeaders headers = new HttpHeaders();
//        headers.setBearerAuth(source.getAccessToken());
//        headers.add("Accept", "application/vnd.github+json");
//        headers.add("X-GitHub-Api-Version", "2022-11-28");
////        headers.add("X-GitHub-Api-Version", "2022-11-28");
//
//        // Construire l'URL pour télécharger le dépôt
////        https://github.com/Furansujin/AngFireStoreNgrx/archive/refs/heads/master.zip
//        String url = String.format("https://api.github.com/repos/%s/%s/zipball/%s", source.getUserName(), nameRepo, defaultBranchName);
//
//
//        // Effectuer la requête
//        ResponseEntity<byte[]> response = restTemplate.exchange(
//                url, HttpMethod.GET, new HttpEntity<>(headers), byte[].class);
//
//        // Vérifier si la réponse est valide
//        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
//            // Créer un fichier temporaire pour stocker le dépôt téléchargé
//            File tempFile = File.createTempFile(nameRepo, ".zip");
//
//            // Écrire les données dans le fichier
//            try (FileOutputStream fos = new FileOutputStream(tempFile)) {
//                fos.write(response.getBody());
//            }
//
//            // Retourner le fichier dans un Stream
//            return Stream.of(tempFile);
//        }
//
//        // Retourner un Stream vide en cas d'échec
//        return Stream.empty();
//    }

    public String getDefaultBranchName(Source source, String repoName) throws IOException {
        RestTemplate restTemplate = new RestTemplate();

        // Définir l'en-tête d'authentification
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(source.getAccessToken());
        headers.add("Accept", "application/vnd.github+json");
        headers.add("X-GitHub-Api-Version", "2022-11-28");
        // Construire l'URL pour obtenir les informations du dépôt
        String repoInfoUrl = String.format("https://api.github.com/repos/%s/%s", source.getUserName(), repoName);

        // Effectuer la requête pour obtenir les informations du dépôt
        ResponseEntity<String> repoInfoResponse = restTemplate.exchange(
                repoInfoUrl, HttpMethod.GET, new HttpEntity<>(headers), String.class);

        if (repoInfoResponse.getStatusCode().is2xxSuccessful() && repoInfoResponse.getBody() != null) {
            // Parser la réponse JSON pour obtenir la branche par défaut
            JSONObject repoInfoJson = new JSONObject(repoInfoResponse.getBody());
            return repoInfoJson.optString("default_branch");
        } else {
            // Gérer les erreurs ou retourner null si la requête échoue
            return null;
        }
    }

    @Override
    public Optional<String> exchangeCodeForAccessToken(String code) {
        RestTemplate restTemplate = new RestTemplate();

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("client_id", this.githubClientId);
        map.add("client_secret", this.githubClientSecret);
        map.add("code", code);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, new HttpHeaders());
        ResponseEntity<String> response = restTemplate.postForEntity("https://github.com/login/oauth/access_token", request, String.class);

        String responseBody = response.getBody();

        if (responseBody != null) {
            String[] parameters = responseBody.split("&");
            for (String parameter : parameters) {
                String[] keyValue = parameter.split("=");
                if (keyValue[0].equals("access_token")) {
                    return keyValue[1].describeConstable();
                }
            }
        }

        return Optional.empty();
    }
    public Optional<GithubUser> useAccessTokenForInformation(String accessToken) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    "https://api.github.com/user", HttpMethod.GET, entity, String.class);

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                ObjectMapper mapper = new ObjectMapper();
                GithubUser user = mapper.readValue(response.getBody(), GithubUser.class);
                return Optional.of(user);
            }
        } catch (Exception e) {
            // Gérer les exceptions (par exemple, imprimer un log d'erreur)
        }

        return Optional.empty();
    }

    public String generateGithubAuthorizeUrl() {
        return UriComponentsBuilder.fromUriString("https://github.com/login/oauth/authorize")
                .queryParam("client_id", this.githubClientId)
                .queryParam("redirect_uri", this.githubRedirectUri)
                .queryParam("scope", "user:email")
                .build()
                .toUriString();
    }

}
