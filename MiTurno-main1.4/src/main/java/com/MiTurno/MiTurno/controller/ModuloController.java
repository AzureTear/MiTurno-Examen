package com.MiTurno.MiTurno.controller;

import java.util.List;

import com.MiTurno.MiTurno.model.Modulo;
import com.MiTurno.MiTurno.service.ModuloService;

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

@Tag(name = "Módulo", description = "Operaciones sobre módulos")
@RestController
@RequestMapping("/api/v1/modulos")
public class ModuloController {

    @Autowired
    private ModuloService moduloService;

    @Operation(summary = "Listar todos los módulos")
    @GetMapping
    public ResponseEntity<List<Modulo>> listar() {
        List<Modulo> lista = moduloService.findAll();
        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(lista);
    }

    @Operation(summary = "Obtener un módulo por ID")
    @GetMapping("/{id}")
    public ResponseEntity<Modulo> buscarId(@PathVariable Long id) {
        Modulo modulo = moduloService.findById(id);
        if (modulo == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(modulo);
    }

    @Operation(summary = "Crear un nuevo módulo")
    @PostMapping
    public ResponseEntity<Modulo> guardar(@RequestBody Modulo modulo) {
        Modulo creado = moduloService.save(modulo);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @Operation(summary = "Actualizar un módulo existente")
    @PutMapping("/{id}")
    public ResponseEntity<Modulo> actualizar(
            @PathVariable Long id,
            @RequestBody Modulo modulo) {
        try {
            moduloService.save(modulo);
            return ResponseEntity.ok(modulo);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Aplicar cambios parciales a un módulo")
    @PatchMapping("/{id}")
    public ResponseEntity<Modulo> patchModulo(
            @PathVariable Long id,
            @RequestBody Modulo partialModulo) {
        try {
            Modulo updated = moduloService.patchModulo(id, partialModulo);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Eliminar un módulo por ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (moduloService.findById(id) == null) {
            return ResponseEntity.notFound().build();
        }
        moduloService.deleteById(id);
        return ResponseEntity.noContent().build();  
    }
    
}
