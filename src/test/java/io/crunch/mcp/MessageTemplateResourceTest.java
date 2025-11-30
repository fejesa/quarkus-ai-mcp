package io.crunch.mcp;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.TestProfile;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import org.jboss.resteasy.reactive.RestResponse;
import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

@QuarkusTest
@QuarkusTestResource(OllamaTestResource.class)
@TestProfile(McpTestProfile.class)
class MessageTemplateResourceTest {

    @Test
    void shouldCreateTemplateThatContainsOnlySupportedPlaceholders() {
        var templateParameters =
                MessageTemplateParameter.<MessageTemplateParameter>streamAll()
                        .map(MessageTemplateParameter::getName)
                        .toList();
        var template = given()
                .when()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .body("{\"content\":\"Hello, abc!\",\"description\":\"Generate simple greeting template.\"}")
                .post("/api")
                .then()
                .statusCode(RestResponse.Status.OK.getStatusCode()).extract()
                .response()
                .getBody()
                .asString();
        var pattern = Pattern.compile("\\[\\[(.+?)]]");
        var matcher = pattern.matcher(template);
        var placeholders = matcher.results().map(m -> m.group(1)).collect(Collectors.toSet());
        assertThat(templateParameters).containsAll(placeholders);
    }
}
