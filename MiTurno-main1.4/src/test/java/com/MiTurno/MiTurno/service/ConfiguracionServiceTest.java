package com.MiTurno.MiTurno.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.MiTurno.MiTurno.model.Configuracion;
import com.MiTurno.MiTurno.repository.ConfiguracionRepository;

@SpringBootTest
public class ConfiguracionServiceTest {

    @Autowired
    private ConfiguracionService configuracionService;

    @MockBean
    private ConfiguracionRepository configuracionRepository;

    private Configuracion createConfiguracion(){
        return new Configuracion(1,true,2345);
    }

    @Test
    public void testFindAll(){
        when(configuracionRepository.findAll()).thenReturn(List.of(createConfiguracion()));
        List<Configuracion> configuracion = configuracionService.findAll();
        assertNotNull(configuracion);
        assertEquals(1, configuracion.size());
    }

    @Test
    public void testFindById() {
        when(configuracionRepository.findById(1L)).thenReturn(java.util.Optional.of(createConfiguracion()));
        Configuracion configuracion = configuracionService.findById(1L);
        assertNotNull(configuracion);
        assertEquals(true, configuracion.getNotificacionesActivadas());
    }

    @Test
    public void testSave() {
        Configuracion configuracion = createConfiguracion();
        when(configuracionRepository.save(configuracion)).thenReturn(configuracion);
        Configuracion savedConfiguracion = configuracionService.save(configuracion);
        assertNotNull(savedConfiguracion);
        assertEquals(true, savedConfiguracion.getNotificacionesActivadas());
    }
    @Test
    public void testPatchConfiguracion() {
        Configuracion existingConfiguracion = createConfiguracion();
        Configuracion patchData = new Configuracion();
        patchData.setNotificacionesActivadas(false);
        when(configuracionRepository.findById(1L)).thenReturn(java.util.Optional.of(existingConfiguracion));
        when(configuracionRepository.save(any(Configuracion.class))).thenReturn(existingConfiguracion);
        Configuracion patchedConfiguracion = configuracionService.patchConfiguracion(1L, patchData);
        assertNotNull(patchedConfiguracion);
        assertEquals(false, patchedConfiguracion.getNotificacionesActivadas());
    }


}
