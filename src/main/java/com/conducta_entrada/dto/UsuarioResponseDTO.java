package com.conducta_entrada.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO (Data Transfer Object) para la respuesta de creación de usuario.
 * Contiene solo el nombre y apellido del usuario creado, ambos en MAYÚSCULAS.
 * Se utiliza como respuesta del requisito 1 de la actividad.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioResponseDTO {

    /** Nombre del usuario en MAYÚSCULAS */
    private String nombre;

    /** Apellido del usuario en MAYÚSCULAS */
    private String apellido;
}
