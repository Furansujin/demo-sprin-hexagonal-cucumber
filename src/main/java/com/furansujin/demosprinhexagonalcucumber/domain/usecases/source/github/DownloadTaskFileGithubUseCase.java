package com.furansujin.demosprinhexagonalcucumber.domain.usecases.source.github;

import com.furansujin.demosprinhexagonalcucumber.domain.entities.OriginalRessource;
import com.furansujin.demosprinhexagonalcucumber.domain.entities.Project;
import com.furansujin.demosprinhexagonalcucumber.domain.entities.Source;
import com.furansujin.demosprinhexagonalcucumber.domain.entities.SourceDownloaderProcess;
import com.furansujin.demosprinhexagonalcucumber.domain.entities.User;
import com.furansujin.demosprinhexagonalcucumber.domain.entities.commun.FileType;
import com.furansujin.demosprinhexagonalcucumber.domain.entities.commun.SourceType;
import com.furansujin.demosprinhexagonalcucumber.domain.entities.commun.StateDownloaderProcess;
import com.furansujin.demosprinhexagonalcucumber.domain.exception.ProjectNotFoundException;
import com.furansujin.demosprinhexagonalcucumber.domain.exception.SourceNotFoundException;
import com.furansujin.demosprinhexagonalcucumber.domain.exception.UserNotFoundException;
import com.furansujin.demosprinhexagonalcucumber.domain.gateways.sources.GithubGateway;
import com.furansujin.demosprinhexagonalcucumber.domain.persistence.OriginalRessourcePersistencePort;
import com.furansujin.demosprinhexagonalcucumber.domain.persistence.ProjectPersistencePort;
import com.furansujin.demosprinhexagonalcucumber.domain.persistence.SourceDownloaderPersistencePort;
import com.furansujin.demosprinhexagonalcucumber.domain.persistence.SourcePersistencePort;
import com.furansujin.demosprinhexagonalcucumber.domain.persistence.UserPersistencePort;
import com.furansujin.demosprinhexagonalcucumber.domain.usecases.UseCaseVoidArgumentless;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class DownloadTaskFileGithubUseCase implements UseCaseVoidArgumentless {



    private final SourceDownloaderPersistencePort sourceDownloaderPersistencePort;

    private final GithubGateway githubGateway;
    private final UserPersistencePort userPersistencePort;
    private final ProjectPersistencePort projectPersistencePort;
    private final SourcePersistencePort sourcePersistencePort;
    private final OriginalRessourcePersistencePort originalRessourcePersistencePort;


    @Value("${batch.resource.saveBatchSize:20}")
    private int batchSize;


    public DownloadTaskFileGithubUseCase(SourceDownloaderPersistencePort sourceDownloaderPersistencePort, GithubGateway githubGateway,
                                         UserPersistencePort userPersistencePort, ProjectPersistencePort projectPersistencePort,
                                         SourcePersistencePort sourcePersistencePort, OriginalRessourcePersistencePort originalRessourcePersistencePort) {
        this.sourceDownloaderPersistencePort = sourceDownloaderPersistencePort;
        this.githubGateway = githubGateway;
        this.userPersistencePort = userPersistencePort;
        this.projectPersistencePort = projectPersistencePort;
        this.originalRessourcePersistencePort = originalRessourcePersistencePort;
        this.sourcePersistencePort = sourcePersistencePort;
    }
    @Override
    public void execute() throws IOException {
        PageRequest pageRequest = PageRequest.of(0, 2, Sort.by(Sort.Order.asc("createDate"))); // Tri par createDate du plus ancien au plus récent
        Page<SourceDownloaderProcess> pageOfSourceDownloaderProcess = this.sourceDownloaderPersistencePort.findByStateDownloaderProcess(StateDownloaderProcess.PENDING, pageRequest);

        for (SourceDownloaderProcess sourceDownloaderProcess : pageOfSourceDownloaderProcess.getContent()) {
            try {
                User user = this.userPersistencePort.findById(sourceDownloaderProcess.getUserId()).orElseThrow(() -> new UserNotFoundException(sourceDownloaderProcess.getUserId()));
                Project project = this.projectPersistencePort.findByIdAndUserId(sourceDownloaderProcess.getProjectId(), sourceDownloaderProcess.getUserId()).orElseThrow(() -> new ProjectNotFoundException(sourceDownloaderProcess.getProjectId()));
                Source source = this.sourcePersistencePort.findByIdAndUserId(sourceDownloaderProcess.getSourceId(), sourceDownloaderProcess.getUserId()).orElseThrow(() -> new SourceNotFoundException(sourceDownloaderProcess.getSourceId()));

                List<OriginalRessource> originalRessources = this.downloadTaskFileGithub(sourceDownloaderProcess, project, user, source);

//                project.setRessources(originalRessources);
//
//                this.projectPersistencePort.save(project);
                sourceDownloaderProcess.setStateDownloader(StateDownloaderProcess.DOWNLOADED);
                this.sourceDownloaderPersistencePort.save(sourceDownloaderProcess);
            } catch (Exception e) {
                sourceDownloaderProcess.setStateDownloader(StateDownloaderProcess.ERROR);
                sourceDownloaderProcess.setErrorMessage(e.getMessage());
                this.sourceDownloaderPersistencePort.save(sourceDownloaderProcess);
                 throw e;
            }
        }



    }
    private List<OriginalRessource> downloadTaskFileGithub(SourceDownloaderProcess sourceDownloaderProcess, Project project, User user, Source source) throws IOException {
        Stream<File> fileStream = this.githubGateway.downloadRepository(source, sourceDownloaderProcess.getRelativePath());

        List<OriginalRessource> allResources = new ArrayList<>();
        List<OriginalRessource> batch = new ArrayList<>();

        fileStream.filter(file -> file.length() <= 1_048_576) // Filtrer les fichiers de taille inférieure ou égale à 1 MB
                //.filter(this::isTextFile) // Filtrer uniquement les fichiers texte
                .forEach(file -> {
                    try {
                        String content = new String(Files.readAllBytes(file.toPath()), StandardCharsets.UTF_8);
                        FileType fileType = getFileType(file);
                        if (fileType != null) {
                            OriginalRessource resource = new OriginalRessource(project.getId(), content, file.getName(), SourceType.GITHUB, fileType, user.getId());
                            batch.add(resource);
                            if (batch.size() == batchSize) {
                                allResources.addAll(originalRessourcePersistencePort.saveAll(batch));
                                batch.clear();
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });

        if (!batch.isEmpty()) {
            allResources.addAll(originalRessourcePersistencePort.saveAll(batch));// Sauvegarder le dernier lot si non vide
        }

        return allResources;
    }
//    private List<OriginalRessource> downloadTaskFileGithub(SourceDownloaderProcess sourceDownloaderProcess, Project project, User user, Source source) throws IOException {
//
//
//
//        Stream<File> fileStream = this.githubGateway.downloadRepository(source, sourceDownloaderProcess.getRelativePath());
//
//
//         fileStream.filter(file -> file.length() <= 1_048_576) // Filter files that are less than or equal to 1 MB
////                .filter(this::isTextFile) // Filter text files only
//                .map(file -> {
//                    try {
//                        String content = new String(Files.readAllBytes(file.toPath()), StandardCharsets.UTF_8); // Assuming UTF-8 encoding for the file
//                        FileType fileType = getFileType(file);
//                        if (fileType != null) {
//                            OriginalRessource resource = new OriginalRessource(content, file.getName(), SourceType.GITHUB, fileType);
//                            // Do something with the OriginalRessource object, e.g., save to database or process further
//                            return resource; // or process the resource here
//                        }
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                    return null;
//                })
//                .filter(Objects::nonNull).toList();
//    }


//    private boolean isTextFile(File file) throws IOException {
//        byte[] bytes = Files.readNBytes(file.toPath(), 1024); // Read the first 1024 bytes
//        for (byte b : bytes) {
//            if (b < 0x20 && b != 0x09 && b != 0x0A && b != 0x0D) { // Check for non-printable characters
//                return false;
//            }
//        }
//        return true;
//    }

    private FileType getFileType(File file) {
        String extension = "";
        int index = file.getName().lastIndexOf('.');
        if (index > 0) {
            extension = file.getName().substring(index);
        }
        try {
            return FileType.fromExtension(extension);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
