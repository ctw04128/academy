package com.ctw.workstation.config;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;
import java.util.Map;

public class WiremockResource implements QuarkusTestResourceLifecycleManager {
    WireMockServer server;
    @Override
    public Map<String, String> start() {
        WireMockConfiguration wireMockConfiguration = new WireMockConfiguration();
        wireMockConfiguration.port(3001);
        server = new WireMockServer(wireMockConfiguration);
        server.start();
        return Map.of("external-api.url", "localhost:" + server.port());
    }

    @Override
    public void stop() {
        server.stop();
    }
}
