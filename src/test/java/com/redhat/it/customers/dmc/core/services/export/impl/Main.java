package com.redhat.it.customers.dmc.core.services.export.impl;

import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.PatternLayout;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.FileAppender;

public class Main {

    public static void main(String[] args) {
        Logger logger = (Logger) LoggerFactory.getLogger("abc.xyz");

        LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
        FileAppender<ILoggingEvent> fileAppender = (FileAppender<ILoggingEvent>) logger
                .getAppender("file");
        if (fileAppender != null) {
            fileAppender.stop();
            fileAppender.setFile("new.log");
            PatternLayout pl = new PatternLayout();
            pl.setPattern("%d %5p %t [%c:%L] %m%n)");
            pl.setContext(lc);
            pl.start();
            fileAppender.setLayout(pl);
            fileAppender.setContext(lc);
            fileAppender.start();
        }
        // ... etc
    }
}