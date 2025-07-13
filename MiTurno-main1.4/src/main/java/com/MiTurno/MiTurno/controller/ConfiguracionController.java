package com.MiTurno.MiTurno.controller;

import java.util.List;

import com.MiTurno.MiTurno.model.Configuracion;
import com.MiTurno.MiTurno.service.ConfiguracionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Tag(name = "Configuración", description = "Operaciones sobre parámetros de configuración")
@RestController
@RequestMapping("/api/v1/configuraciones")
public class ConfiguracionController {

    @Autowired
    private ConfiguracionService configuracionService;

    @Operation(summary = "Listar todas las configuraciones")
    @GetMapping
    public ResponseEntity<List<Configuracion>> listar() {
        List<Configuracion> configuracion = configuracionService.findAll();
        if (configuracion.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(configuracion);
    }
    @Operation(summary = "Obtener una configuración por ID")
    @GetMapping("/{id}")
    public ResponseEntity<Configuracion> buscarId(@PathVariable Long id) {
        Configuracion configuracion = configuracionService.findById(id);
        if (configuracion == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(configuracion);
    }
    @Operation(summary = "Actualizar una configuración existente")
    @PutMapping("/{id}")
    public ResponseEntity<Configuracion> actualizar(
            @PathVariable Long id,
            @RequestBody Configuracion configuracion) {
        try {
            configuracionService.save(configuracion);
            return ResponseEntity.ok(configuracion);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
    @Operation(summary = "Aplicar cambios parciales a una configuración")
    @PatchMapping("/{id}")
    public ResponseEntity<Configuracion> patchConfiguracion(
            @PathVariable Long id,
            @RequestBody Configuracion partialConfiguracion) {
        try {
            Configuracion updatedConfiguracion = configuracionService.patchConfiguracion(id, partialConfiguracion);
            return ResponseEntity.ok(updatedConfiguracion);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
