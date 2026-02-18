package com.conducta_entrada.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidad JPA que representa la tabla "usuarios" en la base de datos.
 * Contiene los campos: id, nombre, apellido, user y password.
 *
 * Anotaciones de Lombok:
 * - @Data: genera getters, setters, toString, equals y hashCode
 * automáticamente.
 * - @NoArgsConstructor: genera un constructor sin argumentos.
 * - @AllArgsConstructor: genera un constructor con todos los argumentos.
 * - @Builder: permite crear instancias utilizando el patrón Builder.
 */
@Entity
@Table(name = "usuarios")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Usuario {

    /**
     * Identificador único del usuario, generado automáticamente por la base de
     * datos.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nombre del usuario. Campo obligatorio en la base de datos.
     */
    @Column(nullable = false)
    private String nombre;

    /**
     * Apellido del usuario. Campo obligatorio en la base de datos.
     */
    @Column(nullable = false)
    private String apellido;

    /**
     * Nombre de usuario para autenticación. Debe ser único y obligatorio.
     */
    @Column(nullable = false, unique = true)
    private String user;

    /**
     * Contraseña del usuario almacenada de forma encriptada con BCrypt.
     * Campo obligatorio en la base de datos.
     */
    @Column(nullable = false)
    private String password;
}
