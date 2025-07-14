package com.MiTurno.MiTurno.controller;

import java.util.List;

import com.MiTurno.MiTurno.model.Rol;
import com.MiTurno.MiTurno.service.RolService;

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

@Tag(name = "Rol", description = "Operaciones sobre roles de usuario")
@RestController
@RequestMapping("/api/v1/roles")
public class RolController {

    @Autowired
    private RolService rolService;

    @Operation(summary = "Listar todos los roles")
    @GetMapping
    public ResponseEntity<List<Rol>> listar() {
        List<Rol> lista = rolService.findAll();
        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(lista);
    }

    @Operation(summary = "Obtener un rol por ID")
    @GetMapping("/{id}")
    public ResponseEntity<Rol> buscarId(@PathVariable Long id) {
        Rol rol = rolService.findById(id);
        if (rol == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(rol);
    }

    @Operation(summary = "Crear un nuevo rol")
    @PostMapping
    public ResponseEntity<Rol> guardar(@RequestBody Rol rol) {
        Rol creado = rolService.save(rol);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @Operation(summary = "Actualizar un rol existente")
    @PutMapping("/{id}")
    public ResponseEntity<Rol> actualizar(
            @PathVariable Long id,
            @RequestBody Rol rol) {
        try {
            rolService.save(rol);
            return ResponseEntity.ok(rol);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Aplicar cambios parciales a un rol")
    @PatchMapping("/{id}")
    public ResponseEntity<Rol> patchRol(
            @PathVariable Long id,
            @RequestBody Rol partialRol) {
        try {
            Rol updated = rolService.patchRol(id, partialRol);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Eliminar un rol por ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        rolService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
