package com.Proyecto.backEnd.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.Proyecto.backEnd.model.MenusModel;

import jakarta.transaction.Transactional;

public interface MenusRepo extends JpaRepository<MenusModel,Integer>{
	
	// Insertar relación individual menú–proceso
	@Modifying
	@Transactional
	@Query(value = "INSERT INTO mepro (codm, codp) VALUES (:codm, :codp)", nativeQuery = true)
	void insertarRelacionMepro(@Param("codm") int codm, @Param("codp") int codp);

	// Eliminar relación individual menú–proceso
	@Modifying
	@Transactional
	@Query(value = "DELETE FROM mepro WHERE codm = :codm AND codp = :codp", nativeQuery = true)
	void eliminarRelacionMepro(@Param("codm") int codm, @Param("codp") int codp);


    // 🔄 Eliminación lógica: cambia estado 1 ↔ 0
    @Modifying
    @Transactional
    @Query(value = "UPDATE menus SET estado = CASE WHEN estado = 1 THEN 0 ELSE 1 END WHERE codm = :codm", nativeQuery = true)
    void eliminar(@Param("codm") int codm);
    // 🔍 Buscar por nombre del menú (insensible a mayúsculas)
    
    @Query(value = "SELECT * FROM menus WHERE LOWER(nombre) LIKE LOWER(CONCAT('%', :valor, '%'))",
           nativeQuery = true)
    List<MenusModel> buscarPorNombre(@Param("valor") String valor);

    // Verificar duplicado por nombre
    boolean existsByNombre(String nombre);

    // Verificar duplicado al modificar (excluyendo el id actual)
    @Query("SELECT CASE WHEN COUNT(m) > 0 THEN true ELSE false END FROM MenusModel m WHERE LOWER(m.nombre) = LOWER(:nombre) AND m.codm <> :codm")
    boolean existsByNombreAndCodmNot(@Param("nombre") String nombre, @Param("codm") int codm);
    
    //menuProceso
    @Query(value = "SELECT codp FROM mepro WHERE codm = :codm", nativeQuery = true)
    List<Integer> obtenerProcesosAsignados(@Param("codm") int codm);
}
