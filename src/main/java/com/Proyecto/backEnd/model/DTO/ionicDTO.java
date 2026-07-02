package com.Proyecto.backEnd.model.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ionicDTO {
    private String codmat;        // Código materia
    private String nombreMateria;
    private int codpar;           
    private String nombreParalelo;
    private int gestion;
    private int codp;             
    private String login;         // 👉 login del docente
    private String nivelMateria;
    private int estado;
}
