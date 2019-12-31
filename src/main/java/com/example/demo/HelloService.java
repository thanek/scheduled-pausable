package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import static java.time.Instant.now;

@Component
public class HelloService {
    private static final Logger logger = LoggerFactory.getLogger(HelloService.class);

    @Scheduled(fixedDelay = 1000)
    public void hello() {
        logger.info("Hello! {}", now().getEpochSecond());
    }
}
