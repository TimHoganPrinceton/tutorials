package com.baeldung.crud.health;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class HealthService {

    public boolean isAlive() {
        log.info("Checking alive");
        return true;
    }

    public boolean isReady() {
        log.info("Checking readiness");
        return true;
    }
}
