package br.com.controleprocessos.view.config.rest;

import java.lang.reflect.Method;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.ext.Provider;

import org.jboss.resteasy.annotations.interception.ServerInterceptor;
import org.jboss.resteasy.core.ResourceMethod;
import org.jboss.resteasy.core.ServerResponse;
import org.jboss.resteasy.spi.Failure;
import org.jboss.resteasy.spi.HttpRequest;
import org.jboss.resteasy.spi.interception.AcceptedByMethod;
import org.jboss.resteasy.spi.interception.PreProcessInterceptor;

@Provider
@ServerInterceptor
public class ResteasySecurityInterceptor implements PreProcessInterceptor, AcceptedByMethod {

	@SuppressWarnings("rawtypes")
	@Override
	public boolean accept(Class clazz, Method method) {
		return true;
	}

	@Override
	public ServerResponse preProcess(HttpRequest request, ResourceMethod method) throws Failure, WebApplicationException {
		ServerResponse serverResponse = new ServerResponse();

		return serverResponse;
	}

}
