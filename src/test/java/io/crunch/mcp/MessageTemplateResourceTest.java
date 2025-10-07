package io.crunch.mcp;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.TestProfile;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

@QuarkusTest
@TestProfile(McpTestProfile.class)
class MessageTemplateResourceTest {

    @Test
    void shouldCreateTemplate() {
        given()
          .when()
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
            .body("{\"templateContent\":\"Hello, abc!\",\"description\":\"Generate simple greeting template.\"}")
            .post("/api")
          .then()
            .statusCode(200);
    }
}
