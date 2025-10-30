package utp.gestionparqueo.sistema_parqueo.model.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import utp.gestionparqueo.sistema_parqueo.model.repository.UsuarioRepository;
import utp.gestionparqueo.sistema_parqueo.model.entity.Usuario;


/**
 * Servicio "para el security" (UserDetailsService).
 * Spring Security lo usa automáticamente durante el login para
 * buscar al usuario por su email (que Spring llama 'username').
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByCorreo(correo)
            .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
        
        return User.builder()
            .username(usuario.getCorreo())
            .password(usuario.getPassword())
            .roles(usuario.getRol().name())
            .build();
    }
}


