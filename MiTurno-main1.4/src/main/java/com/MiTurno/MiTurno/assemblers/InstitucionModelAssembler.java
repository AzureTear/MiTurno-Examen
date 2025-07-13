package com.MiTurno.MiTurno.assemblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.MiTurno.MiTurno.controller.InstitucionControllerV2;
import com.MiTurno.MiTurno.model.Institucion;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class InstitucionModelAssembler implements RepresentationModelAssembler<Institucion, EntityModel<Institucion>>{

    @SuppressWarnings("null")
    @Override
    public EntityModel<Institucion> toModel(Institucion institucion){
        return EntityModel.of(institucion,
                linkTo(methodOn(InstitucionControllerV2.class).getInstitucionById(Long.valueOf(institucion.getId()))).withSelfRel(),
                linkTo(methodOn(InstitucionControllerV2.class).getAllInstituciones()).withRel("institucion"),
                linkTo(methodOn(InstitucionControllerV2.class).actualizarInstitucion(Long.valueOf(institucion.getId()), institucion)).withRel("actualizar institucion"),
                linkTo(methodOn(InstitucionControllerV2.class).patchInstitucion(Long.valueOf(institucion.getId()), institucion)).withRel("actualizar-parcial"),
                linkTo(methodOn(InstitucionControllerV2.class).deleteInstitucion(Long.valueOf(institucion.getId()))).withRel("eliminar")
        );
    }
}
