package com.conducta_entrada.controller;

import com.conducta_entrada.dto.UsuarioCreateDTO;
import com.conducta_entrada.dto.UsuarioListDTO;
import com.conducta_entrada.dto.UsuarioResponseDTO;
import com.conducta_entrada.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para la gestión de usuarios.
 * Expone los endpoints principales de la aplicación según los requisitos de la
 * actividad.
 *
 * Base URL: /api/usuarios
 */
@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    /** Servicio de lógica de negocio de usuarios */
    private final UsuarioService usuarioService;

    /**
     * Requisito 1: Crear un nuevo usuario.
     * Retorna el nombre y apellido del usuario en MAYÚSCULAS.
     * Solo puede crear el usuario que esté autenticado (requiere token JWT).
     *
     * URL: POST /api/usuarios
     * Acceso: Requiere autenticación (token JWT en header Authorization)
     *
     * @param request DTO con los datos del usuario a crear
     * @return nombre y apellido en mayúsculas con código HTTP 201 (Created)
     */
    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> crearUsuario(@Valid @RequestBody UsuarioCreateDTO request) {
        UsuarioResponseDTO response = usuarioService.crearUsuario(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Requisito 2: Obtener todos los usuarios registrados.
     * Acceso sin autenticación, cualquier persona puede consultar la lista.
     *
     * URL: GET /api/usuarios
     * Acceso: Público (sin autenticación)
     *
     * @return lista de usuarios con código HTTP 200 (OK)
     */
    @GetMapping
    public ResponseEntity<List<UsuarioListDTO>> obtenerTodos() {
        List<UsuarioListDTO> usuarios = usuarioService.obtenerTodos();
        return ResponseEntity.ok(usuarios);
    }

    /**
     * Requisito 3: Obtener un usuario por su ID.
     * Requiere autenticación con token JWT.
     *
     * URL: GET /api/usuarios/{id}
     * Acceso: Requiere autenticación (token JWT en header Authorization)
     *
     * @param id identificador del usuario a buscar
     * @return datos del usuario con código HTTP 200 (OK)
     */
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioListDTO> obtenerPorId(@PathVariable Long id) {
        UsuarioListDTO usuario = usuarioService.obtenerPorId(id);
        return ResponseEntity.ok(usuario);
    }
}
