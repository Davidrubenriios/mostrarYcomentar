/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.io.Serializable;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
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
@Table(name = "fotos")
@NamedQueries({
    @NamedQuery(name = "Fotos.findAll", query = "SELECT f FROM Fotos f"),
    @NamedQuery(name = "Fotos.findByIdFotos", query = "SELECT f FROM Fotos f WHERE f.idFotos = :idFotos"),
    @NamedQuery(name = "Fotos.findByNombreFoto", query = "SELECT f FROM Fotos f WHERE f.nombreFoto = :nombreFoto"),
    @NamedQuery(name = "Fotos.findByFechaFoto", query = "SELECT f FROM Fotos f WHERE f.fechaFoto = :fechaFoto")})
public class Fotos implements Serializable {

    @Basic(optional = false)
    @Lob
    @Column(name = "imgFoto")
    private byte[] imgFoto;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idFotos")
    private Integer idFotos;
    @Basic(optional = false)
    @Column(name = "nombreFoto")
    private String nombreFoto;
    @Basic(optional = false)
    @Column(name = "fechaFoto")
    @Temporal(TemporalType.DATE)
    private Date fechaFoto;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idFotos")
    private List<Publicacionfotos> publicacionfotosList;

    public Fotos() {
    }

    public Fotos(Integer idFotos) {
        this.idFotos = idFotos;
    }

    public Fotos(Integer idFotos, String nombreFoto, byte[] imgFoto, Date fechaFoto) {
        this.idFotos = idFotos;
        this.nombreFoto = nombreFoto;
        this.imgFoto = imgFoto;
        this.fechaFoto = fechaFoto;
    }

    public Integer getIdFotos() {
        return idFotos;
    }

    public void setIdFotos(Integer idFotos) {
        this.idFotos = idFotos;
    }

    public String getNombreFoto() {
        return nombreFoto;
    }

    public void setNombreFoto(String nombreFoto) {
        this.nombreFoto = nombreFoto;
    }


    public Date getFechaFoto() {
        return fechaFoto;
    }

    public void setFechaFoto(Date fechaFoto) {
        this.fechaFoto = fechaFoto;
    }

    public List<Publicacionfotos> getPublicacionfotosList() {
        return publicacionfotosList;
    }

    public void setPublicacionfotosList(List<Publicacionfotos> publicacionfotosList) {
        this.publicacionfotosList = publicacionfotosList;
    }
    
        public String getBase64Image() {
        if (imgFoto != null) {
            return Base64.getEncoder().encodeToString(imgFoto);
        }
        return "";
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idFotos != null ? idFotos.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Fotos)) {
            return false;
        }
        Fotos other = (Fotos) object;
        if ((this.idFotos == null && other.idFotos != null) || (this.idFotos != null && !this.idFotos.equals(other.idFotos))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Fotos[ idFotos=" + idFotos + " ]";
    }

    public byte[] getImgFoto() {
        return imgFoto;
    }

    public void setImgFoto(byte[] imgFoto) {
        this.imgFoto = imgFoto;
    }
    
}
