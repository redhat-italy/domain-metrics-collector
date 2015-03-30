/**
 * 
 */
package com.redhat.it.customers.dmc.core.logger.appender;

import java.io.IOException;

import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.CDI;

import ch.qos.logback.core.rolling.RollingFileAppender;

/**
 * @author Andrea Battaglia (Red Hat)
 *
 */
public class HeaderRollingFileAppender<E> extends RollingFileAppender<E> {
    @Override
    public void rollover() {
        super.rollover();
        writeHeader();
    }

    private void writeHeader() {
        try {
            String txt = getHeader();
            getOutputStream().write(txt.getBytes());
            getOutputStream().flush();
        } catch (IOException e) {
        }

    }

    private String getHeader() {
        // BeanManager beanManager = CDI.current().getBeanManager();
        return "cicciopasticcio,pippo,pluto,paperino";
    }
}
