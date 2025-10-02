package io.crunch.mcp;

import io.quarkiverse.mcp.server.Tool;
import io.quarkiverse.mcp.server.ToolArg;
import io.quarkus.logging.Log;
import io.quarkus.panache.common.Page;

import java.util.List;

public class MessageTemplateTool {

    @Tool(
            name = "list_template_parameters",
            description = "List available message template parameters with pagination. Provide page index (starting from 0) and page size.")
    public List<TemplateParameter> getTemplateParameters(
            @ToolArg(description = "The page index", defaultValue = "0") int page,
            @ToolArg(description = "The page size", defaultValue = "5") int size) {
        Log.info("Getting template parameters with page index %d and size %d".formatted(page, size));
        return TemplateParameter.findAll().page(Page.of(page, size)).list();
    }

    @Tool(
            name = "list_template_descriptors",
            description = "List available message template names with pagination. Provide page index (starting from 0) and page size.")
    public List<MessageTemplateDescriptor> getTemplateDescriptors(
            @ToolArg(description = "The page index", defaultValue = "0") int page,
            @ToolArg(description = "The page size", defaultValue = "5") int size) {
        Log.info("Getting template names with page index %d and size %d".formatted(page, size));
        return MessageTemplateDescriptor.findAll().page(Page.of(page, size)).list();
    }
}
