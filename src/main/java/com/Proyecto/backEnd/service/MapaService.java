package com.Proyecto.backEnd.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Proyecto.backEnd.exception.DuplicateRelationException;
import com.Proyecto.backEnd.model.MapaModel;
import com.Proyecto.backEnd.model.DTO.MapaDTO;
import com.Proyecto.backEnd.model.DTO.ParaleloMateriaDTO;
import com.Proyecto.backEnd.repository.MapaRepo;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MapaService {
    @Autowired
    private MapaRepo mapaRepo;

    @Autowired
    private GeneralService generalService;

    public List<MapaModel> listaMapas() {
        return mapaRepo.findAll();
    }

    public void crearMapa(String codmat, int codpar) {

        int gestion = generalService.obtenerGestionActual();

        if (mapaRepo.existsByMapaCodmatAndMapaCodparAndMapaGestion(codmat, codpar, gestion)) {
            throw new DuplicateRelationException("La relación ya existe para la gestión: " + gestion);
        }

        mapaRepo.insertarMapa(codmat, codpar, gestion);
    }

    public void eliminarMapa(String codmat, int codpar) {
    	int gestion = generalService.obtenerGestionActual();
        mapaRepo.eliminarPorCodmatCodpar(codmat, codpar);
    }
    
    public List<ParaleloMateriaDTO> obtenerParalelosPorMateria(String codmat) {
        // Buscar todos los mapas de la materia
        List<MapaModel> mapas = mapaRepo.findByMapaCodmat(codmat);

        // Transformar a DTO incluyendo la gestión desde Mapa
        return mapas.stream()
                .map(m -> new ParaleloMateriaDTO(
                		m.getMapa().getCodmat(),
                        m.getParalelo().getCodpar(),
                        m.getParalelo().getNombre(),
                        m.getParalelo().getEstado(),
                        m.getMapa().getGestion() // Tomamos la gestión desde la PK de Mapa
                ))
                .toList();
    }
    //----------------------------------
    
    
    
    
    public List<MapaDTO> getAllMapaInfo() {
        List<Object[]> rawData = mapaRepo.findAllMapaInfoRaw();

        return rawData.stream().map(row -> new MapaDTO(
                (String) row[0],   // codmat
                (String) row[1],   // nombreMateria
                (Integer) row[2],  // codpar
                (String) row[3],   // nombreParalelo
                (Integer) row[4],  // gestion
                (String) row[5]    // nivelMateria
        )).collect(Collectors.toList());
    }
}
