package com.exemplo.taskmanager.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exemplo.taskmanager.model.Usuario;
import com.exemplo.taskmanager.service.UsuarioService;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
 @Autowired
 private UsuarioService usuarioService;

 @GetMapping
 public List<Usuario> findAll() {
  return usuarioService.findAll();
 }

 @PostMapping
 public Usuario save(@RequestBody Usuario usuario) {
  return usuarioService.save(usuario);
 }
}
