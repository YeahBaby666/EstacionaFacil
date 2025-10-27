package utp.gestionparqueo.sistema_parqueo.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Nueva entidad para almacenar los múltiples vehículos (placas)
 * de un solo usuario.
 */
@Entity
@Table(name = "vehiculos", uniqueConstraints = {
    // Un usuario no puede tener la misma placa dos veces
    @UniqueConstraint(columnNames = {"id_usuario", "placa"})
})
@Data
@NoArgsConstructor
public class Vehiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String placa;

    @Column
    private String alias; // Ej: "Mi Coche", "Moto"

    // --- RELACIÓN INVERSA ---
    // Muchos vehículos pertenecen a Un usuario.
    // Esta es la columna que une las tablas.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;
}
