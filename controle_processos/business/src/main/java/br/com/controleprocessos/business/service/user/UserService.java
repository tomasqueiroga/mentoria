package br.com.controleprocessos.business.service.user;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.TypedQuery;

import org.apache.commons.lang3.StringUtils;

import br.com.controleprocessos.business.domain.UserCP;
import br.com.controleprocessos.business.domain.ValidationException;
import br.com.controleprocessos.business.service.SuperService;

public class UserService extends SuperService {

	@SuppressWarnings("unchecked")
	@Override
	public Class<UserCP> getServiceClass() {
		return UserCP.class;
	}

	public List<UserCP> getByLogin(String login) {
		if (StringUtils.isBlank(login))
			return new ArrayList<UserCP>();

		TypedQuery<UserCP> query = emw.createQuery("FROM " + UserCP.class.getSimpleName() + " u WHERE u.login = :login", UserCP.class);
		query.setParameter("login", login);

		return query.getResultList();
	}

	public UserCP save(UserCP newUser) {
		UserCP user = super.save(newUser);

		if (getByLogin(user.getLogin()).size() != 1)
			throw new ValidationException("login-must-be-unique");

		return user;
	}

	public boolean remove(Long id) {
		UserCP user = getById(id);

		if (user != null) {
			user.setDeleted(true);
			save(user);
			return true;
		}

		return false;
	}

}
