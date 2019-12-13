package com.baeldung.crud.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ShutdownService{

    @Autowired
    private ApplicationContext appContext;

    private boolean timeToDie = false;

    public void shutdownApplication(){
        log.warn("Application will shutdown in 10 seconds!");
        this.timeToDie = true;
    }

    public boolean isDead() {
        return timeToDie;
    }

//    @Scheduled(fixedDelay = 10000)  //poll every 10 seconds to see if we should run a synch
//    private void stopMyself() {
//        if(timeToDie) {
//            int returnCode = -1;
//            log.error("SYSTEM CRASH!!");
//            SpringApplication.exit(appContext, () -> returnCode);
//        }
//    }
}
