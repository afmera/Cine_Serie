/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.webapi_pelicula_serie.apis.abstract_;

import spark.Request;
import spark.Response;

/**
 *
 * @author ANDRÉS FELIPE MERA TRÓCHEZ
 */
public interface IApi {

    /**
     * Ruta base del api
     *
     * @return
     */
    public String getPath();

    /**
     * Metodo public para crear un recurso.
     *
     * @param rq de tipo Request.
     * @param rs de tipo Response.
     * @return de tipo Object.
     */
    public Object create(Request rq, Response rs);

    /**
     * Metodo public para actualizar un recurso.
     *
     * @param rq de tipo Request.
     * @param rs de tipo Response.
     * @return de tipo Object.
     */
    public Object update(Request rq, Response rs);

    /**
     * Metodo public para eliminar un recurso.
     *
     * @param rq de tipo Request.
     * @param rs de tipo Response.
     * @return de tipo Object.
     */
    public Object delete(Request rq, Response rs);

    /**
     * Metodo public para consultar todos los registros.
     *
     * @param rq de tipo Request.
     * @param rs de tipo Response.
     * @return de tipo Object.
     */
    public Object getAll(Request rq, Response rs);

    /**
     * Metodo public para consultar un registro por detalle.
     *
     * @param rq de tipo Request.
     * @param rs de tipo Response.
     * @return de tipo Object.
     */
    public Object getById(Request rq, Response rs);
}
