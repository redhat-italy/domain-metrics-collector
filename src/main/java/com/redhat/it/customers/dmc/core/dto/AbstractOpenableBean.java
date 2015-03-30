/**
 * 
 */
package com.redhat.it.customers.dmc.core.dto;

import java.util.concurrent.atomic.AtomicBoolean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.redhat.it.customers.dmc.core.exceptions.BeanLifecycleException;
import com.redhat.it.customers.dmc.core.exceptions.DMCCloseException;
import com.redhat.it.customers.dmc.core.exceptions.DMCOpenException;
import com.redhat.it.customers.dmc.core.interfaces.Openable;

/**
 * The Class AbstractStartableBean.
 *
 * @author Andrea Battaglia
 */
public abstract class AbstractOpenableBean implements Openable {
    
    /** Logger for this class. */
    private static final Logger LOG = LoggerFactory
            .getLogger(AbstractOpenableBean.class);

    /** The started. */
    private final AtomicBoolean open;

    /**
     * Instantiates a new abstract startable bean.
     */
    public AbstractOpenableBean() {
        open = new AtomicBoolean();
    }

    @Override
    public void open() throws DMCOpenException {
        if (!open.compareAndSet(false, true)){
            LOG.error("Bean already opened.");
        }
    }

    /**
     * @see com.redhat.it.customers.dmc.core.interfaces.Openable#close()
     */
    @Override
    public void close() throws DMCCloseException {
        if (!open.compareAndSet(true, false)){
            LOG.error("Bean already closed.");
            throw new BeanLifecycleException("Bean already closed.");
        }
    }

}
