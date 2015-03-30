package com.redhat.it.customers.dmc.core.services.query.impl;
///**
// * 
// */
//package com.redhat.it.customers.dmc.core.services.query.impl;
//
//import java.util.regex.Pattern;
//
//import javax.enterprise.context.ApplicationScoped;
//import javax.enterprise.context.Initialized;
//import javax.enterprise.event.Observes;
//import javax.inject.Inject;
//
//import org.slf4j.Logger;
//
//import com.redhat.it.customers.dmc.core.dto.configuration.Configuration;
//import com.redhat.it.customers.dmc.core.exceptions.DMRException;
//import com.redhat.it.customers.dmc.core.services.configuration.ConfigurationService;
//import com.redhat.it.customers.dmc.core.services.query.ConnectionService;
//import com.redhat.it.customers.dmc.core.services.query.QueryExecutor;
//
///**
// * The Class ConnectionManagerServiceImpl.
// *
// * @author Andrea Battaglia
// */
//@ApplicationScoped
//public class ConnectionServiceImpl implements ConnectionService {
//
//    /** The log. */
//    @Inject
//    private Logger LOG;
//
//    @Inject
//    private ConfigurationService configurationService;
//
//    void init(@Observes @Initialized(ApplicationScoped.class) Object event) {
//        LOG.debug("Starting");
//    }
//
//    /**
//     * @throws DMRException
//     * @see com.redhat.it.customers.dmc.core.services.query.ConnectionService#getControllerConnection()
//     */
//    @Override
//    public QueryExecutor getQueryExecutor(String configurationId)
//            throws DMRException {
//        AbstractQueryExecutorImpl queryExecutor = null;
//        Configuration configuration = configurationService
//                .getConfiguration(configurationId);
//        queryExecutor = new AbstractQueryExecutorImpl();
//        queryExecutor.setUsername(configuration.getUsername());
//        queryExecutor.setPassword(configuration.getPassword());
//        queryExecutor.setRealm(configuration.getRealm());
//        queryExecutor
//                .setPatternHostname(configuration.getRegexpHostname() == null ? null
//                        : Pattern.compile(configuration.getRegexpHostname()));
//        queryExecutor
//                .setPatternServer(configuration.getRegexpServer() == null ? null
//                        : Pattern.compile(configuration.getRegexpServer()));
////        queryExecutor.setApps(configuration.getApps());
//        return queryExecutor;
//    }
//}
