package com.conducta_entrada.security;

import com.conducta_entrada.model.Usuario;
import com.conducta_entrada.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * Servicio personalizado para cargar los datos de usuario requeridos por Spring
 * Security.
 * Implementa la interfaz UserDetailsService para buscar usuarios en la base de
 * datos
 * y convertirlos al formato que Spring Security necesita para la autenticación.
 */
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    /** Repositorio para buscar usuarios en la base de datos */
    private final UsuarioRepository usuarioRepository;

    /**
     * Busca un usuario por su nombre de usuario en la base de datos.
     * Convierte la entidad Usuario a un objeto UserDetails de Spring Security.
     * Se utiliza tanto en el login como en la validación del token JWT.
     *
     * @param username nombre de usuario a buscar
     * @return UserDetails con los datos del usuario para Spring Security
     * @throws UsernameNotFoundException si el usuario no existe en la base de datos
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Buscar el usuario en la base de datos por su campo "user"
        Usuario usuario = usuarioRepository.findByUser(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));

        // Convertir la entidad a UserDetails de Spring Security
        // Se usa una lista vacía de authorities ya que no se manejan roles
        return new User(
                usuario.getUser(),
                usuario.getPassword(),
                new ArrayList<>());
    }
}
