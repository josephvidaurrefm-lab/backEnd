package com.Proyecto.backEnd.model.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MapaDTO {
    private String codmat;          // Código de la materia
    private String nombreMateria;   // Nombre de la materia
    private int codpar;             // Código del paralelo
    private String nombreParalelo;  // Nombre del paralelo
    private int gestion;            // Gestión
    private String nivelMateria;    // Nombre del nivel de la materia
}
