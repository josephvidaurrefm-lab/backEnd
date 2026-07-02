package com.Proyecto.backEnd.model.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ItemMateriaDTO {
	private String codmat;
    private int codi;
    private String nombreItem;
    private int estadoItem;
    private int ponderacion;
    private int gestion;
}
