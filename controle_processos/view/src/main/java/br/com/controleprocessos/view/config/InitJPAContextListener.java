package br.com.controleprocessos.view.config;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import br.com.controleprocessos.business.config.persist.EntityManagerFactoryWrapper;

public class InitJPAContextListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		try {
			EntityManagerFactoryWrapper.getEntityManager();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
	}

}
