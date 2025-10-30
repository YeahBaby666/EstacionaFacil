package utp.gestionparqueo.sistema_parqueo.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import utp.gestionparqueo.sistema_parqueo.model.entity.Vehiculo;
import utp.gestionparqueo.sistema_parqueo.model.entity.Usuario;

import java.util.List;

@Repository
public interface VehiculoRepository extends JpaRepository<Vehiculo, Long> {
    List<Vehiculo> findByUsuario(Usuario usuario);
}
