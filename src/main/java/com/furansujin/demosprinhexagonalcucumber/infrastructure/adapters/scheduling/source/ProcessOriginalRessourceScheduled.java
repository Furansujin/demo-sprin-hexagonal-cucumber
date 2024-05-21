package com.furansujin.demosprinhexagonalcucumber.infrastructure.adapters.scheduling.source;

import com.furansujin.demosprinhexagonalcucumber.domain.usecases.source.github.ProcessOriginalRessourceUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicBoolean;

@Component
@RequiredArgsConstructor
public class ProcessOriginalRessourceScheduled {


    private final ProcessOriginalRessourceUseCase processOriginalRessourceUseCase;

    private AtomicBoolean enCours = new AtomicBoolean(false);

    @Scheduled(fixedDelay = 1000)
    public void execute() {
        if (enCours.compareAndSet(false, true)) {
            try {
                processOriginalRessourceUseCase.execute();
            } finally {
                enCours.set(false);
            }
        }
    }
}
