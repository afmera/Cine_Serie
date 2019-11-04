/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.webapi_pelicula_serie.jpa.entities;

import com.google.gson.annotations.Expose;
import java.io.Serializable;
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
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author ANDRÉS FELIPE MERA TRÓCHEZ
 */
@Entity
@Table(name = "genero", catalog = "db_cine_serie", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Genero.findAll", query = "SELECT g FROM Genero g ORDER BY g.genNombre"),
    @NamedQuery(name = "Genero.findByGenId", query = "SELECT g FROM Genero g WHERE g.genId = :genId"),
    @NamedQuery(name = "Genero.findByGenNombre", query = "SELECT g FROM Genero g WHERE g.genNombre = :genNombre")})
public class Genero implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "gen_id", nullable = false)
    @Expose
    private Integer genId;
    @Basic(optional = false)
    @Column(name = "gen_nombre", nullable = false, length = 100)
    @Expose
    private String genNombre;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkGenero")
    private List<ContenidoGenero> contenidoGeneroList;

    public Genero() {
    }

    public Genero(Integer genId) {
        this.genId = genId;
    }

    public Genero(Integer genId, String genNombre) {
        this.genId = genId;
        this.genNombre = genNombre;
    }

    public Integer getGenId() {
        return genId;
    }

    public void setGenId(Integer genId) {
        this.genId = genId;
    }

    public String getGenNombre() {
        return genNombre;
    }

    public void setGenNombre(String genNombre) {
        this.genNombre = genNombre;
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
        hash += (genId != null ? genId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Genero)) {
            return false;
        }
        Genero other = (Genero) object;
        if ((this.genId == null && other.genId != null) || (this.genId != null && !this.genId.equals(other.genId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.webapi_pelicula_serie.jpa.entities.Genero[ genId=" + genId + " ]";
    }
    
}
