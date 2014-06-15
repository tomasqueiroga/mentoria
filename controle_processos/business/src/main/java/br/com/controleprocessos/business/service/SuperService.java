package br.com.controleprocessos.business.service;

import java.util.List;

import br.com.controleprocessos.business.config.persist.EntityManagerFactoryWrapper;
import br.com.controleprocessos.business.config.persist.EntityManagerWrapper;
import br.com.controleprocessos.business.domain.SuperEntity;

public abstract class SuperService {

	protected EntityManagerWrapper emw = EntityManagerFactoryWrapper.getEntityManagerWrapper();

	public <T extends SuperEntity> T save(T t) {
		if (t == null)
			return null;

		t.validate();

		if (t.getId() == null)
			return emw.persist(t);

		return emw.merge(t);
	}

	public abstract <T extends SuperEntity> Class<T> getServiceClass();

	@SuppressWarnings("unchecked")
	public <T extends SuperEntity> List<T> getAll() {
		return (List<T>) emw.findAll(getServiceClass());
	}

	@SuppressWarnings("unchecked")
	public <T extends SuperEntity> T getById(Long id) {
		if (id == null)
			return null;

		return (T) emw.find(getServiceClass(), id);
	}

	public boolean remove(Long id) {
		SuperEntity entity = getById(id);

		if (entity != null) {
			emw.remove(entity);
			return true;
		}

		return false;
	}
		
}
