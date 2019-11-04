/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.webapi_pelicula_serie.apis.abstract_;

import com.mycompany.webapi_pelicula_serie.jpa.utils.JsonTransformer;
import spark.Spark;

/**
 *
 * @author ANDRÉS FELIPE MERA TRÓCHEZ
 */
public abstract class BasicApi {

    public void init() {
        if (this instanceof IApi) {
            JsonTransformer jt = JsonTransformer.singleton();
            //-----------------------------------------------
            IApi ia = (IApi) this;
            /**
             * Traer todos
             */
            Spark.get(ia.getPath(), (rq, rs) -> ia.getAll(rq, rs), jt);
            /**
             * Traer el detalle de una tupla.
             */
            Spark.get(ia.getPath() + "/:id", (rq, rs) -> ia.getById(rq, rs), jt);
            /**
             * Genera un recurso.
             */
            Spark.post(ia.getPath(), (rq, rs) -> ia.create(rq, rs), jt);
            /**
             * Actualizo el recurso.
             */
            Spark.put(ia.getPath() + "/:id", (rq, rs) -> ia.update(rq, rs), jt);
            /**
             * Elimina un recurso.
             */
            Spark.delete(ia.getPath() + "/:id", (rq, rs) -> ia.delete(rq, rs), jt);
        } else {
            System.out.println("No ha implementado la IApi.");
        }
    }
}
