package utp.gestionparqueo.sistema_parqueo.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import utp.gestionparqueo.sistema_parqueo.model.entity.enums.EstadoEspacio;

@Data
@AllArgsConstructor
public class EstadoParqueoDTO {
    // Mensaje SALIENTE hacia el cliente JS (actualización en tiempo real)
    private Long idEspacio;
    private EstadoEspacio nuevoEstado;
    private String ubicacion; // ej. "A-01"
    private String placaVehiculo; // Placa del vehículo que ocupó el espacio
}
