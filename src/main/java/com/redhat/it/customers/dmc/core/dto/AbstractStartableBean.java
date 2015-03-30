/**
 * 
 */
package com.redhat.it.customers.dmc.core.dto;

import java.util.concurrent.atomic.AtomicBoolean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.redhat.it.customers.dmc.core.exceptions.BeanLifecycleException;
import com.redhat.it.customers.dmc.core.exceptions.DMCStartException;
import com.redhat.it.customers.dmc.core.exceptions.DMCStopException;
import com.redhat.it.customers.dmc.core.interfaces.Startable;

/**
 * The Class AbstractStartableBean.
 *
 * @author Andrea Battaglia
 */
public abstract class AbstractStartableBean implements Startable {

    /** Logger for this class. */
    private static final Logger LOG = LoggerFactory
            .getLogger(AbstractStartableBean.class);

    /** The started. */
    private final AtomicBoolean started;

    /**
     * Instantiates a new abstract startable bean.
     */
    public AbstractStartableBean() {
        started = new AtomicBoolean();
    }

    /**
     *
     * @throws DMCStartException
     *             the start exception
     * @see com.redhat.it.customers.dmc.core.interfaces.Startable#start()
     */
    @Override
    public void start() throws DMCStartException {
        if (!started.compareAndSet(false, true)) {
            LOG.error("Bean already started.");
//            throw new BeanLifecycleException("Bean already started.");
        }
    }

    /**
     *
     * @throws DMCStopException
     *             the stop exception
     * @see com.redhat.it.customers.dmc.core.interfaces.Startable#stop()
     */
    @Override
    public void stop() throws DMCStopException {
        if (!started.compareAndSet(true, false)) {
            LOG.error("Bean already stopped.");
            throw new BeanLifecycleException("Bean already stopped.");
        }
    }

}
