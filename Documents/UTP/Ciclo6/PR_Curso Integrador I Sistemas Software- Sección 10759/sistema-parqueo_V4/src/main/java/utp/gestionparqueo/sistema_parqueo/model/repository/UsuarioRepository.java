package utp.gestionparqueo.sistema_parqueo.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import utp.gestionparqueo.sistema_parqueo.model.entity.Usuario;

import java.util.Optional;

/**
 * Repositorio que actúa como DAO (Data Access Object) para la entidad Usuario.
 * Extiende JpaRepository para heredar operaciones CRUD básicas y proporciona
 * métodos específicos para el acceso a datos de Usuario.
 *
 * Principios SOLID aplicados:
 * - Single Responsibility (S): Maneja únicamente el acceso a datos de usuarios
 * - Open/Closed (O): Extiende JpaRepository sin modificarlo
 * - Interface Segregation (I): Define solo los métodos específicos necesarios
 * - Dependency Inversion (D): Es una interfaz de la que dependen los servicios
 */
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByCorreo(String correo);

    boolean existsByCorreo(String string);
}
