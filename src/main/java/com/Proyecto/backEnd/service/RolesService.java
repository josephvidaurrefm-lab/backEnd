package com.Proyecto.backEnd.service;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Proyecto.backEnd.exception.ResourceNotFoundException;
import com.Proyecto.backEnd.model.MenusModel;
import com.Proyecto.backEnd.model.RolesModel;
import com.Proyecto.backEnd.model.DTO.RolMenuDTO;
import com.Proyecto.backEnd.repository.MenusRepo;
import com.Proyecto.backEnd.repository.RolesRepo;
import com.Proyecto.backEnd.utils.ApiResponse;
import com.Proyecto.backEnd.utils.CustomReponseBuilder;

import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
@Service
public class RolesService {
	private final CustomReponseBuilder customResponseBuilder;
	@Autowired
	RolesRepo rolRepo;
	
	@Autowired
	MenusRepo menRepo;

	public List<RolesModel> listaRolesExeption(){
		return rolRepo.findAll();
	}
    // Ver un rol por codr
    public Optional<RolesModel> verRol(int codr) {
        return rolRepo.findById(codr);
    }
    // ======= Nuevo: buscar por nombre (coincidencia parcial, insensible a mayúsculas)
    public List<RolesModel> buscarPorNombre(String valor) {
        return rolRepo.buscarPorNombre(valor);
    }

 // POST → Agregar rol
    public ResponseEntity<ApiResponse> agreRolResponse(RolesModel rol) {
        if (rolRepo.existsByNombre(rol.getNombre())) {
            throw new ResourceNotFoundException("Ya existe un rol con el nombre: " + rol.getNombre());
        }
        RolesModel guardado = rolRepo.save(rol);
        return customResponseBuilder.buildResponse("Rol agregado con éxito", guardado);
    }

 // PUT → Modificar rol
    public ResponseEntity<ApiResponse> modRolResponse(int codr, RolesModel rol) {
        if (rolRepo.existsByNombreAndCodrNot(rol.getNombre(), codr)) {
            throw new ResourceNotFoundException("Ya existe otro rol con el nombre: " + rol.getNombre());
        }
        rol.setCodr(codr);
        RolesModel modificado = rolRepo.save(rol);
        return customResponseBuilder.buildResponse("Rol modificado con éxito", modificado);
    }

    // DELETE → Eliminación lógica del rol
    public ResponseEntity<ApiResponse> eliRolResponse(int codr) {
        // Primero obtenemos el rol actual
        RolesModel rol = rolRepo.findById(codr)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró un rol con codr: " + codr));

        // Ejecutamos la eliminación lógica (cambia estado 1↔0)
        rolRepo.eliminar(codr);

        // Obtenemos el nuevo estado
        int nuevoEstado = rol.getEstado() == 1 ? 0 : 1;

        // Construimos el mensaje según el nuevo estado
        String mensaje = (nuevoEstado == 0) ? "Rol eliminado con éxito" : "Rol habilitado con éxito";

        // Pasamos el nombre del rol como data
        return customResponseBuilder.buildResponse(mensaje,rol.getNombre());
    }

    // Asignar un menú individual al rol
    public ResponseEntity<ApiResponse> asignarMenuARol(int codr, int codm) {
        rolRepo.insertarRelacionRolMe(codr, codm);
        return customResponseBuilder.buildResponse("Menú asignado al rol con éxito", codm);
    }

    // Desasignar un menú individual del rol
    public ResponseEntity<ApiResponse> desasignarMenuARol(int codr, int codm) {
        rolRepo.eliminarRelacionRolMe(codr, codm);
        return customResponseBuilder.buildResponse("Menú desasignado del rol con éxito", codm);
    }

    // Listar menús por rol con flag asignado
    public List<RolMenuDTO> listarMenusPorRol(int codr) {
        List<MenusModel> todos = menRepo.findAll(); // Todos los menús
        List<Integer> asignados = rolRepo.obtenerMenusAsignados(codr); // Codm asignados

        return todos.stream()
                .map(m -> new RolMenuDTO(m.getCodm(), m.getNombre(), asignados.contains(m.getCodm())))
                .toList();
    }


}
