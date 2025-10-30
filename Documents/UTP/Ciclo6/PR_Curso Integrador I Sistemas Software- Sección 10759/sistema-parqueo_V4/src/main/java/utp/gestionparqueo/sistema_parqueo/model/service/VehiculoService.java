package utp.gestionparqueo.sistema_parqueo.model.service;

import org.apache.commons.lang3.StringUtils; // <-- RÚBRICA: Apache Commons
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import utp.gestionparqueo.sistema_parqueo.model.entity.Usuario;
import utp.gestionparqueo.sistema_parqueo.model.entity.Vehiculo;

import utp.gestionparqueo.sistema_parqueo.model.repository.VehiculoRepository;

import java.util.List;


@Service
public class VehiculoService {
    @Autowired private VehiculoRepository vehiculoRepo;

    public List<Vehiculo> getVehiculosPorUsuario(Usuario usuario) {
        return vehiculoRepo.findByUsuario(usuario);
    }

    public Vehiculo guardarVehiculo(Vehiculo vehiculo, Usuario usuario) {
        // Validación usando Apache Commons Lang
        if (StringUtils.isBlank(vehiculo.getPlaca())) {
            throw new IllegalArgumentException("La placa no puede estar vacía");
        }
        vehiculo.setUsuario(usuario);
        return vehiculoRepo.save(vehiculo);
    }

    public void eliminarVehiculo(Long id, Usuario usuario) {
        Vehiculo v = vehiculoRepo.findById(id)
            .orElseThrow(() -> new RuntimeException("Vehículo no encontrado"));
        
        // Asegurarnos que el usuario solo borre SUS vehículos
        if (v.getUsuario().getIdUsuario().equals(usuario.getIdUsuario())) {
            vehiculoRepo.delete(v);
        } else {
            throw new SecurityException("Acceso no autorizado para eliminar este vehículo");
        }
    }
}
