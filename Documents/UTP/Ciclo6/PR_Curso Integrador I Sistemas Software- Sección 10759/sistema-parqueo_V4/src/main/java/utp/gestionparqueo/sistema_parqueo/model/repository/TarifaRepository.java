package utp.gestionparqueo.sistema_parqueo.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import utp.gestionparqueo.sistema_parqueo.model.entity.Tarifa;
import utp.gestionparqueo.sistema_parqueo.model.entity.enums.TipoTarifa;

import java.util.List;
public interface TarifaRepository extends JpaRepository<Tarifa, Long> {
    // MODIFICADO: Devuelve una Lista para evitar NonUniqueResultException
    List<Tarifa> findByTipo(TipoTarifa tipo);
}

