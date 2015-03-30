/**
 * 
 */
package com.redhat.it.customers.dmc.core.dto.configuration;

import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonSubTypes;
import org.codehaus.jackson.annotate.JsonSubTypes.Type;
import org.codehaus.jackson.annotate.JsonTypeInfo;

import com.redhat.it.customers.dmc.core.constants.Constants;
import com.redhat.it.customers.dmc.core.enums.MetricType;

/**
 * The Class Configuration.
 *
 * @author Andrea Battaglia
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Configuration", namespace = "dto.dmc.customers.it.redhat.com")
@XmlSeeAlso({ AppConfiguration.class, InstanceConfiguration.class,
        JvmConfiguration.class })
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "metricType")
@JsonSubTypes({ @Type(value = AppConfiguration.class, name = "APP"),
        @Type(value = InstanceConfiguration.class, name = "INSTANCE"),
        @Type(value = JvmConfiguration.class, name = "JVM") })
public abstract class Configuration {

    /** The id. */
    protected String id;

    /** The metric type. */
    protected MetricType metricType;

    /** The hostname. */
    protected String hostname = Constants.DEFAULT_HOST.getValue();

    /** The port. */
    protected int port = Integer.parseInt(Constants.DEFAULT_PORT.getValue());

    /** The username. */
    protected String username;

    /** The password. */
    protected String password;

    /** The realm. */
    protected String realm;

    /** The regexp hostname. */
    protected String regexpHostname = Constants.REGEXP_CATCH_ALL.getValue();

    /** The regexp server. */
    protected String regexpServer = Constants.REGEXP_CATCH_ALL.getValue();

    /** The scan interval. */
    protected int scanInterval = Integer
            .parseInt(Constants.DEFAULT_SCAN_INTERVAL.getValue());

    /** The start. */
    protected boolean start;

    /** The depth. */
//    protected int depth = Integer.parseInt(Constants.DEFAULT_APP_OBJECT_DEPTH
//            .getValue());

    /** The app object attribute configurations. */
//    protected Map<Integer, AppObjectAttributeConfiguration> appObjectAttributeConfigurations;

    /**
     * Instantiates a new configuration.
     */
    public Configuration() {
    }

    /**
     * Gets the id.
     *
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the id.
     *
     * @param id
     *            the new id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the metric type.
     *
     * @return the metric type
     */
    @JsonIgnore
    public MetricType getMetricType() {
        return metricType;
    }

    /**
     * Gets the hostname.
     *
     * @return the hostname
     */
    public String getHostname() {
        return hostname;
    }

    /**
     * Sets the hostname.
     *
     * @param hostname
     *            the new hostname
     */
    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    /**
     * Gets the port.
     *
     * @return the port
     */
    public int getPort() {
        return port;
    }

    /**
     * Sets the port.
     *
     * @param port
     *            the new port
     */
    public void setPort(int port) {
        this.port = port;
    }

    /**
     * Gets the username.
     *
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username.
     *
     * @param username
     *            the new username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets the password.
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password.
     *
     * @param password
     *            the new password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the realm.
     *
     * @return the realm
     */
    public String getRealm() {
        return realm;
    }

    /**
     * Sets the realm.
     *
     * @param realm
     *            the new realm
     */
    public void setRealm(String realm) {
        this.realm = realm;
    }

    /**
     * Gets the regexp hostname.
     *
     * @return the regexp hostname
     */
    public String getRegexpHostname() {
        return regexpHostname;
    }

    /**
     * Sets the regexp hostname.
     *
     * @param regexpHostname
     *            the new regexp hostname
     */
    public void setRegexpHostname(String regexpHostname) {
        this.regexpHostname = regexpHostname;
    }

    /**
     * Gets the regexp server.
     *
     * @return the regexp server
     */
    public String getRegexpServer() {
        return regexpServer;
    }

    /**
     * Sets the regexp server.
     *
     * @param regexpServer
     *            the new regexp server
     */
    public void setRegexpServer(String regexpServer) {
        this.regexpServer = regexpServer;
    }

    /**
     * Gets the scan interval.
     *
     * @return the scan interval
     */
    public int getScanInterval() {
        return scanInterval;
    }

    /**
     * Sets the scan interval.
     *
     * @param scanInterval
     *            the new scan interval
     */
    public void setScanInterval(int scanInterval) {
        this.scanInterval = scanInterval;
    }

    /**
     * Checks if is start.
     *
     * @return the start
     */
    public boolean isStart() {
        return start;
    }

    /**
     * Sets the start.
     *
     * @param start
     *            the start to set
     */
    public void setStart(boolean start) {
        this.start = start;
    }

    /**
     * Gets the depth.
     *
     * @return the depth
     */
//    public int getDepth() {
//        return depth;
//    }

    /**
     * Sets the depth.
     *
     * @param depth
     *            the new depth
     */
//    public void setDepth(int depth) {
//        this.depth = depth;
//    }

    /**
     * Gets the app object attribute configurations.
     *
     * @return the app object attribute configurations
     */
//    public Map<Integer, AppObjectAttributeConfiguration> getAppObjectAttributeConfigurations() {
//        return appObjectAttributeConfigurations;
//    }

    /**
     * Sets the app object attribute configurations.
     *
     * @param appObjectAttributeConfigurations
     *            the app object attribute configurations
     */
//    public void setAppObjectAttributeConfigurations(
//            Map<Integer, AppObjectAttributeConfiguration> appObjectAttributeConfigurations) {
//        this.appObjectAttributeConfigurations = appObjectAttributeConfigurations;
//    }

    /**
     * Hash code.
     *
     * @return the int
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = (prime * result) + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    /**
     * Equals.
     *
     * @param obj
     *            the obj
     * @return true, if successful
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Configuration other = (Configuration) obj;
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        return true;
    }

    /**
     * To string.
     *
     * @return the string
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Configuration [id=");
        builder.append(id);
        builder.append(", hostname=");
        builder.append(hostname);
        builder.append(", port=");
        builder.append(port);
        builder.append(", username=");
        builder.append(username);
        builder.append(", password=");
        builder.append(password);
        builder.append(", realm=");
        builder.append(realm);
        builder.append(", regexpHostname=");
        builder.append(regexpHostname);
        builder.append(", regexpServer=");
        builder.append(regexpServer);
        builder.append(", scanInterval=");
        builder.append(scanInterval);
        builder.append(", start=");
        builder.append(start);
        builder.append("]");
        return builder.toString();
    }

}
