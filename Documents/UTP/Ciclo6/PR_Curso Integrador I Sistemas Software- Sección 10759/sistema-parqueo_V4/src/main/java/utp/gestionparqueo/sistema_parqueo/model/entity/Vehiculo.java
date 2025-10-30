package utp.gestionparqueo.sistema_parqueo.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data @Entity
public class Vehiculo {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idVehiculo;
    private String placa;
    private String modelo;
    private String color;
    @ManyToOne @JoinColumn(name = "id_usuario")
    private Usuario usuario;
}