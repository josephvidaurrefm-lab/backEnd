package com.Proyecto.backEnd.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.Proyecto.backEnd.model.NivelesModel;
import com.Proyecto.backEnd.service.NivelesService;
import com.Proyecto.backEnd.utils.ApiResponse;

@RestController
public class NivelesController {

    @Autowired
    NivelesService nivService;

    // LISTAR TODOS LOS NIVELES
    @GetMapping("/api/niveles")
    public List<NivelesModel> listaNiveles() {
        return nivService.listaNiveles();
    }

    // BUSCAR POR ID O POR NOMBRE
    @GetMapping("/api/niveles/{valor}")
    public List<NivelesModel> buscarNivelPorIdONombre(@PathVariable String valor) {
        try {
            int id = Integer.parseInt(valor);
            Optional<NivelesModel> nivel = nivService.verNivel(id);
            return nivel.map(List::of).orElse(List.of());
        } catch (NumberFormatException e) {
            // No es número → buscar por nombre parcial
            return nivService.buscarPorNombre(valor);
        }
    }

    // AGREGAR NIVEL
    @PostMapping("/api/niveles")
    public ResponseEntity<ApiResponse> agreNivel(@RequestBody NivelesModel nivel) {
        return nivService.agreNivelResponse(nivel);
    }

    // MODIFICAR NIVEL
    @PutMapping("/api/niveles/{id}")
    public ResponseEntity<ApiResponse> modNivel(@RequestBody NivelesModel nivel, @PathVariable int id) {
        return nivService.modNivelResponse(id, nivel);
    }

    // ELIMINAR (LÓGICO) NIVEL
    @DeleteMapping("/api/niveles/{id}")
    public ResponseEntity<ApiResponse> eliNivel(@PathVariable int id) {
        return nivService.eliNivelResponse(id);
    }
}
