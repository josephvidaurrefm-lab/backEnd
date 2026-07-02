package com.Proyecto.backEnd.repository;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.Proyecto.backEnd.model.ItematModel;
import com.Proyecto.backEnd.model.ItematPK;

import jakarta.transaction.Transactional;

public interface IteMatRepo extends JpaRepository<ItematModel,ItematPK>{
	List<ItematModel> findByItematCodmat(String codmat);
	
    // DELETE NATIVO por codmat y codi
    @Modifying
    @Transactional
    @Query(value = """
        DELETE FROM itemat 
        WHERE codmat = :codmat
        AND codi = :codi
    """, nativeQuery = true)
    void eliminarPorCodmatCodi(String codmat, int codi);
    
    @Modifying
    @Transactional
    @Query(value = """
        INSERT INTO itemat (codmat, codi, gestion, estado, ponderacion)
        VALUES (:codmat, :codi, :gestion, 1, :ponderacion)
    """, nativeQuery = true)
    void insertarRelacionItemat(
            String codmat,
            int codi,
            int gestion,
            int ponderacion
    );
    // Verifica si ya existe la relación en la gestión actual
    boolean existsByItematCodmatAndItematCodiAndItematGestion(
            String codmat,
            int codi,
            int gestion
    );
}
