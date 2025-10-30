package utp.gestionparqueo.sistema_parqueo.model.dto;

import lombok.Data;

@Data
public class WebSocketMessage {
    // Mensaje ENTRANTE desde el cliente JS (al reservar/estacionar)
    private Long idEspacio;
    private Long idVehiculo; // Necesario para saber qué vehículo estacionar
}
