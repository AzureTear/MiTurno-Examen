package com.MiTurno.MiTurno.assemblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.MiTurno.MiTurno.controller.RolControllerV2;
import com.MiTurno.MiTurno.model.Rol;

@Component
public class RolModelAssembler implements RepresentationModelAssembler<Rol, EntityModel<Rol>>{

    @SuppressWarnings("null")
    @Override
    public EntityModel<Rol> toModel(Rol rol){
        return EntityModel.of(rol,
                linkTo(methodOn(RolControllerV2.class).getRolById(Long.valueOf(rol.getId()))).withSelfRel(),
                linkTo(methodOn(RolControllerV2.class).getAllRoles()).withRel("Rol"),
                linkTo(methodOn(RolControllerV2.class).actualizarRol(Long.valueOf(rol.getId()), rol)).withRel("actualizar rol"),
                linkTo(methodOn(RolControllerV2.class).patchRol(Long.valueOf(rol.getId()), rol)).withRel("actualizar-parcial"),
                linkTo(methodOn(RolControllerV2.class).deleteRol(Long.valueOf(rol.getId()))).withRel("eliminar")
        );
    }
}
