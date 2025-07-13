package com.MiTurno.MiTurno.controller;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
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

import com.MiTurno.MiTurno.assemblers.TurnoModelAssembler;
import com.MiTurno.MiTurno.model.Turno;
import com.MiTurno.MiTurno.service.TurnoService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/v2/turnos")
public class TurnoControllerV2 {

    @Autowired
    private TurnoService turnoService;

    @Autowired
    private TurnoModelAssembler assembler;
    @Operation(summary = "Listar todos los turnos")
    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<Turno>> getAllTurnoes() {
        List<EntityModel<Turno>> turno = turnoService.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(turno,
                linkTo(methodOn(TurnoControllerV2.class).getAllTurnoes()).withSelfRel());
    }
    @Operation(summary = "Obtener un turno por ID")
    @GetMapping(value = "/{id}",produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Turno>> getTurnoById(@PathVariable Long id){
        Turno turno = turnoService.findById(id);
        if (turno == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(turno));
    }
    @Operation(summary = "Crear un nuevo turno")
    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Turno>> createTurno(@RequestBody Turno turno) {
        Turno nuevaTurno = turnoService.save(turno);
        return ResponseEntity
                .created(linkTo(methodOn(TurnoControllerV2.class).getTurnoById(Long.valueOf(nuevaTurno.getId()))).toUri())
                .body(assembler.toModel(nuevaTurno));
    }
    @Operation(summary = "Actualizar un turno existente")
    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Turno>> actualizarTurno(@PathVariable Long id, @RequestBody Turno turno) {
        turno.setId(id.intValue());
        Turno updated = turnoService.save(turno);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(updated));
    }
    @Operation(summary = "Aplicar cambios parciales a un turno")
    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Turno>> patchTurno(@PathVariable Long id, @RequestBody Turno turno) {
        Turno updatedTurno = turnoService.patchTurno(id, turno);
        if (updatedTurno == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(updatedTurno));
    }
    @Operation(summary = "Eliminar un turno por ID")
    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<Void> deleteTurno(@PathVariable Long id) {
        Turno turno = turnoService.findById(id);
        if (turno == null) {
            return ResponseEntity.notFound().build();
        }
        turnoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/Usuario/{idUsuario}/Estado/{estado}", produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<Turno>> getUsuarioIdAndModuloEstado(@PathVariable Integer idUsuario, String estado ){
        List<EntityModel<Turno>> turnos = turnoService.getUsuarioIdAndModuloEstado(idUsuario, estado).stream()
        .map(assembler::toModel)
        .collect(Collectors.toList());

        return CollectionModel.of(turnos,
        linkTo(methodOn(TurnoControllerV2.class).getUsuarioIdAndModuloEstado(idUsuario, estado)).withSelfRel());
        }
    
    @Operation(summary = "Encontrar turno entre Hora Atencion y creacion")
    @GetMapping(value = "/hora/{horaCreacion}/{horaAtencion}", produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<Turno>> getFechaBetween(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date horaCreacion, @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date horaAtencion){
        List<EntityModel<Turno>> turnos = turnoService.getFechaBetween(horaCreacion,horaAtencion).stream()
        .map(assembler::toModel)
        .collect(Collectors.toList());

        return CollectionModel.of(turnos,
        linkTo(methodOn(TurnoControllerV2.class).getFechaBetween(horaCreacion, horaAtencion)).withSelfRel());

    }

    @GetMapping(value = "/usuarioNombre", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<Turno>>> getUsuarioNombre(@RequestParam("usuarioNombre") String usuarioNombre) {
        List<EntityModel<Turno>> turnos = turnoService.findByUsuarioNombre(usuarioNombre).stream()
            .map(assembler::toModel)
            .collect(Collectors.toList());
        if (turnos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(CollectionModel.of(turnos));
    }

    @GetMapping(value = "/descripcion/estado", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<Turno>>> getDescripcionAndEstado(@RequestParam("descripcion") String descripcion,@RequestParam("estado") String titulo) {
        List<EntityModel<Turno>> turnos = turnoService.findByDescripcionAndEstado(descripcion,titulo).stream()
            .map(assembler::toModel)
            .collect(Collectors.toList());
        if (turnos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(CollectionModel.of(turnos));
    }

}
