package utp.gestionparqueo.sistema_parqueo.model.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "estacionamientos")
public class Estacionamiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    // El código se guarda encriptado. La unicidad
    // se valida en la capa de servicio.
    @Column(nullable = false, unique = true)
    private String codigoAcceso;

    @Column(nullable = false)
    private Integer capacidadTotal = 0;

    // Muchos estacionamientos pertenecen a un usuario
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "propietario_id", nullable = false)
    private Usuario propietario;
}
