package io.crunch.mcp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkiverse.mcp.server.Content;
import io.quarkiverse.mcp.server.TextContent;
import io.quarkiverse.mcp.server.test.McpAssured;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.TestProfile;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@QuarkusTest
@TestProfile(McpTestProfile.class)
class MessageTemplateToolTest {

    @Test
    void shouldListTemplateParameters() {
        var client = McpAssured.newConnectedStreamableClient();
        client.when()
            .toolsCall("list_template_parameters", r -> {
                var paramNames = r.content()
                        .stream()
                        .map(Content::asText)
                        .map(TextContent::text)
                        .map(this::toTemplateParameter)
                        .map(MessageTemplateParameter::getName)
                        .toList();
                assertThat(paramNames).containsAll(List.of("account_number", "account_last_digits"));
            }).thenAssertResults();
    }

    @Test
    void shouldListTemplateDescriptors() {
        var client = McpAssured.newConnectedStreamableClient();
        client.when()
            .toolsCall("get_message_templates", r -> {
                var paramNames = r.content()
                        .stream()
                        .map(Content::asText)
                        .map(TextContent::text)
                        .map(this::toMessageTemplate)
                        .map(MessageTemplate::name)
                        .toList();
                assertThat(paramNames).containsAll(List.of("account_activation_reminder", "account_opening_confirmation"));
            }).thenAssertResults();
    }

    @Test
    void shouldReadMessageFooter() {
        var client = McpAssured.newConnectedStreamableClient();
        client.when()
            .toolsCall("get_message_template_footer", r -> {
                var footer = r.content()
                        .stream()
                        .map(Content::asText)
                        .map(TextContent::text)
                        .toList().getFirst();
                assertThat(footer).contains("quarkus.png", "© 2026 Quarkus Bank, Inc.");
            }).thenAssertResults();
    }

    private MessageTemplate toMessageTemplate(String t) {
        try {
            return new ObjectMapper().readValue(t, MessageTemplate.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private MessageTemplateParameter toTemplateParameter(String t) {
        try {
            return new ObjectMapper().readValue(t, MessageTemplateParameter.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
