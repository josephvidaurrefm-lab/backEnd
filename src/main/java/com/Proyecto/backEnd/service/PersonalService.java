package com.Proyecto.backEnd.service;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Proyecto.backEnd.model.PersonalModel;
import com.Proyecto.backEnd.repository.PersonalRepo;

@Service
public class PersonalService {
	@Autowired
	PersonalRepo perRepo;
	public List<PersonalModel> listaPersonal(){
		return perRepo.findAll();
	}
    public Optional<PersonalModel> verPersonal(int codp){
        return perRepo.findById(codp);
    }

    public PersonalModel agrePersonal(PersonalModel personal){
        return perRepo.save(personal);
    }

    public PersonalModel modPersonal(int codp, PersonalModel personal){
        personal.setCodp(codp);
        return perRepo.save(personal);
    }

    public void eliPersonal(int codp){
        perRepo.toggleEstadoByCodp(codp);
    }
    // ✅ Servicio para buscar por nombre o apellido
    // Servicio para buscar por nombre o apellidos
    public List<PersonalModel> buscarPorNombreOApellido(String valor){
        return perRepo.buscarPorNombreOApellido(valor);
    }
}
