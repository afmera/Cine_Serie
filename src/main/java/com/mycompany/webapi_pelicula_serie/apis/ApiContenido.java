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
import com.mycompany.webapi_pelicula_serie.jpa.controllers.ContenidoJpaController;
import com.mycompany.webapi_pelicula_serie.jpa.entities.Contenido;
import com.mycompany.webapi_pelicula_serie.jpa.utils.JsonTransformer;
import com.mycompany.webapi_pelicula_serie.jpa.utils.Utils;
import java.util.Hashtable;
import java.util.List;
import javax.persistence.PersistenceException;
import spark.Request;
import spark.Response;
import spark.Spark;

/**
 *
 * @author ANDRÉS FELIPE MERA TRÓCHEZ
 */
public class ApiContenido extends BasicApi implements IApi {

    /**
     * Atributos de la Clase Api.
     */
    private static ApiContenido instance = null;
    private String path = "/contenido";
    private Gson gson = null;
    private ContenidoJpaController contenidoController = null;

    /**
     * Privado constructo para inicialisar y generar espacio en memoria de los
     * atributo de la clase.
     */
    private ApiContenido() {
        contenidoController = new ContenidoJpaController(Utils.getEM());
        gson = JsonTransformer.singleton().getGson();
        init();
        Spark.get(path + "/byContenidoTitulo/:titulo", (rq, rs) -> this.byContenidoTitulo(rq, rs), JsonTransformer.singleton());
    }

    /**
     * Metodo public para verificar e inicilizar el patron de diseño.
     *
     * @return de tipo de la clase determinda.
     */
    public static ApiContenido singleton() {
        if (instance == null) {
            instance = new ApiContenido();
        }
        return instance;
    }

    /**
     * Metodo para consultar un registro por un parametro en esécifico.
     *
     * @param rq de tipo Request.
     * @param rs de tipo Response.
     * @return de tipo Hashtable.
     */
    private Hashtable<String, Object> byContenidoTitulo(Request rq, Response rs) {
        Hashtable<String, Object> retorno = new Hashtable<>();
        try {
            String body = rq.body();
            List<Contenido> data = contenidoController.findByContenidoTitulo(body);
            if (data.size() > 0) {
                retorno.put("status", 200);
                retorno.put("message", "departamento encontrados con exito!");
            } else {
                rs.status(404);
                retorno.put("status", 404);
                retorno.put("message", "No hay registros asociados con el id@" + body + "!");
            }
            retorno.put("data", data);
        } catch (Exception e) {
            rs.status(400);
            retorno.put("status", 400);
            retorno.put("message", e.getMessage());
        }
        return retorno;
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
            Contenido newEntity = gson.fromJson(body, Contenido.class);
            contenidoController.create(newEntity);
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
            Contenido newEntity = gson.fromJson(body, Contenido.class);
            Contenido oldEntity = contenidoController.findContenido(id);
            if (oldEntity != null) {
                oldEntity.setConAnoLanzamiento(newEntity.getConAnoLanzamiento());
                oldEntity.setConCantidad(newEntity.getConCantidad());
                oldEntity.setConLongitudMinutos(newEntity.getConLongitudMinutos());
                oldEntity.setConSinopsis(newEntity.getConSinopsis());
                oldEntity.setConTipo(newEntity.getConTipo());
                oldEntity.setConTitulo(newEntity.getConTitulo());
                contenidoController.edit(oldEntity);
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
            contenidoController.destroy(id);
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
            List<Contenido> entities = contenidoController.findContenidoEntities();
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
            Contenido entity = contenidoController.findContenido(id);
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
