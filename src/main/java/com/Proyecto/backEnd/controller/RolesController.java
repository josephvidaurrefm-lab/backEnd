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


import com.Proyecto.backEnd.model.RolesModel;
import com.Proyecto.backEnd.model.DTO.RolMenuDTO;
import com.Proyecto.backEnd.service.RolesService;
import com.Proyecto.backEnd.utils.ApiResponse;

@RestController
public class RolesController {
	@Autowired
	RolesService rolService;
	
	@GetMapping("/api/roles")
	public List<RolesModel> listaDeDocentes2(){
		return rolService.listaRolesExeption();
	}
	
    @GetMapping("/api/roles/{valor}")
    public List<RolesModel> buscarRolPorIdONombre(@PathVariable String valor) {
        try {
            int codr = Integer.parseInt(valor);
            Optional<RolesModel> rol = rolService.verRol(codr);
            return rol.map(List::of).orElse(List.of());
        } catch (NumberFormatException e) {
            // No es número → buscar por nombre parcial (insensible a mayúsculas)
            return rolService.buscarPorNombre(valor);
        }
    }

    @PostMapping("/api/roles")
    public ResponseEntity<ApiResponse> agreRol(@RequestBody RolesModel rol) {
        return rolService.agreRolResponse(rol);
    }

    @PutMapping("/api/roles/{codr}")
    public ResponseEntity<ApiResponse> modRol(@RequestBody RolesModel rol, @PathVariable int codr) {
        return rolService.modRolResponse(codr, rol);
    }

    @DeleteMapping("/api/roles/{codr}")
    public ResponseEntity<ApiResponse> eliRol(@PathVariable int codr) {
        return rolService.eliRolResponse(codr);
    }
    
    // Asignar un menú al rol
    @PostMapping("/api/roles/{codr}/menus/{codm}")
    public ResponseEntity<ApiResponse> asignarMenu(@PathVariable int codr, @PathVariable int codm) {
        return rolService.asignarMenuARol(codr, codm);
    }

    // Desasignar un menú del rol
    @DeleteMapping("/api/roles/{codr}/menus/{codm}")
    public ResponseEntity<ApiResponse> desasignarMenu(@PathVariable int codr, @PathVariable int codm) {
        return rolService.desasignarMenuARol(codr, codm);
    }

    // Obtener todos los menús con flag asignado para un rol
    @GetMapping("/api/roles/{codr}/menus")
    public List<RolMenuDTO> obtenerMenusPorRol(@PathVariable int codr) {
        return rolService.listarMenusPorRol(codr);
    }

}
