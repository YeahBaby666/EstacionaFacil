package utp.gestionparqueo.sistema_parqueo.model.dto;

import lombok.Data;

@Data
public class UsuarioRegistroDTO {
    private String nombre;
    private String correo;
    private String password;
}
