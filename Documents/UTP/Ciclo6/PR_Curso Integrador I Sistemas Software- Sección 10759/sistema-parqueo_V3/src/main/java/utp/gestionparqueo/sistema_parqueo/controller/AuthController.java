package utp.gestionparqueo.sistema_parqueo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import utp.gestionparqueo.sistema_parqueo.model.entity.Usuario;
import utp.gestionparqueo.sistema_parqueo.model.service.UsuarioService;


@Controller
public class AuthController {
    private final UsuarioService usuarioService;
    public AuthController(UsuarioService uS) { this.usuarioService = uS; }

    @GetMapping("/login")
    public String viewLoginPage() { return "login"; }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        // Preparamos un objeto Usuario vacío para el formulario (th:object)
        model.addAttribute("usuario", new Usuario());
        return "register";
    }

    /**
     * Procesar el registro.
     * Actualizado para manejar errores (ej: email duplicado)
     * y mostrar mensajes al usuario.
     */
    @PostMapping("/register")
    public String processRegister(Usuario usuario, RedirectAttributes redirectAttributes) {
        try {
            // Intentamos registrar al usuario
            usuarioService.registrar(usuario);
            
            // Si tiene éxito, mandamos un mensaje al login
            redirectAttributes.addFlashAttribute("successMessage", "¡Registro exitoso! Por favor, inicia sesión.");
            return "redirect:/login?register_success";

        } catch (Exception e) {
            // Si falla (ej: email duplicado), volvemos al registro con un error
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/register";
        }
    }
}

