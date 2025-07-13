package com.MiTurno.MiTurno.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.MiTurno.MiTurno.model.Rol;
import com.MiTurno.MiTurno.repository.RolRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class RolService {
    @Autowired
    private RolRepository rolRepository;

    public List<Rol> findAll(){
        return rolRepository.findAll();
    }

    public Rol findById(Long id) {
        return rolRepository.findById(id).orElse( null);
    }
    
    public void delete(Long id){
        rolRepository.deleteById(id);
    }

    public Rol save(Rol institucion) {
        return rolRepository.save(institucion);
    } 

    public Rol patchRol(Long id, Rol parcialRol){
        Optional<Rol> rolOptional = rolRepository.findById(id);
        if (rolOptional.isPresent()) {
            
            Rol rolToUpdate = rolOptional.get();
            
            if (parcialRol.getDescripcion() != null) {
                rolToUpdate.setDescripcion(parcialRol.getDescripcion());   
            }
            if(parcialRol.getAdmin() != null) {
                rolToUpdate.setAdmin((parcialRol.getAdmin()));
            }
            return rolRepository.save(rolToUpdate);
        } else {
            return null; 
        }
    }    
}
