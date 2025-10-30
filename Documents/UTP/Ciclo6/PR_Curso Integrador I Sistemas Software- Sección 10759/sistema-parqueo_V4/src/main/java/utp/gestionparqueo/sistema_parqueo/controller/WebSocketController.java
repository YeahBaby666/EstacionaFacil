package utp.gestionparqueo.sistema_parqueo.controller;

import utp.gestionparqueo.sistema_parqueo.model.dto.WebSocketMessage;
import utp.gestionparqueo.sistema_parqueo.model.entity.Usuario;
import utp.gestionparqueo.sistema_parqueo.model.repository.UsuarioRepository;
import utp.gestionparqueo.sistema_parqueo.model.service.ReservaService;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
// import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
// import utp.gestionparqueo.sistema_parqueo.model.service.ReservaService;



@Controller
public class WebSocketController {
    @Autowired private ReservaService reservaService;
    @Autowired private UsuarioRepository usuarioRepo;

    @MessageMapping("/reservar")
    public void reservarEspacio(@Payload WebSocketMessage message, Principal principal) {
        Usuario usuario = usuarioRepo.findByCorreo(principal.getName()).orElseThrow();
        reservaService.iniciarReserva(message.getIdEspacio(), message.getIdVehiculo(), usuario);
    }

    @MessageMapping("/estacionar")
    public void estacionarEspacio(@Payload WebSocketMessage message, Principal principal) {
        Usuario usuario = usuarioRepo.findByCorreo(principal.getName()).orElseThrow();
        // Asumimos que el ID del espacio es suficiente para encontrar la reserva PENDIENTE
        reservaService.confirmarEstacionamiento(message.getIdEspacio(), usuario);
    }
}
