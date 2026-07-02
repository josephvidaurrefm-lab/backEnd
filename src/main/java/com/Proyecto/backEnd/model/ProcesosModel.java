package com.Proyecto.backEnd.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="procesos")
@Getter
@Setter
public class ProcesosModel {
	@Id
	int codp;
	String nombre;
	String enlace;
	String ayuda;
	int estado;
	
}
