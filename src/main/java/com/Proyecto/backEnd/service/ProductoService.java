package com.Proyecto.backEnd.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Proyecto.backEnd.model.ProductoModel;
import com.Proyecto.backEnd.repository.ProductoRepo;

@Service
public class ProductoService {

    @Autowired
    ProductoRepo proRepo;

    // Listar productos
    public List<ProductoModel> listaProductos() {
        return proRepo.findAll();
    }

    // Ver un producto por ID
    public Optional<ProductoModel> verProducto(int idProducto) {
        return proRepo.findById(idProducto);
    }

    // Agregar un producto
    public ProductoModel agreProducto(ProductoModel producto) {
        return proRepo.save(producto);
    }

    // Modificar un producto
    public ProductoModel modProducto(int idProducto, ProductoModel producto) {
        producto.setIdProducto(idProducto);
        return proRepo.save(producto);
    }

    // Eliminar un producto
    public void eliProducto(int idProducto) {
        proRepo.eliminar(idProducto);
    }

}