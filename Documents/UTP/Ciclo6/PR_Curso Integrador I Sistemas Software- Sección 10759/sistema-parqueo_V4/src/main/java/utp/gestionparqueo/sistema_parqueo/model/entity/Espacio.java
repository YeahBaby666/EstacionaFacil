package utp.gestionparqueo.sistema_parqueo.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import utp.gestionparqueo.sistema_parqueo.model.entity.enums.EstadoEspacio;

@Data @Entity
public class Espacio {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEspacio;
    @Column(unique = true) // Añadido: La ubicación debe ser única
    private String ubicacion;
    @Enumerated(EnumType.STRING)
    private EstadoEspacio estado;
}

