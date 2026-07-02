package com.Proyecto.backEnd.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="personal")
@Getter
@Setter
public class PersonalModel {
	@Id
	int	codp;
	String nombre;
	String ap;
	String am;
	int estado;
	LocalDate fnac; 	 
	String ecivil;
	String genero;
	String direc;
	String telf; 
	String tipo;
	String foto;
	
	@OneToOne(mappedBy="personal")
	@JsonBackReference
	private UsuariosModel usuarios;

	@OneToOne(mappedBy="personal", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonManagedReference
	private DatosModel datos;


}