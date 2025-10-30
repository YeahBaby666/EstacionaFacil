package utp.gestionparqueo.sistema_parqueo.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import utp.gestionparqueo.sistema_parqueo.model.entity.enums.TipoTarifa;

import java.math.BigDecimal;

@Data @Entity
public class Tarifa {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTarifa;
    private String descripcion;
    private BigDecimal monto;
    @Enumerated(EnumType.STRING)
    private TipoTarifa tipo;
}

