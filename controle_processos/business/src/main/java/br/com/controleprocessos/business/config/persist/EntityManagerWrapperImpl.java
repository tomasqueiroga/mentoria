package br.com.controleprocessos.business.config.persist;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EntityManagerWrapperImpl implements EntityManagerWrapper {

	private static final Logger LOG = LoggerFactory.getLogger(EntityManagerWrapperImpl.class);

	private EntityManager em;

	public EntityManagerWrapperImpl() {
		em = EntityManagerUtil.getEntityManager();
	}

	@Override
	public Query createNativeQuery(String quertStr) {
		return em.createNativeQuery(quertStr);
	}

	@Override
	public <T> Query createNativeQuery(String queryStr, Class<T> resultClass) {
		return em.createNativeQuery(queryStr, resultClass);
	}

	@Override
	public Query createQuery(String queryStr) {
		return em.createQuery(queryStr);
	}

	@Override
	public <T> TypedQuery<T> createQuery(String queryStr, Class<T> resultClass) {
		return em.createQuery(queryStr, resultClass);
	}

	@Override
	public <T> T find(Class<T> resultClass, Object id) {
		return em.find(resultClass, id);
	}

	@Override
	public <T> List<T> findAll(Class<T> resultClass) {
		TypedQuery<T> qry = em.createQuery("FROM " + resultClass.getSimpleName() + " e ", resultClass);

		return qry.getResultList();
	}

	@Override
	public void flush() {
		em.flush();
	}

	@Override
	public <T> T merge(T t) {
		return em.merge(t);
	}

	@Override
	public <T> T persist(T t) {
		em.persist(t);
		return t;
	}

	@Override
	public <T> T refresh(T entity) {
		em.refresh(entity);
		return entity;
	}

	@Override
	public void remove(Object t) {
		t = em.merge(t);
		em.remove(t);
	}

	@Override
	public EntityTransaction getTransaction() {
		return em.getTransaction();
	}

	@Override
	public void detach(Object entity) {
		em.detach(entity);
	}

	@Override
	public void importSQL(URL url, String name) {
		LOG.info("Import sql file: " + url + " on " + name);

		BufferedReader sqlFile = null;
		try {
			sqlFile = new BufferedReader(new InputStreamReader(url.openStream()));

			importSQL(sqlFile, name);
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			if (sqlFile != null)
				try {
					sqlFile.close();
				} catch (IOException e) {
					LOG.error("Error closing...", e);
				}
		}
	}

	private void importSQL(BufferedReader in, String name) {
		Connection connection = null;
		Statement statement = null;

		try {
			connection = ((DataSource) new InitialContext().lookup(name)).getConnection();
			statement = connection.createStatement();

			String line = null;
			do {
				line = in.readLine();
				if (line != null) {
					if (LOG.isDebugEnabled()) {
						LOG.debug("executeUpdate: " + line);
					}
					statement.executeUpdate(line);
				}
			} while (line != null);

			connection.commit();
		} catch (NamingException e) {
			throw new RuntimeException(e);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			close(statement);
			close(connection);
		}
	}

	private void close(Connection connection) {
		if (connection != null)
			try {
				connection.close();
			} catch (SQLException e) {
				LOG.warn("Error closing", e);
			}
	}

	private void close(Statement statement) {
		if (statement != null)
			try {
				statement.close();
			} catch (SQLException e) {
				LOG.warn("Error closing", e);
			}
	}

}
