package com.example.eventify.controllers;

import com.example.eventify.requests.RegisterRequest;
import com.example.eventify.services.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UsuarioService userService;

    public AuthController(UsuarioService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest registerRequest) {
        // Lógica para registrar el usuario
        if (userService.existsByUsername(registerRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body("Error: El nombre de usuario ya está en uso!");
        }

        if (userService.existsByEmail(registerRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body("Error: El correo electrónico ya está en uso!");
        }

        // Crear nuevo usuario
        userService.registerUser(registerRequest);

        return ResponseEntity.ok("Usuario registrado con éxito");
    }
}
