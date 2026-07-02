package com.Proyecto.backEnd.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.repository.query.Param;

import com.Proyecto.backEnd.model.ItemsModel;

public interface ItemsRepo extends JpaRepository<ItemsModel, Integer> {

    // 🔄 Eliminación lógica: cambia estado 1 ↔ 0
    @Modifying
    @Transactional
    @Query(value = "UPDATE items SET estado = CASE WHEN estado = 1 THEN 0 ELSE 1 END WHERE codi = :codi", nativeQuery = true)
    void eliminar(@Param("codi") int codi);

    // 🔍 Buscar por nombre del ítem
    @Query(value = "SELECT * FROM items WHERE LOWER(nombre) LIKE LOWER(CONCAT('%', :valor, '%'))",
           nativeQuery = true)
    List<ItemsModel> buscarPorNombre(@Param("valor") String valor);

    // ⚠️ Para validar duplicados
    boolean existsByNombre(String nombre);

    @Query("SELECT CASE WHEN COUNT(i) > 0 THEN true ELSE false END "
         + "FROM ItemsModel i WHERE i.nombre = :nombre AND i.codi <> :codi")
    boolean existsByNombreAndCodiNot(@Param("nombre") String nombre, @Param("codi") int codi);
}
