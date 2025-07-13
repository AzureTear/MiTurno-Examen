package com.MiTurno.MiTurno.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.MiTurno.MiTurno.model.Modulo;
import com.MiTurno.MiTurno.repository.ModuloRepository;


import jakarta.transaction.Transactional;

@Service
@Transactional
public class ModuloService {
    @Autowired
    private ModuloRepository moduloRepository;

    public List<Modulo> findAll(){
        return moduloRepository.findAll();
    }

    public Modulo findById(Long id) {
        return moduloRepository.findById(id).orElse( null);
    }
    
    public void deleteById(Long id){
        moduloRepository.deleteById(id);
    }
    public Modulo save(Modulo institucion) {
        return moduloRepository.save(institucion);
    } 

    public Modulo patchModulo(Long id, Modulo parcialModulo){
        Optional<Modulo> moduloOptional = moduloRepository.findById(id);
        if (moduloOptional.isPresent()) {
            
            Modulo moduloToUpdate = moduloOptional.get();
            
            if (parcialModulo.getTitulo() != null) {
                moduloToUpdate.setTitulo(parcialModulo.getTitulo());   
            }
            if(parcialModulo.getTitulo() != null) {
                moduloToUpdate.setTitulo((parcialModulo.getTitulo()));
            }
            return moduloRepository.save(moduloToUpdate);
        } else {
            return null; 
        }
    }

}
