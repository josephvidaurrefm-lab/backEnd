package com.Proyecto.backEnd.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.Proyecto.backEnd.exception.ResourceNotFoundException;
import com.Proyecto.backEnd.model.MateriasModel;
import com.Proyecto.backEnd.service.MateriasService;
import com.Proyecto.backEnd.utils.ApiResponse;

@RestController
@RequestMapping("/api/materias")
public class MateriasController {

    @Autowired
    private MateriasService matService;

    // ✔ Obtener todas las materias
    @GetMapping
    public List<MateriasModel> listaMaterias() {
        return matService.listaMaterias();
    }

    // ✔ Buscar por codmat o por nombre
    @GetMapping("/{valor}")
    public List<MateriasModel> buscarMateriaPorIdONombre(@PathVariable String valor) {
        try {
            // Intentar buscar por codmat
            MateriasModel materia = matService.verMateria(valor);
            return List.of(materia);
        } catch (ResourceNotFoundException e) {
            // Si no existe codmat, buscar por nombre
            return matService.buscarPorNombre(valor);
        }
    }


    // ✔ Agregar materia
    @PostMapping
    public ResponseEntity<ApiResponse> agregarMateria(@RequestBody MateriasModel materia) {
        return matService.agregarMateria(materia);
    }

    // ✔ Modificar materia
    @PutMapping("/{codmat}")
    public ResponseEntity<ApiResponse> modificarMateria(
            @RequestBody MateriasModel materia,
            @PathVariable String codmat) {
        return matService.modificarMateria(codmat, materia);
    }

    // ✔ Eliminar / Habilitar (toggle)
    @DeleteMapping("/{codmat}")
    public ResponseEntity<ApiResponse> eliminarMateria(@PathVariable String codmat) {
        return matService.eliminarMateria(codmat);
    }
}