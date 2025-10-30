package utp.gestionparqueo.sistema_parqueo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import utp.gestionparqueo.sistema_parqueo.model.dto.UsuarioRegistroDTO;
import utp.gestionparqueo.sistema_parqueo.model.service.UsuarioService;


@Controller
public class AuthController {
    @Autowired private UsuarioService usuarioService;

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("usuario", new UsuarioRegistroDTO());
        return "register";
    }

    @PostMapping("/register")
    public String registerUserAccount(@ModelAttribute("usuario") UsuarioRegistroDTO registroDTO, RedirectAttributes redirectAttributes) {
        try {
            usuarioService.registrarNuevoCliente(registroDTO);
            redirectAttributes.addFlashAttribute("success", "¡Registro exitoso! Por favor, inicia sesión.");
            return "redirect:/login";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/register";
        }
    }
}

