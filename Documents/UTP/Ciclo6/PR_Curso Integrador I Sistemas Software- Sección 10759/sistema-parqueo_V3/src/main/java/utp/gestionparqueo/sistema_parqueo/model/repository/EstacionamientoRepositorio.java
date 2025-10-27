package utp.gestionparqueo.sistema_parqueo.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import utp.gestionparqueo.sistema_parqueo.model.entity.Estacionamiento;

@Repository
public interface EstacionamientoRepositorio extends JpaRepository<Estacionamiento, Long> {

    // Devuelve todos los estacionamientos que coincidan con el nombre.
    List<Estacionamiento> findAllByNombre(String nombre);

}
