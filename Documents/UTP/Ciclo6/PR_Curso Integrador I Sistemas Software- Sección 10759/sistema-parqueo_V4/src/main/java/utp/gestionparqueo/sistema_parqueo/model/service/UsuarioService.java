package utp.gestionparqueo.sistema_parqueo.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import utp.gestionparqueo.sistema_parqueo.model.repository.UsuarioRepository;
import utp.gestionparqueo.sistema_parqueo.model.dto.UsuarioRegistroDTO;
import utp.gestionparqueo.sistema_parqueo.model.entity.Usuario;
import utp.gestionparqueo.sistema_parqueo.model.entity.enums.RolUsuario;

/**
 * Servicio de Usuario REFACTORIZADO.
 * Contiene la lógica de negocio para el registro.
 *
 * Principios SOLID aplicados:
 * - Single Responsibility (S): Esta clase maneja únicamente la lógica relacionada con usuarios
 * - Open/Closed (O): Se puede extender sin modificar (por ejemplo, para añadir validaciones)
 * - Liskov Substitution (L): Implementación consistente con el comportamiento esperado
 * - Interface Segregation (I): Usa solo los métodos necesarios del repository
 * - Dependency Inversion (D): Depende de abstracciones (UsuarioRepository, PasswordEncoder)
 */
@Service
public class UsuarioService {
    @Autowired private UsuarioRepository usuarioRepo;
    @Autowired private PasswordEncoder passwordEncoder;

    public Usuario registrarNuevoCliente(UsuarioRegistroDTO registroDTO) {
        if (usuarioRepo.findByCorreo(registroDTO.getCorreo()).isPresent()) {
            throw new RuntimeException("El correo ya está registrado");
        }
        Usuario usuario = new Usuario();
        usuario.setNombre(registroDTO.getNombre());
        usuario.setCorreo(registroDTO.getCorreo());
        usuario.setPassword(passwordEncoder.encode(registroDTO.getPassword()));
        usuario.setRol(RolUsuario.CLIENTE); // Solo se pueden registrar CLIENTES
        return usuarioRepo.save(usuario);
    }
}

