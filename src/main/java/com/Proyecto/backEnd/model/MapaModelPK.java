package com.Proyecto.backEnd.model;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;
@Embeddable
@Getter
@Setter
public class MapaModelPK implements Serializable{
	protected String codmat;
	protected int codpar;
	protected int gestion;
	@Override
	public int hashCode() {
		return Objects.hash(codmat, codpar, gestion);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MapaModelPK other = (MapaModelPK) obj;
		return Objects.equals(codmat, other.codmat) && codpar == other.codpar && gestion == other.gestion;
	}
	
}
