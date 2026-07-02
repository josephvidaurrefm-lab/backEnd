package com.Proyecto.backEnd.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.Proyecto.backEnd.model.ProductoModel;
import com.Proyecto.backEnd.service.ProductoService;

@RestController
public class ProductoController {

    @Autowired
    ProductoService proService;

    // Listar todos los productos
    @GetMapping("/api/producto")
    public List<ProductoModel> listaProductos() {
        return proService.listaProductos();
    }

    // Ver un producto por ID
    @GetMapping("/api/producto/{idProducto}")
    public Optional<ProductoModel> verProducto(@PathVariable int idProducto) {
        return proService.verProducto(idProducto);
    }

    // Agregar un producto
    @PostMapping("/api/producto")
    public ProductoModel agreProducto(@RequestBody ProductoModel producto) {
        return proService.agreProducto(producto);
    }

    // Modificar un producto
    @PutMapping("/api/producto/{idProducto}")
    public ProductoModel modProducto(
            @RequestBody ProductoModel producto,
            @PathVariable int idProducto) {

        return proService.modProducto(idProducto, producto);
    }

    // Eliminar un producto
    @DeleteMapping("/api/producto/{idProducto}")
    public void eliProducto(@PathVariable int idProducto) {
        proService.eliProducto(idProducto);
    }

}