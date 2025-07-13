package com.MiTurno.MiTurno.controller;

import java.util.List;

import com.MiTurno.MiTurno.model.Institucion;
import com.MiTurno.MiTurno.service.InstitucionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Tag(name = "Institución", description = "Operaciones sobre instituciones")
@RestController
@RequestMapping("/api/v1/institucion")
public class InstitucionController {

    @Autowired
    private InstitucionService institucionService;

    @Operation(summary = "Listar todas las instituciones")
    @GetMapping
    public ResponseEntity<List<Institucion>> listar() {
        List<Institucion> lista = institucionService.findAll();
        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(lista);
    }

    @Operation(summary = "Obtener una institución por ID")
    @GetMapping("/{id}")
    public ResponseEntity<Institucion> buscarId(@PathVariable Long id) {
        Institucion institucion = institucionService.findById(id);
        if (institucion == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(institucion);
    }

    @Operation(summary = "Crear una nueva institución")
    @PostMapping
    public ResponseEntity<Institucion> guardar(@RequestBody Institucion institucion) {
        Institucion creada = institucionService.save(institucion);
        return ResponseEntity.status(HttpStatus.CREATED).body(creada);
    }

    @Operation(summary = "Actualizar una institución existente")
    @PutMapping("/{id}")
    public ResponseEntity<Institucion> actualizar(
            @PathVariable Long id,
            @RequestBody Institucion institucion) {
        try {
            institucionService.save(institucion);
            return ResponseEntity.ok(institucion);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Aplicar cambios parciales a una institución")
    @PatchMapping("/{id}")
    public ResponseEntity<Institucion> patchInstitucion(
            @PathVariable Long id,
            @RequestBody Institucion partialInstitucion) {
        try {
            Institucion updated = institucionService.patchInstitucion(id, partialInstitucion);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Eliminar una institución por ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        institucionService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
