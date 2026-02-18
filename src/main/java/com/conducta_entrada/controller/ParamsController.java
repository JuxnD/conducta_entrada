package com.conducta_entrada.controller;

import com.conducta_entrada.dto.NombreCompletoDTO;
import com.conducta_entrada.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador REST para el endpoint de parámetros en URL.
 * Implementa el requisito 4 de la actividad.
 *
 * Base URL: /api
 */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ParamsController {

    /** Servicio de lógica de negocio de usuarios */
    private final UsuarioService usuarioService;

    /**
     * Requisito 4: Recibir parámetros en la URL y retornar el nombre completo.
     * Concatena el nombre y apellido recibidos como parámetros de consulta.
     *
     * URL: GET /api/params?nombre=xxx&apellido=yyy
     * Ejemplo: http://localhost:9000/api/params?nombre=Juan&apellido=Perez
     * Respuesta: {"nombreCompleto": "Juan Perez"}
     *
     * Acceso: Público (sin autenticación)
     *
     * @param nombre   nombre del usuario recibido como parámetro de consulta
     * @param apellido apellido del usuario recibido como parámetro de consulta
     * @return DTO con el nombre completo concatenado
     */
    @GetMapping("/params")
    public ResponseEntity<NombreCompletoDTO> obtenerNombreCompleto(
            @RequestParam String nombre,
            @RequestParam String apellido) {

        NombreCompletoDTO response = usuarioService.obtenerNombreCompleto(nombre, apellido);
        return ResponseEntity.ok(response);
    }
}
