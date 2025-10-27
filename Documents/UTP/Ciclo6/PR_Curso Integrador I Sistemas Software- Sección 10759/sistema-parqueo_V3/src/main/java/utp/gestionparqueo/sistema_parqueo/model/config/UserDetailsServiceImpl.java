package utp.gestionparqueo.sistema_parqueo.model.config;

import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import utp.gestionparqueo.sistema_parqueo.model.entity.Usuario;
import utp.gestionparqueo.sistema_parqueo.model.repository.UsuarioRepository;

import java.util.Collections;

@Service
public class UserDetailsServiceImpl implements UserDetailsService { // Viene de Spring Security
    private final UsuarioRepository repo;
    public UserDetailsServiceImpl(UsuarioRepository repo) { this.repo = repo;}

    @Override
    public UserDetails loadUserByUsername(String username) {
        Usuario u = repo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("No hallado"));
        return new org.springframework.security.core.userdetails.User(
                u.getUsername(), u.getPassword(), Collections.emptyList());
    }
}
