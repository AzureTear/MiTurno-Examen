package com.MiTurno.MiTurno.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.MiTurno.MiTurno.model.Modulo;
import com.MiTurno.MiTurno.model.Rol;
import com.MiTurno.MiTurno.model.Sucursal;
import com.MiTurno.MiTurno.model.Trabajador;
import com.MiTurno.MiTurno.repository.TrabajadorRepository;



@SpringBootTest
public class TrabajadorServiceTest {

    @Autowired
    private TrabajadorService trabajadorService;

    @MockBean
    private TrabajadorRepository trabajadorRepository;

    private Trabajador createTrabajador(){
        return new Trabajador(1, "Benjamin", "Matias", "Torres", new Sucursal(), new Rol(), new Modulo());
    }

    @Test
    public void testFindAll(){
        when(trabajadorRepository.findAll()).thenReturn(List.of(createTrabajador()));
        List<Trabajador> trabajador = trabajadorService.findAll();
        assertNotNull(trabajador);
        assertEquals(1, trabajador.size());
    }

    @Test
    public void testFindById() {
        when(trabajadorRepository.findById(1L)).thenReturn(java.util.Optional.of(createTrabajador()));
        Trabajador trabajador = trabajadorService.findById(1L);
        assertNotNull(trabajador);
        assertEquals("Benjamin", trabajador.getNombre());
    }

    @Test
    public void testSave() {
        Trabajador trabajador = createTrabajador();
        when(trabajadorRepository.save(trabajador)).thenReturn(trabajador);
        Trabajador savedTrabajador = trabajadorService.save(trabajador);
        assertNotNull(savedTrabajador);
        assertEquals("Benjamin", savedTrabajador.getNombre());
    }
    @Test
    public void testPatchTrabajador() {
        Trabajador existingTrabajador = createTrabajador();
        Trabajador patchData = new Trabajador();
        patchData.setNombre("Alvaro");
        when(trabajadorRepository.findById(1L)).thenReturn(java.util.Optional.of(existingTrabajador));
        when(trabajadorRepository.save(any(Trabajador.class))).thenReturn(existingTrabajador);
        Trabajador patchedTrabajador = trabajadorService.patchTrabajador(1L, patchData);
        assertNotNull(patchedTrabajador);
        assertEquals("Alvaro", patchedTrabajador.getNombre());
    }

    @Test
    public void testDeleteById() {
        doNothing().when(trabajadorRepository).deleteById(1L);
        trabajadorService.delete(1L);
        verify(trabajadorRepository, times(1)).deleteById(1L);
    }
}
