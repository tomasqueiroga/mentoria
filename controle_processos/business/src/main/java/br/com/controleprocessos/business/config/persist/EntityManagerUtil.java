package br.com.controleprocessos.business.config.persist;

import javax.persistence.EntityManager;

public class EntityManagerUtil {

	public static final ThreadLocal<EntityManager> ENTITY_MANAGERS = new ThreadLocal<EntityManager>();

	public static EntityManager getEntityManager() {
		return ENTITY_MANAGERS.get();
	}
}
