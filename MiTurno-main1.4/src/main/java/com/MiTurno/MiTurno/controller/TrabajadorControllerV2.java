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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.MiTurno.MiTurno.assemblers.TrabajadorModelAssembler;
import com.MiTurno.MiTurno.model.Trabajador;
import com.MiTurno.MiTurno.service.TrabajadorService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/v2/trabajadores")
public class TrabajadorControllerV2 {

    @Autowired
    private TrabajadorService trabajadorService;

    @Autowired
    private TrabajadorModelAssembler assembler;
    @Operation(summary = "Listar todos los trabajadores")
    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<Trabajador>> getAllTrabajadores() {
        List<EntityModel<Trabajador>> trabajador = trabajadorService.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(trabajador,
                linkTo(methodOn(TrabajadorControllerV2.class).getAllTrabajadores()).withSelfRel());
    }
    @Operation(summary = "Obtener un trabajador por ID")
    @GetMapping(value = "/{id}",produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Trabajador>> getTrabajadorById(@PathVariable Long id){
        Trabajador trabajador = trabajadorService.findById(id);
        if (trabajador == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(trabajador));
    }
    @Operation(summary = "Crear un nuevo trabajador")
    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Trabajador>> createTrabajador(@RequestBody Trabajador trabajador) {
        Trabajador nuevaTrabajador = trabajadorService.save(trabajador);
        return ResponseEntity
                .created(linkTo(methodOn(TrabajadorControllerV2.class).getTrabajadorById(Long.valueOf(nuevaTrabajador.getId()))).toUri())
                .body(assembler.toModel(nuevaTrabajador));
    }
    @Operation(summary = "Actualizar un trabajador existente")
    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Trabajador>> actualizarTrabajador(@PathVariable Long id, @RequestBody Trabajador trabajador) {
        trabajador.setId(id.intValue());
        Trabajador updated = trabajadorService.save(trabajador);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(updated));
    }
    @Operation(summary = "Aplicar cambios parciales a un trabajador")
    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Trabajador>> patchTrabajador(@PathVariable Long id, @RequestBody Trabajador trabajador) {
        Trabajador updatedTrabajador = trabajadorService.patchTrabajador(id, trabajador);
        if (updatedTrabajador == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(updatedTrabajador));
    }
    @Operation(summary = "Eliminar un trabajador por ID")
    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<Void> deleteTrabajador(@PathVariable Long id) {
        Trabajador trabajador = trabajadorService.findById(id);
        if (trabajador == null) {
            return ResponseEntity.notFound().build();
        }
        trabajadorService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Buscar por Id Sucursal y Capacidad Maxima Mayor que")
    @GetMapping(value = "/admin/{admin}/capacidadMaxima/{capacidadMaxima}", produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<Trabajador>> getRolAdminAndSucursalCapacidadMaximaGreaterThan(@PathVariable Boolean admin, @PathVariable Integer capacidadMaxima) {
        List<EntityModel<Trabajador>> trabajadores = trabajadorService.getRolAdminAndSucursalCapacidadMaximaGreaterThan(admin,  capacidadMaxima)
                .stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(
                trabajadores,
                linkTo(methodOn(TrabajadorControllerV2.class).getRolAdminAndSucursalCapacidadMaximaGreaterThan(admin, capacidadMaxima)).withSelfRel()
        );
    }

    @GetMapping(value = "/sucursalId", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<Trabajador>>> getSucursalId(@RequestParam("idS") Integer idS) {
        List<EntityModel<Trabajador>> trabajadores = trabajadorService.findBySucursalId(idS).stream()
            .map(assembler::toModel)
            .collect(Collectors.toList());
        if (trabajadores.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(CollectionModel.of(trabajadores));
    }
}
