package com.MiTurno.MiTurno.assemblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


import com.MiTurno.MiTurno.controller.SucursalControllerV2;
import com.MiTurno.MiTurno.model.Sucursal;

@Component
public class SucursalModelAssembler implements RepresentationModelAssembler<Sucursal, EntityModel<Sucursal>>{

    @SuppressWarnings("null")
    @Override
    public EntityModel<Sucursal> toModel(Sucursal sucursal){
        return EntityModel.of(sucursal,
                linkTo(methodOn(SucursalControllerV2.class).getSucursalById(Long.valueOf(sucursal.getId()))).withSelfRel(),
                linkTo(methodOn(SucursalControllerV2.class).getAllSucursales()).withRel("Sucursal"),
                linkTo(methodOn(SucursalControllerV2.class).actualizarSucursal(Long.valueOf(sucursal.getId()), sucursal)).withRel("actualizar sucursal"),
                linkTo(methodOn(SucursalControllerV2.class).patchSucursal(Long.valueOf(sucursal.getId()), sucursal)).withRel("actualizar-parcial"),
                linkTo(methodOn(SucursalControllerV2.class).deleteSucursal(Long.valueOf(sucursal.getId()))).withRel("eliminar"),
                linkTo(methodOn(SucursalControllerV2.class).getInstitucionNombre(sucursal.getInstitucion().getNombre())).withRel("sucursal-nombre-institucion")               
                /* ,
                linkTo(methodOn(SucursalControllerV2.class).getSucursalesNombreConConfiguracion()).withRel("obtener Sucursales con nombre y config")*/
                
        );
    }
}
