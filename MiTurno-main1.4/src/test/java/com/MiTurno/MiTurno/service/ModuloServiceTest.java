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

import com.MiTurno.MiTurno.model.EstadoModulo;
import com.MiTurno.MiTurno.model.Modulo;
import com.MiTurno.MiTurno.repository.ModuloRepository;

@SpringBootTest
public class ModuloServiceTest {

    @Autowired
    private ModuloService moduloService;

    @MockBean
    private ModuloRepository moduloRepository;

    private Modulo createModulo(){
        return new Modulo(1,"Modulo A","Bueno",new EstadoModulo());
    }

    @Test
    public void testFindAll(){
        when(moduloRepository.findAll()).thenReturn(List.of(createModulo()));
        List<Modulo> modulo = moduloService.findAll();
        assertNotNull(modulo);
        assertEquals(1, modulo.size());
    }

    @Test
    public void testFindById() {
        when(moduloRepository.findById(1L)).thenReturn(java.util.Optional.of(createModulo()));
        Modulo modulo = moduloService.findById(1L);
        assertNotNull(modulo);
        assertEquals("Bueno", modulo.getEstado());
    }

    @Test
    public void testSave() {
        Modulo modulo = createModulo();
        when(moduloRepository.save(modulo)).thenReturn(modulo);
        Modulo savedModulo = moduloService.save(modulo);
        assertNotNull(savedModulo);
        assertEquals("Bueno", savedModulo.getEstado());
    }
    @Test
    public void testPatchModulo() {
        Modulo existingModulo = createModulo();
        Modulo patchData = new Modulo();
        patchData.setEstado("Malo");
        when(moduloRepository.findById(1L)).thenReturn(java.util.Optional.of(existingModulo));
        when(moduloRepository.save(any(Modulo.class))).thenReturn(existingModulo);
        Modulo patchedModulo = moduloService.patchModulo(1L, patchData);
        assertNotNull(patchedModulo);
        assertEquals("Malo", patchedModulo.getEstado());
    }

    @Test
    public void testDeleteById() {
        doNothing().when(moduloRepository).deleteById(1L);
        moduloService.deleteById(1L);
        verify(moduloRepository, times(1)).deleteById(1L);
    }
}
