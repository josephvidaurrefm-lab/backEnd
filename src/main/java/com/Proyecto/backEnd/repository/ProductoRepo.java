package com.Proyecto.backEnd.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.Proyecto.backEnd.model.ProductoModel;

public interface ProductoRepo extends JpaRepository<ProductoModel, Integer> {

    @Modifying
    @Transactional
    @Query(value = "UPDATE producto SET estado = CASE WHEN estado = 1 THEN 0 ELSE 1 END WHERE idproducto = :idProducto", nativeQuery = true)
    void eliminar(@Param("idProducto") int idProducto);

}