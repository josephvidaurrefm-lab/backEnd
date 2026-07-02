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

import com.Proyecto.backEnd.model.ItemsModel;
import com.Proyecto.backEnd.service.ItemsService;
import com.Proyecto.backEnd.utils.ApiResponse;

@RestController
public class ItemsController {

    @Autowired
    ItemsService iteService;

    // LISTAR TODOS LOS ITEMS
    @GetMapping("/api/items")
    public List<ItemsModel> listaItems() {
        return iteService.listaItems();
    }

    // BUSCAR POR ID O POR NOMBRE
    @GetMapping("/api/items/{valor}")
    public List<ItemsModel> buscarItemPorIdONombre(@PathVariable String valor) {
        try {
            int codi = Integer.parseInt(valor);
            Optional<ItemsModel> item = iteService.verItem(codi);
            return item.map(List::of).orElse(List.of());
        } catch (NumberFormatException e) {
            // No es número → buscar por nombre parcial
            return iteService.buscarPorNombre(valor);
        }
    }

    // AGREGAR ITEM
    @PostMapping("/api/items")
    public ResponseEntity<ApiResponse> agreItem(@RequestBody ItemsModel item) {
        return iteService.agreItemResponse(item);
    }

    // MODIFICAR ITEM
    @PutMapping("/api/items/{codi}")
    public ResponseEntity<ApiResponse> modItem(@RequestBody ItemsModel item, @PathVariable int codi) {
        return iteService.modItemResponse(codi, item);
    }

    // ELIMINAR (LÓGICO) ITEM
    @DeleteMapping("/api/items/{codi}")
    public ResponseEntity<ApiResponse> eliItem(@PathVariable int codi) {
        return iteService.eliItemResponse(codi);
    }
}
