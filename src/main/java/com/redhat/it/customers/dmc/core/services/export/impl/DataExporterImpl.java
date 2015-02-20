/**
 * 
 */
package com.redhat.it.customers.dmc.core.services.export.impl;

import java.io.IOException;

import javax.enterprise.context.Dependent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.redhat.it.customers.dmc.core.dto.configuration.AbstractOpenableBean;
import com.redhat.it.customers.dmc.core.services.export.DataExporter;
import com.redhat.it.customers.dmc.core.util.CSVFunctions;

/**
 * @author Andrea Battaglia
 *
 */
// @ThreadScoped
@Dependent
public class DataExporterImpl extends AbstractOpenableBean implements
        DataExporter {
    /**
     * Logger for this class
     */
    private static final Logger LOG = LoggerFactory.getLogger("exportLogger");

    private CSVFunctions csvFunctions = null;

    private char separator;

    private char quotechar;

    private char escapechar;

    private String lineEnd;

    // private static final Logger templateLogger = (Logger) LoggerFactory
    // .getLogger(Constants.TEMPLATE_LOGGER_NAME.getValue());
    // private PatternLayoutEncoder encoder = null;
    // private RollingFileAppender<ILoggingEvent> rollingFileAppender = null;
    // private TimeBasedRollingPolicy<ILoggingEvent> timeBasedRollingPolicy =
    // null;

    @Override
    public void setFormatterConfiguration(char separator, char quotechar,
            char escapechar, String lineEnd) {
        this.separator = separator;
        this.quotechar = quotechar;
        this.escapechar = escapechar;
        this.lineEnd = lineEnd;

    }

    private void buildFileAppender() {
        // LoggerContext context = null;
        // DefaultTimeBasedFileNamingAndTriggeringPolicy<ILoggingEvent>
        // timeBasedTriggeringPolicy = null;
        //
        // context = templateLogger.getLoggerContext();
        //
        // // String logDir = context.getProperty("HOME_PATH");
        //
        // encoder = new PatternLayoutEncoder();
        // encoder.setPattern("%m");
        // encoder.setContext(context);
        //
        // timeBasedTriggeringPolicy = new
        // DefaultTimeBasedFileNamingAndTriggeringPolicy<ILoggingEvent>();
        // timeBasedTriggeringPolicy.setContext(context);
        //
        // timeBasedRollingPolicy = new TimeBasedRollingPolicy<ILoggingEvent>();
        // timeBasedRollingPolicy.setContext(context);
        // timeBasedRollingPolicy.setFileNamePattern(fileName + '.'
        // + context.getProperty("MYAPP_ROLLING_TEMPLATE"));
        // timeBasedRollingPolicy
        // .setTimeBasedFileNamingAndTriggeringPolicy(timeBasedTriggeringPolicy);
        // timeBasedTriggeringPolicy
        // .setTimeBasedRollingPolicy(timeBasedRollingPolicy);
        //
        // rollingFileAppender = new RollingFileAppender<ILoggingEvent>();
        // rollingFileAppender.setAppend(true);
        // rollingFileAppender.setContext(context);
        // rollingFileAppender.setEncoder(encoder);
        // rollingFileAppender.setFile(fileName);
        // rollingFileAppender.setName(configurationId + "Appender");
        // rollingFileAppender.setPrudent(false);
        // rollingFileAppender.setRollingPolicy(timeBasedRollingPolicy);
        // rollingFileAppender.setTriggeringPolicy(timeBasedTriggeringPolicy);
        //
        // timeBasedRollingPolicy.setParent(rollingFileAppender);
        //
        //
        // Logger exportLogger = (Logger) LogUtil.getLogger(name);
        // exportLogger.setLevel(templateLogger.getLevel());
        // exportLogger.setAdditive(false);
        // exportLogger.addAppender(rollingFileAppender);
        //
        // templateLogger.addAppender(rollingFileAppender);
    }

    @Override
    public void open() throws IOException {
        super.open();
        if (this.lineEnd != null) {
            csvFunctions = CSVFunctions.getInstance(separator, quotechar,
                    escapechar, lineEnd);
        } else {
            csvFunctions = CSVFunctions.getInstance();
        }
    }

    @Override
    public void close() throws IOException {
        super.close();
        csvFunctions = null;
        // rollingFileAppender.stop();
    }

    /**
     * @see com.redhat.it.customers.dmc.core.services.export.DataExporter#writeData(java.lang.String)
     */
    @Override
    public void writeData(String[] rawData) {
        LOG.trace(csvFunctions.prepareCSVLine(rawData));
    }

}
