package com.conducta_entrada.repository;

import com.conducta_entrada.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositorio de acceso a datos para la entidad Usuario.
 * Extiende JpaRepository para heredar operaciones CRUD básicas como:
 * save(), findById(), findAll(), deleteById(), entre otras.
 *
 * Spring Data JPA genera la implementación automáticamente en tiempo de
 * ejecución.
 */
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    /**
     * Busca un usuario por su nombre de usuario (campo "user").
     * Se utiliza en el proceso de autenticación para verificar credenciales.
     *
     * @param user nombre de usuario a buscar
     * @return Optional con el usuario encontrado, o vacío si no existe
     */
    Optional<Usuario> findByUser(String user);

    /**
     * Verifica si ya existe un usuario con el nombre de usuario proporcionado.
     * Se utiliza antes de crear un nuevo usuario para evitar duplicados.
     *
     * @param user nombre de usuario a verificar
     * @return true si el usuario ya existe, false en caso contrario
     */
    boolean existsByUser(String user);
}
