package com.mycompany.webapi_pelicula_serie.jpa.controllers.exceptions;
/**
 * 
 * @author ANDRÉS FELIPE MERA TRÓCHEZ
 */
public class PreexistingEntityException extends Exception {
    public PreexistingEntityException(String message, Throwable cause) {
        super(message, cause);
    }
    public PreexistingEntityException(String message) {
        super(message);
    }
}
