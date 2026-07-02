package com.Proyecto.backEnd.model;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class ItematPK implements Serializable{
	protected String codmat;
	protected int codi;
	protected int gestion;
	@Override
	public int hashCode() {
		return Objects.hash(codi, codmat, gestion);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ItematPK other = (ItematPK) obj;
		return codi == other.codi && Objects.equals(codmat, other.codmat) && gestion == other.gestion;
	}

	
}
