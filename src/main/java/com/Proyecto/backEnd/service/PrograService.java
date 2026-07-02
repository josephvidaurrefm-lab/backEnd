package com.Proyecto.backEnd.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Proyecto.backEnd.model.PrograModel;
import com.Proyecto.backEnd.model.PrograModelPK;
import com.Proyecto.backEnd.model.UsuariosModel;
import com.Proyecto.backEnd.model.DTO.DictaDTO;
import com.Proyecto.backEnd.model.DTO.MapaDTO;
import com.Proyecto.backEnd.model.DTO.ionicDTO;
import com.Proyecto.backEnd.repository.PrograRepo;
import com.Proyecto.backEnd.utils.ApiResponse;
import com.Proyecto.backEnd.utils.CustomReponseBuilder;

import jakarta.transaction.Transactional;

@Service
public class PrograService {
	 @Autowired
	 private PrograRepo proRepo;
	
    @Autowired
    private MapaService mapaService;
    
    @Autowired
    private UsuariosService uService;
    
    @Autowired
    private CustomReponseBuilder customResponseBuilder;
    
    public List<PrograModel> getAllProgra() {
        return proRepo.findAll();
    }
    
    //POST
    public PrograModel agregarProgra(PrograModel progra) {
        // Si quieres, puedes verificar que no exista ya antes de guardar
        if(proRepo.existsById(progra.getId())) {
            throw new RuntimeException("Ya existe un Progra con esa PK");
        }

        return proRepo.save(progra);
    }
    
    //PUT
    @Transactional
    public PrograModel actualizarPrograCompleto(String codmatOld, int codparOld, int gestionOld, int codpOld, PrograModel prograActualizado) {
        PrograModelPK pkOld = new PrograModelPK(codmatOld, codparOld, gestionOld, codpOld);

        // Buscamos el registro existente
        PrograModel existente = proRepo.findById(pkOld)
                .orElseThrow(() -> new RuntimeException(
                        "No se encontró un progra con codmat: " + codmatOld +
                        ", codpar: " + codparOld +
                        ", gestion: " + gestionOld +
                        ", codp: " + codpOld
                ));

        // Validación de duplicado: verifica si ya existe un registro con la nueva PK
        PrograModelPK pkNew = prograActualizado.getId();
        boolean duplicado = proRepo.existsById(pkNew);
        if (duplicado && !pkOld.equals(pkNew)) {
            throw new RuntimeException("No se puede actualizar, ya existe otro Progra con esa PK.");
        }

        // Si cambian los valores de PK, eliminamos el registro antiguo y creamos uno nuevo
        if (!pkOld.equals(pkNew)) {
            proRepo.deleteById(pkOld);
        }

        // Guardamos el registro actualizado (nuevo o modificado)
        return proRepo.save(prograActualizado);
    }
    
    // ELIMINAR 
    public ResponseEntity<ApiResponse> eliminarProgra(String codmat, int codpar, int gestion, int codp) {

        // Validación de existencia
        PrograModel progra = proRepo.findById(new PrograModelPK(codmat, codpar, gestion, codp))
                .orElseThrow(() -> new RuntimeException(
                        "No se encontró Progra con codmat: " + codmat +
                        ", codpar: " + codpar +
                        ", gestion: " + gestion +
                        ", codp: " + codp
                ));

        // Eliminación real
        proRepo.eliminarProgra(codmat, codpar, gestion, codp);

        return customResponseBuilder.buildResponse(
                "Progra eliminado correctamente",
                "Progra: " + codmat + " - CodPar: " + codpar
        );
    }

    //------------------------------------------------------------------------
    // Obtener todos los dicta como DTO simple
    public List<DictaDTO> getAllPrograInfo() {
        List<Object[]> rawData = proRepo.findAllPrograInfoRaw();

        return rawData.stream().map(row -> new DictaDTO(
                (String) row[0],   // codmat
                (String) row[1],   // nombreMateria
                (Integer) row[2],  // codpar
                (String) row[3],   // nombreParalelo
                (Integer) row[4],  // gestion
                (Integer) row[5],  // codp
                (String) row[6],   // nombrePersona
                (String) row[7]  
        )).collect(Collectors.toList());
    }
    
    public List<DictaDTO> buscarPorMateriaOProfesor(String valor) {
        List<Object[]> rawData = proRepo.buscarPorMateriaOProfesor(valor);
        return rawData.stream().map(row -> new DictaDTO(
                (String) row[0],   // codmat
                (String) row[1],   // nombreMateria
                (Integer) row[2],  // codpar
                (String) row[3],   // nombreParalelo
                (Integer) row[4],  // gestion
                (Integer) row[5],  // codp
                (String) row[6],   // nombrePersona
                (String) row[7]   // nivelMateria
       
        )).collect(Collectors.toList());
    }

    
    // GET todo lo que está en mapa
    public List<MapaDTO> getAllMapaFromProgra() {
        return mapaService.getAllMapaInfo();
    }
    //Get Persoas
    public List<UsuariosModel> obtenerUsuarios() {
        return uService.listaUsuarios(); // Llamada directa
    }
    //-------------------
    //ionic
    public List<ionicDTO> getAllPrograInfoLogin() {

        List<Object[]> rawData = proRepo.findAllPrograInfoLogin();

        return rawData.stream().map(row -> new ionicDTO(
                (String) row[0],   // codmat
                (String) row[1],   // nombreMateria
                (Integer) row[2],  // codpar
                (String) row[3],   // nombreParalelo
                (Integer) row[4],  // gestion
                (Integer) row[5],  // codp
                (String) row[6],   // login
                (String) row[7],   // nivelMateria 
                (Integer) row[8]
        )).collect(Collectors.toList());
    }

}
