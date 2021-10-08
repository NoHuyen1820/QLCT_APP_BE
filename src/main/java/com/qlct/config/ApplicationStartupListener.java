package com.qlct.config;

import com.qlct.database.FirebaseInitialize;
import io.micronaut.context.event.ApplicationEventListener;
import io.micronaut.context.event.StartupEvent;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

@Singleton
@Slf4j
public class ApplicationStartupListener implements ApplicationEventListener<StartupEvent> {

    @Inject
    FirebaseInitialize firebaseInitialize;

    @Override
    public void onApplicationEvent(StartupEvent event) {
        log.info("Help firebase");
        firebaseInitialize.initialize();

    }
}
