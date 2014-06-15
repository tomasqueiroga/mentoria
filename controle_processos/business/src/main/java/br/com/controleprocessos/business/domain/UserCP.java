package br.com.controleprocessos.business.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;

import org.apache.commons.lang3.StringUtils;

@Entity
public class UserCP extends SuperEntity {

	private String name;
	private String login;
	private String password;
	private boolean deleted = false;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	@Override
	public void validate() throws ValidationException {
		List<String> erros = new ArrayList<String>();

		if (StringUtils.isBlank(getName()))
			erros.add("name-required");
		if (StringUtils.isBlank(login))
			erros.add("login-required");
		if (StringUtils.isBlank(password))
			erros.add("password-required");

		if (!erros.isEmpty())
			throw new ValidationException(erros);
	}

}
