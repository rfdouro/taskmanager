package com.exemplo.taskmanager.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.FetchType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
public class Usuario implements UserDetails {
 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 private Long id;

 @Column(unique = true)
 private String login;

 private String senha;

 @ElementCollection(fetch = FetchType.EAGER)
 private List<String> permissoes;

 // Getters e Setters
 public Long getId() {
  return id;
 }

 public void setId(Long id) {
  this.id = id;
 }

 public String getLogin() {
  return login;
 }

 public void setLogin(String login) {
  this.login = login;
 }

 public String getSenha() {
  return senha;
 }

 public void setSenha(String senha) {
  this.senha = senha;
 }

 public List<String> getPermissoes() {
  return permissoes;
 }

 public void setPermissoes(List<String> permissoes) {
  this.permissoes = permissoes;
 }

 @Override
 public Collection<? extends GrantedAuthority> getAuthorities() {
  List<GrantedAuthority> lg = new ArrayList<>();
  this.permissoes.forEach(p -> {
   GrantedAuthority ga = new SimpleGrantedAuthority(p);
   lg.add(ga);
  });
  return lg;
 }

 @Override
 public String getPassword() {
  return this.senha;
 }

 @Override
 public String getUsername() {
  return this.login;
 }

}
