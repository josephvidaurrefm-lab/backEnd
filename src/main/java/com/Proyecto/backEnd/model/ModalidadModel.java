package com.Proyecto.backEnd.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="modalidad")
@Getter
@Setter
public class ModalidadModel {
	@Id
	int codm;
	String nombre;
	int estado;
    // Evitamos recursión usando JsonManagedReference
    @OneToMany(mappedBy = "modalidad")
    @JsonManagedReference
    private List<DModalidadModel> detalles;
}
