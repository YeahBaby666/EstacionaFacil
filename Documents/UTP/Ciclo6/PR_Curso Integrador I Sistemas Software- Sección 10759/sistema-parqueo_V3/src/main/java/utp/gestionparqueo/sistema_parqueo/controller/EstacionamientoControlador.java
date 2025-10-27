package utp.gestionparqueo.sistema_parqueo.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import utp.gestionparqueo.sistema_parqueo.model.entity.Estacionamiento;
import utp.gestionparqueo.sistema_parqueo.model.entity.Usuario;
import utp.gestionparqueo.sistema_parqueo.model.repository.UsuarioRepository;
import utp.gestionparqueo.sistema_parqueo.model.service.EstacionamientoServicio;

@Controller
@RequestMapping("/estacionamiento")
public class EstacionamientoControlador {
    
    @Autowired
    private EstacionamientoServicio estacionamientoServicio;
    
    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping("/crear")
    public String crear(@RequestParam String nombre,
                       @RequestParam String codigoAcceso,
                       @AuthenticationPrincipal UserDetails userDetails,
                       RedirectAttributes attributes) {
        
        Usuario propietario = usuarioRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Optional<Estacionamiento> optNuevo = estacionamientoServicio.crearEstacionamiento(nombre, codigoAcceso, propietario);

        if (optNuevo.isPresent()) {
            Estacionamiento nuevo = optNuevo.get();
            // Redirección directa a la página de gestión del nuevo estacionamiento.
            return "redirect:/gestion/" + nuevo.getId();
        } else {
            attributes.addFlashAttribute("errorCreacion", "Ese código de acceso ya está en uso. Por favor, elige otro.");
            return "redirect:/";
        }
    }
    
    @PostMapping("/buscar")
    public String buscar(@RequestParam String nombre, @RequestParam String codigoAcceso, RedirectAttributes attributes) {
        Optional<Estacionamiento> optEst = estacionamientoServicio.validarYObtenerEstacionamiento(nombre, codigoAcceso);

        if (optEst.isPresent()) {
            return "redirect:/gestion/" + optEst.get().getId();
        } else {
            attributes.addFlashAttribute("errorBusqueda", "Nombre o código de acceso incorrecto.");
            return "redirect:/";
        }
    }
}