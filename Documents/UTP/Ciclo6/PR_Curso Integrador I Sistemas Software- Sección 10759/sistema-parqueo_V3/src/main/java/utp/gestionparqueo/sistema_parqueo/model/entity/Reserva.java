package utp.gestionparqueo.sistema_parqueo.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import utp.gestionparqueo.sistema_parqueo.model.entity.enums.EstadoReserva;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Table(name = "reservas")
@Data
@NoArgsConstructor
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_espacio", nullable = false)
    private Espacio espacio;
    
    // --- ¡CAMBIO CLAVE! ---
    // Guardamos como texto la placa que el usuario seleccionó
    // de su lista de vehículos al momento de reservar.
    @Column(name = "placa_usada", nullable = false)
    private String placaUsada;

    @Column(name = "hora_entrada", nullable = false, updatable = false)
    private OffsetDateTime horaEntrada;

    @Column(name = "hora_salida")
    private OffsetDateTime horaSalida;

    @Column(name = "costo_total")
    private BigDecimal costoTotal;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private EstadoReserva estado;

    @PrePersist
    protected void onCreate() {
        horaEntrada = OffsetDateTime.now();
        // El estado (ej: ACTIVA) y la placaUsada se deben asignar en el servicio
        // antes de guardar (persistir).
    }
}

