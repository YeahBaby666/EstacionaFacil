package utp.gestionparqueo.sistema_parqueo.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import utp.gestionparqueo.sistema_parqueo.model.entity.Reserva;
import utp.gestionparqueo.sistema_parqueo.model.entity.Usuario;
import utp.gestionparqueo.sistema_parqueo.model.entity.enums.EstadoReserva;
import java.util.List;
import java.util.Optional;
import java.time.LocalDateTime;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {
    // MODIFICADO: Devuelve una Lista para evitar NonUniqueResultException
    List<Reserva> findByUsuarioAndEstadoIn(Usuario usuario, List<EstadoReserva> estados);
    
    Optional<Reserva> findByIdReservaAndUsuario(Long idReserva, Usuario usuario);

    // Para el reporte de pagos del Admin
    List<Reserva> findByEstadoInAndHoraFinBetween(
        List<EstadoReserva> estados, 
        LocalDateTime inicio, 
        LocalDateTime fin
    );
}
