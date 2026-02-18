package com.conducta_entrada.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO (Data Transfer Object) para la creación de un nuevo usuario.
 * Contiene todos los campos necesarios para registrar un usuario.
 * Todos los campos son obligatorios y validados con @NotBlank.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioCreateDTO {

    /** Nombre del usuario. Campo obligatorio. */
    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    /** Apellido del usuario. Campo obligatorio. */
    @NotBlank(message = "El apellido es obligatorio")
    private String apellido;

    /** Nombre de usuario para autenticación. Campo obligatorio y debe ser único. */
    @NotBlank(message = "El usuario es obligatorio")
    private String user;

    /** Contraseña del usuario. Campo obligatorio. Se almacenará encriptada. */
    @NotBlank(message = "La contraseña es obligatoria")
    private String password;
}
