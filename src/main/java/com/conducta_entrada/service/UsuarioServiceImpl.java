package com.conducta_entrada.service;

import com.conducta_entrada.dto.*;
import com.conducta_entrada.model.Usuario;
import com.conducta_entrada.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementación del servicio de usuarios.
 * Contiene toda la lógica de negocio para gestionar los usuarios de la
 * aplicación.
 */
@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    /** Repositorio para acceder a los datos de usuarios en la base de datos */
    private final UsuarioRepository usuarioRepository;

    /**
     * Codificador de contraseñas (BCrypt) para encriptar las contraseñas antes de
     * guardarlas
     */
    private final PasswordEncoder passwordEncoder;

    /**
     * Crea un nuevo usuario en la base de datos.
     * Primero verifica que no exista un usuario con el mismo nombre de usuario.
     * La contraseña se encripta con BCrypt antes de almacenarse.
     * Retorna el nombre y apellido en MAYÚSCULAS como pide la actividad.
     *
     * @param dto datos del usuario a crear
     * @return DTO con nombre y apellido en mayúsculas
     * @throws RuntimeException si el nombre de usuario ya existe
     */
    @Override
    public UsuarioResponseDTO crearUsuario(UsuarioCreateDTO dto) {
        // Verificar si el nombre de usuario ya está registrado
        if (usuarioRepository.existsByUser(dto.getUser())) {
            throw new RuntimeException("El usuario '" + dto.getUser() + "' ya existe");
        }

        // Construir la entidad Usuario con la contraseña encriptada
        Usuario usuario = Usuario.builder()
                .nombre(dto.getNombre())
                .apellido(dto.getApellido())
                .user(dto.getUser())
                .password(passwordEncoder.encode(dto.getPassword()))
                .build();

        // Guardar el usuario en la base de datos
        Usuario guardado = usuarioRepository.save(usuario);

        // Retornar solo nombre y apellido en MAYÚSCULAS
        return UsuarioResponseDTO.builder()
                .nombre(guardado.getNombre().toUpperCase())
                .apellido(guardado.getApellido().toUpperCase())
                .build();
    }

    /**
     * Obtiene todos los usuarios registrados en la base de datos.
     * Transforma cada entidad Usuario a un DTO sin incluir la contraseña.
     *
     * @return lista de DTOs con los datos públicos de cada usuario
     */
    @Override
    public List<UsuarioListDTO> obtenerTodos() {
        return usuarioRepository.findAll().stream()
                .map(u -> UsuarioListDTO.builder()
                        .id(u.getId())
                        .nombre(u.getNombre())
                        .apellido(u.getApellido())
                        .user(u.getUser())
                        .build())
                .collect(Collectors.toList());
    }

    /**
     * Busca un usuario por su ID en la base de datos.
     * Lanza una excepción si no se encuentra el usuario.
     *
     * @param id identificador del usuario
     * @return DTO con los datos del usuario encontrado
     * @throws RuntimeException si el usuario no existe
     */
    @Override
    public UsuarioListDTO obtenerPorId(Long id) {
        // Buscar usuario por ID, lanzar excepción si no existe
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario con id " + id + " no encontrado"));

        // Convertir la entidad a DTO para la respuesta
        return UsuarioListDTO.builder()
                .id(usuario.getId())
                .nombre(usuario.getNombre())
                .apellido(usuario.getApellido())
                .user(usuario.getUser())
                .build();
    }

    /**
     * Genera el nombre completo concatenando nombre y apellido.
     * Este método no accede a la base de datos, solo procesa los parámetros
     * recibidos.
     *
     * @param nombre   nombre recibido como parámetro de URL
     * @param apellido apellido recibido como parámetro de URL
     * @return DTO con el nombre completo concatenado
     */
    @Override
    public NombreCompletoDTO obtenerNombreCompleto(String nombre, String apellido) {
        return new NombreCompletoDTO(nombre + " " + apellido);
    }
}
