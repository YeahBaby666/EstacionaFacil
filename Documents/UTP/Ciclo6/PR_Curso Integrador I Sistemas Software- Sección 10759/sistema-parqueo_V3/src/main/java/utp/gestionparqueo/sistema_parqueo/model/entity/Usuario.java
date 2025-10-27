package utp.gestionparqueo.sistema_parqueo.model.entity;

import java.util.List;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "usuarios") // Buen hábito: nombrar tablas en plural
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;
    
    // Un usuario puede tener muchos estacionamientos
    @OneToMany(mappedBy = "propietario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Estacionamiento> estacionamientos;

}
