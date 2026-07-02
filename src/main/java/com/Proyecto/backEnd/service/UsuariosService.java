package com.Proyecto.backEnd.service;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Proyecto.backEnd.model.RolesModel;
import com.Proyecto.backEnd.model.UsuariosModel;
import com.Proyecto.backEnd.model.DTO.UsuarioRolDTO;
import com.Proyecto.backEnd.repository.RolesRepo;
import com.Proyecto.backEnd.repository.UsuariosRepo;
import com.Proyecto.backEnd.utils.ApiResponse;
import com.Proyecto.backEnd.utils.CustomReponseBuilder;

import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
@Service
public class UsuariosService {
	@Autowired
	UsuariosRepo usuRepo;
	
    @Autowired
    RolesRepo rolesRepo;
    
    @Autowired
    CustomReponseBuilder customResponseBuilder;
    
	public List<UsuariosModel> listaUsuarios(){
		return usuRepo.findAll();
	}
    public UsuariosModel buscarPorLogin(String login) {
        return usuRepo.findByLogin(login)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado: " + login));
    }
    public Optional<UsuariosModel> verUsuario(String login){
        return usuRepo.findByLogin(login);
    }

    public UsuariosModel agreUsuario(UsuariosModel usuario){
        return usuRepo.save(usuario);
    }

    public UsuariosModel modUsuario(String login, UsuariosModel usuario){
        usuario.setLogin(login);
        return usuRepo.save(usuario);
    }

    public void eliUsuario(String login){
        usuRepo.toggleEstadoByLogin(login);
    }
    
    public ResponseEntity<ApiResponse> asignarRolAUsuario(String login, int codr) {
        usuRepo.insertarRelacionUsuarioRol(login, codr);
        return customResponseBuilder.buildResponse("Rol asignado al usuario con éxito", codr);
    }

    public ResponseEntity<ApiResponse> desasignarRolDeUsuario(String login, int codr) {
        usuRepo.eliminarRelacionUsuarioRol(login, codr);
        return customResponseBuilder.buildResponse("Rol desasignado del usuario con éxito", codr);
    }


    // Listar roles de usuario con flag asignado
    public List<UsuarioRolDTO> listarRolesPorUsuario(String login) {
        List<RolesModel> todosRoles = rolesRepo.findAll();
        List<Integer> rolesAsignados = usuRepo.obtenerRolesAsignados(login);

        return todosRoles.stream()
                .map(r -> new UsuarioRolDTO(r.getCodr(), r.getNombre(), rolesAsignados.contains(r.getCodr())))
                .toList();
    }
}
