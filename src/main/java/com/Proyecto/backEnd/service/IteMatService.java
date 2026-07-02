package com.Proyecto.backEnd.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Proyecto.backEnd.exception.DuplicateItemMateriaException;
import com.Proyecto.backEnd.model.ItematModel;
import com.Proyecto.backEnd.model.DTO.ItemMateriaDTO;
import com.Proyecto.backEnd.repository.IteMatRepo;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class IteMatService {
	@Autowired
	IteMatRepo iteRepo;
	
    
    @Autowired
    private GeneralService generalService;
	
    public List<ItematModel> listaItemat() {
        return iteRepo.findAll();
    }
    
    public List<ItemMateriaDTO> obtenerItemsPorMateria(String codmat) {

        // Buscar registros itemat por codmat
        List<ItematModel> lista = iteRepo.findByItematCodmat(codmat);

        // Mapear a DTO
        return lista.stream().map(it -> new ItemMateriaDTO(
                it.getItemat().getCodmat(),            // codmat
                it.getItemat().getCodi(),              // codi (PK)
                it.getItem().getNombre(),              // nombre del item (desde ItemsModel)
                it.getItem().getEstado(),                        // estado del itemat
                it.getPonderacion(),                   // ponderación del itemat
                it.getItemat().getGestion()            // gestión (PK)
        )).toList();
    }
    


    public void eliminarItemat(String codmat, int codi) {
        iteRepo.eliminarPorCodmatCodi(codmat, codi);
    }
    
    public void agregarItemat(String codmat, int codi, int ponderacion) {

        int gestion = generalService.obtenerGestionActual();

        if (iteRepo.existsByItematCodmatAndItematCodiAndItematGestion(codmat, codi, gestion)) {
            throw new DuplicateItemMateriaException(
                    "La relación Item-Materia ya existe para la gestión: " + gestion
            );
        }

        iteRepo.insertarRelacionItemat(codmat, codi, gestion, ponderacion);
    }

  }
