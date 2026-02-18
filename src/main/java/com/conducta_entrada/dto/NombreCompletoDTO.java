package com.conducta_entrada.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO (Data Transfer Object) para la respuesta del endpoint de parámetros.
 * Contiene el nombre completo formado por la concatenación de nombre y
 * apellido.
 * Utilizado en el requisito 4: GET /api/params?nombre=xxx&apellido=yyy
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NombreCompletoDTO {

    /** Nombre completo del usuario (nombre + apellido concatenados) */
    private String nombreCompleto;
}
