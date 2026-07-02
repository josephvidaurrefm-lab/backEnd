package com.Proyecto.backEnd.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.Proyecto.backEnd.model.ModalidadModel;
import com.Proyecto.backEnd.service.ModalidadService;
import com.Proyecto.backEnd.utils.ApiResponse;

@RestController
public class ModalidadController {

    @Autowired
    ModalidadService modService;

    // LISTAR TODAS LAS MODALIDADES
    @GetMapping("/api/modalidad")
    public List<ModalidadModel> listaModalidades() {
        return modService.listaModalidades();
    }

    // BUSCAR POR CODM O POR NOMBRE
    @GetMapping("/api/modalidad/{valor}")
    public List<ModalidadModel> buscarModalidadPorCodmONombre(@PathVariable String valor) {
        try {
            int codm = Integer.parseInt(valor);
            Optional<ModalidadModel> modalidad = modService.verModalidad(codm);
            return modalidad.map(List::of).orElse(List.of());
        } catch (NumberFormatException e) {
            // Buscar por nombre parcial
            return modService.buscarPorNombre(valor);
        }
    }

    // AGREGAR MODALIDAD
    @PostMapping("/api/modalidad")
    public ResponseEntity<ApiResponse> agreModalidad(@RequestBody ModalidadModel modalidad) {
        return modService.agreModalidadResponse(modalidad);
    }

    // MODIFICAR MODALIDAD
    @PutMapping("/api/modalidad/{codm}")
    public ResponseEntity<ApiResponse> modModalidad(
            @RequestBody ModalidadModel modalidad,
            @PathVariable int codm) {

        return modService.modModalidadResponse(codm, modalidad);
    }

    // ELIMINAR (LÓGICO) MODALIDAD
    @DeleteMapping("/api/modalidad/{codm}")
    public ResponseEntity<ApiResponse> eliModalidad(@PathVariable int codm) {
        return modService.eliModalidadResponse(codm);
    }
}
