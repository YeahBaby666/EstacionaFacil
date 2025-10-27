package utp.gestionparqueo.sistema_parqueo.model.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import utp.gestionparqueo.sistema_parqueo.model.entity.Usuario;
import utp.gestionparqueo.sistema_parqueo.model.repository.UsuarioRepository;


@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository uR, PasswordEncoder pE) {
        this.usuarioRepository = uR;
        this.passwordEncoder = pE;
    }

    public Usuario registrar(Usuario usuario) {
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        return usuarioRepository.save(usuario);
    }
}
