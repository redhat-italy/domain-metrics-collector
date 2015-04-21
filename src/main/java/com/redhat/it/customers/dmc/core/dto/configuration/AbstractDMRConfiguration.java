/**
 *
 */
package com.redhat.it.customers.dmc.core.dto.configuration;

/**
 * The Class AbstractDMRConfiguration.
 *
 * @author Andrea Battaglia (Red Hat)
 */
public class AbstractDMRConfiguration extends Configuration {

    /** The subsystem. */
    private String subsystem;

    /**
     * Gets the subsystem.
     *
     * @return the subsystem
     */
    public String getSubsystem() {
        return subsystem;
    }

    /**
     * Sets the subsystem.
     *
     * @param subsystem
     *            the subsystem to set
     */
    public void setSubsystem(String subsystem) {
        this.subsystem = subsystem;
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
        builder.append("AbstractDMRConfiguration [\n    subsystem=");
        builder.append(subsystem);
        builder.append(", \n    toString()=");
        builder.append(super.toString());
        builder.append("\n]");
        return builder.toString();
    }

}
