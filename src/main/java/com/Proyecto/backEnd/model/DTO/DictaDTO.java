package com.Proyecto.backEnd.model.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DictaDTO {
    private String codmat;          // Código de la materia
    private String nombreMateria;   // Nombre de la materia
    private int codpar;             // Código del paralelo
    private String nombreParalelo; 
    private int gestion;            // Gestión
    private int codp;               // Código de la persona
    private String nombrePersona;   // Nombre de la persona
    private String nivelMateria;    // Nombre del nivel de la materia            // Estado del dicta (1=activo, 0=eliminado)
}
