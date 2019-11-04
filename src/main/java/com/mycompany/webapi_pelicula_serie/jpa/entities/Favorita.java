/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.webapi_pelicula_serie.jpa.entities;

import com.google.gson.annotations.Expose;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ANDRÉS FELIPE MERA TRÓCHEZ
 */
@Entity
@Table(name = "favorita", catalog = "db_cine_serie", schema = "", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"fk_contenido"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Favorita.findAll", query = "SELECT f FROM Favorita f"),
    @NamedQuery(name = "Favorita.findByFavId", query = "SELECT f FROM Favorita f WHERE f.favId = :favId"),
    @NamedQuery(name = "Favorita.findByFavCalificacion", query = "SELECT f FROM Favorita f WHERE f.favCalificacion = :favCalificacion"),
    @NamedQuery(name = "Favorita.findByFavComentario", query = "SELECT f FROM Favorita f WHERE f.favComentario = :favComentario")})
public class Favorita implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "fav_id", nullable = false)
    @Expose
    private Integer favId;
    @Basic(optional = false)
    @Column(name = "fav_calificacion", nullable = false, length = 2)
    @Expose
    private String favCalificacion;
    @Basic(optional = false)
    @Column(name = "fav_comentario", nullable = false, length = 10000)
    @Expose
    private String favComentario;
    @JoinColumn(name = "fk_contenido", referencedColumnName = "con_id")
//    @OneToOne
    @Expose
    private Contenido fkContenido;

    public Favorita() {
    }

    public Favorita(Integer favId) {
        this.favId = favId;
    }

    public Favorita(Integer favId, String favCalificacion, String favComentario) {
        this.favId = favId;
        this.favCalificacion = favCalificacion;
        this.favComentario = favComentario;
    }

    public Integer getFavId() {
        return favId;
    }

    public void setFavId(Integer favId) {
        this.favId = favId;
    }

    public String getFavCalificacion() {
        return favCalificacion;
    }

    public void setFavCalificacion(String favCalificacion) {
        this.favCalificacion = favCalificacion;
    }

    public String getFavComentario() {
        return favComentario;
    }

    public void setFavComentario(String favComentario) {
        this.favComentario = favComentario;
    }

    public Contenido getFkContenido() {
        return fkContenido;
    }

    public void setFkContenido(Contenido fkContenido) {
        this.fkContenido = fkContenido;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (favId != null ? favId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Favorita)) {
            return false;
        }
        Favorita other = (Favorita) object;
        if ((this.favId == null && other.favId != null) || (this.favId != null && !this.favId.equals(other.favId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.webapi_pelicula_serie.jpa.entities.Favorita[ favId=" + favId + " ]";
    }
    
}
