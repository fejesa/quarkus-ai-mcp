package io.crunch.mcp;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import io.quarkiverse.langchain4j.RegisterAiService;
import io.quarkiverse.langchain4j.mcp.runtime.McpToolBox;

/**
 * <p>
 * The {@code MessageTemplateGeneratorAssistant} interface defines an AI-powered service
 * for generating and refining HTML message templates in the context of
 * Banking and Financial Services communications.
 * </p>
 *
 * <p>
 * This service uses the <b>LangChain4j</b> integration for Quarkus and operates as an AI assistant
 * capable of dynamically creating message templates based on textual descriptions
 * or enhancing existing templates based on their content.
 * </p>
 *
 * <h3>Core Responsibilities</h3>
 * <ul>
 *   <li>Generate new HTML message templates when no prior template content is provided.</li>
 *   <li>Refine or update an existing message template to better align with a given description.</li>
 *   <li>Integrate with MCP tools such as {@code list_template_parameters} and {@code get_message_templates}
 *       to ensure consistent placeholder usage and stylistic conformity.</li>
 * </ul>
 *
 * <h3>Integration and Configuration</h3>
 * <ul>
 *   <li>Annotated with {@link RegisterAiService}, allowing Quarkus to automatically
 *       register the interface as an AI service implementation.</li>
 *   <li>Uses {@link McpToolBox} to integrate with the "template-generator" toolchain,
 *       enabling the AI to access message templates and parameters dynamically.</li>
 *   <li>Relies on LangChain4j annotations to handle user prompts and system-level context messages.</li>
 * </ul>
 *
 * <h3>Template Generation Behavior</h3>
 * <p>
 * The AI assistant operates under a controlled instruction set defined by the {@code @SystemMessage}
 * annotation (not shown here), which specifies rules for structure, style, and formatting.
 * </p>
 *
 * <p>
 * Key behavioral principles include:
 * </p>
 * <ul>
 *   <li>Only use placeholders returned by the {@code list_template_parameters} tool.</li>
 *   <li>Base the structure and tone on reference templates retrieved via {@code get_message_templates}.</li>
 *   <li>Always include standard header and closing blocks in the generated HTML output.</li>
 *   <li>Ensure all templates maintain a formal, professional tone suitable for bank-customer communication.</li>
 *   <li>Output only valid, self-contained HTML content (no JSON, metadata, or tool traces).</li>
 * </ul>
 *
 * <h3>Example Use Case</h3>
 * <pre>{@code
 * var request = new MessageTemplateRequest("Hello World", "Generate simple greeting template");
 * var htmlTemplate = assistant.generateTemplate(request);
 * }</pre>
 *
 * <p>
 * The above example will prompt the AI assistant to generate a new, fully formatted
 * HTML message template tailored for an overdue payment reminder scenario.
 * </p>
 *
 * @see dev.langchain4j.service.UserMessage
 * @see io.quarkiverse.langchain4j.RegisterAiService
 * @see io.quarkiverse.langchain4j.mcp.runtime.McpToolBox
 * @see MessageTemplateRequest
 */
@RegisterAiService()
public interface MessageTemplateGeneratorAssistant {

    /**
     * Generates or refines a Banking/Financial Services message template based on
     * the provided {@link MessageTemplateRequest}.
     *
     * <p>
     * The method behavior is determined by the state of the {@code templateContent} field:
     * </p>
     * <ul>
     *   <li>If {@code templateContent} is empty, the AI generates a new HTML template.</li>
     *   <li>If {@code templateContent} contains HTML, the AI refines and improves the existing template.</li>
     * </ul>
     *
     * <p>
     * The AI assistant uses the results from the registered MCP tools to:
     * </p>
     * <ul>
     *   <li>Determine which placeholders are available and must be used.</li>
     *   <li>Reference the structure, tone, and format of existing templates.</li>
     *   <li>Ensure consistent formatting, tone, and placeholder accuracy.</li>
     * </ul>
     *
     * <p>
     * The generated result is a valid, self-contained HTML string
     * containing header, body, and closing sections written in a formal,
     * customer-facing tone.
     * </p>
     *
     * @param request The {@link MessageTemplateRequest} object containing the message
     *                description and optional existing template content.
     * @return A refined or newly generated HTML message template as a {@link String}.
     */
    @SystemMessage(
        """
        You are an AI assistant specialized in generating and refining HTML message templates for a Banking / Financial Services workflow.
        Each message template includes dynamic placeholders in the format [[placeholder_name]], and must be written in a formal, polite tone appropriate for communication between a bank and its customers.
        
        Your behavior depends on the user input:
        - If the field `templateContent` is empty, generate a new HTML message template based on the provided `description`.
        - If `templateContent` contains HTML, update and improve that template so that it better matches the `description`.
        
        Execution flow:
        
        1. Tool results:
           - You are provided with the results from the tools `list_template_parameters` and `get_message_templates`.
           - **Carefully analyze these results** before generating any template.
           - Only use the placeholders returned by `list_template_parameters`.
           - **Do not invent or add any new placeholders.**
           - Select only those placeholders relevant to the requested message description.
           - Use the fetched templates from `get_message_templates` as strict references for style, tone, structure, and formatting.
        
        2. Generate or update the message template:
           - If generating a **new template** (templateContent is empty):
               * **Always start the template with a standard header block** including the following placeholders in this order, if available: customer_id, account_number, branch_name, branch_id, message_creation_date. You may include additional placeholders from the tool results only if relevant to the description.
               * **Always end the template with a standard closing block** with a polite signature, e.g., `<p>Sincerely,</p><p>Your [[bank_name]] Customer Care Team</p>`.
           - If updating an existing template (templateContent is provided):
               * Preserve and refine the existing header and closing blocks.
               * Adjust the body to match the description, placeholders, and style of reference templates.
           - Follow the structure, style, and tone of the reference templates exactly.
        
        Formatting and content rules:
        - Use only <p>, <b>, <i>, <u>, <ul>, <ol>, <li>, <br>, <h2>, <h3>.
        - Placeholders must be in the format [[placeholder_name]].
        - Include a greeting, main body, and closing.
        - Text content ~800–1500 characters (excluding HTML tags).
        - Maintain formal, courteous, and clear tone.
        
        Important:
        - **Always include the standard header and closing blocks in every template**, even for new templates.
        - Do not return a function call or JSON object.
        - After reading and integrating the tool results, return only the final HTML template content as plain text.
        - Do not include explanations, reasoning, or tool traces.
        - The output must be a valid, self-contained HTML string.
        - Use only the placeholders provided by the tools.
        - Do not invent any new placeholders or fields.
        """
    )
    @McpToolBox("template-generator")
    String generateTemplate(@UserMessage MessageTemplateRequest request);
}
