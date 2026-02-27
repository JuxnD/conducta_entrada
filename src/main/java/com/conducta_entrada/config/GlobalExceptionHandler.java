package com.conducta_entrada.config;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.conducta_entrada.exception.BadRequestException;
import com.conducta_entrada.exception.NotFoundException;

import java.util.HashMap;
import java.util.Map;

/**
 * Manejador global de excepciones para toda la aplicación.
 * Intercepta las excepciones lanzadas en cualquier controlador y retorna
 * respuestas HTTP con mensajes de error claros en formato JSON.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    
    /**
     * Maneja excepciones de tipo NotFound.
     * Se dispara cuando un recurso no existe.
     *
     * @param ex excepción capturada
     * @return respuesta HTTP 404 (Not Found) con el mensaje de error
     */
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Map<String,String>> handleNotfound (NotFoundException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    /**
     * Maneja excepciones de tipo BadRequest.
     * Se dispara cuando ocurre un error en la peticion,
     * por ejemplo: usuario duplicado.
     *
     * @param ex excepción capturada
     * @return respuesta HTTP 400 (Bad Request) con el mensaje de error
     */
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Map<String, String>> handleBadRequest (BadRequestException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    /**
     * Maneja excepciones de credenciales inválidas.
     * Se dispara cuando el usuario intenta iniciar sesión con
     * un usuario o contraseña incorrectos.
     *
     * @param ex excepción de credenciales inválidas
     * @return respuesta HTTP 401 (Unauthorized) con mensaje genérico
     */
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Map<String, String>> handleBadCredentials(BadCredentialsException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", "Credenciales inválidas");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }

    /**
     * Maneja excepciones de validación de campos.
     * Se dispara cuando los datos enviados no cumplen con las validaciones
     * definidas con @NotBlank u otras anotaciones de validación en los DTOs.
     *
     * @param ex excepción de validación con los errores de cada campo
     * @return respuesta HTTP 400 (Bad Request) con el detalle de cada campo
     *         inválido
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidation(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(err -> errors.put(err.getField(), err.getDefaultMessage()));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    /**
     * Maneja excepciones de tipo MissingServletRequestParameter.
     * Se dispara cuando los parametros requeridos no se envian.
     *
     * @param ex excepción capturada
     * @return respuesta HTTP 400 (Bad Request) con el mensaje de error
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<Map<String, String>> handleMissingServletRequestParameter (MissingServletRequestParameterException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", "Los parametros son obligatorios ");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
}
