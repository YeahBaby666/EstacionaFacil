package utp.gestionparqueo.sistema_parqueo.model.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import utp.gestionparqueo.sistema_parqueo.model.entity.Usuario;
import utp.gestionparqueo.sistema_parqueo.model.entity.enums.RolUsuario;
import utp.gestionparqueo.sistema_parqueo.model.repository.UsuarioRepository;

@Component
public class InitialDataLoader implements CommandLineRunner {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        // Verificar si el usuario admin ya existe
        if (!usuarioRepository.existsByCorreo("admin@estacionafacil.com")) {
            // Crear usuario admin
            Usuario admin = new Usuario();
            admin.setNombre("Administrador");
            admin.setCorreo("admin@estacionafacil.com");
            admin.setPassword(passwordEncoder.encode("admin")); // La contraseña será "admin"
            admin.setRol(RolUsuario.ADMIN);
            
            usuarioRepository.save(admin);
        }
    }
}