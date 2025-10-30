package utp.gestionparqueo.sistema_parqueo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import utp.gestionparqueo.sistema_parqueo.model.entity.Reserva;
import utp.gestionparqueo.sistema_parqueo.model.entity.Usuario;
import utp.gestionparqueo.sistema_parqueo.model.entity.Vehiculo;
import utp.gestionparqueo.sistema_parqueo.model.repository.EspacioRepository;
import utp.gestionparqueo.sistema_parqueo.model.repository.ReservaRepository;
import utp.gestionparqueo.sistema_parqueo.model.repository.UsuarioRepository;
import utp.gestionparqueo.sistema_parqueo.model.service.ReservaService;
import utp.gestionparqueo.sistema_parqueo.model.service.VehiculoService;


@Controller
public class AppController {
    @Autowired private EspacioRepository espacioRepo;
    @Autowired private UsuarioRepository usuarioRepo;
    @Autowired private VehiculoService vehiculoService;
    @Autowired private ReservaService reservaService;
    
    private Usuario getUsuarioActual(UserDetails userDetails) {
        return usuarioRepo.findByCorreo(userDetails.getUsername()).orElseThrow();
    }

    @GetMapping("/")
    public String index(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        Usuario usuario = getUsuarioActual(userDetails);
        
        model.addAttribute("espacios", espacioRepo.findAll());
        model.addAttribute("reservaActiva", reservaService.findReservaActiva(usuario));
        model.addAttribute("vehiculos", vehiculoService.getVehiculosPorUsuario(usuario));
        model.addAttribute("espaciosDisponibles", espacioRepo.findByEstado(utp.gestionparqueo.sistema_parqueo.model.entity.enums.EstadoEspacio.DISPONIBLE).size());
        
        return "index";
    }

    @GetMapping("/vehiculos")
    public String gestionVehiculos(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        Usuario usuario = getUsuarioActual(userDetails);
        model.addAttribute("vehiculos", vehiculoService.getVehiculosPorUsuario(usuario));
        model.addAttribute("vehiculo", new Vehiculo()); // Para el formulario de "Añadir"
        return "gestion-vehiculos";
    }

    @PostMapping("/vehiculos/guardar")
    public String guardarVehiculo(@ModelAttribute Vehiculo vehiculo, @AuthenticationPrincipal UserDetails userDetails) {
        Usuario usuario = getUsuarioActual(userDetails);
        vehiculoService.guardarVehiculo(vehiculo, usuario);
        return "redirect:/vehiculos";
    }

    @PostMapping("/vehiculos/eliminar")
    public String eliminarVehiculo(@RequestParam Long id, @AuthenticationPrincipal UserDetails userDetails) {
        Usuario usuario = getUsuarioActual(userDetails);
        vehiculoService.eliminarVehiculo(id, usuario);
        return "redirect:/vehiculos";
    }
    
    @PostMapping("/salir")
    public String salirYGenerarTicket(@RequestParam Long idReserva, @AuthenticationPrincipal UserDetails userDetails, RedirectAttributes redirectAttributes) {
        Usuario usuario = getUsuarioActual(userDetails);
        try {
            Reserva reservaPagada = reservaService.finalizarEstacionamiento(idReserva, usuario);
            redirectAttributes.addFlashAttribute("idReservaPagada", reservaPagada.getIdReserva());
            return "redirect:/ticket";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/";
        }
    }
    
    @Autowired private ReservaRepository reservaRepo; // Add this line

    @GetMapping("/ticket")
    public String mostrarTicket(@ModelAttribute("idReservaPagada") Long idReservaPagada, Model model) {
        if (idReservaPagada == null || idReservaPagada == 0) {
            return "redirect:/"; // No hay ticket que mostrar
        }
        Reserva reserva = reservaRepo.findById(idReservaPagada).orElseThrow();
        
        model.addAttribute("reserva", reserva);
        model.addAttribute("desglose", reservaService.calcularDesgloseCosto(reserva));
        return "ticket";
    }
}
