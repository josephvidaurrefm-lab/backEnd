package com.Proyecto.backEnd.model;


import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="mapa")
@Getter
@Setter
public class MapaModel {
	@EmbeddedId
	private MapaModelPK mapa;
	private int estado;
	
    @ManyToOne
    @JoinColumn(name = "codpar", insertable = false, updatable = false)
    private ParalelosModel paralelo;
    
    @ManyToOne
    @JoinColumn(name = "codmat", insertable = false, updatable = false)
    private MateriasModel materia;  // Solo se ve desde Mapa
}
