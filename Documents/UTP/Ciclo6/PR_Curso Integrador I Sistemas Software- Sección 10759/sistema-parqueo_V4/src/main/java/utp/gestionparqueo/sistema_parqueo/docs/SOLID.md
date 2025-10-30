# Principios SOLID en el Proyecto

Este documento describe cómo se aplican los principios SOLID en el sistema de gestión de parqueo.

## Single Responsibility Principle (SRP)

Cada clase tiene una única responsabilidad:

### Servicios
- `UsuarioService`: Gestiona la lógica de usuarios y autenticación
- `ReservaService`: Maneja la lógica de reservas de estacionamiento
- `AdminService`: Gestiona funcionalidades administrativas

### Repositories
- `UsuarioRepository`: Acceso a datos de usuarios
- `VehiculoRepository`: Acceso a datos de vehículos
- `ReservaRepository`: Acceso a datos de reservas

## Open/Closed Principle (OCP)

El sistema está diseñado para ser extensible sin modificar el código existente:

- Los repositories extienden `JpaRepository` sin modificar su comportamiento base
- Los servicios pueden ser extendidos mediante herencia
- Los DTOs pueden ser ampliados para nuevos casos de uso

### Ejemplo:
```java
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    // Extendemos funcionalidad sin modificar JpaRepository
    Optional<Usuario> findByEmail(String email);
}
```

## Liskov Substitution Principle (LSP)

Las implementaciones respetan los contratos de sus interfaces:

- `UserDetailsServiceImpl` implementa correctamente `UserDetailsService`
- Todas las entidades pueden ser usadas donde se espera su tipo base
- Los repositories cumplen el contrato de JpaRepository

### Ejemplo:
```java
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String email) {
        // Implementación que respeta el contrato
    }
}
```

## Interface Segregation Principle (ISP)

Las interfaces son específicas y cohesivas:

- Cada repository tiene su propia interfaz específica
- Los DTOs están separados por caso de uso
- No hay interfaces que obliguen a implementar métodos innecesarios

### Ejemplos:
- `RegistroDTO` para registro de usuarios
- `ReporteReservaDTO` para reportes administrativos
- Interfaces de repository separadas por entidad

## Dependency Inversion Principle (DIP)

El sistema depende de abstracciones, no de implementaciones concretas:

### Inyección de Dependencias
```java
@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, 
                         PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }
}
```

- Los servicios dependen de interfaces de repository
- Los controllers dependen de interfaces de servicio
- La seguridad depende de interfaces de autenticación

## Beneficios

La aplicación de estos principios nos proporciona:

1. Mayor mantenibilidad
2. Código más testeable
3. Facilidad para extender funcionalidades
4. Mejor organización del código
5. Reducción de acoplamiento
6. Mayor cohesión en los componentes