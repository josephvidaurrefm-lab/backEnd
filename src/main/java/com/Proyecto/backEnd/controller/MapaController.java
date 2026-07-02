package com.Proyecto.backEnd.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Proyecto.backEnd.model.MapaModel;
import com.Proyecto.backEnd.model.DTO.ParaleloMateriaDTO;
import com.Proyecto.backEnd.service.MapaService;

@RestController
@RequestMapping("/api/mapa")
public class MapaController {

    @Autowired
    private MapaService mapaService;

    @GetMapping
    public List<MapaModel> listaMapas() {
        return mapaService.listaMapas();
    }
    @GetMapping("/{codmat}")
    public List<ParaleloMateriaDTO> getParalelosPorMateria(@PathVariable String codmat) {
        return mapaService.obtenerParalelosPorMateria(codmat);
    }

    @DeleteMapping("/{codmat}/{codpar}")
    public String eliminarMapa(@PathVariable String codmat, @PathVariable int codpar) {
        mapaService.eliminarMapa(codmat, codpar);
        return "Relación eliminada correctamente";
    }

    @PostMapping("/{codmat}/{codpar}")
    public String adicionarMapa(@PathVariable String codmat, @PathVariable int codpar) {
        mapaService.crearMapa(codmat, codpar);
        return "Relación creada correctamente";
    }
}
