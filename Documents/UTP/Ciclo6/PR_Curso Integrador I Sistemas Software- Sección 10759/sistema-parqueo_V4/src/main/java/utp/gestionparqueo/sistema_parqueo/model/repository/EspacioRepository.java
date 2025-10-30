package utp.gestionparqueo.sistema_parqueo.model.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import utp.gestionparqueo.sistema_parqueo.model.entity.Espacio;
import utp.gestionparqueo.sistema_parqueo.model.entity.enums.EstadoEspacio;


@Repository
public interface EspacioRepository extends JpaRepository<Espacio, Long> {
    // NUEVO: Para buscar por nombre de ubicación (A-01)
    Optional<Espacio> findByUbicacion(String ubicacion);
    
    // NUEVO: Para el dropdown del formulario
    List<Espacio> findByEstado(EstadoEspacio estado);
}


