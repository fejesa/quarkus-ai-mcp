package io.crunch.mcp;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * Represents a dynamic placeholder that can be used inside message templates.
 * <p>
 * Each {@code MessageTemplateParameter} entry defines a parameter name and its description,
 * describing the meaning and purpose of the placeholder (for example, {@code [[customer_id]]}
 * or {@code [[account_number]]}).
 * <p>
 * These parameters are stored in the database and can be listed through the
 * {@code list_template_parameters} tool exposed by {@link MessageTemplateTool}.
 * The AI model uses them to ensure that only valid placeholders are included
 * in generated or updated templates.
 *
 * <h2>Database Mapping</h2>
 * <ul>
 *   <li><b>Table name:</b> {@code TEMPLATE_PARAMETER}</li>
 *   <li><b>Primary key:</b> inherited from {@link PanacheEntity#id}</li>
 *   <li><b>Fields:</b> {@code name}, {@code description}</li>
 * </ul>
 *
 * <h2>Usage</h2>
 * Typical lifecycle operations include:
 * <ul>
 *   <li>Inserting placeholder metadata into the database (name and description).</li>
 *   <li>Retrieving all placeholders via {@code MessageTemplateParameter.listAll()}.</li>
 *   <li>Returning these parameters as tool output to the AI model.</li>
 * </ul>
 *
 * @see MessageTemplateTool
 * @see MessageTemplateDescriptor
 */
@Entity
@Table(name = "TEMPLATE_PARAMETER")
public class MessageTemplateParameter extends PanacheEntity {

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
