package com.mycompany.webapi_pelicula_serie.jpa.entities;

import com.mycompany.webapi_pelicula_serie.jpa.entities.ContenidoGenero;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-11-02T12:35:42")
@StaticMetamodel(Genero.class)
public class Genero_ { 

    public static volatile SingularAttribute<Genero, Integer> genId;
    public static volatile ListAttribute<Genero, ContenidoGenero> contenidoGeneroList;
    public static volatile SingularAttribute<Genero, String> genNombre;

}