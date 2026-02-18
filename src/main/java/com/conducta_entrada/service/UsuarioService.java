package com.conducta_entrada.service;

import com.conducta_entrada.dto.*;

import java.util.List;

/**
 * Interfaz del servicio de usuarios.
 * Define las operaciones de negocio disponibles para gestionar usuarios.
 * La implementación concreta se encuentra en UsuarioServiceImpl.
 */
public interface UsuarioService {

    /**
     * Crea un nuevo usuario en la base de datos.
     * Retorna el nombre y apellido en MAYÚSCULAS como indica la actividad.
     *
     * @param dto datos del usuario a crear (nombre, apellido, user, password)
     * @return DTO con el nombre y apellido del usuario en mayúsculas
     */
    UsuarioResponseDTO crearUsuario(UsuarioCreateDTO dto);

    /**
     * Obtiene la lista completa de todos los usuarios registrados.
     * Este endpoint es accesible sin autenticación.
     *
     * @return lista de usuarios con sus datos (sin incluir la contraseña)
     */
    List<UsuarioListDTO> obtenerTodos();

    /**
     * Obtiene un usuario específico por su identificador.
     * Este endpoint requiere autenticación con token JWT.
     *
     * @param id identificador del usuario a buscar
     * @return datos del usuario encontrado
     */
    UsuarioListDTO obtenerPorId(Long id);

    /**
     * Concatena nombre y apellido para retornar el nombre completo.
     * Utilizado por el endpoint de parámetros en URL (/api/params).
     *
     * @param nombre   nombre del usuario
     * @param apellido apellido del usuario
     * @return DTO con el nombre completo concatenado
     */
    NombreCompletoDTO obtenerNombreCompleto(String nombre, String apellido);
}
