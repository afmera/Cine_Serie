/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.webapi_pelicula_serie.jpa.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import spark.ResponseTransformer;

/**
 *
 * @author ANDRÉS FELIPE MERA TRÓCHEZ
 */
public class JsonTransformer implements ResponseTransformer {

    //Primero generamos una instancia del mismo estatica.
    private static JsonTransformer instance = null;
    private Gson gson = null;

    /**
     * Genero un constructor privado.
     */
    private JsonTransformer() {
//        gson=new Gson();
        gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
    }

    /**
     * Genero un metodo statico publico, para acceder y verificar si la
     * instancia es valida.
     *
     * @return de tipo JsonTransformer.
     */
    public static JsonTransformer singleton() {
        if (instance == null) {
            instance = new JsonTransformer();
        }
        return instance;
    }

    /**
     * Metodo publico para obtener el objeto Gson.
     *
     * @return de tipo Gson.
     */
    public Gson getGson() {
        return gson;
    }

    /**
     * Metodo publico para renderizar el valor de json.
     *
     * @param o de tipo Object.
     * @return de tipo String.
     * @throws Exception Mensaje de error.
     */
    @Override
    public String render(Object o) throws Exception {
        String json = gson.toJson(o);
        return json;
    }

}
