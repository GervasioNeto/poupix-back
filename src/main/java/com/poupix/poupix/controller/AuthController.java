package com.poupix.poupix.controller;

import com.poupix.poupix.components.JwtUtil;
import com.poupix.poupix.dto.UserDTO;
import com.poupix.poupix.entity.User;
import com.poupix.poupix.service.UserService;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<UserDTO> login(@RequestHeader("Authorization") String authorization) {

        String base64Credentials = authorization.substring("Basic ".length());
        String credentials = new String(Base64.getDecoder().decode(base64Credentials), StandardCharsets.UTF_8);

        String[] values = credentials.split(":", 2);
        String email = values[0];
        String password = values[1];

        User user = userService.authenticate(email, password);

        return ResponseEntity.ok(userService.toDTO(user));
    }

    @GetMapping("/protected")
    public ResponseEntity<String> protectedEndpoint(Authentication authentication) {
        return ResponseEntity.ok("Acesso permitido! Usuario: " + authentication);
    }
}
