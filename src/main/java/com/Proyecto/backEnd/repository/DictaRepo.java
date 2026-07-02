package com.Proyecto.backEnd.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.Proyecto.backEnd.model.DictaModel;
import com.Proyecto.backEnd.model.DictaModelPK;

import jakarta.transaction.Transactional;

public interface DictaRepo extends JpaRepository<DictaModel,DictaModelPK>{
    // Eliminación 
	@Modifying
	@Transactional
	@Query(value = "DELETE FROM dicta WHERE codmat = :codmat AND codpar = :codpar AND gestion = :gestion AND codp = :codp", 
	       nativeQuery = true)
	void eliminarDicta(
	        @Param("codmat") String codmat,
	        @Param("codpar") int codpar,
	        @Param("gestion") int gestion,
	        @Param("codp") int codp
	);


    
    @Query(value = """
            SELECT 
                d.codmat AS codmat,
                m.nombre AS nombreMateria,
                d.codpar AS codpar,
                pa.nombre AS nombreParalelo,
                d.gestion AS gestion,
                d.codp AS codp,
                p.nombre AS nombrePersona,
                n.nombre AS nivelMateria
            FROM dicta d
            JOIN personal p ON d.codp = p.codp
            JOIN materias m ON d.codmat = m.codmat
            JOIN niveles n ON m.codn = n.codn
            JOIN paralelos pa ON d.codpar = pa.codpar
            """, nativeQuery = true)
        List<Object[]> findAllDictaInfoRaw();
        
        
        @Query("SELECT CASE WHEN COUNT(d) > 0 THEN true ELSE false END " +
        	       "FROM DictaModel d " +
        	       "WHERE d.id.codmat = :codmat AND d.id.codpar = :codpar AND d.id.gestion = :gestion AND d.id.codp = :codp " +
        	       "AND d.id <> :pk")
        	boolean existsByIdNotAndCodmatAndCodparAndGestionAndCodp(
        	        @Param("pk") DictaModelPK pk,
        	        @Param("codmat") String codmat,
        	        @Param("codpar") int codpar,
        	        @Param("gestion") int gestion,
        	        @Param("codp") int codp
        	);
        
        //put
        @Modifying
        @Transactional
        @Query(
            value = "UPDATE dicta SET login = :login" +
                    "WHERE codmat = :codmat AND codpar = :codpar AND gestion = :gestion AND codp = :codp",
            nativeQuery = true
        )
        void actualizarDictaNative(
            @Param("codmat") String codmat,
            @Param("codpar") int codpar,
            @Param("gestion") int gestion,
            @Param("codp") int codp,
            @Param("login") String login
        );
        
        //GETPORNOMBRE
        @Query(value = "SELECT d.codmat, m.nombre AS nombreMateria, d.codpar, p.nombre AS nombreParalelo, " +
                "d.gestion, d.codp, per.nombre AS nombrePersona, n.nombre AS nivelMateria " +
                "FROM dicta d " +
                "JOIN materias m ON d.codmat = m.codmat " +
                "JOIN paralelos p ON d.codpar = p.codpar " +
                "JOIN personal per ON d.codp = per.codp " +
                "JOIN niveles n ON m.codn = n.codn " +
                "WHERE LOWER(m.nombre) LIKE LOWER(CONCAT('%', :valor, '%')) " +
                "   OR LOWER(per.nombre) LIKE LOWER(CONCAT('%', :valor, '%'))",
        nativeQuery = true)
 List<Object[]> buscarPorMateriaOProfesor(@Param("valor") String valor);
        
    
    // Buscar Dicta por PK (Optional, como findByDemodalidadCoddm)
    Optional<DictaModel> findById(DictaModelPK id);
}
