package com.Proyecto.backEnd.model;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class DatosModelPK implements Serializable{
	protected int codp;
	protected String cedula;
	@Override
	public int hashCode() {
		return Objects.hash(cedula, codp);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DatosModelPK other = (DatosModelPK) obj;
		return Objects.equals(cedula, other.cedula) && codp == other.codp;
	}
	
}
