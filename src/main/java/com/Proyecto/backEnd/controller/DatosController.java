package com.Proyecto.backEnd.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.Proyecto.backEnd.model.DatosModel;
import com.Proyecto.backEnd.model.DatosModelPK;
import com.Proyecto.backEnd.service.DatosService;

@RestController
@RequestMapping("/api/datos")
public class DatosController {

    @Autowired
    private DatosService datService;

    // GET todos
    @GetMapping
    public List<DatosModel> getAll() {
        return datService.findAll();
    }

    // GET por PK
    @GetMapping("/{codp}/{cedula}")
    public Optional<DatosModel> getById(@PathVariable int codp, @PathVariable String cedula) {
        DatosModelPK id = new DatosModelPK();
        id.setCodp(codp);
        id.setCedula(cedula);
        return datService.findById(id);
    }


}
