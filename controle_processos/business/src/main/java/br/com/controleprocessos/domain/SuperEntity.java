package br.com.controleprocessos.domain;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class SuperEntity implements Comparable<SuperEntity> {

	@Id
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		final int prime = 257;

		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());

		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;

		if (obj == null || getClass() != obj.getClass())
			return false;

		SuperEntity other = (SuperEntity) obj;

		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;

		return true;
	}

	@Override
	public int compareTo(SuperEntity o) {
		if (o == null || o.getId() == null)
			return -1;

		if (this.getId() == null)
			return 1;

		return this.getId().compareTo(o.getId());
	}

}
