
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
@Table(name = "respuesta")
@NamedQueries({
    @NamedQuery(name = "Respuesta.findAll", query = "SELECT r FROM Respuesta r"),
    @NamedQuery(name = "Respuesta.findByIdRespuesta", query = "SELECT r FROM Respuesta r WHERE r.idRespuesta = :idRespuesta"),
    @NamedQuery(name = "Respuesta.findByNombre", query = "SELECT r FROM Respuesta r WHERE r.nombre = :nombre"),
    @NamedQuery(name = "Respuesta.findByFechaRespuesta", query = "SELECT r FROM Respuesta r WHERE r.fechaRespuesta = :fechaRespuesta")})
public class Respuesta implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idRespuesta")
    private Integer idRespuesta;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @Lob
    @Column(name = "msjRespuesta")
    private String msjRespuesta;
    @Basic(optional = false)
    @Column(name = "fechaRespuesta")
    @Temporal(TemporalType.DATE)
    private Date fechaRespuesta;
    @JoinColumn(name = "idComentarios", referencedColumnName = "idComentarios")
    @ManyToOne(optional = false)
    private Comentarios idComentarios;

    public Respuesta() {
    }

    public Respuesta(Integer idRespuesta) {
        this.idRespuesta = idRespuesta;
    }

    public Respuesta(Integer idRespuesta, String nombre, String msjRespuesta, Date fechaRespuesta) {
        this.idRespuesta = idRespuesta;
        this.nombre = nombre;
        this.msjRespuesta = msjRespuesta;
        this.fechaRespuesta = fechaRespuesta;
    }

    public Integer getIdRespuesta() {
        return idRespuesta;
    }

    public void setIdRespuesta(Integer idRespuesta) {
        this.idRespuesta = idRespuesta;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getMsjRespuesta() {
        return msjRespuesta;
    }

    public void setMsjRespuesta(String msjRespuesta) {
        this.msjRespuesta = msjRespuesta;
    }

    public Date getFechaRespuesta() {
        return fechaRespuesta;
    }

    public void setFechaRespuesta(Date fechaRespuesta) {
        this.fechaRespuesta = fechaRespuesta;
    }

    public Comentarios getIdComentarios() {
        return idComentarios;
    }

    public void setIdComentarios(Comentarios idComentarios) {
        this.idComentarios = idComentarios;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idRespuesta != null ? idRespuesta.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Respuesta)) {
            return false;
        }
        Respuesta other = (Respuesta) object;
        if ((this.idRespuesta == null && other.idRespuesta != null) || (this.idRespuesta != null && !this.idRespuesta.equals(other.idRespuesta))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Respuesta[ idRespuesta=" + idRespuesta + " ]";
    }
    
}
