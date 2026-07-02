package com.Proyecto.backEnd.repository;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.Proyecto.backEnd.model.MapaModel;
import com.Proyecto.backEnd.model.MapaModelPK;

import jakarta.transaction.Transactional;

public interface MapaRepo extends JpaRepository<MapaModel,MapaModelPK>{
    List<MapaModel> findByMapaCodmat(String codmat);

    // DELETE NATIVO por codmat y codpar
    @Modifying
    @Transactional
    @Query(value = """
        DELETE FROM mapa 
        WHERE codmat = :codmat 
        AND codpar = :codpar
    """, nativeQuery = true)
    void eliminarPorCodmatCodpar(String codmat, int codpar);

    // INSERT NATIVO
    @Modifying
    @Transactional
    @Query(value = """
        INSERT INTO mapa(codmat, codpar, gestion, estado) 
        VALUES(:codmat, :codpar, :gestion, 1)
    """, nativeQuery = true)
    void insertarMapa(String codmat, int codpar, int gestion);

    boolean existsByMapaCodmatAndMapaCodparAndMapaGestion(
    	    String codmat,
    	    int codpar,
    	    int gestion
    	);

//-----------------------------
    @Query(value = """
            SELECT 
                m.codmat AS codmat,
                ma.nombre AS nombreMateria,
                m.codpar AS codpar,
                p.nombre AS nombreParalelo,
                m.gestion AS gestion,
                n.nombre AS nivelMateria
            FROM mapa m
            JOIN materias ma ON m.codmat = ma.codmat
            JOIN niveles n ON ma.codn = n.codn
            JOIN paralelos p ON m.codpar = p.codpar
            """, nativeQuery = true)
        List<Object[]> findAllMapaInfoRaw();

}
