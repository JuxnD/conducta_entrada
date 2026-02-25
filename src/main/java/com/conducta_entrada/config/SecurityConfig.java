package com.conducta_entrada.config;

import com.conducta_entrada.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Configuración de seguridad de la aplicación.
 * Define qué endpoints son públicos y cuáles requieren autenticación JWT.
 * También configura los beans necesarios para la autenticación.
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    /** Filtro personalizado que intercepta las peticiones y valida el token JWT */
    private final JwtAuthenticationFilter jwtAuthFilter;

    /** Servicio que carga los datos del usuario desde la base de datos */
    private final UserDetailsService userDetailsService;

    /**
     * Configura la cadena de filtros de seguridad HTTP.
     * Define las reglas de acceso para cada endpoint de la aplicación.
     *
     * Endpoints públicos (sin autenticación):
     * - GET /api/usuarios → Obtener todos los usuarios
     * - GET /api/params → Parámetros en URL
     * - POST /api/auth/** → Login y registro
     *
     * Endpoints protegidos (requieren token JWT):
     * - POST /api/usuarios → Crear usuario
     * - GET /api/usuarios/{id} → Obtener usuario por ID
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Deshabilitar CSRF ya que usamos tokens JWT (API REST sin estado)
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // Requisito 2: Obtener todos los usuarios - SIN autenticación
                        .requestMatchers(HttpMethod.GET, "/api/usuarios").permitAll()
                        // Requisito 4: Parámetros en URL - SIN autenticación
                        .requestMatchers(HttpMethod.GET, "/api/params").permitAll()
                        // Login y registro inicial - SIN autenticación
                        .requestMatchers(HttpMethod.POST, "/api/auth/**").permitAll()
                        // Todos los demás endpoints requieren autenticación
                        .anyRequest().authenticated())
                // Configurar sesión sin estado (Stateless) para usar JWT
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // Configurar el proveedor de autenticación personalizado
                .authenticationProvider(authenticationProvider())
                // Agregar el filtro JWT antes del filtro de autenticación por defecto
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /**
     * Configura el proveedor de autenticación.
     * Utiliza DaoAuthenticationProvider para buscar usuarios en la base de datos
     * y comparar contraseñas encriptadas con BCrypt.
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    /**
     * Expone el AuthenticationManager como bean de Spring.
     * Necesario para que el AuthController pueda inyectarlo y usarlo en el login.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    /**
     * Configura el codificador de contraseñas BCrypt.
     * Todas las contraseñas se encriptan antes de almacenarse en la base de datos.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
