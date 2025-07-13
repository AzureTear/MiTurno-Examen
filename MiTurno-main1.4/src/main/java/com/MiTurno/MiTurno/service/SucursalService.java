package com.MiTurno.MiTurno.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.MiTurno.MiTurno.model.Sucursal;
import com.MiTurno.MiTurno.repository.SucursalRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class SucursalService {
    @Autowired
    private SucursalRepository sucursalRepository;

    public List<Sucursal> findAll(){
        return sucursalRepository.findAll();
    }

    public Sucursal findById(Long id) {
        return sucursalRepository.findById(id).orElse( null);
    }

    public void delete(Long id){
        sucursalRepository.deleteById(id);
    }

    public Sucursal save(Sucursal institucion) {
        return sucursalRepository.save(institucion);
    } 

    public List<Map<String, Object>> obtenerSucursalesConNombreYConfiguracion() {
        List<Object[]> resultados = sucursalRepository.findSucursalConConfigYInsti();
        List<Map<String, Object>> lista = new ArrayList<>();
        for (Object[] fila : resultados) {
            Map<String, Object> datos = new HashMap<>();
            datos.put("sucursal", fila[0]);
            datos.put("nombreInstitucion", fila[1]);
            lista.add(datos);
        }
        return lista;
    }

    public Sucursal patchSucursal(Long id, Sucursal parcialSucursal){
        Optional<Sucursal> sucursalOptional = sucursalRepository.findById(id);
        if (sucursalOptional.isPresent()) {
            
            Sucursal sucursalToUpdate = sucursalOptional.get();
            
            if (parcialSucursal.getDireccion() != null) {
                sucursalToUpdate.setDireccion(parcialSucursal.getDireccion());   
            }
            if(parcialSucursal.getTelefono() != null) {
                sucursalToUpdate.setTelefono((parcialSucursal.getTelefono()));
            }
            if (parcialSucursal.getHorario() != null) {
                sucursalToUpdate.setHorario(parcialSucursal.getHorario());   
            }
            if(parcialSucursal.getCapacidadMaxima() != null) {
                sucursalToUpdate.setCapacidadMaxima((parcialSucursal.getCapacidadMaxima()));
            }
            return sucursalRepository.save(sucursalToUpdate);
        } else {
            return null; 
        }
    }


    public List<Sucursal> getInstitucionIdYNotificacionesActivadas(Integer idInsti, Boolean notificaciones_activadas) {
        return sucursalRepository.findByInstitucionIdAndConfiguracionNotificacionesActivadas(idInsti, notificaciones_activadas);
    }

    public List<Sucursal> findByInstitucionNombre(String instiNombre){
        List<Sucursal> sucursales = sucursalRepository.findByInstitucionNombre(instiNombre);
        if (sucursales !=null) {
            return sucursalRepository.findByInstitucionNombre(instiNombre);
        }
        return null;
    }
    
}
