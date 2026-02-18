package com.conducta_entrada.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO (Data Transfer Object) para la respuesta del inicio de sesión.
 * Contiene el token JWT generado tras una autenticación exitosa.
 * Este token debe enviarse en el header Authorization de las peticiones
 * protegidas.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponseDTO {

    /** Token JWT generado para el usuario autenticado */
    private String token;
}
