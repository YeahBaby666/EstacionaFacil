package utp.gestionparqueo.sistema_parqueo.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import utp.gestionparqueo.sistema_parqueo.model.entity.enums.RolUsuario;



@Data @Entity @Table(name = "usuario")
public class Usuario {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUsuario;
    private String nombre;
    @Column(unique = true) private String correo;
    private String password;
    @Enumerated(EnumType.STRING) private RolUsuario rol;
}
