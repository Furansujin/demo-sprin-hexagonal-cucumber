package com.furansujin.demosprinhexagonalcucumber.infrastructure.adapters.controller.conversation;

import com.furansujin.demosprinhexagonalcucumber.domain.usecases.conversation.CreateConversationUseCase;
import com.furansujin.demosprinhexagonalcucumber.domain.usecases.conversation.GetAllConversationProjectUseCase;
import com.furansujin.demosprinhexagonalcucumber.infrastructure.adapters.controller.conversation.payload.CreateConversationRequest;
import com.furansujin.demosprinhexagonalcucumber.infrastructure.adapters.controller.conversation.payload.SimpleConversationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/conversation")
public class ConversationController {


    private final GetAllConversationProjectUseCase getAllConversationProjectUseCase ;
    private final CreateConversationUseCase createConversationUseCase ;


    @GetMapping("/project/{projectId}/all")
    public List<SimpleConversationResponse> getAllConversationByIdProject(@PathVariable UUID projectId) throws Exception {
        return SimpleConversationResponse.fromDomain(getAllConversationProjectUseCase.execute(new GetAllConversationProjectUseCase.GetAllConversationRequest(projectId)));
    }


//

   @PostMapping("/create")
    public SimpleConversationResponse createConversation( @RequestBody CreateConversationRequest createConversationRequest) throws Exception {
        return SimpleConversationResponse.fromDomain(createConversationUseCase
                .execute(new CreateConversationUseCase
                        .CreateConversationRequest(createConversationRequest.getProjectId(),createConversationRequest.getTitle())
                )
        );
    }
}
