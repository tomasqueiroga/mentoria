package br.com.controleprocessos.business.config.persist;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManagerFactoryWrapper {

	private static EntityManagerFactory factory;

	public static EntityManager getEntityManager() {
		return getEntityManagerFactory().createEntityManager();
	}

	public static EntityManager getEntityManager(String jndi) {
		try {
			return (EntityManager) new InitialContext().lookup(jndi);
		} catch (NamingException e) {
			throw new RuntimeException(e);
		}
	}

	public static synchronized EntityManagerFactory getEntityManagerFactory() {
		if (factory == null)
			factory = Persistence.createEntityManagerFactory(System.getProperty("persistence-unit", "controleprocessos-pu"));

		return factory;
	}

	public static EntityManagerWrapper getEntityManagerWrapper() {
		return new EntityManagerWrapperImpl();
	}

}
