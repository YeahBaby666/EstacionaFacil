package utp.gestionparqueo.sistema_parqueo.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import utp.gestionparqueo.sistema_parqueo.model.entity.enums.EstadoEspacio;
import utp.gestionparqueo.sistema_parqueo.model.entity.enums.TipoVehiculo;

@Entity
@Table(name = "espacios")
@Data
@NoArgsConstructor
public class Espacio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "numero_espacio", nullable = false, unique = true)
    private String numeroEspacio; // Ej: "A-01"

    @Column(nullable = false)
    private int piso;

    // --- ¡CAMBIO CLAVE AQUÍ! ---
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false)
    private TipoVehiculo tipo;

    // --- ¡CAMBIO CLAVE AQUÍ! ---
    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private EstadoEspacio estado;
}

