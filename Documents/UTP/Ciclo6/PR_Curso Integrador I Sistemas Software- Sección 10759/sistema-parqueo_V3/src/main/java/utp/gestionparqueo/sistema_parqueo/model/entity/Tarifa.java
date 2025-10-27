package utp.gestionparqueo.sistema_parqueo.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import utp.gestionparqueo.sistema_parqueo.model.entity.enums.TipoVehiculo;

import java.math.BigDecimal;

@Entity
@Table(name = "tarifas")
@Data
@NoArgsConstructor
public class Tarifa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // --- ¡CAMBIO CLAVE AQUÍ! ---
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false)
    private TipoVehiculo tipo;

    @Column(name = "costo_por_hora", nullable = false)
    private BigDecimal costoPorHora;

    @Column(nullable = false)
    private boolean activa;
}

