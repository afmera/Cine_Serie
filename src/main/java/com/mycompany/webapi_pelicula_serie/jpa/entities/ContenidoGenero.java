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
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ANDRÉS FELIPE MERA TRÓCHEZ
 */
@Entity
@Table(name = "contenido_genero", catalog = "db_cine_serie", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ContenidoGenero.findAll", query = "SELECT c FROM ContenidoGenero c"),
    @NamedQuery(name = "ContenidoGenero.findByCgId", query = "SELECT c FROM ContenidoGenero c WHERE c.cgId = :cgId")})
public class ContenidoGenero implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cg_id", nullable = false)
    @Expose
    private Integer cgId;
    @JoinColumn(name = "fk_contenido", referencedColumnName = "con_id", nullable = false)
    @ManyToOne(optional = false)
    @Expose
    private Contenido fkContenido;
    @JoinColumn(name = "fk_genero", referencedColumnName = "gen_id", nullable = false)
    @ManyToOne(optional = false)
    @Expose
    private Genero fkGenero;

    public ContenidoGenero() {
    }

    public ContenidoGenero(Integer cgId) {
        this.cgId = cgId;
    }

    public Integer getCgId() {
        return cgId;
    }

    public void setCgId(Integer cgId) {
        this.cgId = cgId;
    }

    public Contenido getFkContenido() {
        return fkContenido;
    }

    public void setFkContenido(Contenido fkContenido) {
        this.fkContenido = fkContenido;
    }

    public Genero getFkGenero() {
        return fkGenero;
    }

    public void setFkGenero(Genero fkGenero) {
        this.fkGenero = fkGenero;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cgId != null ? cgId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ContenidoGenero)) {
            return false;
        }
        ContenidoGenero other = (ContenidoGenero) object;
        if ((this.cgId == null && other.cgId != null) || (this.cgId != null && !this.cgId.equals(other.cgId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.webapi_pelicula_serie.jpa.entities.ContenidoGenero[ cgId=" + cgId + " ]";
    }
    
}
