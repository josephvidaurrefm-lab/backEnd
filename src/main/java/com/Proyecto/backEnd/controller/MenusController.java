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

import com.Proyecto.backEnd.model.MenusModel;
import com.Proyecto.backEnd.model.DTO.ProcesoMenuDTO;
import com.Proyecto.backEnd.service.MenusService;
import com.Proyecto.backEnd.utils.ApiResponse;

@RestController
public class MenusController {
	@Autowired
	MenusService menService;
	
	@GetMapping("/api/menus")
	public List<MenusModel> listaMenus(){
		return menService.listaMenus();
	}
    // Ver un menú específico por codm

    @GetMapping("/api/menus/{valor}")
    public List<MenusModel> buscarMenuPorIdONombre(@PathVariable String valor) {
        try {
            int codm = Integer.parseInt(valor);
            Optional<MenusModel> menu = menService.verMenu(codm);
            return menu.map(List::of).orElse(List.of());
        } catch (NumberFormatException e) {
            // No es número → buscar por nombre parcial (insensible a mayúsculas)
            return menService.buscarPorNombre(valor);
        }
    }

    // Agregar un menú
    @PostMapping("/api/menus")
    public ResponseEntity<ApiResponse> agreMenu(@RequestBody MenusModel menu) {
        return menService.agreMenuResponse(menu);
    }

    // Modificar un menú
    @PutMapping("/api/menus/{codm}")
    public ResponseEntity<ApiResponse> modMenu(@RequestBody MenusModel menu, @PathVariable int codm) {
        return menService.modMenuResponse(codm, menu);
    }

    // Eliminar un menú
    @DeleteMapping("/api/menus/{codm}")
    public ResponseEntity<ApiResponse> eliMenu(@PathVariable int codm) {
        return menService.eliMenuResponse(codm);
    }

 // Asignar proceso al menú
    @PostMapping("/api/menus/{codm}/procesos/{codp}")
    public ResponseEntity<ApiResponse> asignarProceso(@PathVariable int codm, @PathVariable int codp) {
        return menService.asignarProcesoAMenu(codm, codp);
    }

    // Desasignar proceso del menú
    @DeleteMapping("/api/menus/{codm}/procesos/{codp}")
    public ResponseEntity<ApiResponse> desasignarProceso(@PathVariable int codm, @PathVariable int codp) {
        return menService.desasignarProcesoAMenu(codm, codp);
    }
    
    @GetMapping("/api/menus/{codm}/procesos")
    public List<ProcesoMenuDTO> obtenerProcesosPorMenu(@PathVariable int codm) {
        return menService.listarProcesosPorMenu(codm);
    }


}
