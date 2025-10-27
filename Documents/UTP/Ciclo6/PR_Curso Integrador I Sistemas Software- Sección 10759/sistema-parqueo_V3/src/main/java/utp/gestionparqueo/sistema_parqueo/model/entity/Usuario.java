package utp.gestionparqueo.sistema_parqueo.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import utp.gestionparqueo.sistema_parqueo.model.entity.enums.RolUsuario;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List; // Importamos List

@Entity
@Table(name = "usuarios")
@Data
@NoArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String apellido;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    // --- ¡CAMPO ELIMINADO! ---
    // Ya no guardamos 'placaVehiculo' aquí.

    @Enumerated(EnumType.STRING)
    @Column(name = "rol", nullable = false)
    private RolUsuario rol;

    @Column(name = "fecha_creacion", updatable = false)
    @CreationTimestamp
    private OffsetDateTime fechaCreacion;

    // --- ¡NUEVA RELACIÓN! ---
    // Un usuario puede tener muchos vehículos.
    // 'mappedBy = "usuario"' se conecta con el campo 'usuario' en la entidad Vehiculo.
    // CascadeType.ALL: Si se borra un usuario, se borran sus vehículos.
    // orphanRemoval = true: Si quitas un vehículo de esta lista, se borra de la BD.
    @OneToMany(
            mappedBy = "usuario",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY // Carga perezosa
    )
    private List<Vehiculo> vehiculos = new ArrayList<>();

    // También eliminamos las reservas de aquí para mantener la entidad limpia
    // La relación ya está en Reserva (Muchos a Uno)
}

