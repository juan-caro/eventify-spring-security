package com.example.eventify.services;

import com.example.eventify.models.Usuario;
import com.example.eventify.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Busca el usuario en la base de datos
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con el nombre de usuario: " + username));

        // Mapea los roles del usuario en GrantedAuthority
        Set<SimpleGrantedAuthority> authorities = usuario.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role)) // Agrega el prefijo 'ROLE_' a cada rol
                .collect(Collectors.toSet());

        // Retorna un UserDetails con la lista de roles
        return new org.springframework.security.core.userdetails.User(
                usuario.getUsername(),
                usuario.getPassword(),
                authorities // Pasa las autoridades obtenidas de los roles
        );
    }
}