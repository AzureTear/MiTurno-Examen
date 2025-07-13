package com.MiTurno.MiTurno.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.MiTurno.MiTurno.model.Configuracion;
import com.MiTurno.MiTurno.repository.ConfiguracionRepository;


import jakarta.transaction.Transactional;

@Service
@Transactional
public class ConfiguracionService {

    @Autowired
    private ConfiguracionRepository configuracionRepository;

    public List<Configuracion> findAll(){
        return configuracionRepository.findAll();
    }

    public Configuracion findById(Long id) {
        return configuracionRepository.findById(id).orElse( null);
    }
    public Configuracion save(Configuracion configuracion) {
        return configuracionRepository.save(configuracion);
    } 

    public Configuracion patchConfiguracion(Long id, Configuracion parcialConfiguracion){
        Optional<Configuracion> configuracionOptional = configuracionRepository.findById(id);
        if (configuracionOptional.isPresent()) {
            
            Configuracion configuracionToUpdate = configuracionOptional.get();
            
            if (parcialConfiguracion.getNotificacionesActivadas() != null) {
                configuracionToUpdate.setNotificacionesActivadas(parcialConfiguracion.getNotificacionesActivadas());   
            }
            if(parcialConfiguracion.getTiempo_anticipacion_notificacion() != null) {
                configuracionToUpdate.setTiempo_anticipacion_notificacion((parcialConfiguracion.getTiempo_anticipacion_notificacion()));
            }
            return configuracionRepository.save(configuracionToUpdate);
        } else {
            return null; 
        }
    }

}
