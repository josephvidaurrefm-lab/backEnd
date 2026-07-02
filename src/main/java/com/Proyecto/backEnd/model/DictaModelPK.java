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
public class DictaModelPK implements Serializable{
    protected String codmat;
    protected int codpar;
    protected int gestion;
    protected int codp;
	@Override
	public int hashCode() {
		return Objects.hash(codmat, codp, codpar, gestion);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DictaModelPK other = (DictaModelPK) obj;
		return Objects.equals(codmat, other.codmat) && codp == other.codp && codpar == other.codpar
				&& gestion == other.gestion;
	}
    
}
