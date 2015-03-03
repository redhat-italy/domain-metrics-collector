package com.redhat.it.customers.dmc.core.services.connection.impl;

import java.io.IOException;
import java.net.ConnectException;
import java.net.MalformedURLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.management.AttributeNotFoundException;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanException;
import javax.management.MBeanServerConnection;
import javax.management.MalformedObjectNameException;
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
import javax.security.sasl.SaslException;

import org.slf4j.Logger;

import com.redhat.it.customers.dmc.core.exceptions.DMRException;
import com.redhat.it.customers.dmc.core.services.connection.JMXFacade;

/**
 * The Class JMXFacade.
 * 
 * @author Andrea Battaglia
 */
@Singleton
public class JMXFacadeImpl implements JMXFacade {

    /** The log. */
    @Inject
    private Logger LOG;

    // /**
    // * @see
    // com.redhat.it.customers.dmc.core.services.connection.JMXFacade#execute(java.lang.String,
    // * java.lang.String, java.lang.String, java.lang.String,
    // * java.lang.String, java.lang.String, java.lang.String)
    // */
    // @Override
    // public String execute(String host, String port, String username,
    // String password, String object, String attribute, String path)
    // throws DMCException, MalformedObjectNameException,
    // AttributeNotFoundException, InstanceNotFoundException,
    // MBeanException, ReflectionException, IOException {
    // if (LOG.isDebugEnabled()) {
    // LOG.debug(
    // "execute(String username={}, String password={}, String host={}, String port={}, String object={}, String attribute={}, String path={}) - start",
    // username, password, host, port, object, attribute, path);
    // }
    //
    // String toReturn = null;
    //
    // String urlString = "service:jmx:remoting-jmx://" + host + ":" + port;
    //
    // LOG.debug("contacting server at " + urlString);
    //
    // JMXServiceURL serviceURL = new JMXServiceURL(urlString);
    // Map<String, String[]> properties = new HashMap<String, String[]>();
    //
    // String[] credentials = new String[] { username, password };
    // properties.put("jmx.remote.credentials", credentials);
    // JMXConnector jmxConnector = JMXConnectorFactory.connect(serviceURL,
    // properties);
    // MBeanServerConnection connection = jmxConnector
    // .getMBeanServerConnection();
    // // LOG.debug("object: " + object);
    // ObjectName objectName = new ObjectName(object);
    // Object value = connection.getAttribute(objectName, attribute);
    // jmxConnector.close();
    // // path "memoryUsageBeforeGc/value/committed/"
    // // try {
    // toReturn = dump(value, null, path);
    // // } catch (SpeedException spe) {
    // // return spe.getMessage() + "\n";
    // // }
    //
    // if (LOG.isDebugEnabled()) {
    // LOG.debug("execute(String, String, String, String, String, String, String) - end");
    // }
    // return toReturn;
    // }
    /**
     * @see com.redhat.it.customers.dmc.core.services.connection.JMXFacade#execute(java.lang.String,
     *      java.lang.String, java.lang.String, java.lang.String,
     *      java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public String execute(final String host, final String port,
            final String username, final String password, final String object,
            final String attribute, final String path) throws DMRException {
        if (LOG.isDebugEnabled()) {
            LOG.debug(
                    "execute(String username={}, String password={}, String host={}, String port={}, String object={}, String attribute={}, String path={}) - start",
                    username, password, host, port, object, attribute, path);
        }

        String toReturn = null;
        String urlString = null;
        JMXServiceURL serviceURL = null;
        Map<String, String[]> properties = null;
        String[] credentials = null;
        JMXConnector jmxConnector = null;
        MBeanServerConnection connection = null;
        ObjectName objectName = null;
        Object value = null;

        urlString = "service:jmx:remoting-jmx://" + host + ":" + port;

        LOG.debug("contacting server at " + urlString);

        try {
            serviceURL = new JMXServiceURL(urlString);
            properties = new HashMap<String, String[]>();

            credentials = new String[] { username, password };
            properties.put("jmx.remote.credentials", credentials);
            credentials = null;
            try {
                jmxConnector = JMXConnectorFactory.connect(serviceURL,
                        properties);
                serviceURL = null;
                properties = null;
                connection = jmxConnector.getMBeanServerConnection();
                // LOG.debug("object: " + object);
                try {
                    objectName = new ObjectName(object);
                    try {
                        value = connection.getAttribute(objectName, attribute);
                    } catch (AttributeNotFoundException e) {
                        // LOG.error("execute(String host=" + host
                        // + ", String port=" + port
                        // + ", String username=" + username
                        // + ", String password=" + password
                        // + ", String object=" + object
                        // + ", String attribute=" + attribute
                        // + ", String path=" + path + ")", e);

                        throw new DMRException(e);
                    } catch (InstanceNotFoundException e) {
                        // LOG.error("execute(String host=" + host
                        // + ", String port=" + port
                        // + ", String username=" + username
                        // + ", String password=" + password
                        // + ", String object=" + object
                        // + ", String attribute=" + attribute
                        // + ", String path=" + path + ")", e);

                        throw new DMRException(e);
                    } catch (MBeanException e) {
                        // LOG.error("execute(String host=" + host
                        // + ", String port=" + port
                        // + ", String username=" + username
                        // + ", String password=" + password
                        // + ", String object=" + object
                        // + ", String attribute=" + attribute
                        // + ", String path=" + path + ")", e);

                        throw new DMRException(e);
                    } catch (ReflectionException e) {
                        // LOG.error("execute(String host=" + host
                        // + ", String port=" + port
                        // + ", String username=" + username
                        // + ", String password=" + password
                        // + ", String object=" + object
                        // + ", String attribute=" + attribute
                        // + ", String path=" + path + ")", e);

                        throw new DMRException(e);
                    }
                } catch (MalformedObjectNameException e) {
                    // LOG.error("execute(String host=" + host +
                    // ", String port="
                    // + port + ", String username=" + username
                    // + ", String password=" + password
                    // + ", String object=" + object
                    // + ", String attribute=" + attribute
                    // + ", String path=" + path + ")", e);

                    throw new DMRException(e);
                } finally {
                    if ((jmxConnector != null)
                            && (jmxConnector.getConnectionId() != null)) {
                        try {
                            jmxConnector.close();
                        } catch (IOException e) {
                            // LOG.error("execute(String host=" + host
                            // + ", String port=" + port
                            // + ", String username=" + username
                            // + ", String password=" + password
                            // + ", String object=" + object
                            // + ", String attribute=" + attribute
                            // + ", String path=" + path + ")", e);
                        }
                    }
                    jmxConnector = null;
                }
                // path "memoryUsageBeforeGc/value/committed/"
                // try {
                try {
                    toReturn = dump(value, null, path);
                    // } catch (SpeedException spe) {
                    // return spe.getMessage() + "\n";
                    // }

                    value = null;
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("execute(String, String, String, String, String, String, String) - end");
                    }
                    return toReturn;
                } catch (DMRException e) {
                    throw e;
                }
            } catch (IOException e) {
                // LOG.error("execute(String host=" + host + ", String port="
                // + port + ", String username=" + username
                // + ", String password=" + password + ", String object="
                // + object + ", String attribute=" + attribute
                // + ", String path=" + path + ")", e);
                String errorMessage = "Error: ";
                String originalMessage = e.getMessage();
                if ((e instanceof ConnectException)
                        && originalMessage.contains("Connection refused")) {
                    errorMessage += "Connection refused: check connection param (host / port)";
                } else if ((e instanceof SaslException)
                        && originalMessage
                                .contains("all available authentication mechanisms failed")) {
                    errorMessage += "Invalid username or password";
                }
                throw new DMRException(errorMessage, e);
            }
        } catch (MalformedURLException e) {
            // LOG.error("execute(String host=" + host + ", String port=" + port
            // + ", String username=" + username + ", String password="
            // + password + ", String object=" + object
            // + ", String attribute=" + attribute + ", String path="
            // + path + ")", e);

            throw new DMRException(e);
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
     * @throws DMRException
     */
    private String dump(Object obj, String initialPath, String... requestedPath)
            throws DMRException {
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
