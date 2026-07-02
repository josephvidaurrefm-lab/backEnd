package com.Proyecto.backEnd.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="niveles")
@Getter
@Setter
public class NivelesModel {
	@Id
	int codn;
	String nombre;
	int estado;
	
	
}
