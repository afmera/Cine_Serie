/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.webapi_pelicula_serie.init;

import com.mycompany.webapi_pelicula_serie.apis.ApiContenido;
import com.mycompany.webapi_pelicula_serie.apis.ApiContenidoGenero;
import com.mycompany.webapi_pelicula_serie.apis.ApiFavorita;
import com.mycompany.webapi_pelicula_serie.apis.ApiGenero;
import spark.Spark;

/**
 *
 * @author ANDRÉS FELIPE MERA TRÓCHEZ
 */
public class Main {
    public static void main(String[] args) {
        //Ruta de archivos staticos.
        Spark.staticFiles.location("/public");
        //----------*----------//
        Spark.port(88);
        //----------*----------//
        Spark.init();
        //----------*----------//
        ApiContenido.singleton();//Invoco el metodo singleton para arracara los servicios del api.
        ApiContenidoGenero.singleton();
        ApiFavorita.singleton();
        ApiGenero.singleton();
        //----------*----------//
    }
}
