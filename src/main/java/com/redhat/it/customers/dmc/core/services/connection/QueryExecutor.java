/**
 * 
 */
package com.redhat.it.customers.dmc.core.services.connection;

import java.io.Closeable;
import java.io.IOException;
import java.util.Set;
import java.util.regex.Pattern;

import org.jboss.as.controller.client.ModelControllerClient;

import com.redhat.it.customers.dmc.core.interfaces.Openable;

// TODO: Auto-generated Javadoc
/**
 * The Interface QueryExecutor.
 *
 * @author Andrea Battaglia
 */
public interface QueryExecutor extends Closeable, Openable {

    /**
     * Extract header.
     *
     * @return the string[]
     * @throws IOException Signals that an I/O exception has occurred.
     */
    String[] extractHeader() throws IOException;

    /**
     * Extract data.
     *
     * @return the string[]
     * @throws IOException Signals that an I/O exception has occurred.
     */
    String[] extractData() throws IOException;

    /**
     * Sets the hostname.
     *
     * @param hostname the new hostname
     */
    void setHostname(String hostname);

    /**
     * Sets the port.
     *
     * @param port the new port
     */
    void setPort(int port);

    /**
     * Sets the username.
     *
     * @param username the new username
     */
    void setUsername(String username);

    /**
     * Sets the password.
     *
     * @param password the new password
     */
    void setPassword(String password);

    /**
     * Sets the realm.
     *
     * @param realm the new realm
     */
    void setRealm(String realm);


    /**
     * Sets the pattern hostname.
     *
     * @param patternHostname the new pattern hostname
     */
    void setPatternHostname(Pattern patternHostname);

    /**
     * Sets the pattern server.
     *
     * @param patternServer the new pattern server
     */
    void setPatternServer(Pattern patternServer);

    /**
     * Sets the apps.
     *
     * @param apps the new apps
     */
    void setApps(Set<String> apps);

}
