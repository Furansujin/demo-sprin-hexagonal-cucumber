package com.furansujin.demosprinhexagonalcucumber.infrastructure.adapters.scheduling.source.github;
import com.furansujin.demosprinhexagonalcucumber.domain.usecases.source.github.DownloadTaskFileGithubUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

@Component
@RequiredArgsConstructor
public class DownloadTaskFileGithubScheduled {

    private final DownloadTaskFileGithubUseCase downloadTaskFileGithubUseCase;

    private AtomicBoolean enCours = new AtomicBoolean(false);

    @Scheduled(fixedDelay = 3000)
    public void execute() {
        if (enCours.compareAndSet(false, true)) {
            try {
                downloadTaskFileGithubUseCase.execute();
            } catch (IOException e) {
                throw new RuntimeException(e);
            } finally {
                enCours.set(false);
            }
        }
    }
}
