package com.redhat.it.customers.dmc.core.cdi;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class SLF4JProducer.
 */
public class SLF4JProducer {

	/**
	 * Producer.
	 * 
	 * @param ip
	 *            the ip
	 * @return the logger
	 */
	@Produces
	public Logger produceLogger(InjectionPoint ip) {
		return LoggerFactory.getLogger(ip.getMember().getDeclaringClass()
				.getName());
	}
}