/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.webapi_pelicula_serie.apis;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.mycompany.webapi_pelicula_serie.apis.abstract_.BasicApi;
import com.mycompany.webapi_pelicula_serie.apis.abstract_.IApi;
import com.mycompany.webapi_pelicula_serie.jpa.controllers.GeneroJpaController;
import com.mycompany.webapi_pelicula_serie.jpa.entities.Genero;
import com.mycompany.webapi_pelicula_serie.jpa.utils.JsonTransformer;
import com.mycompany.webapi_pelicula_serie.jpa.utils.Utils;
import java.util.Hashtable;
import java.util.List;
import javax.persistence.PersistenceException;
import spark.Request;
import spark.Response;

/**
 *
 * @author ANDRÉS FELIPE MERA TRÓCHEZ
 */
public class ApiGenero extends BasicApi implements IApi {

    /**
     * Atributos de la Clase Api.
     */
    private static ApiGenero instance = null;
    private String path = "/genero";
    private Gson gson = null;
    private GeneroJpaController generoController = null;

    /**
     * Privado constructo para inicialisar y generar espacio en memoria de los
     * atributo de la clase.
     */
    private ApiGenero() {
        generoController = new GeneroJpaController(Utils.getEM());
        gson = JsonTransformer.singleton().getGson();
        init();
    }

    /**
     * Metodo public para verificar e inicilizar el patron de diseño.
     *
     * @return de tipo de la clase determinda.
     */
    public static ApiGenero singleton() {
        if (instance == null) {
            instance = new ApiGenero();
        }
        return instance;
    }

    @Override
    public String getPath() {
        return path;
    }

    @Override
    public Object create(Request rq, Response rs) {
        Hashtable<String, Object> retorno = new Hashtable<>();
        try {
            String body = rq.body();
            Genero newEntity = gson.fromJson(body, Genero.class);
            generoController.create(newEntity);
            retorno.put("status", 201);
            retorno.put("message", "Registro creado con exito.");
            retorno.put("data", newEntity);
        } catch (JsonSyntaxException | PersistenceException e) {
            rs.status(400);
            retorno.put("status", 400);
            retorno.put("message", e.getMessage());
        }
        return retorno;
    }

    @Override
    public Object update(Request rq, Response rs) {
        Hashtable<String, Object> retorno = new Hashtable<>();
        try {
            int id = Integer.parseInt(rq.params("id"));
            String body = rq.body();
            Genero newEntity = gson.fromJson(body, Genero.class);
            Genero oldEntity = generoController.findGenero(id);
            if (oldEntity != null) {
                oldEntity.setGenNombre(newEntity.getGenNombre());
                generoController.edit(oldEntity);
                rs.status(201);
                retorno.put("status", 201);
                retorno.put("message", "Registro con el id " + id + " esta actualizado.");
                retorno.put("data", oldEntity);
            } else {
                rs.status(404);
                retorno.put("status", 404);
                retorno.put("message", "Registro con el id " + id + " no existe.");
                retorno.put("data", oldEntity);
            }
        } catch (Exception ex) {
            rs.status(400);
            retorno.put("status", 400);
            retorno.put("message", ex.getMessage());
        }
        return retorno;
    }

    @Override
    public Object delete(Request rq, Response rs) {
        Hashtable<String, Object> retorno = new Hashtable<>();
        try {
            int id = Integer.parseInt(rq.params("id"));
            generoController.destroy(id);
            retorno.put("status", 200);
            retorno.put("message", "Registro elimnado con exito.");
        } catch (Exception ex) {
            rs.status(400);
            retorno.put("status", 400);
            retorno.put("message", ex.getMessage());
        }
        return retorno;
    }

    @Override
    public Object getAll(Request rq, Response rs) {
        Hashtable<String, Object> retorno = new Hashtable<>();
        try {
            List<Genero> entities = generoController.findGeneroEntities();
            if (entities.size() > 0) {
                retorno.put("status", 200);
                retorno.put("message", "Registros encontrado con exito.");
                retorno.put("data", entities);
            } else {
                retorno.put("status", 404);
                retorno.put("message", "Registros NO econtrados.");
                retorno.put("data", entities);
            }
        } catch (Exception ex) {
            rs.status(400);
            retorno.put("status", 400);
            retorno.put("message", ex.getMessage());
        }
        return retorno;
    }

    @Override
    public Object getById(Request rq, Response rs) {
        Hashtable<String, Object> retorno = new Hashtable<>();
        try {
            int id = Integer.parseInt(rq.params("id"));
            Genero entity = generoController.findGenero(id);
            if (entity != null) {
                retorno.put("status", 200);
                retorno.put("message", "Registros con el id " + id + " encontrado con exito.");
                retorno.put("data", entity);
            } else {
                retorno.put("status", 404);
                retorno.put("message", "Registros con el id " + id + " NO existe.");
                retorno.put("data", entity);
            }
        } catch (Exception ex) {
            rs.status(400);
            retorno.put("status", 400);
            retorno.put("message", ex.getMessage());
        }
        return retorno;
    }

}
