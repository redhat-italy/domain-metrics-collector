package com.redhat.it.customers.dmc.core.cdi.interceptors;

import java.io.Serializable;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;


@Logged
@Interceptor
public class LoggedInterceptor implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 3304422099528695578L;

    @AroundInvoke
    public Object logMethodEntry(InvocationContext invocationContext)
            throws Exception {
        System.out.println("Entering method: "
                + invocationContext.getMethod().getName() + " in class "
                + invocationContext.getMethod().getDeclaringClass().getName());

        return invocationContext.proceed();
    }
}