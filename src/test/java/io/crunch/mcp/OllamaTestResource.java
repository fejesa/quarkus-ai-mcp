package io.crunch.mcp;

import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;
import org.testcontainers.ollama.OllamaContainer;

import java.io.IOException;
import java.util.Map;

public class OllamaTestResource implements QuarkusTestResourceLifecycleManager {

    private final OllamaContainer ollama;

    public OllamaTestResource() throws IOException, InterruptedException {
        this.ollama = new OllamaContainer("ollama/ollama:0.1.26");
        ollama.start();
        ollama.execInContainer("ollama", "pull", "qwen3:0.6b");
    }

    @Override
    public Map<String, String> start() {
        return Map.of();
    }

    @Override
    public void stop() {
        ollama.stop();
    }
}
