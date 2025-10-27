package utp.gestionparqueo.sistema_parqueo.model.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import utp.gestionparqueo.sistema_parqueo.model.entity.Usuario;
import utp.gestionparqueo.sistema_parqueo.model.entity.enums.RolUsuario;
import utp.gestionparqueo.sistema_parqueo.model.repository.UsuarioRepository;

/**
 * Servicio de Usuario REFACTORIZADO.
 * Contiene la lógica de negocio para el registro.
 */
@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    // Inyectamos las dependencias por constructor
    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Método de registro llamado por AuthController.
     * Recibe el objeto Usuario directamente del formulario (Thymeleaf).
     */
    public void registrar(Usuario usuario) throws Exception {

        // 1. Verificamos si el email ya existe
        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            throw new Exception("El email ya está registrado");
        }

        // 2. Encriptamos la contraseña
        // El campo 'passwordHash' de la entidad Usuario recibe la contraseña en texto plano
        // del formulario, así que la leemos de ahí y la encriptamos.
        String passwordPlano = usuario.getPasswordHash();
        String passwordHash = passwordEncoder.encode(passwordPlano);

        // 3. Establecemos los valores por defecto antes de guardar
        usuario.setPasswordHash(passwordHash); // Guardamos la contraseña YA encriptada
        usuario.setRol(RolUsuario.ROLE_CLIENTE); // Todos los registros son Clientes

        // 4. Guardamos el usuario en la base de datos
        usuarioRepository.save(usuario);
    }
}

