package com.Proyecto.backEnd.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.Proyecto.backEnd.model.ProcesosModel;
import com.Proyecto.backEnd.service.ProcesosService;

@RestController
public class ProcesosController {
	@Autowired
	ProcesosService proService;
	@GetMapping("/api/procesos")
	public List<ProcesosModel> listaProcesos(){
		return proService.listaProcesos();
	}
    // Ver un proceso específico por codp
    @GetMapping("/api/procesos/{valor}")
    public List<ProcesosModel> buscarMenuPorIdONombre(@PathVariable String valor) {
        try {
            int codp = Integer.parseInt(valor);
            Optional<ProcesosModel> proceso = proService.verProceso(codp);
            return proceso.map(List::of).orElse(List.of());
        } catch (NumberFormatException e) {
            // No es número → buscar por nombre parcial (insensible a mayúsculas)
            return proService.buscarPorNombre(valor);
        }
    }

    // Agregar un proceso
    @PostMapping("/api/procesos")
    public ProcesosModel agreProceso(@RequestBody ProcesosModel proceso) {
        return proService.agreProceso(proceso);
    }

    // Modificar un proceso
    @PutMapping("/api/procesos/{codp}")
    public ProcesosModel modProceso(
            @RequestBody ProcesosModel proceso,
            @PathVariable int codp
    ) {
        return proService.modProceso(codp, proceso);
    }

    // Eliminar un proceso
    @DeleteMapping("/api/procesos/{codp}")
    public void eliProceso(@PathVariable int codp) {
        proService.eliProceso(codp);
    }
}
