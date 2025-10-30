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
        // --- OTRA MANERA: Volvemos a poner el CSP, pero SIN 'unsafe-eval' ---
        String scriptSrc = "'self' https://cdnjs.cloudflare.com https://cdn.tailwindcss.com";
        String styleSrc = "'self' 'unsafe-inline' https://fonts.googleapis.com";
        String fontSrc = "'self' https://fonts.gstatic.com";
        String connectSrc = "'self' ws: wss:"; // Permite WebSocket nativo

        http
            .authorizeHttpRequests(authz -> authz
                .requestMatchers("/login", "/register", "/css/**", "/js/**", "/ws/**").permitAll()
                // MODIFICADO: La nueva ruta /app/accion debe ser permitida para usuarios autenticados
                .requestMatchers("/app/accion").authenticated()
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .defaultSuccessUrl("/", true)
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")
                .permitAll()
            )
            .csrf(csrf -> csrf
                .ignoringRequestMatchers("/ws/**") // Deshabilitar CSRF para WebSocket
            )
            // --- AÑADIDO: Configuración de Cabeceras y CSP (Sin 'unsafe-eval') ---
            .headers(headers -> headers
                .contentSecurityPolicy(csp -> csp
                    .policyDirectives(
                        "default-src 'self'; " +
                        "script-src " + scriptSrc + "; " +
                        "style-src " + styleSrc + "; " +
                        "font-src " + fontSrc + "; " +
                        "connect-src " + connectSrc + ";"
                    )
                )
            );
            
        return http.build();
    }

}

