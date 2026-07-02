package com.Proyecto.backEnd.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Proyecto.backEnd.exception.ResourceNotFoundException;
import com.Proyecto.backEnd.model.NivelesModel;
import com.Proyecto.backEnd.utils.ApiResponse;
import com.Proyecto.backEnd.utils.CustomReponseBuilder;
import com.Proyecto.backEnd.repository.NivelesRepo;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class NivelesService {

    private final CustomReponseBuilder customResponseBuilder;

    @Autowired
    NivelesRepo nivRepo;

    // LISTAR TODOS LOS NIVELES
    public List<NivelesModel> listaNiveles() {
        return nivRepo.findAll();
    }

    // VER UN NIVEL POR ID
    public Optional<NivelesModel> verNivel(int id) {
        return nivRepo.findById(id);
    }

    // BUSCAR POR NOMBRE
    public List<NivelesModel> buscarPorNombre(String valor) {
        return nivRepo.buscarPorNombre(valor);
    }

    // POST – Agregar nivel
    public ResponseEntity<ApiResponse> agreNivelResponse(NivelesModel nivel) {
        if (nivRepo.existsByNombre(nivel.getNombre())) {
            throw new ResourceNotFoundException("Ya existe un nivel con el " + nivel.getNombre());
        }

        NivelesModel guardado = nivRepo.save(nivel);
        return customResponseBuilder.buildResponse("Nivel agregado con éxito", guardado);
    }

    // PUT – Modificar nivel
    public ResponseEntity<ApiResponse> modNivelResponse(int id, NivelesModel nivel) {

        if (nivRepo.existsByNombreAndIdNot(nivel.getNombre(), id)) {
            throw new ResourceNotFoundException(
                "Ya existe otro nivel con el nombre: " + nivel.getNombre()
            );
        }

        nivel.setCodn(id);

        NivelesModel modificado = nivRepo.save(nivel);

        return customResponseBuilder.buildResponse(
            "Nivel modificado con éxito", 
            modificado
        );
    }

    // DELETE – Eliminación lógica con mensaje dinámico
    public ResponseEntity<ApiResponse> eliNivelResponse(int id) {
        NivelesModel nivel = nivRepo.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException(
                "No se encontró un nivel con id: " + id
            ));

        nivRepo.eliminar(id);

        int nuevoEstado = nivel.getEstado() == 1 ? 0 : 1;

        String mensaje = (nuevoEstado == 0)
                ? "Nivel eliminado con éxito"
                : "Nivel habilitado con éxito";

        return customResponseBuilder.buildResponse(mensaje, nivel.getNombre());
    }
}
