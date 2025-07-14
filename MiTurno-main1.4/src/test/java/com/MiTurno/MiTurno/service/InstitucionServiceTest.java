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

import com.MiTurno.MiTurno.model.Institucion;
import com.MiTurno.MiTurno.repository.InstitucionRepository;

@SpringBootTest
public class InstitucionServiceTest {
    @Autowired
    private InstitucionService institucionService;

    @MockBean
    private InstitucionRepository institucionRepository;

    private Institucion createInstitucion(){
        return new Institucion(1,"Duoc","example@duocuc.cl",987640394);
    }

    @Test
    public void testFindAll(){
        when(institucionRepository.findAll()).thenReturn(List.of(createInstitucion()));
        List<Institucion> institucion = institucionService.findAll();
        assertNotNull(institucion);
        assertEquals(1, institucion.size());
    }

    @Test
    public void testFindById() {
        when(institucionRepository.findById(1L)).thenReturn(java.util.Optional.of(createInstitucion()));
        Institucion institucion = institucionService.findById(1L);
        assertNotNull(institucion);
        assertEquals("Duoc", institucion.getNombre());
    }

    @Test
    public void testSave() {
        Institucion institucion = createInstitucion();
        when(institucionRepository.save(institucion)).thenReturn(institucion);
        Institucion savedInstitucion = institucionService.save(institucion);
        assertNotNull(savedInstitucion);
        assertEquals("Duoc", savedInstitucion.getNombre());
    }
    @Test
    public void testPatchInstitucion() {
        Institucion existingInstitucion = createInstitucion();
        Institucion patchData = new Institucion();
        patchData.setNombre("Tottus");
        when(institucionRepository.findById(1L)).thenReturn(java.util.Optional.of(existingInstitucion));
        when(institucionRepository.save(any(Institucion.class))).thenReturn(existingInstitucion);
        Institucion patchedInstitucion = institucionService.patchInstitucion(1L, patchData);
        assertNotNull(patchedInstitucion);
        assertEquals("Tottus", patchedInstitucion.getNombre());
    }

    @Test
    public void testDeleteById() {
        doNothing().when(institucionRepository).deleteById(1L);
        institucionService.deleteById(1L);
        verify(institucionRepository, times(1)).deleteById(1L);
    }
}
