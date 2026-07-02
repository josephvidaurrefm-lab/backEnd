package com.Proyecto.backEnd.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Proyecto.backEnd.exception.ResourceNotFoundException;
import com.Proyecto.backEnd.model.ItemsModel;
import com.Proyecto.backEnd.utils.ApiResponse;
import com.Proyecto.backEnd.utils.CustomReponseBuilder;
import com.Proyecto.backEnd.repository.ItemsRepo;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ItemsService {

    private final CustomReponseBuilder customResponseBuilder;

    @Autowired
    ItemsRepo iteRepo;

    // LISTAR TODOS LOS ÍTEMS
    public List<ItemsModel> listaItems() {
        return iteRepo.findAll();
    }

    // VER UN ÍTEM POR CODI
    public Optional<ItemsModel> verItem(int codi) {
        return iteRepo.findById(codi);
    }

    // BUSCAR POR NOMBRE
    public List<ItemsModel> buscarPorNombre(String valor) {
        return iteRepo.buscarPorNombre(valor);
    }

    // POST – Agregar ítem
    public ResponseEntity<ApiResponse> agreItemResponse(ItemsModel item) {
        if (iteRepo.existsByNombre(item.getNombre())) {
            throw new ResourceNotFoundException("Ya existe un ítem con el nombre: " + item.getNombre());
        }

        ItemsModel guardado = iteRepo.save(item);
        return customResponseBuilder.buildResponse("Ítem agregado con éxito", guardado);
    }

    // PUT – Modificar ítem
    public ResponseEntity<ApiResponse> modItemResponse(int codi, ItemsModel item) {

        // Verifica si existe OTRO registro con el mismo nombre
        if (iteRepo.existsByNombreAndCodiNot(item.getNombre(), codi)) {
            throw new ResourceNotFoundException(
                "Ya existe otro ítem con el nombre: " + item.getNombre()
            );
        }

        // Aseguramos que el código sea el del ítem que se está modificando
        item.setCodi(codi);

        ItemsModel modificado = iteRepo.save(item);

        return customResponseBuilder.buildResponse(
            "Ítem modificado con éxito", 
            modificado
        );
    }


    // DELETE – Eliminación lógica con mensaje dinámico
    public ResponseEntity<ApiResponse> eliItemResponse(int codi) {
        ItemsModel item = iteRepo.findById(codi)
            .orElseThrow(() -> new ResourceNotFoundException(
                "No se encontró un ítem con codi: " + codi
            ));

        // Ejecuta el toggle 1 ↔ 0
        iteRepo.eliminar(codi);

        int nuevoEstado = item.getEstado() == 1 ? 0 : 1;

        String mensaje = (nuevoEstado == 0)
                ? "Ítem eliminado con éxito"
                : "Ítem habilitado con éxito";

        return customResponseBuilder.buildResponse(mensaje, item.getNombre());
    }
}
