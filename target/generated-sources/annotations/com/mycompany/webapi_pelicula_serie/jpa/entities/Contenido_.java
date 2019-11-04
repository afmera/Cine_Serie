package com.mycompany.webapi_pelicula_serie.jpa.entities;

import com.mycompany.webapi_pelicula_serie.jpa.entities.ContenidoGenero;
import com.mycompany.webapi_pelicula_serie.jpa.entities.Favorita;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-11-02T12:35:42")
@StaticMetamodel(Contenido.class)
public class Contenido_ { 

    public static volatile SingularAttribute<Contenido, String> conSinopsis;
    public static volatile SingularAttribute<Contenido, Integer> conCantidad;
    public static volatile SingularAttribute<Contenido, String> conTitulo;
    public static volatile SingularAttribute<Contenido, String> conLongitudMinutos;
    public static volatile ListAttribute<Contenido, ContenidoGenero> contenidoGeneroList;
    public static volatile SingularAttribute<Contenido, Favorita> favorita;
    public static volatile SingularAttribute<Contenido, Integer> conId;
    public static volatile SingularAttribute<Contenido, String> conTipo;
    public static volatile SingularAttribute<Contenido, Date> conAnoLanzamiento;

}