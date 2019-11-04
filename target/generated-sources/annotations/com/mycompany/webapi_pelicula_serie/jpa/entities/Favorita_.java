package com.mycompany.webapi_pelicula_serie.jpa.entities;

import com.mycompany.webapi_pelicula_serie.jpa.entities.Contenido;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-11-02T12:35:42")
@StaticMetamodel(Favorita.class)
public class Favorita_ { 

    public static volatile SingularAttribute<Favorita, String> favComentario;
    public static volatile SingularAttribute<Favorita, String> favCalificacion;
    public static volatile SingularAttribute<Favorita, Contenido> fkContenido;
    public static volatile SingularAttribute<Favorita, Integer> favId;

}