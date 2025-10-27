package utp.gestionparqueo.sistema_parqueo.model.config;

import org.springframework.context.annotation.*;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            //.csrf(csrf -> csrf.disable()) // Deshabilitado para la prueba de WebSocket
            .authorizeHttpRequests(authz -> authz
                .requestMatchers("/register", "/login").permitAll()
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .defaultSuccessUrl("/index", true) // ¡Redirigir aquí!
                .permitAll()
            )
            .logout(logout -> logout.permitAll());
        return http.build();
    }
}