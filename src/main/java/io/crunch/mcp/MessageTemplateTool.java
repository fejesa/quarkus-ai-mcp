package io.crunch.mcp;

import io.quarkiverse.mcp.server.Tool;
import io.quarkus.logging.Log;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class MessageTemplateTool {

    private final Path templatesFolder;

    public MessageTemplateTool(@ConfigProperty(name = "app.templates.location") Path templatesFolder) {
        this.templatesFolder = Paths.get(".").resolve(templatesFolder).toAbsolutePath().normalize();
    }

    @Tool(
            name = "list_template_parameters",
            description = "List available message template parameters.")
    public List<MessageTemplateParameter> getTemplateParameters() {
        Log.info("Getting template parameters");
        List<MessageTemplateParameter> messageTemplateParameters = MessageTemplateParameter.listAll();
        return messageTemplateParameters;
    }

    @Tool(
            name = "get_message_templates",
            description = "Fetch all message templates")
    public List<MessageTemplate> messageTemplates() {
        Log.info("Loading all templates from folder: " + templatesFolder);
        return MessageTemplateDescriptor.streamAll()
                .map(e -> (MessageTemplateDescriptor) e)
                .map(descriptor -> new MessageTemplate(descriptor.getName(), descriptor.getDescription(), loadTemplate(descriptor.getName())))
                .toList();
    }

    private String loadTemplate(String name) {
        try {
            return Files.readString(Paths.get(templatesFolder.toString(), name));
        } catch (IOException e) {
            throw new MessageTemplateException("Error reading template file: " + name, e);
        }
    }
}
