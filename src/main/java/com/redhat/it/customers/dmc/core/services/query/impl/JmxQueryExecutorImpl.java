package com.redhat.it.customers.dmc.core.services.query.impl;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;
import javax.management.AttributeNotFoundException;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanException;
import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.ReflectionException;
import javax.management.openmbean.CompositeData;
import javax.management.openmbean.CompositeDataSupport;
import javax.management.openmbean.CompositeType;
import javax.management.openmbean.OpenType;
import javax.management.openmbean.SimpleType;
import javax.management.openmbean.TabularData;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

import org.jboss.weld.environment.se.contexts.ThreadScoped;
import org.slf4j.Logger;

import com.redhat.it.customers.dmc.core.constants.Constants;
import com.redhat.it.customers.dmc.core.dto.collector.qd.QueryData;
import com.redhat.it.customers.dmc.core.dto.collector.qd.raw.AbstractRawQueryData;
import com.redhat.it.customers.dmc.core.exceptions.DMCCloseException;
import com.redhat.it.customers.dmc.core.exceptions.DMCOpenException;
import com.redhat.it.customers.dmc.core.exceptions.DMRException;

/**
 * The Class JmxQueryExecutorImpl.
 * 
 * @author Andrea Battaglia
 */
// @ThreadScoped
public class JmxQueryExecutorImpl extends
        AbstractQueryExecutorImpl<JMXConnector> {

    @Inject
    private Logger LOG;

    protected String object;
    protected String attribute;
    protected String path;

    private JMXConnector jmxConnector;

    /**
     * Sets the object.
     *
     * @param object
     *            the new object
     */
    public void setObject(String object) {
        this.object = object;
    }

    /**
     * Sets the path.
     *
     * @param path
     *            the new path
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * Sets the attribute.
     *
     * @param attribute
     *            the new attribute
     */
    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    /**
     * @see com.redhat.it.customers.dmc.core.services.query.QueryExecutor#extractData()
     */
    @Override
    public AbstractRawQueryData extractData() throws Exception {
        String toReturn = null;
        MBeanServerConnection connection = jmxConnector
                .getMBeanServerConnection();

        // LOG.debug("object: " + object);
        ObjectName objectName = new ObjectName(object);

        Object value = connection.getAttribute(objectName, attribute);
        // path "memoryUsageBeforeGc/value/committed/"
        // try {
        toReturn = dump(value, null, path);
        // } catch (SpeedException spe) {
        // return spe.getMessage() + "\n";
        // }

        return null;
    }

    /**
     * @see com.redhat.it.customers.dmc.core.interfaces.Openable#open()
     */
    @Override
    public void open() throws DMCOpenException {
        try {
            String urlString = Constants.SERVICE_JMX_REMOTING_PREFIX + hostname
                    + ":" + port;

            LOG.debug("contacting server at " + urlString);

            JMXServiceURL serviceURL = new JMXServiceURL(urlString);

            Map<String, String[]> properties = new HashMap<String, String[]>();

            String[] credentials = new String[] { username, password.trim() };
            properties.put("jmx.remote.credentials", credentials);

            jmxConnector = JMXConnectorFactory.connect(serviceURL, properties);
        } catch (Exception e) {
            throw new DMCOpenException(e);
        }
    }

    /**
     * @see java.io.Closeable#close()
     */
    @Override
    public void close() throws DMCCloseException {
        try {
            jmxConnector.close();
        } catch (IOException e) {
            throw new DMCCloseException(e);
        }
    }

    /**
     * Dump.
     *
     * @param obj
     *            the obj
     * @param initialPath
     *            the initial path
     * @param requestedPath
     *            the requested path
     * @return the string
     * @throws SpeedException
     *             the speed exception
     */
    protected String dump(Object obj, String initialPath,
            String... requestedPath) throws DMRException {
        StringBuffer strBuf = new StringBuffer("");
        String path = null;

        if (obj instanceof CompositeDataSupport) {

            CompositeDataSupport data = (CompositeDataSupport) obj;
            CompositeType compositeType = data.getCompositeType();
            Set<String> keys = compositeType.keySet();
            for (String key : keys) {
                if (initialPath != null) {
                    path = initialPath + ((key.length() != 0) ? key + "/" : "");
                } else {
                    path = (key.length() != 0) ? key + "/" : "";
                }
                // strBuf.append("\n" + path );
                Object value = data.get(key);
                OpenType<?> type = compositeType.getType(key);
                if (type instanceof SimpleType) {
                    if ((requestedPath != null) && (requestedPath[0] != null)
                            && (requestedPath.length > 0)
                            && requestedPath[0].equals(path)) {
                        throw new DMRException(value.toString());
                    }
                    strBuf.append("\n" + key + ":" + value);

                } else {
                    if ((value instanceof CompositeData)
                            || (value instanceof TabularData)) {
                        // strBuf.append();
                        if ((requestedPath != null)
                                && (requestedPath[0] != null)
                                && (requestedPath.length > 0)
                                && requestedPath[0].equals(path)) {
                            throw new DMRException("\n"
                                    + key
                                    + ":"
                                    + dump(value, path, requestedPath)
                                            .replaceAll("\\n", "\\\n\\\t"));
                        }
                        strBuf.append("\n"
                                + key
                                + ":"
                                + dump(value, path, requestedPath).replaceAll(
                                        "\\n", "\\\n\\\t"));
                    }

                }

            }
            return strBuf.toString();

        } else if (obj instanceof TabularData) {
            if (initialPath != null) {
                if (!initialPath.endsWith("/")) {
                    path = initialPath + "/";
                } else {
                    path = initialPath;
                }
            }

            TabularData mapToDump = (TabularData) obj;
            Collection<?> values = mapToDump.values();
            for (Object value : values) {
                if (value instanceof CompositeDataSupport) {
                    CompositeDataSupport compData = (CompositeDataSupport) value;
                    if ((requestedPath != null) && (requestedPath[0] != null)
                            && (requestedPath.length > 0)
                            && requestedPath[0].equals(path)) {
                        throw new DMRException(dump(compData, path,
                                requestedPath));
                    }
                    strBuf.append(dump(compData, path, requestedPath));

                    return strBuf.toString();
                }
            }

        }

        return obj.toString();

    }

}
