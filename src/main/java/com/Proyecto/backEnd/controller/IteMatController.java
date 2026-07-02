package com.Proyecto.backEnd.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Proyecto.backEnd.model.ItematModel;
import com.Proyecto.backEnd.model.DTO.ItemMateriaDTO;
import com.Proyecto.backEnd.service.IteMatService;

@RestController
public class IteMatController {
	@Autowired
	IteMatService iteService;
	
	@GetMapping("/api/itemat")
    public List<ItematModel> listaItemat() {
        return iteService.listaItemat();
    }
    @GetMapping("/api/itemat/{codmat}")
    public List<ItemMateriaDTO> getItemsPorMateria(@PathVariable String codmat) {
        return iteService.obtenerItemsPorMateria(codmat);
    }
    
    @DeleteMapping("/api/itemat/{codmat}/{codi}")
    public void eliminarItemat(
            @PathVariable String codmat,
            @PathVariable int codi) {
        iteService.eliminarItemat(codmat, codi);
    }
    
    @PostMapping("/api/itemat/{codmat}/{codi}/{ponderacion}")
    public void agregarItemat(
            @PathVariable String codmat,
            @PathVariable int codi,
            @PathVariable int ponderacion
    ) {
        iteService.agregarItemat(codmat, codi, ponderacion);
    }

}
