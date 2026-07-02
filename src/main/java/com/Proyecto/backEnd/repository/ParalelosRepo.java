package com.Proyecto.backEnd.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.Proyecto.backEnd.model.ParalelosModel;

public interface ParalelosRepo extends JpaRepository<ParalelosModel, Integer> {

    // 🔄 Alternar estado (1 ↔ 0)
    @Modifying
    @Transactional
    @Query(value = "UPDATE paralelos SET estado = CASE WHEN estado = 1 THEN 0 ELSE 1 END WHERE codpar = :codpar",
           nativeQuery = true)
    void eliminar(@Param("codpar") int codpar);

    // 🔍 Buscar por nombre
    @Query(value = "SELECT * FROM paralelos WHERE LOWER(nombre) LIKE LOWER(CONCAT('%', :valor, '%'))",
           nativeQuery = true)
    List<ParalelosModel> buscarPorNombre(@Param("valor") String valor);

    // ✔️ Verificar duplicado al crear
    boolean existsByNombre(String nombre);

    // ✔️ Verificar duplicado al modificar, excluyendo el id
    @Query("SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END " +
           "FROM ParalelosModel p WHERE LOWER(p.nombre) = LOWER(:nombre) AND p.codpar <> :codpar")
    boolean existsByNombreAndCodparNot(@Param("nombre") String nombre, @Param("codpar") int codpar);
    
}
