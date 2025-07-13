package com.MiTurno.MiTurno.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.MiTurno.MiTurno.model.Estado;
import com.MiTurno.MiTurno.model.Modulo;
import com.MiTurno.MiTurno.model.Sucursal;
import com.MiTurno.MiTurno.model.Turno;
import com.MiTurno.MiTurno.model.Usuario;
import com.MiTurno.MiTurno.repository.TurnoRepository;


@SpringBootTest
public class TurnoServiceTest {

    @Autowired
    private TurnoService turnoService;

    @MockBean
    private TurnoRepository turnoRepository;

    private Turno createTurno(){
        return new Turno(
            1,
            new Date(System.currentTimeMillis()),
            908765678, 
            new Date(), 
            new Date(System.currentTimeMillis()), 
            new Date(System.currentTimeMillis()+3600000), 
            new Sucursal(), 
            new Estado(), 
            new Usuario(), 
            new Modulo());
    }

    @Test
    public void testFindAll(){
        when(turnoRepository.findAll()).thenReturn(List.of(createTurno()));
        List<Turno> turno = turnoService.findAll();
        assertNotNull(turno);
        assertEquals(1, turno.size());
    }

    @Test
    public void testFindById() {
        when(turnoRepository.findById(1L)).thenReturn(java.util.Optional.of(createTurno()));
        Turno turno = turnoService.findById(1L);
        assertNotNull(turno);
        assertEquals(908765678, turno.getNumero());
    }

    @Test
    public void testSave() {
        Turno turno = createTurno();
        when(turnoRepository.save(turno)).thenReturn(turno);
        Turno savedTurno = turnoService.save(turno);
        assertNotNull(savedTurno);
        assertEquals(908765678, savedTurno.getNumero());
    }
    @Test
    public void testPatchTurno() {
        Turno existingTurno = createTurno();
        Turno patchData = new Turno();
        patchData.setNumero(912345678);
        when(turnoRepository.findById(1L)).thenReturn(java.util.Optional.of(existingTurno));
        when(turnoRepository.save(any(Turno.class))).thenReturn(existingTurno);
        Turno patchedTurno = turnoService.patchTurno(1L, patchData);
        assertNotNull(patchedTurno);
        assertEquals(912345678, patchedTurno.getNumero());
    }

    @Test
    public void testDeleteById() {
        doNothing().when(turnoRepository).deleteById(1L);
        turnoService.delete(1L);
        verify(turnoRepository, times(1)).deleteById(1L);
    }
}
