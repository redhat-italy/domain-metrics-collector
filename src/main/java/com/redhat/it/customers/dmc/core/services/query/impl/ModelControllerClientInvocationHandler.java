package com.redhat.it.customers.dmc.core.services.query.impl;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.ConnectException;

import org.jboss.as.controller.client.ModelControllerClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ModelControllerClientInvocationHandler implements
        InvocationHandler {
    private static final Logger LOG = LoggerFactory
            .getLogger(ModelControllerClientInvocationHandler.class);
    private ModelControllerClient client;

    public static ModelControllerClient buildProxy(ModelControllerClient client) {
        return (ModelControllerClient) Proxy.newProxyInstance(
                ModelControllerClient.class.getClassLoader(),
                new Class[] { ModelControllerClient.class },
                new ModelControllerClientInvocationHandler(client));
    }

    public void await() {
        try {
            int retryTime = 1;
            synchronized (this) {
                wait((int) (2 * retryTime * Math.random()));
            }
        } catch (Exception e) {
            LOG.trace("sleep()", e);
        }
    }

    private ModelControllerClientInvocationHandler(
            ModelControllerClient clabService) {
        this.client = clabService;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args)
            throws Throwable {

        String methodName = method.getName();
        if (methodName.startsWith("sendImmediateMessage")) {
            return method.invoke(client, args);
        }

        if (methodName.equals("borrowToken")) {
            return (callConnectMethod(method, args));
        } else {
            return callAuthenticatedMethod(method, args);
        }
    }

    private Object callConnectMethod(Method method, Object[] args)
            throws Throwable {
        LOG.trace(
                "callBorrowTokenMethod(Object clabService={}, Method method={}, Object[] args={}) - start",
                client, method, args);

        while (true) {
            try {
                return method.invoke(client, args);
            } catch (InvocationTargetException e) {
                Throwable targetException = e.getTargetException();
                Throwable targetCause = targetException.getCause();
                if (targetCause != null
                        && targetCause instanceof ConnectException) {
                    LOG.error("", targetException);
                } else {
                    throw targetException;
                }
            }
            await();
        }
    }

    private Object callAuthenticatedMethod(Method method, Object[] args)
            throws Throwable {

        Throwable targetException = null;
        for (int i = 0; i < 100; i++) {
            try {
                return method.invoke(client, args);
            } catch (InvocationTargetException e) {
                targetException = e.getTargetException();
                Throwable targetCause = targetException.getCause();
                if (targetCause == null
                        || !(targetCause instanceof ConnectException)) {
                    throw targetException;
                }
                LOG.error("", targetException);
            }
            await();
        }
        if (targetException != null) {
            throw targetException;
        }
        return null;
    }

    public static ModelControllerClient buildProxy(String hostname, int port,
            String username, String password, String realm) {
        // TODO Auto-generated method stub
        return null;
    }
}
