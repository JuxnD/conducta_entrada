package com.conducta_entrada;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Clase principal de la aplicación Spring Boot.
 * La anotación @SpringBootApplication habilita la configuración automática,
 * el escaneo de componentes y la definición de beans adicionales.
 */
@SpringBootApplication
public class ConductaEntradaApplication {

    /**
     * Método principal que inicia la aplicación Spring Boot.
     * 
     * @param args argumentos de línea de comandos
     */
    public static void main(String[] args) {
        SpringApplication.run(ConductaEntradaApplication.class, args);
    }
}
