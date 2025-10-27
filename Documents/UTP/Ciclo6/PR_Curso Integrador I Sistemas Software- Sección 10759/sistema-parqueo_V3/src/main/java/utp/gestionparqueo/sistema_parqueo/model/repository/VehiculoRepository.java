package utp.gestionparqueo.sistema_parqueo.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import utp.gestionparqueo.sistema_parqueo.model.entity.Vehiculo;

import java.util.List;

/**
 * Nuevo repositorio para la entidad Vehiculo.
 */
@Repository
public interface VehiculoRepository extends JpaRepository<Vehiculo, Long> {

    // Útil para el futuro: buscar todos los vehículos de un usuario
    List<Vehiculo> findByUsuarioId(Long usuarioId);
}
