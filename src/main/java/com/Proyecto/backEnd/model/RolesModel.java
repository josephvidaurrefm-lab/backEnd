package com.Proyecto.backEnd.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="roles")
@Getter
@Setter
public class RolesModel {
	@Id
	int codr;
	String nombre;
	int estado;
	    
    @ManyToMany
    @JoinTable(
        name = "rolme",
        joinColumns =  @JoinColumn(name = "codr"),
        inverseJoinColumns = @JoinColumn(name = "codm")
    )
    @JsonIgnoreProperties("roles")
    List<MenusModel> menus;
}