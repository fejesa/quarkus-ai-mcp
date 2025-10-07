package io.crunch.mcp;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * Represents a stored message template definition, describing its metadata.
 * <p>
 * A {@code MessageTemplateDescriptor} defines the logical properties of a template,
 * including its {@code name} (which also corresponds to the file name on disk)
 * and its {@code description}.
 * <p>
 * The corresponding HTML template content is stored in a file located inside the
 * configured templates directory (see {@link MessageTemplateTool}). This descriptor
 * serves as a database-level index to those template files.
 *
 * <h2>Database Mapping</h2>
 * <ul>
 *   <li><b>Table name:</b> {@code TEMPLATE_DESCRIPTOR}</li>
 *   <li><b>Primary key:</b> inherited from {@link PanacheEntity#id}</li>
 *   <li><b>Fields:</b> {@code name}, {@code description}</li>
 * </ul>
 *
 * <h2>Usage</h2>
 * Typical use cases include:
 * <ul>
 *   <li>Defining all available message templates in the database.</li>
 *   <li>Loading descriptors and resolving their corresponding files via {@link MessageTemplateTool}.</li>
 *   <li>Providing the AI model with reference templates for tone, formatting, and structure.</li>
 * </ul>
 *
 * @see MessageTemplateTool
 * @see MessageTemplateParameter
 * @see MessageTemplate
 */
@Entity
@Table(name = "TEMPLATE_DESCRIPTOR")
public class MessageTemplateDescriptor extends PanacheEntity {

    private String name;

    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
