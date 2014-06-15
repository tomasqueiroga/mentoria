package br.com.controleprocessos.view.config.rest;

import java.util.HashSet;
import java.util.Set;

import br.com.controleprocessos.view.user.UserRS;

public class Application extends javax.ws.rs.core.Application {

	@Override
	public Set<Class<?>> getClasses() {
		Set<Class<?>> classes = new HashSet<Class<?>>();

		classes.add(UserRS.class);

		return classes;
	}
	
}
