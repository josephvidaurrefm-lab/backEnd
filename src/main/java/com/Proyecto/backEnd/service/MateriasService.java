package com.Proyecto.backEnd.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Proyecto.backEnd.exception.ResourceNotFoundException;
import com.Proyecto.backEnd.model.ItematModel;
import com.Proyecto.backEnd.model.MapaModel;
import com.Proyecto.backEnd.model.MateriasModel;
import com.Proyecto.backEnd.model.ParalelosModel;
import com.Proyecto.backEnd.repository.IteMatRepo;
import com.Proyecto.backEnd.repository.MapaRepo;
import com.Proyecto.backEnd.repository.MateriasRepo;
import com.Proyecto.backEnd.utils.ApiResponse;
import com.Proyecto.backEnd.utils.CustomReponseBuilder;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MateriasService {

    private final CustomReponseBuilder customResponseBuilder;

    @Autowired
    MateriasRepo matRepo;
    
    @Autowired
    private MapaRepo mapRepo;

    @Autowired
    private IteMatRepo itematRepo;

    // Listar todas las materias
    public List<MateriasModel> listaMaterias() {
        return matRepo.findAll();
    }

    // Ver una materia
    public MateriasModel verMateria(String codmat) {
        return matRepo.findById(codmat)
                .orElseThrow(() -> new ResourceNotFoundException("No existe materia con codmat: " + codmat));
    }

    // Buscar por nombre
    public List<MateriasModel> buscarPorNombre(String valor) {
        return matRepo.buscarPorNombre(valor);
    }

    @Transactional
    public ResponseEntity<ApiResponse> agregarMateria(MateriasModel materia) {

        // Insert nativo → siempre intenta insertar → si duplica → error automático
        matRepo.insertarMateria(
                materia.getCodmat(),
                materia.getNombre(),
                materia.getEstado(),
                materia.getCodn()
        );

        return customResponseBuilder.buildResponse(
                "Materia agregada con éxito",
                materia
        );
    }



    // Modificar materia
    public ResponseEntity<ApiResponse> modificarMateria(String codmat, MateriasModel materia) {
        if (matRepo.existsByCodmatAndCodmatNot(materia.getCodmat(), codmat)) {
            throw new ResourceNotFoundException("Ya existe otra materia con el código: " + materia.getCodmat());
        }

        materia.setCodmat(codmat);
        MateriasModel modificada = matRepo.save(materia);

        return customResponseBuilder.buildResponse("Materia modificada con éxito", modificada);
    }

    // Eliminación lógica (alternar estado)
    public ResponseEntity<ApiResponse> eliminarMateria(String codmat) {
        MateriasModel materia = matRepo.findById(codmat)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró materia con codmat: " + codmat));

        // Alternar estado 1 ↔ 0
        materia.setEstado(materia.getEstado() == 1 ? 0 : 1);
        matRepo.save(materia);

        String mensaje = (materia.getEstado() == 0) ? "Materia desactivada con éxito" : "Materia habilitada con éxito";
        return customResponseBuilder.buildResponse(mensaje, materia.getNombre());
    }
}
