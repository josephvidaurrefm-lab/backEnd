package com.Proyecto.backEnd.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.Proyecto.backEnd.model.UsuariosModel;

import jakarta.transaction.Transactional;

public interface UsuariosRepo extends JpaRepository<UsuariosModel,String>{
	Optional<UsuariosModel> findByLogin(String login);
    @Modifying
    @Transactional
    @Query(value = "UPDATE usuarios SET estado = CASE WHEN estado = 1 THEN 0 ELSE 1 END WHERE login = ?1", nativeQuery = true)
    void toggleEstadoByLogin(String login);
    
    // Insertar relación usuario–rol
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO usurol (login, codr) VALUES (:login, :codr)", nativeQuery = true)
    void insertarRelacionUsuarioRol(@Param("login") String login, @Param("codr") int codr);

    // Eliminar relación usuario–rol
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM usurol WHERE login = :login AND codr = :codr", nativeQuery = true)
    void eliminarRelacionUsuarioRol(@Param("login") String login, @Param("codr") int codr);

    // Obtener roles asignados a un usuario
    @Query(value = "SELECT codr FROM usurol WHERE login = :login", nativeQuery = true)
    List<Integer> obtenerRolesAsignados(@Param("login") String login);
}
