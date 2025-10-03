package io.crunch.mcp;

import io.quarkiverse.mcp.server.test.McpAssured;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@QuarkusTest
class MessageTemplateResourcesTest {

    @Test
    void shouldGetMessageTemplate() {

        var client = McpAssured.newConnectedStreamableClient();
        client
            .when()
            .resourcesTemplatesList(p -> {
                var uriTemplate = p.findByUriTemplate("file:///message_templates/{name}");
                assertThat(uriTemplate).isNotNull();
                assertThat(uriTemplate.description()).isEqualTo("Fetch a message template by name from the configured templates folder.");
            }).thenAssertResults();

        List<MessageTemplateDescriptor> templates = MessageTemplateDescriptor.listAll();
        templates.
            stream()
            .map(MessageTemplateDescriptor::getName)
            .forEach(name ->
                client
                    .when()
                    .resourcesRead("file:///message_templates/" + name, r -> {
                        assertThat(r.contents().getFirst().asText().text()).isNotEmpty();
                    }).thenAssertResults()
            );
    }
}
