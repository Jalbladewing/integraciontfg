package com.tfgllopis.integracion;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "Pirata_has_Nave")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PiratahasNave.findAll", query = "SELECT p FROM PiratahasNave p")
    , @NamedQuery(name = "PiratahasNave.findByNavenombreNave", query = "SELECT p FROM PiratahasNave p WHERE p.piratahasNavePK.navenombreNave = :navenombreNave")
    , @NamedQuery(name = "PiratahasNave.findByPirataidPirata", query = "SELECT p FROM PiratahasNave p WHERE p.piratahasNavePK.pirataidPirata = :pirataidPirata")
    , @NamedQuery(name = "PiratahasNave.findByPirataidPirata_NavenombreNave", query = "SELECT p FROM PiratahasNave p WHERE p.piratahasNavePK.pirataidPirata = :pirataidPirata AND p.piratahasNavePK.navenombreNave = :navenombreNave")
    , @NamedQuery(name = "PiratahasNave.findByCantidadDefecto", query = "SELECT p FROM PiratahasNave p WHERE p.cantidadDefecto = :cantidadDefecto")})
public class PiratahasNave implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected PiratahasNavePK piratahasNavePK;
    @Basic(optional = false)
    @Column(name = "cantidadDefecto")
    private int cantidadDefecto;
    @JoinColumn(name = "Nave_nombreNave", referencedColumnName = "nombreNave", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Nave nave;
    @JoinColumn(name = "Pirata_idPirata", referencedColumnName = "idPirata", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Pirata pirata;

    public PiratahasNave() {
    }

    public PiratahasNave(PiratahasNavePK piratahasNavePK, int cantidadDefecto) {
        this.piratahasNavePK = piratahasNavePK;
        this.cantidadDefecto = cantidadDefecto;
    }

    public PiratahasNave(String navenombreNave, int pirataidPirata, int cantidadDefecto) {
        this.piratahasNavePK = new PiratahasNavePK(navenombreNave, pirataidPirata);
        this.cantidadDefecto = cantidadDefecto;

    }

    public PiratahasNavePK getPiratahasNavePK() {
        return piratahasNavePK;
    }

    public String getNavenombreNave() 
    {
        return piratahasNavePK.getNavenombreNave();
    }

    public int getPirataidPirata() 
    {
        return piratahasNavePK.getPirataidPirata();
    }

    public int getCantidadDefecto() {
        return cantidadDefecto;
    }

    public void setCantidadDefecto(int cantidadDefecto) {
        this.cantidadDefecto = cantidadDefecto;
    }

    public Nave getNave() {
        return nave;
    }

    public void setNave(Nave nave) {
        this.nave = nave;
    }

    public Pirata getPirata() {
        return pirata;
    }

    public void setPirata(Pirata pirata) {
        this.pirata = pirata;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (piratahasNavePK != null ? piratahasNavePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PiratahasNave)) {
            return false;
        }
        PiratahasNave other = (PiratahasNave) object;
        if ((this.piratahasNavePK == null && other.piratahasNavePK != null) || (this.piratahasNavePK != null && !this.piratahasNavePK.equals(other.piratahasNavePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tfgllopis.tecnicotfg.PiratahasNave[ piratahasNavePK=" + piratahasNavePK + " ]";
    }
    
}
