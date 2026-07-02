package com.Proyecto.backEnd.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Proyecto.backEnd.model.DictaModel;
import com.Proyecto.backEnd.model.DictaModelPK;
import com.Proyecto.backEnd.model.UsuariosModel;
import com.Proyecto.backEnd.model.DTO.DictaDTO;
import com.Proyecto.backEnd.model.DTO.MapaDTO;
import com.Proyecto.backEnd.repository.DictaRepo;
import com.Proyecto.backEnd.utils.ApiResponse;
import com.Proyecto.backEnd.utils.CustomReponseBuilder;

import jakarta.transaction.Transactional;

@Service
public class DictaService {

    @Autowired
    private DictaRepo dictaRepo;
    
    @Autowired
    private MapaService mapaService;
    
    @Autowired
    private UsuariosService uService;
    
    @Autowired
    private CustomReponseBuilder customResponseBuilder;

    // Método para obtener todos los registros de DICTA
    public List<DictaModel> getAllDicta() {
        return dictaRepo.findAll();
    }
    //POST
    public DictaModel agregarDicta(DictaModel dicta) {
        // Si quieres, puedes verificar que no exista ya antes de guardar
        if(dictaRepo.existsById(dicta.getId())) {
            throw new RuntimeException("Ya existe un Dicta con esa PK");
        }

        return dictaRepo.save(dicta);
    }
    
    //PUT
    @Transactional
    public DictaModel actualizarDictaCompleto(String codmatOld, int codparOld, int gestionOld, int codpOld, DictaModel dictaActualizado) {
        DictaModelPK pkOld = new DictaModelPK(codmatOld, codparOld, gestionOld, codpOld);

        // Buscamos el registro existente
        DictaModel existente = dictaRepo.findById(pkOld)
                .orElseThrow(() -> new RuntimeException(
                        "No se encontró un Dicta con codmat: " + codmatOld +
                        ", codpar: " + codparOld +
                        ", gestion: " + gestionOld +
                        ", codp: " + codpOld
                ));

        // Validación de duplicado: verifica si ya existe un registro con la nueva PK
        DictaModelPK pkNew = dictaActualizado.getId();
        boolean duplicado = dictaRepo.existsById(pkNew);
        if (duplicado && !pkOld.equals(pkNew)) {
            throw new RuntimeException("No se puede actualizar, ya existe otro Dicta con esa PK.");
        }

        // Si cambian los valores de PK, eliminamos el registro antiguo y creamos uno nuevo
        if (!pkOld.equals(pkNew)) {
            dictaRepo.deleteById(pkOld);
        }

        // Guardamos el registro actualizado (nuevo o modificado)
        return dictaRepo.save(dictaActualizado);
    }
     
    // ELIMINAR / HABILITAR DICTA
    public ResponseEntity<ApiResponse> eliminarDicta(String codmat, int codpar, int gestion, int codp) {

        DictaModel dicta = dictaRepo.findById(new DictaModelPK(codmat, codpar, gestion, codp))
                .orElseThrow(() -> new RuntimeException(
                        "No se encontró DICTA con codmat: " + codmat +
                        ", codpar: " + codpar +
                        ", gestion: " + gestion +
                        ", codp: " + codp
                ));

        // Eliminación real
        dictaRepo.eliminarDicta(codmat, codpar, gestion, codp);

        return customResponseBuilder.buildResponse(
                "Dicta eliminado correctamente",
                "Dicta: " + codmat + " - CodPar: " + codpar
        );
    }

    //------------------------------------------------------------------------
    // Obtener todos los dicta como DTO simple
    public List<DictaDTO> getAllDictaInfo() {
        List<Object[]> rawData = dictaRepo.findAllDictaInfoRaw();

        return rawData.stream().map(row -> new DictaDTO(
                (String) row[0],   // codmat
                (String) row[1],   // nombreMateria
                (Integer) row[2],  // codpar
                (String) row[3],   // nombreParalelo
                (Integer) row[4],  // gestion
                (Integer) row[5],  // codp
                (String) row[6],   // nombrePersona
                (String) row[7]  // estado
        )).collect(Collectors.toList());
    }
    
    public List<DictaDTO> buscarPorMateriaOProfesor(String valor) {
        List<Object[]> rawData = dictaRepo.buscarPorMateriaOProfesor(valor);
        return rawData.stream().map(row -> new DictaDTO(
                (String) row[0],   // codmat
                (String) row[1],   // nombreMateria
                (Integer) row[2],  // codpar
                (String) row[3],   // nombreParalelo
                (Integer) row[4],  // gestion
                (Integer) row[5],  // codp
                (String) row[6],   // nombrePersona
                (String) row[7]  // estado
        )).collect(Collectors.toList());
    }

    
    // GET todo lo que está en mapa
    public List<MapaDTO> getAllMapaFromDicta() {
        return mapaService.getAllMapaInfo();
    }
    //Get Persoas
    public List<UsuariosModel> obtenerUsuarios() {
        return uService.listaUsuarios(); // Llamada directa
    }
}
