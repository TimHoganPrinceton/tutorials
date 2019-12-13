package com.baeldung.crud.health;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class HealthController {

    @Autowired
    HealthService healthService;

    @GetMapping("/health/alive")
    public String aliveProbe() {
        if(!healthService.isAlive()) {
            throw new HealthException();
        }
        return "I am alive";
    }

    @GetMapping("/health/ready")
    public String readyProbe() {
        if(!healthService.isReady()) {
            throw new HealthException();
        }
        return "I am ready";
    }
}
