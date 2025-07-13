package com.MiTurno.MiTurno.assemblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.MiTurno.MiTurno.controller.TurnoControllerV2;
import com.MiTurno.MiTurno.model.Turno;
@Component
public class TurnoModelAssembler implements RepresentationModelAssembler<Turno, EntityModel<Turno>>{

    @SuppressWarnings("null")
    @Override
    public EntityModel<Turno> toModel(Turno turno){
        return EntityModel.of(turno,
                linkTo(methodOn(TurnoControllerV2.class).getTurnoById(Long.valueOf(turno.getId()))).withSelfRel(),
                linkTo(methodOn(TurnoControllerV2.class).getAllTurnoes()).withRel("Turno"),
                linkTo(methodOn(TurnoControllerV2.class).actualizarTurno(Long.valueOf(turno.getId()), turno)).withRel("actualizar turno"),
                linkTo(methodOn(TurnoControllerV2.class).patchTurno(Long.valueOf(turno.getId()), turno)).withRel("actualizar-parcial"),
                linkTo(methodOn(TurnoControllerV2.class).deleteTurno(Long.valueOf(turno.getId()))).withRel("eliminar"),
                linkTo(methodOn(TurnoControllerV2.class).getUsuarioNombre(turno.getUsuario().getNombre())).withRel("turnos-nombre-usuario")
        );
    }
}
