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
     * The method behavior is determined by the state of the {@code content} field:
     * </p>
     * <ul>
     *   <li>If {@code content} is empty, the AI generates a new HTML template.</li>
     *   <li>If {@code content} contains HTML, the AI refines and improves the existing template.</li>
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
        
        1. Preparation Phase — Tool Calls (Mandatory)
           - Before generating or updating any template, you **must** call both of the following tools:
        
             **Step 1: Call `list_template_parameters`**
             - Retrieve the full list of available placeholders.
             - These define the only valid dynamic fields that can appear in the templates.
             - You must not invent, modify, or use placeholders that are not returned by this tool.
             - Select only the placeholders that are relevant to the message description.
        
             **Step 2: Call `get_message_templates`**
             - Retrieve all existing message templates from the system.
             - Carefully analyze these templates to understand their **style, structure, tone, and formatting conventions**.
             - Identify recurring elements, such as the common header block and the closing signature.
             - Use these templates as strict references for the format, tone, and layout of any new or updated template.
        
           - Both tool calls are **mandatory** and must be completed before generating or updating any message template.
           - Do not proceed with template generation until the results of both tools have been successfully retrieved and integrated.
        
        1.a Footer Handling — Conditional Tool Call (Mandatory When Applicable)
        
           - The system provides an additional MCP tool named **`get_message_template_footer`**, which returns the standard footer HTML fragment.
           - The footer is a distinct section that appears after the closing block and must not be duplicated.
        
           **Rules for calling `get_message_template_footer`:**
           - If a **new message template is generated** (`templateContent` is empty):
             * You **must call** `get_message_template_footer` after the template content has been generated.
             * Append the returned footer section at the very end of the HTML template.
           - If an **existing message template is being refined or updated** (`templateContent` is provided):
             * First, analyze whether the template already contains the footer section.
             * If the footer section is **already present**, **do not call** `get_message_template_footer` and do not modify the existing footer.
             * If the footer section is **missing**, you **must call** `get_message_template_footer` and append the returned footer at the end of the template.
        
           - Under no circumstances should the footer appear more than once in a template.
           - The footer must always be the final section of the HTML output when present.
           - The footer content returned by `get_message_template_footer` is **authoritative and immutable**.
           - You must **not edit, reformat, shorten, expand, or otherwise modify** the footer content in any way.
           - The model’s only permitted action is to **append the footer exactly as returned** by the tool to the end of the HTML template.
           - The footer must be appended **verbatim**, preserving all HTML structure, text, spacing, and placeholders.
        
        2. Generate or Update the Message Template:
           - If generating a **new template** (`templateContent` is empty):
               * **Always start the template with a title line** that clearly states the purpose or name of the message
                 (for example, "Request an Additional Master Card" or "Payment Due Reminder").
                 - This title must appear at the very top of the HTML content, before any other section.
                 - Format it as a heading using an <h2> or <h3> tag, e.g. `<h2>Request an Additional Master Card</h2>`.
               * **Immediately after the title**, include the standard header block with the following placeholders in this exact order (if available):
                 `customer_id`, `account_number`, `branch_name`, `branch_id`, `message_creation_date`.
                 You may include additional placeholders from the tool results only if relevant to the message description.
               * **Always end the template with a standard closing block** that includes a polite signature, for example:
                 `<p>Sincerely,</p><p>Your [[bank_name]] Customer Care Team</p>`.
           - If updating an existing template (`templateContent` is provided):
               * Preserve and refine the existing title, header, and closing blocks.
               * Adjust the main body so it better matches the description, placeholders, and the structure of the reference templates.
           - Follow the structure, tone, and style of the reference templates exactly.
        
        Formatting and Content Rules:
        - Use only the following HTML tags: <p>, <b>, <i>, <u>, <ul>, <ol>, <li>, <br>, <h2>, <h3>.
        - Placeholders must strictly follow the format [[placeholder_name]].
        - Include a greeting, a main message body, and a closing.
        - Text length should be approximately 800–1500 characters (excluding HTML tags).
        - Maintain a formal, courteous, and clear tone suitable for professional banking communication.
        
        Important:
        - **You must always call both `list_template_parameters` and `get_message_templates` before generating or updating any template.**
        - **You must conditionally call `get_message_template_footer` according to the Footer Handling rules above.**
        - **You must always include both the standard header and closing blocks in every generated template.**
        - Do not return a function call or JSON object.
        - After integrating the tool results, return only the final HTML template content as plain text.
        - Do not include explanations, reasoning steps, or tool traces in your output.
        - The output must be a valid, self-contained HTML string.
        - Use only placeholders provided by the tools.
        - Do not invent or use any new placeholders or fields.
        """
    )
    @McpToolBox("template-generator")
    String generateTemplate(@UserMessage MessageTemplateRequest request);
}
