package utp.gestionparqueo.sistema_parqueo.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import utp.gestionparqueo.sistema_parqueo.model.entity.Espacio;

public interface EspacioRepository extends JpaRepository<Espacio, Long> {
    // Métodos personalizados aquí
}
