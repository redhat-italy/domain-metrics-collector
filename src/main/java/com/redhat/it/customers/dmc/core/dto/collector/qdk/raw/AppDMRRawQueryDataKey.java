package com.redhat.it.customers.dmc.core.dto.collector.qdk.raw;

/**
 * The Class AppDMRRawQueryDataKey.
 * 
 * @author Andrea Battaglia (Red Hat)
 */
public class AppDMRRawQueryDataKey extends DMRRawQueryDataKey {

    /** The deploy. */
    private final String deploy;

    /** The subdeploy. */
    private final String subdeploy;

    /** The subsystem. */
    private final String subsystem;

    /**
     * Instantiates a new app dmr raw query data key.
     *
     * @param configurationName
     *            the configuration name
     * @param timestamp
     *            the timestamp
     * @param host
     *            the host
     * @param server
     *            the server
     * @param deploy
     *            the deploy
     * @param subdeploy
     *            the subdeploy
     * @param subsystem
     *            the subsystem
     */
    public AppDMRRawQueryDataKey(final String configurationName,
            final long timestamp, final String host, final String server,
            final String deploy, final String subdeploy, final String subsystem) {
        super(configurationName, timestamp, host, server);
        this.deploy = deploy;
        this.subdeploy = subdeploy;
        this.subsystem = subsystem;
    }

    /**
     * Gets the deploy.
     *
     * @return the deploy
     */
    public String getDeploy() {
        return deploy;
    }

    /**
     * Gets the subdeploy.
     *
     * @return the subdeploy
     */
    public String getSubdeploy() {
        return subdeploy;
    }

    /**
     * Gets the subsystem.
     *
     * @return the subsystem
     */
    public String getSubsystem() {
        return subsystem;
    }

    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((deploy == null) ? 0 : deploy.hashCode());
        result = prime * result
                + ((subdeploy == null) ? 0 : subdeploy.hashCode());
        result = prime * result
                + ((subsystem == null) ? 0 : subsystem.hashCode());
        return result;
    }

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        AppDMRRawQueryDataKey other = (AppDMRRawQueryDataKey) obj;
        if (deploy == null) {
            if (other.deploy != null)
                return false;
        } else if (!deploy.equals(other.deploy))
            return false;
        if (subdeploy == null) {
            if (other.subdeploy != null)
                return false;
        } else if (!subdeploy.equals(other.subdeploy))
            return false;
        if (subsystem == null) {
            if (other.subsystem != null)
                return false;
        } else if (!subsystem.equals(other.subsystem))
            return false;
        return true;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return new AppDMRRawQueryDataKey(getConfigurationName(),
                getTimestamp(), getHost(), getServer(), deploy, subdeploy,
                subsystem)

        ;
    }

    /**
     * @see com.redhat.it.customers.dmc.core.dto.collector.qdk.raw.AbstractRawQueryDataKey#toString()
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("AppDMRRawQueryDataKey [");
        builder.append("\n    configurationName()=");
        builder.append(getConfigurationName());
        builder.append(", \n    timestamp()=");
        builder.append(getTimestamp());
        builder.append(", \n    host()=");
        builder.append(getHost());
        builder.append(", \n    server()=");
        builder.append(getServer());
        builder.append(", \n    deploy=");
        builder.append(deploy);
        builder.append(", \n    subdeploy=");
        builder.append(subdeploy);
        builder.append(", \n    subsystem=");
        builder.append(subsystem);
        builder.append("\n]");
        return builder.toString();
    }

}
