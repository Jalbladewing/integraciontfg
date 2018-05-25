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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name = "Pirata", catalog = "mydb2", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pirata.findAll", query = "SELECT p FROM Pirata p")
    , @NamedQuery(name = "Pirata.findByIdPirata", query = "SELECT p FROM Pirata p WHERE p.idPirata = :idPirata")})
public class Pirata implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "idPirata")
    private Integer idPirata;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pirata")
    private List<PiratahasInstalacion> piratahasInstalacionList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pirata")
    private List<PiratahasNave> piratahasNaveList;
    @OneToMany(mappedBy = "pirataidPirata")
    private List<Planeta> planetaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pirata")
    private List<PiratahasDesbloqueoNave> piratahasDesbloqueoNaveList;

    public Pirata() {
    }

    public Pirata(Integer idPirata) {
        this.idPirata = idPirata;
    }

    public int getIdPirata() {
        return idPirata;
    }

    @XmlTransient
    public List<PiratahasInstalacion> getPiratahasInstalacionList() {
        return piratahasInstalacionList;
    }

    public void setPiratahasInstalacionList(List<PiratahasInstalacion> piratahasInstalacionList) {
        this.piratahasInstalacionList = piratahasInstalacionList;
    }

    @XmlTransient
    public List<PiratahasNave> getPiratahasNaveList() {
        return piratahasNaveList;
    }

    public void setPiratahasNaveList(List<PiratahasNave> piratahasNaveList) {
        this.piratahasNaveList = piratahasNaveList;
    }

    @XmlTransient
    public List<Planeta> getPlanetaList() {
        return planetaList;
    }

    public void setPlanetaList(List<Planeta> planetaList) {
        this.planetaList = planetaList;
    }

    @XmlTransient
    public List<PiratahasDesbloqueoNave> getPiratahasDesbloqueoNaveList() {
        return piratahasDesbloqueoNaveList;
    }

    public void setPiratahasDesbloqueoNaveList(List<PiratahasDesbloqueoNave> piratahasDesbloqueoNaveList) {
        this.piratahasDesbloqueoNaveList = piratahasDesbloqueoNaveList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPirata != null ? idPirata.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pirata)) {
            return false;
        }
        Pirata other = (Pirata) object;
        if ((this.idPirata == null && other.idPirata != null) || (this.idPirata != null && !this.idPirata.equals(other.idPirata))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tfgllopis.moduloTecnico.Pirata[ idPirata=" + idPirata + " ]";
    }
    
}
