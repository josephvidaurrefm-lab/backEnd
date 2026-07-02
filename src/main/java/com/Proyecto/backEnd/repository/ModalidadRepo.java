package com.Proyecto.backEnd.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.Proyecto.backEnd.model.ModalidadModel;

public interface ModalidadRepo extends JpaRepository<ModalidadModel, Integer> {

    // 🔄 Eliminación lógica
    @Modifying
    @Transactional
    @Query(value = "UPDATE modalidad SET estado = CASE WHEN estado = 1 THEN 0 ELSE 1 END WHERE codm = :codm", nativeQuery = true)
    void eliminar(@Param("codm") int codm);

    // 🔍 Buscar por nombre
    @Query(value = "SELECT * FROM modalidad WHERE LOWER(nombre) LIKE LOWER(CONCAT('%', :valor, '%'))",
           nativeQuery = true)
    List<ModalidadModel> buscarPorNombre(@Param("valor") String valor);

    // ⚠️ Validar duplicados
    boolean existsByNombre(String nombre);

    @Query("SELECT CASE WHEN COUNT(m) > 0 THEN true ELSE false END "
         + "FROM ModalidadModel m WHERE m.nombre = :nombre AND m.codm <> :codm")
    boolean existsByNombreAndIdNot(@Param("nombre") String nombre, @Param("codm") int codm);
}
