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

import com.MiTurno.MiTurno.model.Configuracion;
import com.MiTurno.MiTurno.model.Institucion;
import com.MiTurno.MiTurno.model.Sucursal;
import com.MiTurno.MiTurno.repository.SucursalRepository;

@SpringBootTest
public class SucursalServiceTest {

    @Autowired
    private SucursalService sucursalService;

    @MockBean
    private SucursalRepository sucursalRepository;

    private Sucursal createSucursal(){
        return new Sucursal(1, "Vicuña Mackena 304", 976878763, new Date(System.currentTimeMillis()), 50, new Configuracion(), new Institucion());
    }

    @Test
    public void testFindAll(){
        when(sucursalRepository.findAll()).thenReturn(List.of(createSucursal()));
        List<Sucursal> sucursal = sucursalService.findAll();
        assertNotNull(sucursal);
        assertEquals(1, sucursal.size());
    }

    @Test
    public void testFindById() {
        when(sucursalRepository.findById(1L)).thenReturn(java.util.Optional.of(createSucursal()));
        Sucursal sucursal = sucursalService.findById(1L);
        assertNotNull(sucursal);
        assertEquals(50, sucursal.getCapacidadMaxima());
    }

    @Test
    public void testSave() {
        Sucursal sucursal = createSucursal();
        when(sucursalRepository.save(sucursal)).thenReturn(sucursal);
        Sucursal savedSucursal = sucursalService.save(sucursal);
        assertNotNull(savedSucursal);
        assertEquals(50, savedSucursal.getCapacidadMaxima());
    }
    @Test
    public void testPatchSucursal() {
        Sucursal existingSucursal = createSucursal();
        Sucursal patchData = new Sucursal();
        patchData.setCapacidadMaxima(100);
        when(sucursalRepository.findById(1L)).thenReturn(java.util.Optional.of(existingSucursal));
        when(sucursalRepository.save(any(Sucursal.class))).thenReturn(existingSucursal);
        Sucursal patchedSucursal = sucursalService.patchSucursal(1L, patchData);
        assertNotNull(patchedSucursal);
        assertEquals(100, patchedSucursal.getCapacidadMaxima());
    }

    @Test
    public void testDeleteById() {
        doNothing().when(sucursalRepository).deleteById(1L);
        sucursalService.delete(1L);
        verify(sucursalRepository, times(1)).deleteById(1L);
    }


    @Test
    public void testFindByInstitucionNombre(){
        when(sucursalRepository.findByInstitucionNombre("Anderson LLC")).thenReturn(List.of(createSucursal()));
        List<Sucursal> sucursal = sucursalService.findByInstitucionNombre("Anderson LLC");
        assertNotNull(sucursal);
        assertEquals(1, sucursal.size());
    }


}
