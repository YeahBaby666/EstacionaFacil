package utp.gestionparqueo.sistema_parqueo.controller;

import org.springframework.messaging.handler.annotation.*;
import org.springframework.stereotype.Controller;

@Controller
public class TestController {

    @MessageMapping("/test.sendMessage")
    @SendTo("/topic/public")
    public String sendMessage(String message) {
        return "Mensaje recibido: " + message;
    }
}
