package com.Proyecto.backEnd.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.repository.query.Param;

import com.Proyecto.backEnd.model.NivelesModel;

public interface NivelesRepo extends JpaRepository<NivelesModel, Integer> {

    // 🔄 Eliminación lógica: cambia estado 1 ↔ 0
    @Modifying
    @Transactional
    @Query(value = "UPDATE niveles SET estado = CASE WHEN estado = 1 THEN 0 ELSE 1 END WHERE codn = :codn", nativeQuery = true)
    void eliminar(@Param("codn") int codn);

    // 🔍 Buscar por nombre del nivel
    @Query(value = "SELECT * FROM niveles WHERE LOWER(nombre) LIKE LOWER(CONCAT('%', :valor, '%'))",
           nativeQuery = true)
    List<NivelesModel> buscarPorNombre(@Param("valor") String valor);

    // ⚠️ Para validar duplicados
    boolean existsByNombre(String nombre);

    @Query("SELECT CASE WHEN COUNT(n) > 0 THEN true ELSE false END "
         + "FROM NivelesModel n WHERE n.nombre = :nombre AND n.codn <> :codn")
    boolean existsByNombreAndIdNot(@Param("nombre") String nombre, @Param("codn") int codn);
}
