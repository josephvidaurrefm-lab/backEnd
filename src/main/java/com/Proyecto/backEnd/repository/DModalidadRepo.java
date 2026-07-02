package com.Proyecto.backEnd.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.Proyecto.backEnd.model.DModalidadModel;
import com.Proyecto.backEnd.model.DModalidadModelPK;

import jakarta.transaction.Transactional;

public interface DModalidadRepo extends JpaRepository<DModalidadModel,DModalidadModelPK> {
	
    // Devuelve todos los DModalidad de un coddm específico
	 Optional<DModalidadModel> findByDemodalidadCoddm(Integer coddm);
	 
	@Modifying
	@Transactional
	@Query(
	    value = "UPDATE dmodalidad " +
	            "SET estado = CASE WHEN estado = 1 THEN 0 ELSE 1 END " +
	            "WHERE coddm = :coddm AND codm = :codm",
	    nativeQuery = true
	)
	void eliminar(
	    @Param("coddm") int coddm,
	    @Param("codm") int codm
	);
	
	@Query(value = "SELECT * FROM dmodalidad WHERE LOWER(nombre) LIKE LOWER(CONCAT('%', :valor, '%'))",
		       nativeQuery = true)
		List<DModalidadModel> buscarPorNombre(@Param("valor") String valor);

	
	@Modifying
	@Transactional
	@Query(
	    value = "UPDATE dmodalidad " +
	            "SET nombre = :nombre, estado = :estado, codm = :codm " +
	            "WHERE coddm = :coddm",
	    nativeQuery = true
	)
	void actualizarDModalidadConCodm(
	        @Param("coddm") int coddm,
	        @Param("nombre") String nombre,
	        @Param("estado") int estado,
	        @Param("codm") int codm
	);

}
