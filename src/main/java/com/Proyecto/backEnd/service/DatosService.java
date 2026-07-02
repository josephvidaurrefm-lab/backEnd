package com.Proyecto.backEnd.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Proyecto.backEnd.model.DatosModel;
import com.Proyecto.backEnd.model.DatosModelPK;
import com.Proyecto.backEnd.repository.DatosRepo;

@Service
public class DatosService {

    @Autowired
    private DatosRepo datRepo;

    // GET todos
    public List<DatosModel> findAll() {
        return datRepo.findAll();
    }

    // GET por PK
    public Optional<DatosModel> findById(DatosModelPK id) {
        return datRepo.findById(id);
    }

}
