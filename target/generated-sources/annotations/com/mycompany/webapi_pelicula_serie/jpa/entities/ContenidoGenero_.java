package com.mycompany.webapi_pelicula_serie.jpa.entities;

import com.mycompany.webapi_pelicula_serie.jpa.entities.Contenido;
import com.mycompany.webapi_pelicula_serie.jpa.entities.Genero;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-11-02T12:35:42")
@StaticMetamodel(ContenidoGenero.class)
public class ContenidoGenero_ { 

    public static volatile SingularAttribute<ContenidoGenero, Integer> cgId;
    public static volatile SingularAttribute<ContenidoGenero, Genero> fkGenero;
    public static volatile SingularAttribute<ContenidoGenero, Contenido> fkContenido;

}