package com.ctw.workstation.config;

import io.quarkus.logging.Log;
import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;

import java.util.Map;

public class CtwAcademyResource  implements QuarkusTestResourceLifecycleManager {

    @Override
    public Map<String, String> start() {
        Log.info("Starting CtwAcademyResource");
        return Map.of();
    }

    @Override
    public void stop() {
    Log.info("Stopping CtwAcademyResource");
    }
}
