package com.Proyecto.backEnd.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.Proyecto.backEnd.model.PrograModel;
import com.Proyecto.backEnd.model.UsuariosModel;
import com.Proyecto.backEnd.model.DTO.DictaDTO;
import com.Proyecto.backEnd.model.DTO.MapaDTO;
import com.Proyecto.backEnd.model.DTO.ionicDTO;
import com.Proyecto.backEnd.service.PrograService;
import com.Proyecto.backEnd.utils.ApiResponse;

@RestController
public class PrograController {

    @Autowired
    private PrograService proService;

    // Endpoint para obtener todos los registros de DICTA
    @GetMapping("/api/progra")
    public List<PrograModel> getAllProgra() {
        return proService.getAllProgra();
    }
    
    @PostMapping("/api/progra")
    public ResponseEntity<PrograModel> agregarProgra(@RequestBody PrograModel progra) {
        PrograModel nuevoProgra = proService.agregarProgra(progra);
        return ResponseEntity.ok(nuevoProgra);
    }
    
    // PUT: Actualizar Dicta por PK
    @PutMapping("/api/progra/{codmat}/{codpar}/{gestion}/{codp}")
    public ResponseEntity<PrograModel> actualizarPrograCompleto(
            @PathVariable String codmat,
            @PathVariable int codpar,
            @PathVariable int gestion,
            @PathVariable int codp,
            @RequestBody PrograModel prograActualizado
    ) {
        PrograModel actualizado = proService.actualizarPrograCompleto(codmat, codpar, gestion, codp, prograActualizado);
        return ResponseEntity.ok(actualizado);
    }
    
    // DELETE lógico de DICTA
    @DeleteMapping("/api/progra/{codmat}/{codpar}/{gestion}/{codp}")
    public ResponseEntity<ApiResponse> eliminarProgra(
            @PathVariable String codmat,
            @PathVariable int codpar,
            @PathVariable int gestion,
            @PathVariable int codp) {

        // Llamar al service que hace el toggle del estado
        return proService.eliminarProgra(codmat, codpar, gestion, codp);
    }
    //--------------------------------------------
    
    @GetMapping("/api/progra/list")
    public List<DictaDTO> getAllPrograSimple() {
        return proService.getAllPrograInfo();
    }
    
    @GetMapping("/api/progra/maper")
    public List<MapaDTO> getMateriaParalelo() {
        return proService.getAllMapaFromProgra();
    }
    
    @GetMapping("/api/progra/personas")
    public List<UsuariosModel> getPersonas() {
        return proService.obtenerUsuarios();
    }
    
    @GetMapping("/api/progra/{texto}")
    public List<DictaDTO> buscarProgra(@PathVariable String texto) {
        return proService.buscarPorMateriaOProfesor(texto);
    }
    
    @GetMapping("/api/progra/ionic")
    public List<ionicDTO> getParaIonic() {
        return proService.getAllPrograInfoLogin();
    }
}
