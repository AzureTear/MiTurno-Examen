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

import com.MiTurno.MiTurno.model.Rol;
import com.MiTurno.MiTurno.repository.RolRepository;

@SpringBootTest
public class RolServiceTest {

    @Autowired
    private RolService rolService;

    @MockBean
    private RolRepository rolRepository;

    private Rol createRol(){
        return new Rol(1,"Admin",true);
    }

    @Test
    public void testFindAll(){
        when(rolRepository.findAll()).thenReturn(List.of(createRol()));
        List<Rol> rol = rolService.findAll();
        assertNotNull(rol);
        assertEquals(1, rol.size());
    }

    @Test
    public void testFindById() {
        when(rolRepository.findById(1L)).thenReturn(java.util.Optional.of(createRol()));
        Rol rol = rolService.findById(1L);
        assertNotNull(rol);
        assertEquals("Admin", rol.getDescripcion());
    }

    @Test
    public void testSave() {
        Rol rol = createRol();
        when(rolRepository.save(rol)).thenReturn(rol);
        Rol savedRol = rolService.save(rol);
        assertNotNull(savedRol);
        assertEquals("Admin", savedRol.getDescripcion());
    }
    @Test
    public void testPatchRol() {
        Rol existingRol = createRol();
        Rol patchData = new Rol();
        patchData.setDescripcion("usuario");
        when(rolRepository.findById(1L)).thenReturn(java.util.Optional.of(existingRol));
        when(rolRepository.save(any(Rol.class))).thenReturn(existingRol);
        Rol patchedRol = rolService.patchRol(1L, patchData);
        assertNotNull(patchedRol);
        assertEquals("Usuario", patchedRol.getDescripcion());
    }

    @Test
    public void testDeleteById() {
        doNothing().when(rolRepository).deleteById(1L);
        rolService.delete(1L);
        verify(rolRepository, times(1)).deleteById(1L);
    }
}
