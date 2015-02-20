/**
 * 
 */
package com.redhat.it.customers.dmc.core.dto.configuration;

import java.util.Set;

/**
 * The Class Configuration.
 *
 * @author Andrea Battaglia
 */
public class Configuration {

    private String id;

    /** The hostname. */
    private String hostname;

    /** The port. */
    private int port;

    /** The username. */
    private String username;

    /** The password. */
    private String password;

    /** The realm. */
    private String realm;

    /** The regexp hostname. */
    private String regexpHostname;

    /** The regexp server. */
    private String regexpServer;

    /** The apps. */
    private Set<String> apps;

    private int scanInterval;

    private String fileName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
     * Gets the apps.
     *
     * @return the apps
     */
    public Set<String> getApps() {
        return apps;
    }

    /**
     * Sets the apps.
     *
     * @param apps
     *            the new apps
     */
    public void setApps(Set<String> apps) {
        this.apps = apps;
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
        builder.append(", apps=");
        builder.append(apps.toString());
        builder.append(", scanInterval=");
        builder.append(scanInterval);
        builder.append("]");
        return builder.toString();
    }

}
