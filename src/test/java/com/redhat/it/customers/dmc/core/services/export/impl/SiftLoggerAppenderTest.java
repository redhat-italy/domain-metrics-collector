package com.redhat.it.customers.dmc.core.services.export.impl;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.*;

import org.jboss.logging.MDC;
import org.junit.AfterClass;
import org.junit.Test;

public class SiftLoggerAppenderTest {
    /**
     * Logger for this class
     */
    private static final Logger LOG = LoggerFactory
            .getLogger(SiftLoggerAppenderTest.class);
    
    static {
        System.setProperty("core.export.path", "/dmc/test/log/sift/");
        System.setProperty("core.export.file.extension", "CSVXXX");
    }

    class MyThread implements Runnable {

        private final int id;

        public MyThread(int id) {
            super();
            this.id = id;
        }

        @Override
        public void run() {
            MDC.put("collectorId", Integer.toString(id));
            for (int i = 0; i < 5; i++) {
                LOG.trace(Long.toString(System.currentTimeMillis()));
                synchronized (this) {
                    try {
                        wait(500);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        }

    }

    @Test
    public void test() {
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 5; i++) {
            executorService.submit(new MyThread(i));
        }
        try {
            executorService.awaitTermination(120, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        executorService.shutdown();
    }

}
