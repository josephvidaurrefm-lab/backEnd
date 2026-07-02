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

import com.Proyecto.backEnd.model.DictaModel;
import com.Proyecto.backEnd.model.UsuariosModel;
import com.Proyecto.backEnd.model.DTO.DictaDTO;
import com.Proyecto.backEnd.model.DTO.MapaDTO;
import com.Proyecto.backEnd.service.DictaService;
import com.Proyecto.backEnd.utils.ApiResponse;

@RestController
public class DictaController {

    @Autowired
    private DictaService dictaService;

    // Endpoint para obtener todos los registros de DICTA
    @GetMapping("/api/dicta")
    public List<DictaModel> getAllDicta() {
        return dictaService.getAllDicta();
    }
    
    @PostMapping("/api/dicta")
    public ResponseEntity<DictaModel> agregarDicta(@RequestBody DictaModel dicta) {
        DictaModel nuevoDicta = dictaService.agregarDicta(dicta);
        return ResponseEntity.ok(nuevoDicta);
    }
    
    // PUT: Actualizar Dicta por PK
    @PutMapping("/api/dicta/{codmat}/{codpar}/{gestion}/{codp}")
    public ResponseEntity<DictaModel> actualizarDictaCompleto(
            @PathVariable String codmat,
            @PathVariable int codpar,
            @PathVariable int gestion,
            @PathVariable int codp,
            @RequestBody DictaModel dictaActualizado
    ) {
        DictaModel actualizado = dictaService.actualizarDictaCompleto(codmat, codpar, gestion, codp, dictaActualizado);
        return ResponseEntity.ok(actualizado);
    }
    
    // DELETE lógico de DICTA
    @DeleteMapping("/api/dicta/{codmat}/{codpar}/{gestion}/{codp}")
    public ResponseEntity<ApiResponse> eliminarDicta(
            @PathVariable String codmat,
            @PathVariable int codpar,
            @PathVariable int gestion,
            @PathVariable int codp) {

        // Llamar al service que hace el toggle del estado
        return dictaService.eliminarDicta(codmat, codpar, gestion, codp);
    }
    //--------------------------------------------
    
    @GetMapping("/api/dicta/list")
    public List<DictaDTO> getAllDictaSimple() {
        return dictaService.getAllDictaInfo();
    }
    
    @GetMapping("/api/dicta/maper")
    public List<MapaDTO> getMateriaParalelo() {
        return dictaService.getAllMapaFromDicta();
    }
    
    @GetMapping("/api/dicta/personas")
    public List<UsuariosModel> getPersonas() {
        return dictaService.obtenerUsuarios();
    }
    
    @GetMapping("/api/dicta/{texto}")
    public List<DictaDTO> buscarDicta(@PathVariable String texto) {
        return dictaService.buscarPorMateriaOProfesor(texto);
    }
}
