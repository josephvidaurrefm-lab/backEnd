package com.Proyecto.backEnd.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.Proyecto.backEnd.model.ParalelosModel;
import com.Proyecto.backEnd.service.ParalelosService;
import com.Proyecto.backEnd.utils.ApiResponse;

@RestController
public class ParalelosController {

    @Autowired
    ParalelosService parService;

    // ✔ Obtener todos
    @GetMapping("/api/paralelos")
    public List<ParalelosModel> listaParalelos() {
        return parService.listaParalelos();
    }

    // ✔ Buscar por codpar o por nombre
    @GetMapping("/api/paralelos/{valor}")
    public List<ParalelosModel> buscarParaleloPorIdONombre(@PathVariable String valor) {
        try {
            int codpar = Integer.parseInt(valor);
            ParalelosModel paralelo = parService.verParalelo(codpar);
            return List.of(paralelo);
        } catch (NumberFormatException e) {
            // Buscar por nombre (LIKE)
            return parService.buscarPorNombre(valor);
        }
    }

    // ✔ Agregar
    @PostMapping("/api/paralelos")
    public ResponseEntity<ApiResponse> agregarParalelo(@RequestBody ParalelosModel paralelo) {
        return parService.agregarParalelo(paralelo);
    }

    // ✔ Modificar
    @PutMapping("/api/paralelos/{codpar}")
    public ResponseEntity<ApiResponse> modificarParalelo(
            @RequestBody ParalelosModel paralelo,
            @PathVariable int codpar) {

        return parService.modificarParalelo(codpar, paralelo);
    }

    // ✔ Eliminar / Habilitar (toggle)
    @DeleteMapping("/api/paralelos/{codpar}")
    public ResponseEntity<ApiResponse> eliminarParalelo(@PathVariable int codpar) {
        return parService.eliminarParalelo(codpar);
    }
}
