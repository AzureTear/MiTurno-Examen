package com.MiTurno.MiTurno.controller;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import com.MiTurno.MiTurno.assemblers.ModuloModelAssembler;
import com.MiTurno.MiTurno.model.Modulo;
import com.MiTurno.MiTurno.service.ModuloService;

import io.swagger.v3.oas.annotations.Operation;



@RestController
@RequestMapping("/api/v2/modulos")
public class ModuloControllerV2 {

    @Autowired
    private ModuloService moduloService;

    @Autowired
    private ModuloModelAssembler assembler;

    @Operation(summary = "Listar todos los módulos")
    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<Modulo>> getAllModulos() {
        List<EntityModel<Modulo>> modulo = moduloService.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(modulo,
                linkTo(methodOn(ModuloControllerV2.class).getAllModulos()).withSelfRel());
    }
    @Operation(summary = "Obtener un módulo por ID")
    @GetMapping(value = "/{id}",produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Modulo>> getModuloById(@PathVariable Long id){
        Modulo modulo = moduloService.findById(id);
        if (modulo == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(modulo));
    }
    @Operation(summary = "Crear un nuevo módulo")
    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Modulo>> createModulo(@RequestBody Modulo modulo) {
        Modulo nuevaModulo = moduloService.save(modulo);
        return ResponseEntity
                .created(linkTo(methodOn(ModuloControllerV2.class).getModuloById(Long.valueOf(nuevaModulo.getId()))).toUri())
                .body(assembler.toModel(nuevaModulo));
    }
    @Operation(summary = "Actualizar un módulo existente")
    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Modulo>> actualizarModulo(@PathVariable Long id, @RequestBody Modulo modulo) {
        modulo.setId(id.intValue());
        Modulo updated = moduloService.save(modulo);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(updated));
    }
    @Operation(summary = "Aplicar cambios parciales a un módulo")
    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Modulo>> patchModulo(@PathVariable Long id, @RequestBody Modulo modulo) {
        Modulo updatedModulo = moduloService.patchModulo(id, modulo);
        if (updatedModulo == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(updatedModulo));
    }
    @Operation(summary = "Eliminar un módulo por ID")
    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<Void> deleteModulo(@PathVariable Long id) {
        Modulo modulo = moduloService.findById(id);
        if (modulo == null) {
            return ResponseEntity.notFound().build();
        }
        moduloService.deleteById(id);
        return ResponseEntity.noContent().build();
    }    
}
