package com.example.eventify.services;

import com.example.eventify.models.Usuario;
import com.example.eventify.repository.UsuarioRepository;
import com.example.eventify.requests.RegisterRequest;
import com.example.eventify.security.jwt.JwtTokenUtil;
import lombok.AllArgsConstructor;
import org.antlr.v4.runtime.Token;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UsuarioService {
    private final UsuarioRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil tokenUtil;

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

    // Lógica de autenticación de usuario
    public boolean authenticateUser(String username, String password) {
        // Buscar el usuario por nombre de usuario
        Usuario usuario = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con el nombre de usuario: " + username));

        System.out.println("USERNAME usuarioOPT: " + usuario.getUsername());

        // Verificar la contraseña usando el PasswordEncoder
        return passwordEncoder.matches(password, usuario.getPassword());
    }

    // Lógica para generar el token JWT
    public String generateToken(String username) {

        return tokenUtil.generateToken(username); // Placeholder para el token
    }
}
