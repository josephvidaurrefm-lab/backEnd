package com.Proyecto.backEnd.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Datos")
@Getter
@Setter
public class DatosModel {

    @EmbeddedId
    private DatosModelPK datos;
    @OneToOne
    @MapsId("codp")
    @JoinColumn(name = "codp",referencedColumnName="codp")
    @JsonBackReference
    private PersonalModel personal;
}
