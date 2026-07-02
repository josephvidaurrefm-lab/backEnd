package com.Proyecto.backEnd.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Proyecto.backEnd.exception.ResourceNotFoundException;
import com.Proyecto.backEnd.model.ModalidadModel;
import com.Proyecto.backEnd.repository.ModalidadRepo;
import com.Proyecto.backEnd.utils.ApiResponse;
import com.Proyecto.backEnd.utils.CustomReponseBuilder;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ModalidadService {

    private final CustomReponseBuilder customResponseBuilder;

    @Autowired
    ModalidadRepo modRepo;

    // LISTAR TODAS LAS MODALIDADES
    public List<ModalidadModel> listaModalidades() {
        return modRepo.findAll();
    }

    // VER UNA MODALIDAD POR CODM
    public Optional<ModalidadModel> verModalidad(int codm) {
        return modRepo.findById(codm);
    }

    // BUSCAR POR NOMBRE
    public List<ModalidadModel> buscarPorNombre(String valor) {
        return modRepo.buscarPorNombre(valor);
    }

    // POST – Agregar modalidad
    public ResponseEntity<ApiResponse> agreModalidadResponse(ModalidadModel modalidad) {

        if (modRepo.existsByNombre(modalidad.getNombre())) {
            throw new ResourceNotFoundException(
                "Ya existe una modalidad con el nombre: " + modalidad.getNombre()
            );
        }

        ModalidadModel guardado = modRepo.save(modalidad);

        return customResponseBuilder.buildResponse("Modalidad agregada con éxito", guardado);
    }

    // PUT – Modificar modalidad
    public ResponseEntity<ApiResponse> modModalidadResponse(int codm, ModalidadModel modalidad) {

        if (modRepo.existsByNombreAndIdNot(modalidad.getNombre(), codm)) {
            throw new ResourceNotFoundException(
                "Ya existe otra modalidad con el nombre: " + modalidad.getNombre()
            );
        }

        modalidad.setCodm(codm);

        ModalidadModel modificado = modRepo.save(modalidad);

        return customResponseBuilder.buildResponse(
            "Modalidad modificada con éxito",
            modificado
        );
    }

    // DELETE – Eliminación lógica
    public ResponseEntity<ApiResponse> eliModalidadResponse(int codm) {

        ModalidadModel modalidad = modRepo.findById(codm)
            .orElseThrow(() -> new ResourceNotFoundException(
                "No se encontró una modalidad con codm: " + codm
            ));

        modRepo.eliminar(codm);

        int nuevoEstado = modalidad.getEstado() == 1 ? 0 : 1;

        String mensaje = (nuevoEstado == 0)
                ? "Modalidad eliminada con éxito"
                : "Modalidad habilitada con éxito";

        return customResponseBuilder.buildResponse(mensaje, modalidad.getNombre());
    }
}

