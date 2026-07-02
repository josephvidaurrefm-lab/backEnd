package com.Proyecto.backEnd.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.Proyecto.backEnd.model.MateriasModel;

import jakarta.transaction.Transactional;

public interface MateriasRepo extends JpaRepository<MateriasModel, String> {

    // 🔍 Buscar por nombre
    @Query(value = "SELECT * FROM materias WHERE LOWER(nombre) LIKE LOWER(CONCAT('%', :valor, '%'))",
           nativeQuery = true)
    List<MateriasModel> buscarPorNombre(@Param("valor") String valor);

    // ✔️ Verificar duplicado al crear
    boolean existsByCodmat(String codmat);

    // ✔️ Verificar duplicado al modificar, excluyendo el id
    @Query("SELECT CASE WHEN COUNT(m) > 0 THEN true ELSE false END " +
           "FROM MateriasModel m WHERE LOWER(m.codmat) = LOWER(:codmat) AND m.codmat <> :codmatActual")
    boolean existsByCodmatAndCodmatNot(@Param("codmat") String codmat, @Param("codmatActual") String codmatActual);
    
    @Modifying
    @Transactional
    @Query(
        value = "INSERT INTO materias (codmat, nombre, estado, codn) VALUES (?1, ?2, ?3, ?4)",
        nativeQuery = true
    )
    void insertarMateria(String codmat, String nombre, int estado, int codn);
}
