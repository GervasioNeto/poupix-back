package com.poupix.poupix.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Map<String, String> credential) {
        return ResponseEntity.ok("Login bem-sucedido! Use credencial");
    }

    @GetMapping("/protected")
    public ResponseEntity<String> protectedEndpoint(Authentication authentication) {
        return ResponseEntity.ok("Acesso permitido! Usuario: " + authentication);
    }
}
