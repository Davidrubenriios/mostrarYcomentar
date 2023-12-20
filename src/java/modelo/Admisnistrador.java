/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

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

/**
 *
 * @author DAVID
 */
@Entity
@Table(name = "admisnistrador")
@NamedQueries({
    @NamedQuery(name = "Admisnistrador.findAll", query = "SELECT a FROM Admisnistrador a"),
    @NamedQuery(name = "Admisnistrador.findByIdAdministrador", query = "SELECT a FROM Admisnistrador a WHERE a.idAdministrador = :idAdministrador"),
    @NamedQuery(name = "Admisnistrador.findByNombre", query = "SELECT a FROM Admisnistrador a WHERE a.nombre = :nombre"),
    @NamedQuery(name = "Admisnistrador.findByApellido", query = "SELECT a FROM Admisnistrador a WHERE a.apellido = :apellido"),
    @NamedQuery(name = "Admisnistrador.findByEmail", query = "SELECT a FROM Admisnistrador a WHERE a.email = :email"),
    @NamedQuery(name = "Admisnistrador.findByClave", query = "SELECT a FROM Admisnistrador a WHERE a.clave = :clave"),
    @NamedQuery(name = "Admisnistrador.findByDni", query = "SELECT a FROM Admisnistrador a WHERE a.dni = :dni"),
    @NamedQuery(name = "Admisnistrador.findByCodigoArea", query = "SELECT a FROM Admisnistrador a WHERE a.codigoArea = :codigoArea"),
    @NamedQuery(name = "Admisnistrador.findByTelefono", query = "SELECT a FROM Admisnistrador a WHERE a.telefono = :telefono")})
public class Admisnistrador implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idAdministrador")
    private Integer idAdministrador;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "apellido")
    private String apellido;
    @Basic(optional = false)
    @Column(name = "email")
    private String email;
    @Basic(optional = false)
    @Column(name = "clave")
    private String clave;
    @Basic(optional = false)
    @Column(name = "dni")
    private int dni;
    @Basic(optional = false)
    @Column(name = "codigoArea")
    private int codigoArea;
    @Basic(optional = false)
    @Column(name = "telefono")
    private int telefono;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idAdministrador")
    private List<Publicacion> publicacionList;

    public Admisnistrador() {
    }

    public Admisnistrador(Integer idAdministrador) {
        this.idAdministrador = idAdministrador;
    }

    public Admisnistrador(Integer idAdministrador, String nombre, String apellido, String email, String clave, int dni, int codigoArea, int telefono) {
        this.idAdministrador = idAdministrador;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.clave = clave;
        this.dni = dni;
        this.codigoArea = codigoArea;
        this.telefono = telefono;
    }

    public Integer getIdAdministrador() {
        return idAdministrador;
    }

    public void setIdAdministrador(Integer idAdministrador) {
        this.idAdministrador = idAdministrador;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public int getDni() {
        return dni;
    }

    public void setDni(int dni) {
        this.dni = dni;
    }

    public int getCodigoArea() {
        return codigoArea;
    }

    public void setCodigoArea(int codigoArea) {
        this.codigoArea = codigoArea;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    public List<Publicacion> getPublicacionList() {
        return publicacionList;
    }

    public void setPublicacionList(List<Publicacion> publicacionList) {
        this.publicacionList = publicacionList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAdministrador != null ? idAdministrador.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Admisnistrador)) {
            return false;
        }
        Admisnistrador other = (Admisnistrador) object;
        if ((this.idAdministrador == null && other.idAdministrador != null) || (this.idAdministrador != null && !this.idAdministrador.equals(other.idAdministrador))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Admisnistrador[ idAdministrador=" + idAdministrador + " ]";
    }
    
}
