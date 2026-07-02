package com.Proyecto.backEnd.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.Proyecto.backEnd.model.PrograModel;
import com.Proyecto.backEnd.model.PrograModelPK;

import jakarta.transaction.Transactional;

public interface PrograRepo extends JpaRepository<PrograModel,PrograModelPK>{
    // Eliminación 
	@Modifying
	@Transactional
	@Query(value = "DELETE FROM progra WHERE codmat = :codmat AND codpar = :codpar AND gestion = :gestion AND codp = :codp",
	       nativeQuery = true)
	void eliminarProgra(
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
            FROM progra d
            JOIN personal p ON d.codp = p.codp
            JOIN materias m ON d.codmat = m.codmat
            JOIN niveles n ON m.codn = n.codn
            JOIN paralelos pa ON d.codpar = pa.codpar
            """, nativeQuery = true)
        List<Object[]> findAllPrograInfoRaw();
        
        
        @Query("SELECT CASE WHEN COUNT(d) > 0 THEN true ELSE false END " +
        	       "FROM PrograModel d " +
        	       "WHERE d.id.codmat = :codmat AND d.id.codpar = :codpar AND d.id.gestion = :gestion AND d.id.codp = :codp " +
        	       "AND d.id <> :pk")
        	boolean existsByIdNotAndCodmatAndCodparAndGestionAndCodp(
        	        @Param("pk") PrograModelPK pk,
        	        @Param("codmat") String codmat,
        	        @Param("codpar") int codpar,
        	        @Param("gestion") int gestion,
        	        @Param("codp") int codp
        	);
        
        //put
        @Modifying
        @Transactional
        @Query(
            value = "UPDATE progra SET login = :login " +
                    "WHERE codmat = :codmat AND codpar = :codpar AND gestion = :gestion AND codp = :codp",
            nativeQuery = true
        )
        void actualizarPrograNative(
            @Param("codmat") String codmat,
            @Param("codpar") int codpar,
            @Param("gestion") int gestion,
            @Param("codp") int codp,
            @Param("login") String login
        );
        
        //GETPORNOMBRE
        @Query(value = "SELECT d.codmat, m.nombre AS nombreMateria, d.codpar, p.nombre AS nombreParalelo, " +
                "d.gestion, d.codp, per.nombre AS nombrePersona, n.nombre AS nivelMateria " +
                "FROM progra d " +
                "JOIN materias m ON d.codmat = m.codmat " +
                "JOIN paralelos p ON d.codpar = p.codpar " +
                "JOIN personal per ON d.codp = per.codp " +
                "JOIN niveles n ON m.codn = n.codn " +
                "WHERE LOWER(m.nombre) LIKE LOWER(CONCAT('%', :valor, '%')) " +
                "   OR LOWER(per.nombre) LIKE LOWER(CONCAT('%', :valor, '%'))",
        nativeQuery = true)
 List<Object[]> buscarPorMateriaOProfesor(@Param("valor") String valor);
        
    
    // Buscar Dicta por PK (Optional, como findByDemodalidadCoddm)
    Optional<PrograModel> findById(PrograModelPK id);
    
    @Query(value = """
			SELECT 
			    d.codmat,
			    m.nombre AS nombreMateria,
			    d.codpar,
			    pa.nombre AS nombreParalelo,
			    d.gestion,
			    d.codp,
			    d.login AS login,
			    n.nombre AS nivelMateria,
			    m.estado  -- <-- agregamos el estado de la materia
			FROM progra d
			JOIN materias m ON d.codmat = m.codmat
			JOIN niveles n ON m.codn = n.codn
			JOIN paralelos pa ON d.codpar = pa.codpar
            """, nativeQuery = true)
    List<Object[]> findAllPrograInfoLogin();
}
