package com.furansujin.demosprinhexagonalcucumber.domain.usecases.source.github;

import com.furansujin.demosprinhexagonalcucumber.domain.entities.OriginalRessource;
import com.furansujin.demosprinhexagonalcucumber.domain.entities.SplitRessource;
import com.furansujin.demosprinhexagonalcucumber.domain.entities.commun.StateProcessSource;
import com.furansujin.demosprinhexagonalcucumber.domain.gateways.authentication.AuthenticationGateway;
import com.furansujin.demosprinhexagonalcucumber.domain.gateways.languageModel.LanguageModelGateway;
import com.furansujin.demosprinhexagonalcucumber.domain.persistence.OriginalRessourcePersistencePort;
import com.furansujin.demosprinhexagonalcucumber.domain.persistence.ProjectPersistencePort;
import com.furansujin.demosprinhexagonalcucumber.domain.persistence.SourceDownloaderPersistencePort;
import com.furansujin.demosprinhexagonalcucumber.domain.persistence.SourcePersistencePort;
import com.furansujin.demosprinhexagonalcucumber.domain.persistence.SplitRessourcePersistencePort;
import com.furansujin.demosprinhexagonalcucumber.domain.usecases.UseCaseVoidArgumentless;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

public class ProcessOriginalRessourceUseCase implements UseCaseVoidArgumentless {


    public static  int baseSegmentSize = 700;
    private final AuthenticationGateway authenticationGateway;

    private final LanguageModelGateway languageModelGateway;
    private final SourcePersistencePort sourcePersistencePort;
    private final SourceDownloaderPersistencePort sourceDownloaderPersistencePort;
    private final OriginalRessourcePersistencePort originalRessourcePersistencePort;
    private final SplitRessourcePersistencePort splitRessourcePersistencePort;

    private final ProjectPersistencePort projectPersistencePort;


    public ProcessOriginalRessourceUseCase(AuthenticationGateway authenticationGateway, ProjectPersistencePort projectPersistencePort, SourcePersistencePort sourcePersistencePort,
                                           SourceDownloaderPersistencePort sourceDownloaderPersistencePort, OriginalRessourcePersistencePort originalRessourcePersistencePort,
                                           LanguageModelGateway languageModelGateway, SplitRessourcePersistencePort splitRessourcePersistencePort) {

        this.authenticationGateway = authenticationGateway;
        this.sourcePersistencePort = sourcePersistencePort;
        this.sourceDownloaderPersistencePort = sourceDownloaderPersistencePort;
        this.originalRessourcePersistencePort = originalRessourcePersistencePort;
        this.languageModelGateway = languageModelGateway;
        this.splitRessourcePersistencePort = splitRessourcePersistencePort;
        this.projectPersistencePort = projectPersistencePort;
    }


    @Override
    public void execute()   {

        PageRequest pageRequest = PageRequest.of(0, 1, Sort.by(Sort.Order.asc("createDate"))); // Tri par createDate du plus ancien au plus récent
        Page<OriginalRessource> pageOfRessources = originalRessourcePersistencePort.findByStateProcessSource(StateProcessSource.PENDING, pageRequest);


        List<OriginalRessource> originalRessources = pageOfRessources.get().peek(ressource -> {
            try {

                List<String> segments = splitText(ressource.getText());

                splitOriginalRessource(ressource, segments);

                ressource.setStateProcessSource(StateProcessSource.PROCESSED);

            } catch (Exception e) {
                e.printStackTrace();
                throw e;
            }
        }).toList();

        this.originalRessourcePersistencePort.saveAll(originalRessources);
    }

    private void splitOriginalRessource(OriginalRessource ressource, List<String> segments) {
        List<SplitRessource> collect = segments.stream().map(segment -> {
            float[] vectorized = this.languageModelGateway.vectorize(segment);

            return new SplitRessource(ressource.getId(), ressource.getProjectId(), vectorized, ressource.getName(), segment, segments.indexOf(segment), segments.size());
        }).toList();

        this.splitRessourcePersistencePort.saveAll(collect);
    }


    /**
     * Divise le texte en segments ajustés selon une tolérance.
     *
     * @param text le texte à diviser
     * @return une liste de segments de texte
     */
    public static List<String> splitText(String text) {
        if(text == null){
            return new ArrayList<>();
        }
        if(text.isEmpty() || text.isBlank() || text.length() == 0){
            return new ArrayList<>();
        }
        if(text.length() < baseSegmentSize){
            return List.of(text);
        }
        // 0.3 est une tolérance arbitraire 20%
        double toleranceSize = 0.2;

        List<String> segments = new ArrayList<>();

        int totalLength = text.length();
        int numberOfSegments = totalLength / baseSegmentSize;
        int remainingChars = totalLength % baseSegmentSize;

        if (remainingChars > 0 && remainingChars <= toleranceSize * baseSegmentSize) {
            // Répartir les caractères restants sur les segments
            int extraCharsPerSegment = remainingChars / numberOfSegments;
            for (int i = 0; i < numberOfSegments; i++) {
                segments.add(text.substring(i * (baseSegmentSize + extraCharsPerSegment),
                        (i + 1) * (baseSegmentSize + extraCharsPerSegment)));
            }
        } else {
            for (int i = 0; i < numberOfSegments; i++) {
                segments.add(text.substring(i * baseSegmentSize, (i + 1) * baseSegmentSize));
            }
            if (remainingChars > 0) {
                segments.add(text.substring(numberOfSegments * baseSegmentSize));
            }
        }

        return segments;
    }


}
