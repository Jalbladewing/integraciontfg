package com.tfgllopis.integracion;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name = "Sistema", catalog = "mydb2", schema = "", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"nombreSistema"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Sistema.findAll", query = "SELECT s FROM Sistema s")
    , @NamedQuery(name = "Sistema.findByCoordenadaX", query = "SELECT s FROM Sistema s WHERE s.coordenadaX = :coordenadaX")
    , @NamedQuery(name = "Sistema.findByCoordenadaY", query = "SELECT s FROM Sistema s WHERE s.coordenadaY = :coordenadaY")
    , @NamedQuery(name = "Sistema.findByNombreSistema", query = "SELECT s FROM Sistema s WHERE s.nombreSistema = :nombreSistema")})
public class Sistema implements Serializable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @Column(name = "coordenadaX", nullable = false)
    private int coordenadaX;
    @Basic(optional = false)
    @Column(name = "coordenadaY", nullable = false)
    private int coordenadaY;
    @Id
    @Basic(optional = false)
    @Column(name = "nombreSistema", nullable = false, length = 50)
    private String nombreSistema;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sistema")
    private List<Planeta> planetaList;

    public Sistema() {
    }

    public Sistema(String nombreSistema, int coordenadaX, int coordenadaY) {
        this.nombreSistema = nombreSistema;
        this.coordenadaX = coordenadaX;
        this.coordenadaY = coordenadaY;
    }

    public int getCoordenadaX() {
        return coordenadaX;
    }

    public int getCoordenadaY() {
        return coordenadaY;
    }

    public String getNombreSistema() {
        return nombreSistema;
    }

    @XmlTransient
    public List<Planeta> getPlanetaList() {
        return planetaList;
    }

    public void setPlanetaList(List<Planeta> planetaList) {
        this.planetaList = planetaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (nombreSistema != null ? nombreSistema.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Sistema)) {
            return false;
        }
        Sistema other = (Sistema) object;
        if ((this.nombreSistema == null && other.nombreSistema != null) || (this.nombreSistema != null && !this.nombreSistema.equals(other.nombreSistema))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.teamburton.Sistema[ nombreSistema=" + nombreSistema + " ]";
    }
    
}
