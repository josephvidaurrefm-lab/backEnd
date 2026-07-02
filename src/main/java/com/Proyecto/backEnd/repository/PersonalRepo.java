package com.Proyecto.backEnd.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.Proyecto.backEnd.model.PersonalModel;

import jakarta.transaction.Transactional;

public interface PersonalRepo extends JpaRepository<PersonalModel,Integer>{
    @Modifying
    @Transactional
    @Query(value = "UPDATE personal SET estado = CASE WHEN estado = 1 THEN 0 ELSE 1 END WHERE codp = ?1", nativeQuery = true)
    void toggleEstadoByCodp(int codp);
   
    // ✅ Buscar por nombre o apellido usando consulta nativa
    // Buscar por nombre o apellidos (insensible a mayúsculas)
    @Query(value = "SELECT * FROM personal WHERE " +
                   "LOWER(nombre) LIKE LOWER(CONCAT('%', :valor, '%')) OR " +
                   "LOWER(ap) LIKE LOWER(CONCAT('%', :valor, '%')) OR " +
                   "LOWER(am) LIKE LOWER(CONCAT('%', :valor, '%'))",
           nativeQuery = true)
    List<PersonalModel> buscarPorNombreOApellido(@Param("valor") String valor);
}
