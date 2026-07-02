package com.Proyecto.backEnd.model;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "progra")
@Getter
@Setter
public class PrograModel {
	 @EmbeddedId
	    private PrograModelPK id;
	    String login;
}
