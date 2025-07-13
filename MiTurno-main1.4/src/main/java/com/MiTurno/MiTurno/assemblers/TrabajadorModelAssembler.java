package com.MiTurno.MiTurno.assemblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.MiTurno.MiTurno.controller.TrabajadorControllerV2;
import com.MiTurno.MiTurno.model.Trabajador;

@Component
public class TrabajadorModelAssembler implements RepresentationModelAssembler<Trabajador, EntityModel<Trabajador>>{

    @SuppressWarnings("null")
    @Override
    public EntityModel<Trabajador> toModel(Trabajador trabajador){
        return EntityModel.of(trabajador,
                linkTo(methodOn(TrabajadorControllerV2.class).getTrabajadorById(Long.valueOf(trabajador.getId()))).withSelfRel(),
                linkTo(methodOn(TrabajadorControllerV2.class).getAllTrabajadores()).withRel("Trabajador"),
                linkTo(methodOn(TrabajadorControllerV2.class).actualizarTrabajador(Long.valueOf(trabajador.getId()), trabajador)).withRel("actualizar trabajador"),
                linkTo(methodOn(TrabajadorControllerV2.class).patchTrabajador(Long.valueOf(trabajador.getId()), trabajador)).withRel("actualizar-parcial"),
                linkTo(methodOn(TrabajadorControllerV2.class).deleteTrabajador(Long.valueOf(trabajador.getId()))).withRel("eliminar")
        );
    }
}
