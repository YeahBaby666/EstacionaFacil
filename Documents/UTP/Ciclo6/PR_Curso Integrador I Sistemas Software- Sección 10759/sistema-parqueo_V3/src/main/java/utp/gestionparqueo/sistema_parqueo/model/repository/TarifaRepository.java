package utp.gestionparqueo.sistema_parqueo.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import utp.gestionparqueo.sistema_parqueo.model.entity.Tarifa;

public interface TarifaRepository extends JpaRepository<Tarifa, Long> {
    // Métodos personalizados aquí
}
