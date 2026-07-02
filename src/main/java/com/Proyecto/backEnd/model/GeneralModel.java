package com.Proyecto.backEnd.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="general")
@Getter
@Setter
public class GeneralModel {
	@Id
	String codg;
	int gestion;
	String login;

}
