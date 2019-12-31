package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.concurrent.ScheduledExecutorService;

@Controller
public class HelloEndpoint {
    private static final Logger logger = LoggerFactory.getLogger(HelloEndpoint.class);
    private final ScheduledExecutorService myScheduledExecutorService;

    public HelloEndpoint(ScheduledExecutorService myScheduledExecutorService) {
        this.myScheduledExecutorService = myScheduledExecutorService;
    }

    @GetMapping("/pause")
    public String pause() {
        logger.info("Pause pressed");
        ((MyScheduledExecutorService)myScheduledExecutorService).pause();
        return "OK";
    }

    @GetMapping("/resume")
    public String resume() {
        logger.info("Resume pressed");
        ((MyScheduledExecutorService)myScheduledExecutorService).resume();
        return "OK";
    }
}
