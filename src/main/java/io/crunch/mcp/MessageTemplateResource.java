package io.crunch.mcp;

import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.ChatMemoryProvider;
import io.quarkus.logging.Log;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;

import java.util.Optional;
import java.util.UUID;

@Path("/api")
public class MessageTemplateResource {

    private final MessageTemplateAssistant templateGeneratorAssistant;

    private final ChatMemoryProvider chatMemoryProvider;

    public MessageTemplateResource(MessageTemplateAssistant templateGeneratorAssistant, ChatMemoryProvider chatMemoryProvider) {
        this.templateGeneratorAssistant = templateGeneratorAssistant;
        this.chatMemoryProvider = chatMemoryProvider;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public String createTemplate(MessageTemplateRequest request) {
        Log.infof("Creating template with description: %s", request.description());
        var sessionId = UUID.randomUUID().toString();
        try {
            var result = templateGeneratorAssistant.generateTemplate(sessionId, request);
            Log.info("Generated template: " + result);
            chatMemoryProvider.get(sessionId)
                    .messages()
                    .forEach(chatMessage -> Log.infof("Chat message type: %s", chatMessage.type().name()));
            return result;
        } finally {
            Optional.ofNullable(chatMemoryProvider.get(sessionId)).ifPresent(ChatMemory::clear);
        }
    }
}
