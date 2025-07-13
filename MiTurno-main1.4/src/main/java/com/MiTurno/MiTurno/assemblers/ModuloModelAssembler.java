package com.MiTurno.MiTurno.assemblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.MiTurno.MiTurno.controller.ModuloControllerV2;
import com.MiTurno.MiTurno.model.Modulo;

@Component
public class ModuloModelAssembler implements RepresentationModelAssembler<Modulo, EntityModel<Modulo>>{

    @SuppressWarnings("null")
    @Override
    public EntityModel<Modulo> toModel(Modulo modulo){
        return EntityModel.of(modulo,
                linkTo(methodOn(ModuloControllerV2.class).getModuloById(Long.valueOf(modulo.getId()))).withSelfRel(),
                linkTo(methodOn(ModuloControllerV2.class).getAllModulos()).withRel("Modulo"),
                linkTo(methodOn(ModuloControllerV2.class).actualizarModulo(Long.valueOf(modulo.getId()), modulo)).withRel("actualizar modulo"),
                linkTo(methodOn(ModuloControllerV2.class).patchModulo(Long.valueOf(modulo.getId()), modulo)).withRel("actualizar-parcial"),
                linkTo(methodOn(ModuloControllerV2.class).deleteModulo(Long.valueOf(modulo.getId()))).withRel("eliminar")
        );
    }
}
