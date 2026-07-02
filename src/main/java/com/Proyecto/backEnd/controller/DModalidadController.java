package com.Proyecto.backEnd.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.Proyecto.backEnd.model.DModalidadModel;
import com.Proyecto.backEnd.service.DModalidadService;
import com.Proyecto.backEnd.utils.ApiResponse;


@RestController
public class DModalidadController {

    @Autowired
    DModalidadService dmodService;

    // LISTAR todos los detalles de modalidad
    @GetMapping("/api/dmodalidad")
    public List<DModalidadModel> listaDModalidades() {
        return dmodService.listaDModalidades();
    }
    
 // Buscar DModalidad por coddm o por nombre parcial
    @GetMapping("/api/dmodalidad/{valor}")
    public List<DModalidadModel> buscarDModalidadPorCoddmONombre(@PathVariable String valor) {
        try {
            // Intentar interpretar el valor como un coddm
            int coddm = Integer.parseInt(valor);

            // Llamamos al service que devuelve ResponseEntity
            ResponseEntity<DModalidadModel> response = dmodService.obtenerDModalidadPorCoddm(coddm);

            // Convertimos a lista
            return response.getBody() != null ? List.of(response.getBody()) : List.of();
        } catch (NumberFormatException e) {
            // Si no es número, buscar por nombre parcial
            return dmodService.buscarDModalidadPorNombre(valor);
        }
    }

    
    @PostMapping("/api/dmodalidad")
    public ResponseEntity<ApiResponse> agregarDModalidad(@RequestBody DModalidadModel dmodalidad) {
        return dmodService.agregarDModalidad(dmodalidad);
    }

    
    @PutMapping("/api/dmodalidad/{coddm}")
    public ResponseEntity<ApiResponse> actualizarDModalidad(
            @PathVariable int coddm,
            @RequestBody DModalidadModel dmodalidadInput
    ) {
        return dmodService.actualizarDModalidad(coddm, dmodalidadInput);
    }

    
    @DeleteMapping("/api/dmodalidad/{coddm}")
    public ResponseEntity<ApiResponse> eliminarDModalidad(@PathVariable int coddm) {
        return dmodService.eliminarDModalidad(coddm);
    }

}
