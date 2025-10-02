package io.crunch.mcp;

import io.quarkus.logging.Log;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;

@Path("/template")
public class MessageTemplateResource {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public String createTemplate(MessageTemplateRequest request) {
        Log.info("Creating template with description: " + request.description());
        return "Template created";
    }
}
