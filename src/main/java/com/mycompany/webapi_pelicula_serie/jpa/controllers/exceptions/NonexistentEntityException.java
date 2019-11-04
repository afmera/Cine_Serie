package com.mycompany.webapi_pelicula_serie.jpa.controllers.exceptions;
/**
 * 
 * @author ANDRÉS FELIPE MERA TRÓCHEZ
 */
public class NonexistentEntityException extends Exception {
    public NonexistentEntityException(String message, Throwable cause) {
        super(message, cause);
    }
    public NonexistentEntityException(String message) {
        super(message);
    }
}
