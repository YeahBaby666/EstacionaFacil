package utp.gestionparqueo.sistema_parqueo.model.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import utp.gestionparqueo.sistema_parqueo.model.entity.Estacionamiento;
import utp.gestionparqueo.sistema_parqueo.model.entity.Usuario;
import utp.gestionparqueo.sistema_parqueo.model.repository.EstacionamientoRepositorio;

@Service
public class EstacionamientoServicio {

    @Autowired
    private EstacionamientoRepositorio estacionamientoRepositorio;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    public Optional<Estacionamiento> crearEstacionamiento(String nombre, String codigoAcceso, Usuario propietario) {
        if (this.buscarPorCodigoAcceso(codigoAcceso).isPresent()) {
            return Optional.empty(); // Código ya en uso
        }
        Estacionamiento nuevo = new Estacionamiento();
        nuevo.setNombre(nombre);
        nuevo.setPropietario(propietario);
        nuevo.setCodigoAcceso(passwordEncoder.encode(codigoAcceso));
        return Optional.of(estacionamientoRepositorio.save(nuevo));
    }
    
    public Optional<Estacionamiento> validarYObtenerEstacionamiento(String nombre, String codigoAccesoClaro) {
        List<Estacionamiento> candidatos = estacionamientoRepositorio.findAllByNombre(nombre);
        for (Estacionamiento candidato : candidatos) {
            if (passwordEncoder.matches(codigoAccesoClaro, candidato.getCodigoAcceso())) {
                return Optional.of(candidato);
            }
        }
        return Optional.empty();
    }
    
    // Método preparado para uso futuro
    public Optional<Estacionamiento> buscarPorCodigoAcceso(String codigoAccesoClaro) {
        List<Estacionamiento> todos = estacionamientoRepositorio.findAll();
        for (Estacionamiento existente : todos) {
            if (passwordEncoder.matches(codigoAccesoClaro, existente.getCodigoAcceso())) {
                return Optional.of(existente);
            }
        }
        return Optional.empty();
    }
}
