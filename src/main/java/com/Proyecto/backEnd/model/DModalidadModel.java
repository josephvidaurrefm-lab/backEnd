package com.Proyecto.backEnd.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="dmodalidad")
@Getter
@Setter

public class DModalidadModel {
    @EmbeddedId
    private DModalidadModelPK demodalidad; 
	String nombre;
	int estado;
    @MapsId("codm")
    @ManyToOne
    @JoinColumn(name = "codm", insertable = false, updatable = false)
    @JsonBackReference // Evita recursión al serializar
    private ModalidadModel modalidad;
}
