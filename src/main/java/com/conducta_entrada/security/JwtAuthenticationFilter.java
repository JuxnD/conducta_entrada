package com.conducta_entrada.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Filtro de autenticación JWT que se ejecuta una vez por cada petición HTTP.
 * Intercepta todas las solicitudes para verificar si contienen un token JWT
 * válido
 * en el header "Authorization". Si el token es válido, establece la
 * autenticación
 * en el contexto de seguridad de Spring.
 */
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    /** Servicio para operaciones con tokens JWT */
    private final JwtService jwtService;

    /** Servicio para cargar los datos del usuario desde la base de datos */
    private final UserDetailsService userDetailsService;

    /**
     * Método principal del filtro que se ejecuta en cada petición HTTP.
     * Extrae el token JWT del header Authorization, lo valida y establece
     * la autenticación si el token es correcto.
     *
     * @param request     petición HTTP entrante
     * @param response    respuesta HTTP
     * @param filterChain cadena de filtros para continuar el procesamiento
     */
    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {

        // Obtener el header "Authorization" de la petición
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String username;

        // Si no hay header o no empieza con "Bearer ", continuar sin autenticación
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Extraer el token JWT (quitar el prefijo "Bearer ")
        jwt = authHeader.substring(7);

        try {
            // Extraer el nombre de usuario del token
            username = jwtService.extractUsername(jwt);

            // Si el usuario existe y no hay autenticación previa en el contexto
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                // Cargar los datos completos del usuario desde la base de datos
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

                // Verificar que el token sea válido para este usuario
                if (jwtService.isTokenValid(jwt, userDetails)) {
                    // Crear el objeto de autenticación con los datos del usuario
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities());
                    authToken.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(request));

                    // Establecer la autenticación en el contexto de seguridad de Spring
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
        } catch (Exception e) {
            // Si el token es inválido o ha expirado, continuar sin autenticación
        }

        // Continuar con la cadena de filtros
        filterChain.doFilter(request, response);
    }
}
