package com.MiTurno.MiTurno.controller;

import java.util.List;

import com.MiTurno.MiTurno.model.Trabajador;
import com.MiTurno.MiTurno.service.TrabajadorService;

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

@Tag(name = "Trabajador", description = "Operaciones sobre trabajadores")
@RestController
@RequestMapping("/api/v1/trabajador")
public class TrabajadorController {

    @Autowired
    private TrabajadorService trabajadorService;

    @Operation(summary = "Listar todos los trabajadores")
    @GetMapping
    public ResponseEntity<List<Trabajador>> listar() {
        List<Trabajador> lista = trabajadorService.findAll();
        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(lista);
    }

    @Operation(summary = "Obtener un trabajador por ID")
    @GetMapping("/{id}")
    public ResponseEntity<Trabajador> buscarId(@PathVariable Long id) {
        Trabajador trabajador = trabajadorService.findById(id);
        if (trabajador == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(trabajador);
    }

    @Operation(summary = "Crear un nuevo trabajador")
    @PostMapping
    public ResponseEntity<Trabajador> guardar(@RequestBody Trabajador trabajador) {
        Trabajador creado = trabajadorService.save(trabajador);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @Operation(summary = "Actualizar un trabajador existente")
    @PutMapping("/{id}")
    public ResponseEntity<Trabajador> actualizar(
            @PathVariable Long id,
            @RequestBody Trabajador trabajador) {
        try {
            trabajadorService.save(trabajador);
            return ResponseEntity.ok(trabajador);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Aplicar cambios parciales a un trabajador")
    @PatchMapping("/{id}")
    public ResponseEntity<Trabajador> patchTrabajador(
            @PathVariable Long id,
            @RequestBody Trabajador partialTrabajador) {
        try {
            Trabajador updated = trabajadorService.patchTrabajador(id, partialTrabajador);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Eliminar un trabajador por ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        trabajadorService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
