package com.conducta_entrada.controller;

import com.conducta_entrada.dto.LoginRequestDTO;
import com.conducta_entrada.dto.LoginResponseDTO;
import com.conducta_entrada.dto.UsuarioCreateDTO;
import com.conducta_entrada.dto.UsuarioResponseDTO;
import com.conducta_entrada.security.JwtService;
import com.conducta_entrada.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador de autenticación.
 * Gestiona el inicio de sesión (login) y el registro inicial de usuarios.
 * Todos los endpoints de este controlador son públicos (no requieren token
 * JWT).
 */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    /** Gestor de autenticación de Spring Security */
    private final AuthenticationManager authenticationManager;

    /** Servicio para generar y validar tokens JWT */
    private final JwtService jwtService;

    /** Servicio de lógica de negocio de usuarios */
    private final UsuarioService usuarioService;

    /**
     * Endpoint de inicio de sesión.
     * Autentica al usuario con sus credenciales y retorna un token JWT.
     *
     * URL: POST /api/auth/login
     * Acceso: Público (sin autenticación)
     *
     * @param request DTO con el usuario y contraseña
     * @return token JWT para usar en peticiones protegidas
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO request) {
        // Autenticar al usuario con Spring Security
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUser(),
                        request.getPassword()));

        // Obtener los detalles del usuario autenticado
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        // Generar el token JWT con los datos del usuario
        String token = jwtService.generateToken(userDetails);

        // Retornar el token en la respuesta
        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    /**
     * Endpoint de registro inicial.
     * Permite crear el primer usuario sin necesidad de autenticación.
     * Esto es necesario para poder obtener el primer token JWT.
     *
     * URL: POST /api/auth/registro
     * Acceso: Público (sin autenticación)
     *
     * @param request DTO con los datos del nuevo usuario
     * @return nombre y apellido del usuario creado en MAYÚSCULAS
     */
    @PostMapping("/registro")
    public ResponseEntity<UsuarioResponseDTO> registro(@Valid @RequestBody UsuarioCreateDTO request) {
        UsuarioResponseDTO response = usuarioService.crearUsuario(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
