package com.MiTurno.MiTurno.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.MiTurno.MiTurno.model.Turno;
import com.MiTurno.MiTurno.repository.TurnoRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class TurnoService {
    @Autowired
    private TurnoRepository turnoRepository;

    
    public List<Turno> findAll(){
        return turnoRepository.findAll();
    }

    public Turno findById(Long id) {
        return turnoRepository.findById(id).orElse( null);
    }
    
    public void delete(Long id){
        turnoRepository.deleteById(id);
    }

    public Turno save(Turno institucion) {
        return turnoRepository.save(institucion);
    } 

    public Turno patchTurno(Long id, Turno parcialTurno){
        Optional<Turno> turnoOptional = turnoRepository.findById(id);
        if (turnoOptional.isPresent()) {
            
            Turno turnoToUpdate = turnoOptional.get();
            
            if (parcialTurno.getFecha() != null) {
                turnoToUpdate.setFecha(parcialTurno.getFecha());   
            }
            if(parcialTurno.getNumero() != null) {
                turnoToUpdate.setNumero((parcialTurno.getNumero()));
            }
            if (parcialTurno.getHoraCreacion() != null) {
                turnoToUpdate.setHoraCreacion(parcialTurno.getHoraCreacion());   
            }
            if(parcialTurno.getHoraAtencion() != null) {
                turnoToUpdate.setHoraAtencion((parcialTurno.getHoraAtencion()));
            }
            if (parcialTurno.getHoraCancelacion() != null) {
                turnoToUpdate.setHoraCancelacion(parcialTurno.getHoraCancelacion());   
            }
            return turnoRepository.save(turnoToUpdate);
        } else {
            return null; 
        }
    }

    public List<Map<String, Object>> obtenerTurnosConIdyNombre() {
        List<Object[]> resultados = turnoRepository.findTurnoConSucursalYUsuario();
        List<Map<String, Object>> lista = new ArrayList<>();

        for (Object[] fila : resultados) {
            Map<String, Object> datos = new HashMap<>();
            datos.put("turnoId", fila[0]);
            datos.put("turnoFecha", fila[1]);
            datos.put("turnoNumero", fila[2]);
            datos.put("turnoHoraCreacion", fila[3]);
            datos.put("turnoHoraAtencion", fila[4]);
            datos.put("turnoHoraCancelacion", fila[5]);
            datos.put("sucursalId", fila[6]);
            datos.put("direccion", fila[7]);
            datos.put("nombreUsuario", fila[8]);
            datos.put("apellidoUsuario", fila[9]);
            lista.add(datos);
        }
        return lista;
    }

        public List<Turno> getUsuarioIdAndModuloEstado(Integer idUsuario,String estado) {
            return turnoRepository.findByUsuarioIdAndModuloEstado(idUsuario, estado);
        }

        public List<Turno> getFechaBetween(Date horaCreacion, Date horaAtencion) {
            return turnoRepository.findByFechaBetween(horaCreacion, horaAtencion);
    }

    public List<Turno> findByUsuarioNombre(String usuarioNombre){
        List<Turno> turno = turnoRepository.findByUsuarioNombre(usuarioNombre);
        if (turno !=null) {
            return turnoRepository.findByUsuarioNombre(usuarioNombre);
        }
        return null;
    }

    public List<Turno> findByDescripcionAndEstado(String descripcion, String titulo){
        List<Turno> turno = turnoRepository.findByDescripcionAndEstado(descripcion,titulo);
        if (turno !=null) {
            return turnoRepository.findByDescripcionAndEstado(descripcion,titulo);
        }
        return null;
    }
}
