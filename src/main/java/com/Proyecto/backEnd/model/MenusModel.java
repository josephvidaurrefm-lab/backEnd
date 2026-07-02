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
@Table(name="menus")
@Getter
@Setter
public class MenusModel {
	@Id
	int codm;
	String nombre;
	int estado;
	
    
    @ManyToMany
    @JoinTable(
        name = "mepro",
        joinColumns =  @JoinColumn(name = "codm"),
        inverseJoinColumns = @JoinColumn(name = "codp")
    )
    @JsonIgnoreProperties("menus")
    List<ProcesosModel> procesos;
}