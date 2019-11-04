/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.webapi_pelicula_serie.jpa.entities;

import com.google.gson.annotations.Expose;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author ANDRÉS FELIPE MERA TRÓCHEZ
 */
@Entity
@Table(name = "contenido", catalog = "db_cine_serie", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Contenido.findAll", query = "SELECT c FROM Contenido c ORDER BY c.conTitulo"),
    @NamedQuery(name = "Contenido.findByConId", query = "SELECT c FROM Contenido c WHERE c.conId = :conId"),
    @NamedQuery(name = "Contenido.findByConTitulo", query = "SELECT c FROM Contenido c WHERE c.conTitulo = :conTitulo"),
    @NamedQuery(name = "Contenido.findByConAnoLanzamiento", query = "SELECT c FROM Contenido c WHERE c.conAnoLanzamiento = :conAnoLanzamiento"),
    @NamedQuery(name = "Contenido.findByConLongitudMinutos", query = "SELECT c FROM Contenido c WHERE c.conLongitudMinutos = :conLongitudMinutos"),
    @NamedQuery(name = "Contenido.findByConSinopsis", query = "SELECT c FROM Contenido c WHERE c.conSinopsis = :conSinopsis"),
    @NamedQuery(name = "Contenido.findByConTipo", query = "SELECT c FROM Contenido c WHERE c.conTipo = :conTipo"),
    @NamedQuery(name = "Contenido.findByConCantidad", query = "SELECT c FROM Contenido c WHERE c.conCantidad = :conCantidad")})
public class Contenido implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "con_id", nullable = false)
    @Expose
    private Integer conId;
    @Basic(optional = false)
    @Column(name = "con_titulo", nullable = false, length = 100)
    @Expose
    private String conTitulo;
    @Column(name = "con_ano_lanzamiento")
    @Temporal(TemporalType.DATE)
    @Expose
    private Date conAnoLanzamiento;
    @Basic(optional = false)
    @Column(name = "con_longitud_minutos", nullable = false, length = 5)
    @Expose
    private String conLongitudMinutos;
    @Basic(optional = false)
    @Column(name = "con_sinopsis", nullable = false, length = 10000)
    @Expose
    private String conSinopsis;
    @Basic(optional = false)
    @Column(name = "con_tipo", nullable = false, length = 9)
    @Expose
    private String conTipo;
    @Column(name = "con_cantidad")
    @Expose
    private Integer conCantidad;
    @OneToOne(mappedBy = "fkContenido")
    private Favorita favorita;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkContenido")
    private List<ContenidoGenero> contenidoGeneroList;

    public Contenido() {
    }

    public Contenido(Integer conId) {
        this.conId = conId;
    }

    public Contenido(Integer conId, String conTitulo, String conLongitudMinutos, String conSinopsis, String conTipo) {
        this.conId = conId;
        this.conTitulo = conTitulo;
        this.conLongitudMinutos = conLongitudMinutos;
        this.conSinopsis = conSinopsis;
        this.conTipo = conTipo;
    }

    public Integer getConId() {
        return conId;
    }

    public void setConId(Integer conId) {
        this.conId = conId;
    }

    public String getConTitulo() {
        return conTitulo;
    }

    public void setConTitulo(String conTitulo) {
        this.conTitulo = conTitulo;
    }

    public Date getConAnoLanzamiento() {
        return conAnoLanzamiento;
    }

    public void setConAnoLanzamiento(Date conAnoLanzamiento) {
        this.conAnoLanzamiento = conAnoLanzamiento;
    }

    public String getConLongitudMinutos() {
        return conLongitudMinutos;
    }

    public void setConLongitudMinutos(String conLongitudMinutos) {
        this.conLongitudMinutos = conLongitudMinutos;
    }

    public String getConSinopsis() {
        return conSinopsis;
    }

    public void setConSinopsis(String conSinopsis) {
        this.conSinopsis = conSinopsis;
    }

    public String getConTipo() {
        return conTipo;
    }

    public void setConTipo(String conTipo) {
        this.conTipo = conTipo;
    }

    public Integer getConCantidad() {
        return conCantidad;
    }

    public void setConCantidad(Integer conCantidad) {
        this.conCantidad = conCantidad;
    }

    public Favorita getFavorita() {
        return favorita;
    }

    public void setFavorita(Favorita favorita) {
        this.favorita = favorita;
    }

    @XmlTransient
    public List<ContenidoGenero> getContenidoGeneroList() {
        return contenidoGeneroList;
    }

    public void setContenidoGeneroList(List<ContenidoGenero> contenidoGeneroList) {
        this.contenidoGeneroList = contenidoGeneroList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (conId != null ? conId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Contenido)) {
            return false;
        }
        Contenido other = (Contenido) object;
        if ((this.conId == null && other.conId != null) || (this.conId != null && !this.conId.equals(other.conId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.webapi_pelicula_serie.jpa.entities.Contenido[ conId=" + conId + " ]";
    }
    
}
