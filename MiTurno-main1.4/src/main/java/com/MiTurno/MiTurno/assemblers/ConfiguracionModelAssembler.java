
package com.MiTurno.MiTurno.assemblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.MiTurno.MiTurno.controller.ConfiguracionControllerV2;
import com.MiTurno.MiTurno.model.Configuracion;

@Component
public class ConfiguracionModelAssembler implements RepresentationModelAssembler<Configuracion, EntityModel<Configuracion>>{

    @SuppressWarnings("null")
    @Override
    public EntityModel<Configuracion> toModel(Configuracion configuracion){
        return EntityModel.of(configuracion,
                linkTo(methodOn(ConfiguracionControllerV2.class).getConfiguracionById(Long.valueOf(configuracion.getId()))).withSelfRel(),
                linkTo(methodOn(ConfiguracionControllerV2.class).getAllConfiguraciones()).withRel("configuracion"),
                linkTo(methodOn(ConfiguracionControllerV2.class).actualizarConfiguracion(Long.valueOf(configuracion.getId()), configuracion)).withRel("actualizar"),
                linkTo(methodOn(ConfiguracionControllerV2.class).patchConfiguracion(Long.valueOf(configuracion.getId()), configuracion)).withRel("actualizar")
        );
    }

}

