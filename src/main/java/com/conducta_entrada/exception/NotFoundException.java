package com.conducta_entrada.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Excepcion personalizada para cuando no exista un recurso 
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException{
    
    public NotFoundException(){
        super();
    } 

    public NotFoundException (String mensaje){
        super(mensaje);
    } 
}
