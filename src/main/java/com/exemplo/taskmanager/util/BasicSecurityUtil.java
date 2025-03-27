package com.exemplo.taskmanager.util;

import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.exemplo.taskmanager.model.Usuario;
import com.exemplo.taskmanager.repository.UsuarioRepository;

@Component
public class BasicSecurityUtil {

 @Autowired
 UsuarioRepository repository;

 public String extractUsername(String authStr) {
  // Decodifica as credenciais
  byte[] decodedBytes = Base64.getDecoder().decode(authStr);
  String credentials = new String(decodedBytes);

  // Separa o usuário e a senha
  String[] userAndPassword = credentials.split(":");
  String username = userAndPassword[0];
  return username;
 }

 public boolean isValidAuth(String authStr, UserDetails user) {

  // Decodifica as credenciais
  byte[] decodedBytes = Base64.getDecoder().decode(authStr);
  String credentials = new String(decodedBytes);

  // Separa o usuário e a senha
  String[] userAndPassword = credentials.split(":");
  String password = userAndPassword[1];

  Usuario usuario = repository.findByLogin(user.getUsername()).get();
  return usuario.getSenha().equals("{noop}" + password);

 }
}
