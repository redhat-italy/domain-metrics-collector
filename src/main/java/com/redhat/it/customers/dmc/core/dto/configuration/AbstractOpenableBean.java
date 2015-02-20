/**
 * 
 */
package com.redhat.it.customers.dmc.core.dto.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

import com.redhat.it.customers.dmc.core.exceptions.BeanLifecycleException;
import com.redhat.it.customers.dmc.core.exceptions.StartException;
import com.redhat.it.customers.dmc.core.exceptions.StopException;
import com.redhat.it.customers.dmc.core.interfaces.Openable;
import com.redhat.it.customers.dmc.core.interfaces.Startable;

/**
 * The Class AbstractStartableBean.
 *
 * @author Andrea Battaglia
 */
public abstract class AbstractOpenableBean implements Openable, Closeable {
    
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
    public void open() throws IOException {
        if (!open.compareAndSet(false, true)){
            LOG.error("Bean already opened.");
            throw new BeanLifecycleException("Bean already opened.");
        }
    }

    /**
     * Stop.
     *
     * @throws StopException the stop exception
     * @see com.redhat.it.customers.dmc.core.interfaces.Startable#stop()
     */
    @Override
    public void close() throws IOException {
        if (!open.compareAndSet(true, false)){
            LOG.error("Bean already closed.");
            throw new BeanLifecycleException("Bean already closed.");
        }
    }

}
