package com.Proyecto.backEnd.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Proyecto.backEnd.exception.ResourceNotFoundException;
import com.Proyecto.backEnd.model.ParalelosModel;
import com.Proyecto.backEnd.repository.ParalelosRepo;
import com.Proyecto.backEnd.utils.ApiResponse;
import com.Proyecto.backEnd.utils.CustomReponseBuilder;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ParalelosService {
	private final CustomReponseBuilder customResponseBuilder;
	@Autowired
	ParalelosRepo parRepo;
	
	public List<ParalelosModel> listaParalelos(){
		return parRepo.findAll();
	}
	
    // ✔️ Ver uno
    public ParalelosModel verParalelo(int codpar) {
        return parRepo.findById(codpar)
                .orElseThrow(() -> new ResourceNotFoundException("No existe paralelo con codpar: " + codpar));
    }

    // ✔️ Buscar por nombre
    public List<ParalelosModel> buscarPorNombre(String valor) {
        return parRepo.buscarPorNombre(valor);
    }

    // ✔️ Crear
    public ResponseEntity<ApiResponse> agregarParalelo(ParalelosModel paralelo) {
        if (parRepo.existsByNombre(paralelo.getNombre())) {
            throw new ResourceNotFoundException("Ya existe un paralelo con el nombre: " + paralelo.getNombre());
        }

        ParalelosModel guardado = parRepo.save(paralelo);
        return customResponseBuilder.buildResponse("Paralelo agregado con éxito", guardado);
    }

    // ✔️ Modificar
    public ResponseEntity<ApiResponse> modificarParalelo(int codpar, ParalelosModel paralelo) {
        if (parRepo.existsByNombreAndCodparNot(paralelo.getNombre(), codpar)) {
            throw new ResourceNotFoundException("Ya existe otro paralelo con el nombre: " + paralelo.getNombre());
        }

        paralelo.setCodpar(codpar);
        ParalelosModel modificado = parRepo.save(paralelo);

        return customResponseBuilder.buildResponse("Paralelo modificado con éxito", modificado);
    }

 // DELETE → Eliminación lógica del paralelo con mensaje dinámico y nombre del paralelo
    public ResponseEntity<ApiResponse> eliminarParalelo(int codpar) {
        // Primero obtenemos el paralelo actual
        ParalelosModel paralelo = parRepo.findById(codpar)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró un paralelo con codpar: " + codpar));

        // Ejecutamos la eliminación lógica (cambia estado 1↔0)
        parRepo.eliminar(codpar);

        // Obtenemos el nuevo estado
        int nuevoEstado = paralelo.getEstado() == 1 ? 0 : 1;

        // Construimos el mensaje según el nuevo estado
        String mensaje = (nuevoEstado == 0) ? "Paralelo eliminado con éxito" : "Paralelo habilitado con éxito";

        // Pasamos el nombre del paralelo como data
        return customResponseBuilder.buildResponse(mensaje, paralelo.getNombre());
    }

}
