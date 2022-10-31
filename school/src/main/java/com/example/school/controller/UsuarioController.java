package com.example.school.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.school.model.Usuario;
import com.example.school.repository.UsuarioRepository;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {
    @Autowired
    private UsuarioRepository _usuarioRepository;

    @GetMapping
    public List<Usuario> findAll() {
        return _usuarioRepository.findAll();
    } 

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> findById(@PathVariable(value = "id") Integer id) {
        Optional<Usuario> usuario = _usuarioRepository.findById(id);
        if(usuario.isPresent()) {
            return new ResponseEntity<Usuario>(usuario.get(), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Usuario post(@RequestBody Usuario usuario) {
        return _usuarioRepository.save(usuario);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> put(@PathVariable(value = "id") Integer id, @RequestBody Usuario newUsuario) {
        Optional<Usuario> oldUsuario = _usuarioRepository.findById(id);
        if(oldUsuario.isPresent()){
            Usuario usuario = oldUsuario.get();
            usuario.setLogin(newUsuario.getLogin());
            usuario.setSenha(newUsuario.getSenha());
            _usuarioRepository.save(usuario);
            return new ResponseEntity<Usuario>(usuario, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable(value = "id") Integer id) {
        Optional<Usuario> usuario = _usuarioRepository.findById(id);
        if(usuario.isPresent()) {
            _usuarioRepository.delete(usuario.get());
            return new ResponseEntity<>(HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
