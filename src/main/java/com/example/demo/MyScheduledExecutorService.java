package com.example.demo;

import com.google.common.util.concurrent.Monitor;

import java.util.concurrent.ScheduledThreadPoolExecutor;

import static com.google.common.util.concurrent.Monitor.*;

public class MyScheduledExecutorService extends ScheduledThreadPoolExecutor {
    public MyScheduledExecutorService(int corePoolSize) {
        super(corePoolSize);
    }

    private boolean isPaused;

    private final Monitor monitor = new Monitor();
    private final Guard paused = new Guard(monitor) {
        @Override
        public boolean isSatisfied() {
            return isPaused;
        }
    };

    private final Guard notPaused = new Guard(monitor) {
        @Override
        public boolean isSatisfied() {
            return !isPaused;
        }
    };

    protected void beforeExecute(Thread t, Runnable r) {
        super.beforeExecute(t, r);
        monitor.enterWhenUninterruptibly(notPaused);
        try {
            monitor.waitForUninterruptibly(notPaused);
        } finally {
            monitor.leave();
        }
    }

    public void pause() {
        monitor.enterIf(notPaused);
        try {
            isPaused = true;
        } finally {
            monitor.leave();
        }
    }

    public void resume() {
        monitor.enterIf(paused);
        try {
            isPaused = false;
        } finally {
            monitor.leave();
        }
    }
}
