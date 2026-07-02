package com.Proyecto.backEnd.service;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Proyecto.backEnd.exception.ResourceNotFoundException;
import com.Proyecto.backEnd.model.MenusModel;
import com.Proyecto.backEnd.model.ProcesosModel;
import com.Proyecto.backEnd.model.DTO.ProcesoMenuDTO;
import com.Proyecto.backEnd.repository.MenusRepo;
import com.Proyecto.backEnd.repository.ProcesosRepo;
import com.Proyecto.backEnd.utils.ApiResponse;
import com.Proyecto.backEnd.utils.CustomReponseBuilder;

import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
@Service
public class MenusService {
	private final CustomReponseBuilder customResponseBuilder;
	@Autowired
	MenusRepo menRepo;
	
	@Autowired
	ProcesosRepo procesoRepo;
	public List<MenusModel> listaMenus(){
		return menRepo.findAll();
	}
    // Ver un menú por codm
    public Optional<MenusModel> verMenu(int codm) {
        return menRepo.findById(codm);
    }
    public List<MenusModel> buscarPorNombre(String valor) {
        return menRepo.buscarPorNombre(valor);
    }
    // POST → Agregar menú
    public ResponseEntity<ApiResponse> agreMenuResponse(MenusModel menu) {
        if (menRepo.existsByNombre(menu.getNombre())) {
            throw new ResourceNotFoundException("Ya existe un menú con el nombre: " + menu.getNombre());
        }
        MenusModel guardado = menRepo.save(menu);
        return customResponseBuilder.buildResponse("Menú agregado con éxito", guardado);
    }

    // PUT → Modificar menú
    public ResponseEntity<ApiResponse> modMenuResponse(int codm, MenusModel menu) {
        if (menRepo.existsByNombreAndCodmNot(menu.getNombre(), codm)) {
            throw new ResourceNotFoundException("Ya existe otro menú con el nombre: " + menu.getNombre());
        }
        menu.setCodm(codm);
        MenusModel modificado = menRepo.save(menu);
        return customResponseBuilder.buildResponse("Menú modificado con éxito", modificado);
    }

 // DELETE → Eliminación lógica del menú con mensaje dinámico y nombre del menú
    public ResponseEntity<ApiResponse> eliMenuResponse(int codm) {
        // Primero obtenemos el menú actual
        MenusModel menu = menRepo.findById(codm)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró un menú con codm: " + codm));
        // Ejecutamos la eliminación lógica (cambia estado 1↔0)
        menRepo.eliminar(codm);

        // Obtenemos el nuevo estado
        int nuevoEstado = menu.getEstado() == 1 ? 0 : 1;

        // Construimos el mensaje según el nuevo estado
        String mensaje = (nuevoEstado == 0) ? "Menú eliminado con éxito" : "Menú habilitado con éxito";

        // Pasamos el nombre del menú como data
        return customResponseBuilder.buildResponse(mensaje, menu.getNombre());
    }
    
 // Asignar un proceso individual al menú
    public ResponseEntity<ApiResponse> asignarProcesoAMenu(int codm, int codp) {
        menRepo.insertarRelacionMepro(codm, codp);
        return customResponseBuilder.buildResponse("Proceso asignado al menú con éxito", codp);
    }

    // Desasignar un proceso individual del menú
    public ResponseEntity<ApiResponse> desasignarProcesoAMenu(int codm, int codp) {
        menRepo.eliminarRelacionMepro(codm, codp);
        return customResponseBuilder.buildResponse("Proceso desasignado del menú con éxito", codp);
    }
    //Lista Proceso por Menu
    public List<ProcesoMenuDTO> listarProcesosPorMenu(int codm) {
        List<ProcesosModel> todos = procesoRepo.findAll(); // Todos los procesos
        List<Integer> asignados = menRepo.obtenerProcesosAsignados(codm); // Codp asignados

        return todos.stream()
                .map(p -> new ProcesoMenuDTO(p.getCodp(), p.getNombre(), asignados.contains(p.getCodp())))
                .toList();
    }
    

}
	
