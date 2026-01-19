package io.crunch.mcp;

import io.quarkiverse.mcp.server.Tool;
import io.quarkus.logging.Log;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * The {@code MessageTemplateTool} provides a set of MCP (Model Context Protocol) tools
 * that expose message template metadata and content to an AI service.
 * <p>
 * It is responsible for:
 * <ul>
 *   <li>Listing all available message template parameters (placeholders) that can be used in template generation.</li>
 *   <li>Loading and returning all stored message templates from the configured template directory.</li>
 * </ul>
 * <p>
 * The class is integrated with the {@code quarkus-langchain4j} or {@code quarkus-mcp} infrastructure,
 * which automatically registers each method annotated with {@link Tool} as an available callable tool
 * for the AI model at runtime.
 *
 * <h2>Configuration</h2>
 * The path to the message templates directory must be defined in the Quarkus configuration:
 * <pre>{@code
 * app.resources.location=resources/templates
 * }</pre>
 * The path is resolved relative to the application root and normalized to an absolute location.
 *
 * <h2>Usage in AI Services</h2>
 * <ul>
 *   <li>{@code list_template_parameters}: returns all valid placeholders (e.g., [[customer_id]], [[bank_name]]).</li>
 *   <li>{@code get_message_templates}: returns a list of all available message templates along with their names and descriptions.</li>
 * </ul>
 *
 * The AI model can use these tool results to:
 * <ul>
 *   <li>Determine which placeholders are allowed and their meaning.</li>
 *   <li>Understand structure, tone, and formatting conventions from existing templates.</li>
 * </ul>
 *
 * @see io.quarkiverse.mcp.server.Tool
 * @see MessageTemplate
 * @see MessageTemplateParameter
 * @see MessageTemplateDescriptor
 */
public class MessageTemplateTool {

    /**
     * Absolute path to the folder where message templates are stored.
     * <p>
     * This folder is configured using the {@code app.resources.location} configuration key.
     * The path is resolved relative to the application's working directory and normalized
     * to ensure consistent file resolution across environments.
     */
    private final Path templatesFolder;

    /**
     * Constructs a new {@code MessageTemplateTool} instance.
     *
     * @param resourcesFolder the base folder path for message templates, injected from configuration
     */
    public MessageTemplateTool(@ConfigProperty(name = "app.resources.location") Path resourcesFolder) {
        this.templatesFolder = Paths.get(".")
                .resolve(resourcesFolder)
                .resolve("templates")
                .toAbsolutePath().normalize();
    }

    /**
     * MCP tool that lists all available message template parameters (placeholders).
     * <p>
     * This tool exposes metadata about each available placeholder, such as its name and description.
     * The placeholders represent dynamic fields that can be inserted into HTML message templates
     * (e.g., [[customer_id]], [[account_number]]).
     * <p>
     * The method is typically called by the AI model before generating or updating templates,
     * ensuring that only valid placeholders are used.
     *
     * @return a list of {@link MessageTemplateParameter} objects representing all available placeholders
     */
    @Tool(
            name = "list_template_parameters",
            description = "List available message template parameters."
    )
    public List<MessageTemplateParameter> getTemplateParameters() {
        Log.info("Getting template parameters");
        return MessageTemplateParameter.listAll();
    }

    /**
     * MCP tool that retrieves all message templates from the configured directory.
     * <p>
     * Each template includes its name, description, and full HTML content.
     * This allows the AI model to analyze existing templates for tone, structure,
     * formatting, and common block conventions.
     * <p>
     * The templates are discovered via {@link MessageTemplateDescriptor} entries
     * stored in the database, and their corresponding content files are loaded from disk.
     *
     * @return a list of {@link MessageTemplate} objects representing all message templates
     */
    @Tool(
            name = "get_message_templates",
            description = "Fetch all message templates"
    )
    public List<MessageTemplate> getMessageTemplates() {
        Log.infof("Loading all templates from folder: %s", templatesFolder);
        return MessageTemplateDescriptor.<MessageTemplateDescriptor>streamAll()
                .map(this::getMessageTemplate)
                .toList();
    }

    /**
     * Converts a {@link MessageTemplateDescriptor} into a {@link MessageTemplate}
     * by reading the corresponding template file from the configured folder.
     *
     * @param descriptor the template descriptor containing name and description
     * @return a fully populated {@link MessageTemplate} with HTML content
     */
    private MessageTemplate getMessageTemplate(MessageTemplateDescriptor descriptor) {
        return new MessageTemplate(
                descriptor.getName(),
                descriptor.getDescription(),
                loadTemplate(descriptor.getName())
        );
    }

    /**
     * Loads the content of a message template file as a string.
     * <p>
     * The file is located inside the configured {@code templatesFolder}.
     * If the file cannot be read, an exception is thrown.
     *
     * @param name the file name of the template to load
     * @return the HTML content of the template file
     * @throws MessageTemplateException if the file cannot be found or read
     */
    private String loadTemplate(String name) {
        try {
            return Files.readString(Paths.get(templatesFolder.toString(), name));
        } catch (IOException e) {
            throw new MessageTemplateException("Error reading template file: " + name, e);
        }
    }
}

record MessageTemplate(String name, String description, String content) {
}
