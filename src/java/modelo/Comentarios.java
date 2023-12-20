/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author DAVID
 */
@Entity
@Table(name = "comentarios")
@NamedQueries({
    @NamedQuery(name = "Comentarios.findAll", query = "SELECT c FROM Comentarios c"),
    @NamedQuery(name = "Comentarios.findByIdComentarios", query = "SELECT c FROM Comentarios c WHERE c.idComentarios = :idComentarios"),
    @NamedQuery(name = "Comentarios.findByNombre", query = "SELECT c FROM Comentarios c WHERE c.nombre = :nombre"),
    @NamedQuery(name = "Comentarios.findByMeGusta", query = "SELECT c FROM Comentarios c WHERE c.meGusta = :meGusta"),
    @NamedQuery(name = "Comentarios.findByFecha", query = "SELECT c FROM Comentarios c WHERE c.fecha = :fecha")})
public class Comentarios implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idComentarios")
    private Integer idComentarios;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @Lob
    @Column(name = "mjsComentarios")
    private String mjsComentarios;
    @Basic(optional = false)
    @Column(name = "meGusta")
    private int meGusta;
    @Basic(optional = false)
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @JoinColumn(name = "idPublicacion", referencedColumnName = "idPublicacion")
    @ManyToOne(optional = false)
    private Publicacion idPublicacion;

    public Comentarios() {
    }

    public Comentarios(Integer idComentarios) {
        this.idComentarios = idComentarios;
    }

    public Comentarios(Integer idComentarios, String nombre, String mjsComentarios, int meGusta, Date fecha) {
        this.idComentarios = idComentarios;
        this.nombre = nombre;
        this.mjsComentarios = mjsComentarios;
        this.meGusta = meGusta;
        this.fecha = fecha;
    }

    public Integer getIdComentarios() {
        return idComentarios;
    }

    public void setIdComentarios(Integer idComentarios) {
        this.idComentarios = idComentarios;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getMjsComentarios() {
        return mjsComentarios;
    }

    public void setMjsComentarios(String mjsComentarios) {
        this.mjsComentarios = mjsComentarios;
    }

    public int getMeGusta() {
        return meGusta;
    }

    public void setMeGusta(int meGusta) {
        this.meGusta = meGusta;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
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
        hash += (idComentarios != null ? idComentarios.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Comentarios)) {
            return false;
        }
        Comentarios other = (Comentarios) object;
        if ((this.idComentarios == null && other.idComentarios != null) || (this.idComentarios != null && !this.idComentarios.equals(other.idComentarios))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Comentarios[ idComentarios=" + idComentarios + " ]";
    }
    
}
