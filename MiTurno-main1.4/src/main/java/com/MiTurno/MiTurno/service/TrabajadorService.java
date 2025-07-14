package com.MiTurno.MiTurno.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.MiTurno.MiTurno.model.Trabajador;
import com.MiTurno.MiTurno.repository.TrabajadorRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class TrabajadorService {
    @Autowired
    private TrabajadorRepository trabajadorRepository;


    public List<Trabajador> findAll(){
        return trabajadorRepository.findAll();
    }

    public Trabajador findById(Long id) {
        return trabajadorRepository.findById(id).orElse( null);
    }
    
    public void delete(Long id){
        trabajadorRepository.deleteById(id);
    }

    public Trabajador save(Trabajador institucion) {
        return trabajadorRepository.save(institucion);
    } 

    public Trabajador patchTrabajador(Long id, Trabajador parcialTrabajador){
        Optional<Trabajador> trabajadorOptional = trabajadorRepository.findById(id);
        if (trabajadorOptional.isPresent()) {
            
            Trabajador trabajadorToUpdate = trabajadorOptional.get();
            
            if (parcialTrabajador.getNombre() != null) {
                trabajadorToUpdate.setNombre(parcialTrabajador.getNombre());   
            }
            trabajadorToUpdate.setSegundoNombre((parcialTrabajador.getSegundoNombre()));
            if (parcialTrabajador.getApellido() != null) {
                trabajadorToUpdate.setApellido(parcialTrabajador.getApellido());   
            }
            return trabajadorRepository.save(trabajadorToUpdate);
        } else {
            return null; 
        }
    }

    public List<Trabajador> getRolAdminAndSucursalCapacidadMaximaGreaterThan(Boolean admin, Integer capacidadMaxima) {
        return trabajadorRepository.findByRolAdminAndSucursalCapacidadMaximaGreaterThan(admin,capacidadMaxima);
    }

    public List<Trabajador> findBySucursalId(Integer idS){
        List<Trabajador> trabajadores = trabajadorRepository.findBySucursalId(idS);
        if (trabajadores !=null) {
            return trabajadorRepository.findBySucursalId(idS);
        }
        return null;
    }

}
