package com.MiTurno.MiTurno.assemblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.MiTurno.MiTurno.controller.UsuarioControllerV2;
import com.MiTurno.MiTurno.model.Usuario;

@Component
public class UsuarioModelAssembler implements RepresentationModelAssembler<Usuario, EntityModel<Usuario>>{

    @Override
    public EntityModel<Usuario> toModel(Usuario usuario){
        return EntityModel.of(usuario,
                linkTo(methodOn(UsuarioControllerV2.class).getUsuarioById(Long.valueOf(usuario.getId()))).withSelfRel(),
                linkTo(methodOn(UsuarioControllerV2.class).getAllUsuarios()).withRel("Usuario"),
                linkTo(methodOn(UsuarioControllerV2.class).actualizarUsuario(Long.valueOf(usuario.getId()), usuario)).withRel("actualizar usuario"),
                linkTo(methodOn(UsuarioControllerV2.class).patchUsuario(Long.valueOf(usuario.getId()), usuario)).withRel("actualizar-parcial"),
                linkTo(methodOn(UsuarioControllerV2.class).deleteUsuario(Long.valueOf(usuario.getId()))).withRel("eliminar")
        );
    }
}
