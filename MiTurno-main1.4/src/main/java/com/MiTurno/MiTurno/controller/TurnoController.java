package com.MiTurno.MiTurno.controller;



import java.util.List;


import com.MiTurno.MiTurno.model.Turno;
import com.MiTurno.MiTurno.service.TurnoService;

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

@Tag(name = "Turno", description = "Operaciones sobre turnos")
@RestController
@RequestMapping("/api/v1/turno")
public class TurnoController {

    @Autowired
    private TurnoService turnoService;

    @Operation(summary = "Listar todos los turnos")
    @GetMapping
    public ResponseEntity<List<Turno>> listar() {
        List<Turno> lista = turnoService.findAll();
        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(lista);
    }

    @Operation(summary = "Obtener un turno por ID")
    @GetMapping("/{id}")
    public ResponseEntity<Turno> buscarId(@PathVariable Long id) {
        Turno turno = turnoService.findById(id);
        if (turno == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(turno);
    }

    @Operation(summary = "Crear un nuevo turno")
    @PostMapping
    public ResponseEntity<Turno> guardar(@RequestBody Turno turno) {
        Turno creado = turnoService.save(turno);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @Operation(summary = "Actualizar un turno existente")
    @PutMapping("/{id}")
    public ResponseEntity<Turno> actualizar(
            @PathVariable Long id,
            @RequestBody Turno turno) {
        try {
            turnoService.save(turno);
            return ResponseEntity.ok(turno);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Aplicar cambios parciales a un turno")
    @PatchMapping("/{id}")
    public ResponseEntity<Turno> patchTurno(
            @PathVariable Long id,
            @RequestBody Turno partialTurno) {
        try {
            Turno updated = turnoService.patchTurno(id, partialTurno);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Eliminar un turno por ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        turnoService.delete(id);
        return ResponseEntity.noContent().build();
    }


}

