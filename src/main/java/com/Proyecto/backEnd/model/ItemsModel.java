package com.Proyecto.backEnd.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="items")
@Getter
@Setter
public class ItemsModel {

    @Id
    private int codi;
    private String nombre;
    private int estado;

    // Relación Uno a Muchos hacia Itemat
    @OneToMany(mappedBy = "item", cascade = CascadeType.MERGE)
    @JsonManagedReference
    private List<ItematModel> itemats;
}
