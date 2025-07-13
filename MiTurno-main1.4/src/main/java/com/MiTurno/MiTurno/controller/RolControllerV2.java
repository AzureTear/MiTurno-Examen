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

import com.MiTurno.MiTurno.assemblers.RolModelAssembler;
import com.MiTurno.MiTurno.model.Rol;
import com.MiTurno.MiTurno.service.RolService;

import io.swagger.v3.oas.annotations.Operation;

import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/v2/roles")
public class RolControllerV2 {

    @Autowired
    private RolService rolService;

    @Autowired 
    private RolModelAssembler assembler;
    @Operation(summary = "Listar todos los roles")
    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<Rol>> getAllRoles() {
        List<EntityModel<Rol>> rol = rolService.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(rol,
                linkTo(methodOn(RolControllerV2.class).getAllRoles()).withSelfRel());
    }
    @Operation(summary = "Obtener un rol por ID")
    @GetMapping(value = "/{id}",produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Rol>> getRolById(@PathVariable Long id){
        Rol rol = rolService.findById(id);
        if (rol == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(rol));
    }
    @Operation(summary = "Crear un nuevo rol")
    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Rol>> createRol(@RequestBody Rol rol) {
        Rol nuevaRol = rolService.save(rol);
        return ResponseEntity
                .created(linkTo(methodOn(RolControllerV2.class).getRolById(Long.valueOf(nuevaRol.getId()))).toUri())
                .body(assembler.toModel(nuevaRol));
    }
    @Operation(summary = "Actualizar un rol existente")
    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Rol>> actualizarRol(@PathVariable Long id, @RequestBody Rol rol) {
        rol.setId(id.intValue());
        Rol updated = rolService.save(rol);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(updated));
    }
    @Operation(summary = "Aplicar cambios parciales a un rol")
    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Rol>> patchRol(@PathVariable Long id, @RequestBody Rol rol) {
        Rol updatedRol = rolService.patchRol(id, rol);
        if (updatedRol == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(updatedRol));
    }
    @Operation(summary = "Eliminar un rol por ID")
    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<Void> deleteRol(@PathVariable Long id) {
        Rol rol = rolService.findById(id);
        if (rol == null) {
            return ResponseEntity.notFound().build();
        }
        rolService.delete(id);
        return ResponseEntity.noContent().build();
    }


}
