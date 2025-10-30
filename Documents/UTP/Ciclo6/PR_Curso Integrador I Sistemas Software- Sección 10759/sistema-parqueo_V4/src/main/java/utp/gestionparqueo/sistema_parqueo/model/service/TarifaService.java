// --- TarifaService.java ---
package utp.gestionparqueo.sistema_parqueo.model.service;
import utp.gestionparqueo.sistema_parqueo.model.entity.Tarifa;
import utp.gestionparqueo.sistema_parqueo.model.entity.enums.TipoTarifa;
import utp.gestionparqueo.sistema_parqueo.model.repository.TarifaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TarifaService {
    @Autowired private TarifaRepository tarifaRepo;

    public Tarifa getTarifa(TipoTarifa tipo) {
        // MODIFICADO: Devolver la ÚLTIMA tarifa de ese tipo (la más reciente)
        List<Tarifa> tarifas = tarifaRepo.findByTipo(tipo);
        if (tarifas.isEmpty()) {
            // Si no hay tarifa, el ReservaService usará CERO.
            return null;
        }
        return tarifas.get(tarifas.size() - 1);
    }

    public List<Tarifa> getAllTarifas() {
        return tarifaRepo.findAll();
    }
}