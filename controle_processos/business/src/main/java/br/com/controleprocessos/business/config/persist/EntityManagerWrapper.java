package br.com.controleprocessos.business.config.persist;

import java.net.URL;
import java.util.List;

import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

public interface EntityManagerWrapper {

	public Query createNativeQuery(String queryStr);

	public <T> Query createNativeQuery(String queryStr, Class<T> resultClass);

	public Query createQuery(String queryStr);

	public <T> TypedQuery<T> createQuery(String queryStr, Class<T> resultClass);

	public <T> T find(Class<T> resultClass, Object id);

	public <T> List<T> findAll(Class<T> resultClass);

	public void flush();

	public <T> T merge(T t);

	public <T> T persist(T t);

	public <T> T refresh(T entity);

	public void remove(Object t);

	public EntityTransaction getTransaction();

	public void detach(Object entity);

	public void importSQL(URL url, String fileName);

}
