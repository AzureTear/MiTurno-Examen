package com.MiTurno.MiTurno.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.MiTurno.MiTurno.model.Usuario;
import com.MiTurno.MiTurno.repository.UsuarioRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;
        public List<Usuario> findAll(){
        return usuarioRepository.findAll();
    }

    public Usuario findById(Long id) {
        return usuarioRepository.findById(id).orElse( null);
    }
    
    public void delete(Long id){
        usuarioRepository.deleteById(id);
    }

    public Usuario save(Usuario usuario) {
        return usuarioRepository.save(usuario);
    } 

    public Usuario patchUsuario(Long id, Usuario parcialUsuario){
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);
        if (usuarioOptional.isPresent()) {
            
            Usuario usuarioToUpdate = usuarioOptional.get();
            
            if (parcialUsuario.getNombre() != null) {
                usuarioToUpdate.setNombre(parcialUsuario.getNombre());   
            }
            if (parcialUsuario.getApellido() != null) {
                usuarioToUpdate.setApellido(parcialUsuario.getApellido());   
            }
            if(parcialUsuario.getRut() != null) {
                usuarioToUpdate.setRut((parcialUsuario.getRut()));
            }
            if (parcialUsuario.getTelefono() != null) {
                usuarioToUpdate.setTelefono(parcialUsuario.getTelefono());   
            }
            if(parcialUsuario.getEmail() != null) {
                usuarioToUpdate.setEmail((parcialUsuario.getEmail()));
            }
            if (parcialUsuario.getContraseña() != null) {
                usuarioToUpdate.setContraseña(parcialUsuario.getContraseña());   
            }
            return usuarioRepository.save(usuarioToUpdate);
        } else {
            return null; 
        }
    }


}
