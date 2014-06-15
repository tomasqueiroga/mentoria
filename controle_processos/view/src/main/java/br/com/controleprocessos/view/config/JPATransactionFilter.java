package br.com.controleprocessos.view.config;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpStatus;
import org.slf4j.Logger;

import br.com.controleprocessos.business.config.persist.EntityManagerFactoryWrapper;
import br.com.controleprocessos.business.config.persist.EntityManagerUtil;
import br.com.controleprocessos.business.domain.ValidationException;
import br.com.controleprocessos.view.utils.GsonUtils;

public class JPATransactionFilter implements javax.servlet.Filter {

	private Logger logger = org.slf4j.LoggerFactory.getLogger(JPATransactionFilter.class);

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		EntityManager entityManager = null;
		EntityTransaction entityTransaction = null;

		try {
			entityManager = EntityManagerFactoryWrapper.getEntityManager();

			entityTransaction = entityManager.getTransaction();
			entityTransaction.begin();
			EntityManagerUtil.ENTITY_MANAGERS.set(entityManager);

			chain.doFilter(request, response);

			EntityManagerUtil.ENTITY_MANAGERS.remove();
			entityTransaction.commit();
		} catch (Exception e) {
			HttpServletResponse resp = (HttpServletResponse) response;
			trataExcecao(e, resp, entityTransaction);
		} finally {
			try {
				if (entityManager != null)
					entityManager.close();
			} catch (Throwable t) {
				logger.error("Error while closing an EntityManager", t);
			}
		}
	}

	private void trataExcecao(Exception e, HttpServletResponse resp, EntityTransaction entityTransaction) throws IOException {
		if (e.getCause() instanceof ValidationException) {
			resp.setStatus(HttpStatus.SC_BAD_REQUEST);
			resp.getWriter().print(new GsonUtils().messagesToJson(((ValidationException) e.getCause()).getMessages()));
		} else {
			e.printStackTrace();
			resp.setStatus(HttpStatus.SC_INTERNAL_SERVER_ERROR);
		}

		try {
			entityTransaction.rollback();
		} catch (Exception ex) {
			logger.warn("Error while doing rollback", ex);
		}
	}

	@Override
	public void destroy() {

	}

}
