/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

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

/**
 *
 * @author DAVID
 */
@Entity
@Table(name = "publicacionfotos")
@NamedQueries({
    @NamedQuery(name = "Publicacionfotos.findAll", query = "SELECT p FROM Publicacionfotos p"),
    @NamedQuery(name = "Publicacionfotos.findByIdPublicacionFotos", query = "SELECT p FROM Publicacionfotos p WHERE p.idPublicacionFotos = :idPublicacionFotos")})
public class Publicacionfotos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idPublicacionFotos")
    private Integer idPublicacionFotos;
    @JoinColumn(name = "idFotos", referencedColumnName = "idFotos")
    @ManyToOne(optional = false)
    private Fotos idFotos;
    @JoinColumn(name = "idPublicacion", referencedColumnName = "idPublicacion")
    @ManyToOne(optional = false)
    private Publicacion idPublicacion;

    public Publicacionfotos() {
    }

    public Publicacionfotos(Integer idPublicacionFotos) {
        this.idPublicacionFotos = idPublicacionFotos;
    }

    public Integer getIdPublicacionFotos() {
        return idPublicacionFotos;
    }

    public void setIdPublicacionFotos(Integer idPublicacionFotos) {
        this.idPublicacionFotos = idPublicacionFotos;
    }

    public Fotos getIdFotos() {
        return idFotos;
    }

    public void setIdFotos(Fotos idFotos) {
        this.idFotos = idFotos;
    }

    public Publicacion getIdPublicacion() {
        return idPublicacion;
    }

    public void setIdPublicacion(Publicacion idPublicacion) {
        this.idPublicacion = idPublicacion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPublicacionFotos != null ? idPublicacionFotos.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Publicacionfotos)) {
            return false;
        }
        Publicacionfotos other = (Publicacionfotos) object;
        if ((this.idPublicacionFotos == null && other.idPublicacionFotos != null) || (this.idPublicacionFotos != null && !this.idPublicacionFotos.equals(other.idPublicacionFotos))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Publicacionfotos[ idPublicacionFotos=" + idPublicacionFotos + " ]";
    }
    
}