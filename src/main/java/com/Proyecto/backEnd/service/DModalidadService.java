package com.Proyecto.backEnd.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Proyecto.backEnd.exception.ResourceNotFoundException;
import com.Proyecto.backEnd.model.DModalidadModel;
import com.Proyecto.backEnd.model.DModalidadModelPK;
import com.Proyecto.backEnd.repository.DModalidadRepo;
import com.Proyecto.backEnd.utils.ApiResponse;
import com.Proyecto.backEnd.utils.CustomReponseBuilder;

import lombok.RequiredArgsConstructor;

import jakarta.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class DModalidadService {

    @Autowired
    private final CustomReponseBuilder customResponseBuilder;

    @Autowired
    private DModalidadRepo dmodRepo;

    // LISTAR todos los DModalidad
    public List<DModalidadModel> listaDModalidades() {
        return dmodRepo.findAll();
    }

    // Obtener por coddm
    public ResponseEntity<DModalidadModel> obtenerDModalidadPorCoddm(int coddm) {
        DModalidadModel dmodalidad = dmodRepo.findByDemodalidadCoddm(coddm)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No se encontró un DModalidad con coddm: " + coddm
                ));
        return ResponseEntity.ok(dmodalidad);
    }

    // Buscar por nombre
    public List<DModalidadModel> buscarDModalidadPorNombre(String valor) {
        return dmodRepo.buscarPorNombre(valor);
    }

    // AGREGAR
    public ResponseEntity<ApiResponse> agregarDModalidad(DModalidadModel dmodalidad) {

        boolean existeNombre = dmodRepo.findAll().stream()
                .anyMatch(d -> d.getNombre().equalsIgnoreCase(dmodalidad.getNombre()));

        if (existeNombre) {
            throw new ResourceNotFoundException(
                    "Ya existe un detalle de modalidad con el nombre: " + dmodalidad.getNombre()
            );
        }

        // Generar nuevo coddm
        Integer maxCoddm = dmodRepo.findAll().stream()
                .map(d -> d.getDemodalidad().getCoddm())
                .max(Integer::compareTo)
                .orElse(0);

        DModalidadModelPK pk = new DModalidadModelPK();
        pk.setCoddm(maxCoddm + 1);
        pk.setCodm(dmodalidad.getDemodalidad().getCodm()); // usar codm enviado desde front

        dmodalidad.setDemodalidad(pk);

        DModalidadModel guardado = dmodRepo.save(dmodalidad);

        return customResponseBuilder.buildResponse(
                "Detalle de modalidad agregado con éxito",
                guardado
        );
    }

    // ACTUALIZAR con posibilidad de cambiar codm
    @Transactional
    public ResponseEntity<ApiResponse> actualizarDModalidad(int coddm, DModalidadModel dmodalidadInput) {

        // Buscar registro existente
        DModalidadModel existente = dmodRepo.findByDemodalidadCoddm(coddm)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No se encontró un DModalidad con coddm: " + coddm
                ));

        // Validar nombre repetido
        if (!dmodalidadInput.getNombre().equalsIgnoreCase(existente.getNombre())) {
            boolean nombreExiste = dmodRepo.findAll().stream()
                    .anyMatch(d -> d.getNombre().equalsIgnoreCase(dmodalidadInput.getNombre()));
            if (nombreExiste) {
                throw new ResourceNotFoundException(
                        "Ya existe otro detalle de modalidad con el nombre: " + dmodalidadInput.getNombre()
                );
            }
        }

        // Actualizar usando query nativa
        dmodRepo.actualizarDModalidadConCodm(
                coddm,
                dmodalidadInput.getNombre(),
                dmodalidadInput.getEstado(),
                dmodalidadInput.getDemodalidad().getCodm() // nuevo codm
        );

        // Retornar la respuesta
        existente.setNombre(dmodalidadInput.getNombre());
        existente.setEstado(dmodalidadInput.getEstado());
        existente.getDemodalidad().setCodm(dmodalidadInput.getDemodalidad().getCodm());

        return customResponseBuilder.buildResponse(
                "Detalle de modalidad modificado con éxito",
                existente
        );
    }

    // ELIMINAR / HABILITAR detalle de modalidad
    public ResponseEntity<ApiResponse> eliminarDModalidad(int coddm) {

        DModalidadModel dmodalidad = dmodRepo.findByDemodalidadCoddm(coddm)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No se encontró un DModalidad con coddm: " + coddm
                ));

        int estadoActual = dmodalidad.getEstado();

        dmodRepo.eliminar(dmodalidad.getDemodalidad().getCoddm(), dmodalidad.getDemodalidad().getCodm());

        int nuevoEstado = (estadoActual == 1) ? 0 : 1;

        String mensaje = (nuevoEstado == 0)
                ? "Detalle de modalidad eliminado con éxito"
                : "Detalle de modalidad habilitado con éxito";

        return customResponseBuilder.buildResponse(mensaje, dmodalidad.getNombre());
    }

}
