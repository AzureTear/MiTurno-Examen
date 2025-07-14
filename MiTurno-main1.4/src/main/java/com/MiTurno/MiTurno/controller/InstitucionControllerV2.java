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

import com.MiTurno.MiTurno.assemblers.InstitucionModelAssembler;
import com.MiTurno.MiTurno.model.Institucion;
import com.MiTurno.MiTurno.service.InstitucionService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/v2/instituciones")
public class InstitucionControllerV2 {

    @Autowired
    private InstitucionService institucionService;

    @Autowired
    private InstitucionModelAssembler assembler;

    @Operation(summary = "Listar todas las instituciones")
    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<Institucion>> getAllInstituciones() {
        List<EntityModel<Institucion>> institucion = institucionService.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(institucion,
                linkTo(methodOn(InstitucionControllerV2.class).getAllInstituciones()).withSelfRel());
    }
    @Operation(summary = "Obtener una institución por ID")
    @GetMapping(value = "/{id}",produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Institucion>> getInstitucionById(@PathVariable Long id){
        Institucion institucion = institucionService.findById(id);
        if (institucion == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(institucion));
    }
    @Operation(summary = "Crear una nueva institución")
    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Institucion>> createInstitucion(@RequestBody Institucion institucion) {
        Institucion nuevaInstitucion = institucionService.save(institucion);
        return ResponseEntity
                .created(linkTo(methodOn(InstitucionControllerV2.class).getInstitucionById(Long.valueOf(nuevaInstitucion.getId()))).toUri())
                .body(assembler.toModel(nuevaInstitucion));
    }
    @Operation(summary = "Actualizar una institución existente")
    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Institucion>> actualizarInstitucion(@PathVariable Long id, @RequestBody Institucion institucion) {
        institucion.setId(id.intValue());
        Institucion updated = institucionService.save(institucion);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(updated));
    }
    @Operation(summary = "Aplicar cambios parciales a una institución")
    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Institucion>> patchInstitucion(@PathVariable Long id, @RequestBody Institucion institucion) {
        Institucion updatedInstitucion = institucionService.patchInstitucion(id, institucion);
        if (updatedInstitucion == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(updatedInstitucion));
    }
    @Operation(summary = "Eliminar una institución por ID")
    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<Void> deleteInstitucion(@PathVariable Long id) {
        Institucion institucion = institucionService.findById(id);
        if (institucion == null) {
            return ResponseEntity.notFound().build();
        }
        institucionService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

