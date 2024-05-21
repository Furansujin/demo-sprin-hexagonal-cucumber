package com.furansujin.demosprinhexagonalcucumber.infrastructure.adapters.gateway.languageModel.api.dto.chat;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.furansujin.demosprinhexagonalcucumber.domain.entities.commun.Message;
import com.furansujin.demosprinhexagonalcucumber.domain.entities.commun.MessageSenderType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * Request body for ChatGPT API.
 * </p>
 * see: <a href="https://platform.openai.com/docs/api-reference/chat">https://platform.openai.com/docs/api-reference/chat</a>
 *
 * @author Saad H.
 * @since 2023/3/2
 */
@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
public class ChatRequest {

    public ChatRequest(String model, List<Message> messages, String personaPromnt) {

        this.model = model;

        this.messages = new ArrayList<>();
        this.messages.add(new MessageDto(MessageSenderType.SYSTEM.sender, personaPromnt));
        int size = messages.size();
        for (int i = 0; i < size; i++) {
            Message message = messages.get(i);
            if (i < size - 1) {
                // Pour tous les éléments sauf le dernier, utilisez getText()
                this.messages.add(new MessageDto(message.getType().sender, message.getText()));
            } else {
                // Pour le dernier élément, utilisez getTextProcessed()
                this.messages.add(new MessageDto(message.getType().sender, message.getTextProcessed()));
            }
        }

        this.n = 1;
        this.temperature = 1f;

    }




        /**
         * Required
         * <p>
         * ID of the model to use. Currently, only gpt-3.5-turbo and gpt-3.5-turbo-0301 are supported.
         */
        @JsonProperty(value = "model")
        private String model;
    /**
     * Required
     * <p>
     * The messages to generate chat completions for, in the
     * <a href=https://platform.openai.com/docs/guides/chat/introduction>chat format</a>.
     */
    @JsonProperty(value = "messages")
    private List<MessageDto> messages;

        /**
         * Optional
         * <p>
         * Defaults to 1
         * <p>
         * What sampling temperature to use, between 0 and 2.
         * Higher values like 0.8 will make the output more random,
         * while lower values like 0.2 will make it more focused and deterministic.
         * <p>
         * We generally recommend altering this or `top_p` but not both.
         */
        @JsonProperty(value = "temperature")
        private Float temperature;

        /**
         * Optional
         * <p>
         * Defaults to 1
         * <p>
         * An alternative to sampling with temperature, called nucleus sampling,
         * where the model considers the results of the tokens with top_p probability mass.
         * So 0.1 means only the tokens comprising the top 10% probability mass are considered.
         * <p>
         * We generally recommend altering this or `temperature` but not both.
         */
        @JsonProperty(value = "top_p")
        private Float topP;

        /**
         * Optional
         * <p>
         * Defaults to 1
         * <p>
         * How many chat completions to generate for each message.
         */
        @JsonProperty(value = "n")
        private Integer n;

        /**
         * Optional
         * <p>
         * Defaults to false
         * <p>
         * If set, partial message deltas will be sent, like in ChatGPT.
         * Tokens will be sent as data-only <a href=https://developer.mozilla.org/en-US/docs/Web/API/Server-sent_events/Using_server-sent_events#event_stream_format>server-sent events</a>
         * as they become available,
         * with the stream terminated by a `data: [DONE]` message.
         */
        @JsonProperty(value = "stream")
        private Boolean stream;

        /**
         * Optional
         * <p>
         * Defaults to null
         * <p>
         * Up to 4 sequences where the API will stop generating further tokens.
         */
        @JsonProperty(value = "stop")
        private List<String> stop;

        /**
         * Optional
         * <p>
         * Defaults to inf
         * <p>
         * The maximum number of tokens allowed for the generated answer.
         * By default, the number of tokens the model can return will be (4096 - prompt tokens).
         */
        @JsonProperty(value = "max_tokens")
        private Integer maxTokens;

        /**
         * Optional
         * <p>
         * Defaults to 0
         * <p>
         * Number between -2.0 and 2.0.
         * Positive values penalize new tokens based on whether they appear in the text so far,
         * increasing the model's likelihood to talk about new topics.
         * <p>
         * <a href=https://platform.openai.com/docs/api-reference/parameter-details>See more information about frequency and presence penalties.</a>
         */
        @JsonProperty(value = "presence_penalty")
        private Float presencePenalty;

        /**
         * Optional
         * <p>
         * Defaults to 0
         * <p>
         * Number between -2.0 and 2.0.
         * Positive values penalize new tokens based on their existing frequency in the text so far,
         * decreasing the model's likelihood to repeat the same line verbatim.
         * <p>
         * <a href=https://platform.openai.com/docs/api-reference/parameter-details>See more information about frequency and presence penalties.</a>
         */
        @JsonProperty(value = "frequency_penalty")
        private Float frequencyPenalty;

        /**
         * Optional
         * <p>
         * Defaults to null
         * <p>
         * Modify the likelihood of specified tokens appearing in the completion.
         * <p>
         * Accepts a json object that maps tokens (specified by their token ID in the tokenizer) to an associated bias value from -100 to 100.
         * Mathematically, the bias is added to the logits generated by the model prior to sampling.
         * The exact effect will vary per model, but values between -1 and 1 should decrease or increase likelihood of selection;
         * values like -100 or 100 should result in a ban or exclusive selection of the relevant token.
         */
        @JsonProperty(value = "logit_bias")
        private Map<Object, Object> logitBias;


        /**
         * Optional
         * <p>
         * A unique identifier representing your end-user, which can help OpenAI to monitor and detect abuse.
         * <p>
         * <a href=https://platform.openai.com/docs/guides/safety-best-practices/end-user-ids>Learn more</a>.
         */
        private String user;




}
