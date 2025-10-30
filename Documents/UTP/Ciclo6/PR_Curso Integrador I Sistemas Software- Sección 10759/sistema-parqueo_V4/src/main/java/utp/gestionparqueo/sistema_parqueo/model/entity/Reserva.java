package utp.gestionparqueo.sistema_parqueo.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import utp.gestionparqueo.sistema_parqueo.model.entity.enums.EstadoReserva;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Data @Entity
public class Reserva {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idReserva;
    
    @ManyToOne @JoinColumn(name = "id_usuario")
    private Usuario usuario;
    
    @ManyToOne @JoinColumn(name = "id_vehiculo")
    private Vehiculo vehiculo;
    
    @ManyToOne @JoinColumn(name = "id_espacio")
    private Espacio espacio;
    
    private LocalDateTime horaInicioReserva; // Cuando apretó "Reservar"
    private LocalDateTime horaInicio;      // Cuando apretó "Estacionar"
    private LocalDateTime horaFin;
    
    @Enumerated(EnumType.STRING)
    private EstadoReserva estado;
    
    private BigDecimal costoTotal;
}