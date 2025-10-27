package utp.gestionparqueo.sistema_parqueo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
        model.addAttribute("usuario", new Usuario());
        return "register";
    }

    @PostMapping("/register")
    public String processRegister(Usuario usuario) {
        usuarioService.registrar(usuario);
        return "redirect:/login?register_success";
    }
}
