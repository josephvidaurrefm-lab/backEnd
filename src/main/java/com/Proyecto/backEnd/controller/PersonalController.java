package com.Proyecto.backEnd.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Proyecto.backEnd.model.PersonalModel;
import com.Proyecto.backEnd.service.PersonalService;

@RestController
public class PersonalController {
    @Autowired
    PersonalService perService;

    @GetMapping("/api/personal")
    public List<PersonalModel> listaPersonal(){
        return perService.listaPersonal();
    }

    // Nuevo endpoint que detecta si es ID o nombre
    @GetMapping("/api/personal/{valor}")
    public List<PersonalModel> buscarPorIdONombre(@PathVariable String valor){
        try {
            int codp = Integer.parseInt(valor);
            Optional<PersonalModel> personal = perService.verPersonal(codp);
            return personal.map(List::of).orElse(List.of());
        } catch (NumberFormatException e) {
            // No es número → buscar por nombre/apellidos
            return perService.buscarPorNombreOApellido(valor);
        }
    }

    @PostMapping("/api/personal")
    public PersonalModel agrePersonal(@RequestBody PersonalModel personal){
        return perService.agrePersonal(personal);
    }

    @PutMapping("/api/personal/{codp}")
    public PersonalModel modPersonal(@RequestBody PersonalModel personal, @PathVariable int codp){
        return perService.modPersonal(codp, personal);
    }

    @DeleteMapping("/api/personal/{codp}")
    public void eliPersonal(@PathVariable int codp){
        perService.eliPersonal(codp);
    }
}