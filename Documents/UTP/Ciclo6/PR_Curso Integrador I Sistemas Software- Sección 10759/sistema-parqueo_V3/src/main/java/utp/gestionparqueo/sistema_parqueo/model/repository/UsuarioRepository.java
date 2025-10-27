package utp.gestionparqueo.sistema_parqueo.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import utp.gestionparqueo.sistema_parqueo.model.entity.Usuario;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByUsername(String username);

    boolean existsByEmail(String email);

    Optional<Usuario> findByEmail(String email);
}
