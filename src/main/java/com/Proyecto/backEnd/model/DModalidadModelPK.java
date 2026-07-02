package com.Proyecto.backEnd.model;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@NoArgsConstructor      // Constructor vacío necesario para JPA
@AllArgsConstructor 
public class DModalidadModelPK implements Serializable {
	protected Integer coddm;
	protected Integer codm;
	@Override
	public int hashCode() {
		return Objects.hash(coddm, codm);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DModalidadModelPK other = (DModalidadModelPK) obj;
		return Objects.equals(coddm, other.coddm) && Objects.equals(codm, other.codm);
	}


    
}
