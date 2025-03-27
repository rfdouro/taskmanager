package com.exemplo.taskmanager.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

 @GetMapping("/login")
 public String login() {
  return "Autenticação realizada com sucesso!";
 }
}
