package com.Proyecto.backEnd.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.Proyecto.backEnd.model.RolesModel;

import jakarta.transaction.Transactional;

public interface RolesRepo extends JpaRepository<RolesModel,Integer>{
    
    // 🔄 Eliminación lógica: cambia estado 1 ↔ 0
    @Modifying
    @Transactional
    @Query(value = "UPDATE roles SET estado = CASE WHEN estado = 1 THEN 0 ELSE 1 END WHERE codr = :codr", nativeQuery = true)
    void eliminar(@Param("codr") int codr);
    
    // 🔍 Buscar por nombre del rol (insensible a mayúsculas)
    @Query(value = "SELECT * FROM roles WHERE LOWER(nombre) LIKE LOWER(CONCAT('%', :valor, '%'))",
           nativeQuery = true)
    List<RolesModel> buscarPorNombre(@Param("valor") String valor);
    
    // Verificar si ya existe un rol con el mismo nombre
    boolean existsByNombre(String nombre);
    
    // Verificar si existe otro rol con el mismo nombre distinto al id dado (para PUT)
    @Query("SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END FROM RolesModel r WHERE LOWER(r.nombre) = LOWER(:nombre) AND r.codr <> :codr")
    boolean existsByNombreAndCodrNot(@Param("nombre") String nombre, @Param("codr") int codr);

    // Insertar relación individual rol–menú
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO rolme (codr, codm) VALUES (:codr, :codm)", nativeQuery = true)
    void insertarRelacionRolMe(@Param("codr") int codr, @Param("codm") int codm);

    // Eliminar relación individual rol–menú
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM rolme WHERE codr = :codr AND codm = :codm", nativeQuery = true)
    void eliminarRelacionRolMe(@Param("codr") int codr, @Param("codm") int codm);

    // Obtener menús asignados a un rol
    @Query(value = "SELECT codm FROM rolme WHERE codr = :codr", nativeQuery = true)
    List<Integer> obtenerMenusAsignados(@Param("codr") int codr);
}
