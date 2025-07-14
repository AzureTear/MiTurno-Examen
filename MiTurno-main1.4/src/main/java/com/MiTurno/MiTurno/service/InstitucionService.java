package com.MiTurno.MiTurno.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.MiTurno.MiTurno.model.Institucion;
import com.MiTurno.MiTurno.model.Sucursal;
import com.MiTurno.MiTurno.repository.InstitucionRepository;
import com.MiTurno.MiTurno.repository.SucursalRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class InstitucionService {
    @Autowired
    private InstitucionRepository institucionRepository;
    @Autowired
    private SucursalService sucursalService;

    @Autowired
    private SucursalRepository sucursalRepository;

    public List<Institucion> findAll(){
        return institucionRepository.findAll();
    }
    public Institucion findById(Long id) {
        return institucionRepository.findById(id).orElse( null);
    }
    
    public Institucion save(Institucion institucion) {
        return institucionRepository.save(institucion);

    } 
    public Institucion patchInstitucion(Long id, Institucion parcialInstitucion){
        Optional<Institucion> institucionOptional = institucionRepository.findById(id);
        if (institucionOptional.isPresent()) {
            
            Institucion institucionToUpdate = institucionOptional.get();
            
            if (parcialInstitucion.getNombre() != null) {
                institucionToUpdate.setNombre(parcialInstitucion.getNombre());   
            }
            if (parcialInstitucion.getEmail() != null) {
                institucionToUpdate.setEmail(parcialInstitucion.getEmail());   
            }
            if(parcialInstitucion.getTelefono() != null) {
                institucionToUpdate.setTelefono((parcialInstitucion.getTelefono()));
            }
            return institucionRepository.save(institucionToUpdate);
        } else {
            return null; 
        }
    }


    public void deleteById(Long id){
        Institucion institucion = institucionRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Institucion no encontrada"));
            
        List<Sucursal> sucursals = sucursalRepository.findByInstitucion(institucion);

        for (Sucursal sucursal : sucursals ) {
            sucursalService.delete(Long.valueOf(sucursal.getId()));
        }

        institucionRepository.delete(institucion);
}
}
