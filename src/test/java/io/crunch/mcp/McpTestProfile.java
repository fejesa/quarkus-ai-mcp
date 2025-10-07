package io.crunch.mcp;

import io.quarkus.test.junit.QuarkusTestProfile;

import java.util.Map;

public class McpTestProfile implements QuarkusTestProfile {

    @Override
    public Map<String, String> getConfigOverrides() {
        return Map.of(
                "quarkus.langchain4j.mcp.template-generator.url", "http://localhost:8081/mcp/sse");
    }
}
