package com.italoasc.authentication_authorization.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/secure")
public class SecureController {

    @GetMapping("/hello")
    public ResponseEntity<String> securedHello(Authentication authentication) {
        String username = authentication.getName();
        return ResponseEntity.ok("Olá, " + username + "! Você acessou um endpoint protegido.");
    }
}

