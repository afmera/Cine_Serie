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
import com.mycompany.webapi_pelicula_serie.jpa.controllers.FavoritaJpaController;
import com.mycompany.webapi_pelicula_serie.jpa.entities.Favorita;
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
public class ApiFavorita extends BasicApi implements IApi {

    /**
     * Atributos de la Clase Api.
     */
    private static ApiFavorita instance = null;
    private String path = "/favorita";
    private Gson gson = null;
    private FavoritaJpaController favoritaController = null;

    /**
     * Privado constructo para inicialisar y generar espacio en memoria de los
     * atributo de la clase.
     */
    private ApiFavorita() {
        favoritaController = new FavoritaJpaController(Utils.getEM());
        gson = JsonTransformer.singleton().getGson();
        init();
    }

    /**
     * Metodo public para verificar e inicilizar el patron de diseño.
     *
     * @return de tipo de la clase determinda.
     */
    public static ApiFavorita singleton() {
        if (instance == null) {
            instance = new ApiFavorita();
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
            Favorita newEntity = gson.fromJson(body, Favorita.class);
            favoritaController.create(newEntity);
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
            Favorita newEntity = gson.fromJson(body, Favorita.class);
            Favorita oldEntity = favoritaController.findFavorita(id);
            if (oldEntity != null) {
                oldEntity.setFavCalificacion(newEntity.getFavCalificacion());
                oldEntity.setFavComentario(newEntity.getFavComentario());
                oldEntity.setFavId(newEntity.getFavId());
                oldEntity.setFkContenido(newEntity.getFkContenido());
                favoritaController.edit(oldEntity);
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
            favoritaController.destroy(id);
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
            List<Favorita> entities = favoritaController.findFavoritaEntities();
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
            Favorita entity = favoritaController.findFavorita(id);
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
