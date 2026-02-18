package com.conducta_entrada.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO (Data Transfer Object) para listar usuarios.
 * Se utiliza cuando se consultan usuarios (obtener todos o buscar por ID).
 * No incluye la contraseña por seguridad.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioListDTO {

    /** Identificador único del usuario */
    private Long id;

    /** Nombre del usuario */
    private String nombre;

    /** Apellido del usuario */
    private String apellido;

    /** Nombre de usuario para autenticación */
    private String user;
}
