package io.crunch.mcp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkiverse.mcp.server.TextContent;
import io.quarkiverse.mcp.server.test.McpAssured;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@QuarkusTest
class MessageTemplateToolTest {

    @Test
    void shouldListTemplateParameters() {
        var client = McpAssured.newConnectedStreamableClient();
        client.when()
                .toolsCall("list_template_parameters", Map.of("page", 0, "size", 10), r -> {
                    var paramNames = ((List<TextContent>) r.content())
                            .stream()
                            .map(TextContent::text)
                            .map(this::toTemplateParameter)
                            .map(TemplateParameter::getName)
                            .toList();
                    assertThat(paramNames).containsAll(List.of("registration_id", "session_id"));
                }).thenAssertResults();
    }

    @Test
    void shouldListTemplateDescriptors() {
        var client = McpAssured.newConnectedStreamableClient();
        client.when()
                .toolsCall("list_template_descriptors", Map.of("page", 0, "size", 10), r -> {
                    var paramNames = ((List<TextContent>) r.content())
                            .stream()
                            .map(TextContent::text)
                            .map(this::toTemplateDescriptor)
                            .map(MessageTemplateDescriptor::getName)
                            .toList();
                    assertThat(paramNames).containsAll(List.of("confirm_registration", "payment_confirmation"));
                }).thenAssertResults();
    }

    private MessageTemplateDescriptor toTemplateDescriptor(String t) {
        try {
            return new ObjectMapper().readValue(t, MessageTemplateDescriptor.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private TemplateParameter toTemplateParameter(String t) {
        try {
            return new ObjectMapper().readValue(t, TemplateParameter.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
