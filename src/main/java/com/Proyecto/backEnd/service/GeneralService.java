package com.Proyecto.backEnd.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Proyecto.backEnd.model.GeneralModel;
import com.Proyecto.backEnd.repository.GeneralRepo;

@Service
public class GeneralService {
    @Autowired
    private GeneralRepo generalRepo;

    public int obtenerGestionActual() {
        GeneralModel g = generalRepo.findById("1").orElse(null);
        return (g != null) ? g.getGestion() : 2025; // valor por defecto si falla
    }
}
