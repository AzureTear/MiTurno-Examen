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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import com.MiTurno.MiTurno.assemblers.ConfiguracionModelAssembler;
import com.MiTurno.MiTurno.model.Configuracion;
import com.MiTurno.MiTurno.service.ConfiguracionService;

import io.swagger.v3.oas.annotations.Operation;


@RestController
@RequestMapping("/api/v2/configuraciones")
public class ConfiguracionControllerV2 {

    @Autowired
    private ConfiguracionService configuracionService;

    @Autowired
    private ConfiguracionModelAssembler assembler;

    @Operation(summary = "Listar todas las configuraciones")
    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<Configuracion>> getAllConfiguraciones() {
        List<EntityModel<Configuracion>> configuracion = configuracionService.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(configuracion,
                linkTo(methodOn(ConfiguracionControllerV2.class).getAllConfiguraciones()).withSelfRel());
    }
    @Operation(summary = "Obtener una configuración por ID")
    @GetMapping(value = "/{id}",produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Configuracion>> getConfiguracionById(@PathVariable Long id){
        Configuracion configuracion = configuracionService.findById(id);
        if (configuracion == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(configuracion));
    }
    @Operation(summary = "Crear una configuración ")
    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Configuracion>> createConfiguracion(@RequestBody Configuracion configuracion) {
        Configuracion nuevaConfiguracion = configuracionService.save(configuracion);
        return ResponseEntity
                .created(linkTo(methodOn(ConfiguracionControllerV2.class).getConfiguracionById(Long.valueOf(nuevaConfiguracion.getId()))).toUri())
                .body(assembler.toModel(nuevaConfiguracion));
    }
    @Operation(summary = "Actualizar una configuración existente")
    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Configuracion>> actualizarConfiguracion(@PathVariable Long id, @RequestBody Configuracion configuracion) {
        configuracion.setId(id.intValue());
        Configuracion updated = configuracionService.save(configuracion);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(updated));
    }
    @Operation(summary = "Aplicar cambios parciales a una configuración")
    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Configuracion>> patchConfiguracion(@PathVariable Long id, @RequestBody Configuracion configuracion) {
        Configuracion updatedConfiguracion = configuracionService.patchConfiguracion(id, configuracion);
        if (updatedConfiguracion == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(updatedConfiguracion));
    }

}
