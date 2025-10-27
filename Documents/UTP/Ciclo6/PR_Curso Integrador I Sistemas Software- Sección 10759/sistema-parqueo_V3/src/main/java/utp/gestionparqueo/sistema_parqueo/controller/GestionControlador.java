package utp.gestionparqueo.sistema_parqueo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import utp.gestionparqueo.sistema_parqueo.model.entity.Estacionamiento;
import utp.gestionparqueo.sistema_parqueo.model.repository.EstacionamientoRepositorio;

@Controller
@RequestMapping("/gestion")
public class GestionControlador {

    @Autowired
    private EstacionamientoRepositorio estacionamientoRepositorio;

    @GetMapping("/{id}")
    public String mostrarPaginaGestion(@PathVariable Long id, Model model) {
        // 1. Buscamos el estacionamiento por su ID.
        Estacionamiento estacionamiento = estacionamientoRepositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Estacionamiento no encontrado"));
        
        // 2. Lo añadimos al modelo para que la vista (HTML) pueda usar sus datos.
        model.addAttribute("estacionamiento", estacionamiento);

        // 3. Devolvemos el nombre del archivo HTML que se debe renderizar.
        return "gestion";
    }
}

