package com.conducta_entrada.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO (Data Transfer Object) para la solicitud de inicio de sesión.
 * Contiene las credenciales necesarias para autenticar a un usuario.
 * Ambos campos son obligatorios gracias a la validación @NotBlank.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequestDTO {

    /** Nombre de usuario para la autenticación. Campo obligatorio. */
    @NotBlank(message = "El usuario es obligatorio")
    private String user;

    /** Contraseña del usuario. Campo obligatorio. */
    @NotBlank(message = "La contraseña es obligatoria")
    private String password;
}
