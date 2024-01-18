/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

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
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author DAVID
 */
@Entity
@Table(name = "publicacion")
@NamedQueries({
    @NamedQuery(name = "Publicacion.findAll", query = "SELECT p FROM Publicacion p"),
    @NamedQuery(name = "Publicacion.findByIdPublicacion", query = "SELECT p FROM Publicacion p WHERE p.idPublicacion = :idPublicacion"),
    @NamedQuery(name = "Publicacion.findByTitulo", query = "SELECT p FROM Publicacion p WHERE p.titulo = :titulo"),
    @NamedQuery(name = "Publicacion.findByFechaPublicacion", query = "SELECT p FROM Publicacion p WHERE p.fechaPublicacion = :fechaPublicacion")})
public class Publicacion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idPublicacion")
    private Integer idPublicacion;
    @Basic(optional = false)
    @Column(name = "titulo")
    private String titulo;
    @Basic(optional = false)
    @Lob
    @Column(name = "texto")
    private String texto;
    @Basic(optional = false)
    @Column(name = "fechaPublicacion")
    @Temporal(TemporalType.DATE)
    private Date fechaPublicacion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idPublicacion")
    private List<Publicacionfotos> publicacionfotosList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idPublicacion")
    private List<Comentarios> comentariosList;
    @JoinColumn(name = "idAdministrador", referencedColumnName = "idAdministrador")
    @ManyToOne(optional = false)
    private Admisnistrador idAdministrador;

    public Publicacion() {
    }

    public Publicacion(Integer idPublicacion) {
        this.idPublicacion = idPublicacion;
    }

    public Publicacion(Integer idPublicacion, String titulo, String texto, Date fechaPublicacion) {
        this.idPublicacion = idPublicacion;
        this.titulo = titulo;
        this.texto = texto;
        this.fechaPublicacion = fechaPublicacion;
    }

    public Integer getIdPublicacion() {
        return idPublicacion;
    }

    public void setIdPublicacion(Integer idPublicacion) {
        this.idPublicacion = idPublicacion;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public Date getFechaPublicacion() {
        return fechaPublicacion;
    }

    public void setFechaPublicacion(Date fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    public List<Publicacionfotos> getPublicacionfotosList() {
        return publicacionfotosList;
    }

    public void setPublicacionfotosList(List<Publicacionfotos> publicacionfotosList) {
        this.publicacionfotosList = publicacionfotosList;
    }

    public List<Comentarios> getComentariosList() {
        return comentariosList;
    }

    public void setComentariosList(List<Comentarios> comentariosList) {
        this.comentariosList = comentariosList;
    }

    public Admisnistrador getIdAdministrador() {
        return idAdministrador;
    }

    public void setIdAdministrador(Admisnistrador idAdministrador) {
        this.idAdministrador = idAdministrador;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPublicacion != null ? idPublicacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Publicacion)) {
            return false;
        }
        Publicacion other = (Publicacion) object;
        if ((this.idPublicacion == null && other.idPublicacion != null) || (this.idPublicacion != null && !this.idPublicacion.equals(other.idPublicacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Publicacion[ idPublicacion=" + idPublicacion + " ]";
    }
    
}