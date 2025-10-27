package utp.gestionparqueo.sistema_parqueo.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import utp.gestionparqueo.sistema_parqueo.model.entity.Reserva;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {
    // Métodos personalizados aquí
}
