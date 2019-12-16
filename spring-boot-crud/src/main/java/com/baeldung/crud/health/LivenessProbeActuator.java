package com.baeldung.crud.health;


import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.annotation.Selector;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

@Component
@Endpoint(id="liveness")
@Slf4j
public class LivenessProbeActuator {

    @ReadOperation
    public Map<String, String> get() {
        log.info("Checking liveness probe");
        Map<String, String> map = new HashMap<>();
        map.put("server.date", LocalDate.now().toString());
        map.put("server.time", LocalTime.now().toString());
        return map;
    }

    @ReadOperation
    public String customEndPointByName(@Selector String name) {
        return "liveness";
    }

}
