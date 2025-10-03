package io.crunch.mcp;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

@QuarkusTest
class MessageTemplateServiceTest {

    @Test
    void shouldCreateTemplate() {
        given()
          .when()
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
            .body("{\"template\":\"Hello, {{name}}!\",\"description\":\"A simple greeting template.\"}")
            .post("/message-template")
          .then()
            .statusCode(200);
    }
}
