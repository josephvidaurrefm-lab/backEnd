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
@Table(name="itemat")
@Getter
@Setter
public class ItematModel {

    @EmbeddedId
    private ItematPK itemat;

    private int estado;
    private int ponderacion;

    // Relación Muchos a Uno hacia Items
    // Relación Muchos a Uno hacia Items
    @ManyToOne
    @MapsId("codi")
    @JoinColumn(name="codi")
    @JsonBackReference
    private ItemsModel item;

    @ManyToOne
    @MapsId("codmat")  // indica que codmat en la PK viene de Materias
    @JoinColumn(name = "codmat")
    private MateriasModel materia;
}
