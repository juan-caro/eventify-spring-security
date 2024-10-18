package com.example.eventify.services;

import com.example.eventify.models.Usuario;
import com.example.eventify.repository.UsuarioRepository;
import com.example.eventify.requests.RegisterRequest;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UsuarioService {
    private final UsuarioRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public void registerUser(RegisterRequest registerRequest) {
        // Crear el nuevo usuario y codificar la contraseña
        Usuario user = new Usuario();
        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));

        // Asignar roles y guardar el usuario
        user.setRoles(registerRequest.getRoles()); // Esto depende de cómo manejes los roles
        userRepository.save(user);
    }
}
