/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.webapi_pelicula_serie.jpa.utils;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author ANDRÉS FELIPE MERA TRÓCHEZ
 */
public class Utils {
    private static final String JPU = "JPU_CINE_SERIE";
    private static EntityManagerFactory em = null;
    
    public static EntityManagerFactory getEM(){
        if(em==null){
            em = Persistence.createEntityManagerFactory(JPU);
        }
        return em;
    }
}
