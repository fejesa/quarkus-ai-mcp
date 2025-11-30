package io.crunch.mcp;

import io.quarkus.test.junit.QuarkusTestProfile;

import java.util.Map;

public class McpTestProfile implements QuarkusTestProfile {

    @Override
    public Map<String, String> getConfigOverrides() {
        return Map.of(
                "quarkus.langchain4j.mcp.template-generator.url", "http://localhost:8081/mcp/sse",
                "quarkus.langchain4j.ollama.chat-model.model-id", "qwen3:0.6b",
                "quarkus.http.test-timeout", "60s",
                "quarkus.otel.enabled", "false",
                "quarkus.otel.metrics.enabled", "false",
                "quarkus.observability.enabled", "false");
    }
}
