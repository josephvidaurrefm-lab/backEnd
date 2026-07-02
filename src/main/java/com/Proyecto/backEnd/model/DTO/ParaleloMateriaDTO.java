package com.Proyecto.backEnd.model.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ParaleloMateriaDTO {
	private String codmat;
    private int codpar;
    private String nombreParalelo;
    private int estadoParalelo;
    private int gestion;
}
