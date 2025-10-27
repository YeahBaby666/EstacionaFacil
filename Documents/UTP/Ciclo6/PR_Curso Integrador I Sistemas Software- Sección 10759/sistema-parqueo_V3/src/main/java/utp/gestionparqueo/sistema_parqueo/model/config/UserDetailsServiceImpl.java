package utp.gestionparqueo.sistema_parqueo.model.config;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import utp.gestionparqueo.sistema_parqueo.model.entity.Usuario;
import utp.gestionparqueo.sistema_parqueo.model.repository.UsuarioRepository;

import java.util.HashSet;
import java.util.Set;

/**
 * Servicio "para el security" (UserDetailsService).
 * Spring Security lo usa automáticamente durante el login para
 * buscar al usuario por su email (que Spring llama 'username').
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public UserDetailsServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
       Usuario usuario = usuarioRepository.findByEmail(email)
    .orElseThrow(() -> new UsernameNotFoundException("No se encontró usuario con el email: " + email));

        // Creamos la lista de "roles" (autoridades)
        Set<GrantedAuthority> authorities = new HashSet<>();
        
        // Spring Security necesita saber el rol, aunque no lo usemos para redirigir
        authorities.add(new SimpleGrantedAuthority(usuario.getRol().name()));

        // Devolvemos el objeto User que Spring Security entiende
        return new User(
                usuario.getEmail(),           // El 'username' es el email
                usuario.getPasswordHash(),    // La contraseña encriptada
                authorities                   // La lista de roles
        );
    }
}

