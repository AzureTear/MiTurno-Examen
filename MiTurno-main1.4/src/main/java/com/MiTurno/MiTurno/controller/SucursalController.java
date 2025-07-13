package com.MiTurno.MiTurno.controller;

import java.util.List;

import com.MiTurno.MiTurno.model.Sucursal;
import com.MiTurno.MiTurno.service.SucursalService;

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

@Tag(name = "Sucursal", description = "Operaciones sobre sucursales")
@RestController
@RequestMapping("/api/v1/sucursal")
public class SucursalController {

    @Autowired
    private SucursalService sucursalService;

    @Operation(summary = "Listar todas las sucursales")
    @GetMapping
    public ResponseEntity<List<Sucursal>> listar() {
        List<Sucursal> lista = sucursalService.findAll();
        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(lista);
    }

    @Operation(summary = "Obtener una sucursal por ID")
    @GetMapping("/{id}")
    public ResponseEntity<Sucursal> buscarId(@PathVariable Long id) {
        Sucursal sucursal = sucursalService.findById(id);
        if (sucursal == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(sucursal);
    }

    @Operation(summary = "Crear una nueva sucursal")
    @PostMapping
    public ResponseEntity<Sucursal> guardar(@RequestBody Sucursal sucursal) {
        Sucursal creada = sucursalService.save(sucursal);
        return ResponseEntity.status(HttpStatus.CREATED).body(creada);
    }

    @Operation(summary = "Actualizar una sucursal existente")
    @PutMapping("/{id}")
    public ResponseEntity<Sucursal> actualizar(
            @PathVariable Long id,
            @RequestBody Sucursal sucursal) {
        try {
            sucursalService.save(sucursal);
            return ResponseEntity.ok(sucursal);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Aplicar cambios parciales a una sucursal")
    @PatchMapping("/{id}")
    public ResponseEntity<Sucursal> patchSucursal(
            @PathVariable Long id,
            @RequestBody Sucursal partialSucursal) {
        try {
            Sucursal updated = sucursalService.patchSucursal(id, partialSucursal);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Eliminar una sucursal por ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        sucursalService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
