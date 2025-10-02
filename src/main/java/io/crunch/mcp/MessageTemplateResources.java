package io.crunch.mcp;

import io.quarkiverse.mcp.server.RequestUri;
import io.quarkiverse.mcp.server.ResourceTemplate;
import io.quarkiverse.mcp.server.TextResourceContents;
import io.quarkus.logging.Log;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MessageTemplateResources {

    private final Path templatesFolder;

    public MessageTemplateResources(@ConfigProperty(name = "app.templates.location") Path templatesFolder) {
        this.templatesFolder = Paths.get(".").resolve(templatesFolder).toAbsolutePath().normalize();
    }

    @ResourceTemplate(
            name = "get_message_template",
            uriTemplate = "file:///message_templates/{name}",
            description = "Fetch a message template by name from the configured templates folder.",
            mimeType = "text/plain")
    public TextResourceContents getMessageTemplate(String name, RequestUri uri) {
        Log.info("Loading template: " + name + " from folder: " + templatesFolder);
        return TextResourceContents.create(uri.value(), loadTemplate(name));
    }

    private String loadTemplate(String name) {
        try {
            return Files.readString(Paths.get(templatesFolder.toString(), name));
        } catch (IOException e) {
            throw new MessageTemplateException("Error reading template file: " + name, e);
        }
    }
}
