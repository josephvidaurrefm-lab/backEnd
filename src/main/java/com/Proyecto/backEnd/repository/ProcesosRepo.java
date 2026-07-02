package com.Proyecto.backEnd.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.Proyecto.backEnd.model.ProcesosModel;

import jakarta.transaction.Transactional;

public interface ProcesosRepo extends JpaRepository<ProcesosModel,Integer> {

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM mepro WHERE codp = :codp", nativeQuery = true)
    void eliminarRelaciones(int codp);
    
    @Query(value = "SELECT * FROM procesos WHERE LOWER(nombre) LIKE LOWER(CONCAT('%', :valor, '%'))", 
            nativeQuery = true)
     List<ProcesosModel> buscarPorNombre(@Param("valor") String valor);
}
