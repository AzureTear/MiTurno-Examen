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

import com.MiTurno.MiTurno.assemblers.SucursalModelAssembler;
import com.MiTurno.MiTurno.model.Sucursal;
import com.MiTurno.MiTurno.service.SucursalService;

import io.swagger.v3.oas.annotations.Operation;



@RestController
@RequestMapping("/api/v2/sucursales")
public class SucursalControllerV2 {

    @Autowired
    private SucursalService sucursalService;

    @Autowired
    private SucursalModelAssembler assembler;

    @Operation(summary = "Listar todas las sucursales")
    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<Sucursal>> getAllSucursales() {
        List<EntityModel<Sucursal>> sucursal = sucursalService.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(sucursal,
                linkTo(methodOn(SucursalControllerV2.class).getAllSucursales()).withSelfRel());
    }

    @Operation(summary = "Obtener una sucursal por ID")
    @GetMapping(value = "/{id}",produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Sucursal>> getSucursalById(@PathVariable Long id){
        Sucursal sucursal = sucursalService.findById(id);
        if (sucursal == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(sucursal));
    }
    @Operation(summary = "Crear una nueva sucursal")
    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Sucursal>> createSucursal(@RequestBody Sucursal sucursal) {
        Sucursal nuevaSucursal = sucursalService.save(sucursal);
        return ResponseEntity
                .created(linkTo(methodOn(SucursalControllerV2.class).getSucursalById(Long.valueOf(nuevaSucursal.getId()))).toUri())
                .body(assembler.toModel(nuevaSucursal));
    }
    @Operation(summary = "Actualizar una sucursal existente")
    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Sucursal>> actualizarSucursal(@PathVariable Long id, @RequestBody Sucursal sucursal) {
        sucursal.setId(id.intValue());
        Sucursal updated = sucursalService.save(sucursal);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(updated));
    }
    @Operation(summary = "Aplicar cambios parciales a una sucursal")
    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Sucursal>> patchSucursal(@PathVariable Long id, @RequestBody Sucursal sucursal) {
        Sucursal updatedSucursal = sucursalService.patchSucursal(id, sucursal);
        if (updatedSucursal == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(updatedSucursal));
    }

    @Operation(summary = "Eliminar una sucursal por ID")
    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<Void> deleteSucursal(@PathVariable Long id) {
        Sucursal sucursal = sucursalService.findById(id);
        if (sucursal == null) {
            return ResponseEntity.notFound().build();
        }
        sucursalService.delete(id);
        return ResponseEntity.noContent().build();
    }


    @Operation(summary = "Buscar por Id institucion y Notificaciones activadas")
    @GetMapping(value = "/instituto/{idInsti}/notificaciones/{notificaciones}", produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<Sucursal>> getInstitucionIdYNotificacionesActivadas(@PathVariable Integer idInsti, @PathVariable Boolean notificaciones) {
        List<EntityModel<Sucursal>> sucursals = sucursalService.getInstitucionIdYNotificacionesActivadas(idInsti,  notificaciones)
                .stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(
                sucursals,
                linkTo(methodOn(SucursalControllerV2.class).getInstitucionIdYNotificacionesActivadas(idInsti, notificaciones)).withSelfRel()
        );
    }
    @GetMapping(value = "/institucionNombre", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<Sucursal>>> getInstitucionNombre(@RequestParam("instiNombre") String instiNombre) {
        List<EntityModel<Sucursal>> sucursales = sucursalService.findByInstitucionNombre(instiNombre).stream()
            .map(assembler::toModel)
            .collect(Collectors.toList());
        if (sucursales.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(CollectionModel.of(sucursales));
    }
}
