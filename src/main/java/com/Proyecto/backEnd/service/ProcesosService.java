package com.Proyecto.backEnd.service;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Proyecto.backEnd.model.ProcesosModel;
import com.Proyecto.backEnd.repository.ProcesosRepo;

@Service
public class ProcesosService {
	@Autowired
	ProcesosRepo proRepo;
	public List<ProcesosModel> listaProcesos(){
		return proRepo.findAll();
	}
    public List<ProcesosModel> buscarPorNombre(String valor) {
        return proRepo.buscarPorNombre(valor);
    }
    // Ver un proceso por codp
    public Optional<ProcesosModel> verProceso(int codp) {
        return proRepo.findById(codp);
    }

    // Agregar un proceso
    public ProcesosModel agreProceso(ProcesosModel proceso) {
        return proRepo.save(proceso);
    }

    // Modificar un proceso
    public ProcesosModel modProceso(int codp, ProcesosModel proceso) {
        proceso.setCodp(codp);
        return proRepo.save(proceso);
    }

    // Eliminar un proceso (y sus relaciones en mepro)
   
    public void eliProceso(int codp) {
        // Primero eliminar relaciones en mepro
        proRepo.eliminarRelaciones(codp);

        // Después eliminar el proceso
        proRepo.deleteById(codp);
    }
}
