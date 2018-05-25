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

/**
 *
 * @author JALLOPISE
 */
@Entity
@Table(name = "Pirata_has_Instalacion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PiratahasInstalacion.findAll", query = "SELECT p FROM PiratahasInstalacion p")
    , @NamedQuery(name = "PiratahasInstalacion.findByPirataidPirata", query = "SELECT p FROM PiratahasInstalacion p WHERE p.piratahasInstalacionPK.pirataidPirata = :pirataidPirata")
    , @NamedQuery(name = "PiratahasInstalacion.findByInstalacionname", query = "SELECT p FROM PiratahasInstalacion p WHERE p.piratahasInstalacionPK.instalacionname = :instalacionname")
    , @NamedQuery(name = "PiratahasInstalacion.findByPirataidPirata_Instalacionname", query = "SELECT p FROM PiratahasInstalacion p WHERE p.piratahasInstalacionPK.pirataidPirata = :pirataidPirata AND p.piratahasInstalacionPK.instalacionname = :instalacionname")
    , @NamedQuery(name = "PiratahasInstalacion.findByNivelDefecto", query = "SELECT p FROM PiratahasInstalacion p WHERE p.nivelDefecto = :nivelDefecto")})
public class PiratahasInstalacion implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected PiratahasInstalacionPK piratahasInstalacionPK;
    @Basic(optional = false)
    @Column(name = "nivelDefecto")
    private int nivelDefecto;
    @JoinColumn(name = "Instalacion_name", referencedColumnName = "name", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Instalacion instalacion;
    @JoinColumn(name = "Pirata_idPirata", referencedColumnName = "idPirata", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Pirata pirata;

    public PiratahasInstalacion() {
    }

    public PiratahasInstalacion(PiratahasInstalacionPK piratahasInstalacionPK, int nivelDefecto) 
    {
        this.piratahasInstalacionPK = piratahasInstalacionPK;
        this.nivelDefecto = nivelDefecto;
    }

    public PiratahasInstalacion(int pirataidPirata, String instalacionname, int nivelDefecto) 
    {
        this.piratahasInstalacionPK = new PiratahasInstalacionPK(pirataidPirata, instalacionname);
        this.nivelDefecto = nivelDefecto;
    }

    public PiratahasInstalacionPK getPiratahasInstalacionPK() {
        return piratahasInstalacionPK;
    }
    
    public int getPirataidPirata() {
        return piratahasInstalacionPK.getPirataidPirata();
    }

    public String getInstalacionname() {
        return piratahasInstalacionPK.getInstalacionname();
    }

    public int getNivelDefecto() {
        return nivelDefecto;
    }

    public void setNivelDefecto(int nivelDefecto) {
        this.nivelDefecto = nivelDefecto;
    }

    public Instalacion getInstalacion() {
        return instalacion;
    }

    public void setInstalacion(Instalacion instalacion) {
        this.instalacion = instalacion;
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
        hash += (piratahasInstalacionPK != null ? piratahasInstalacionPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PiratahasInstalacion)) {
            return false;
        }
        PiratahasInstalacion other = (PiratahasInstalacion) object;
        if ((this.piratahasInstalacionPK == null && other.piratahasInstalacionPK != null) || (this.piratahasInstalacionPK != null && !this.piratahasInstalacionPK.equals(other.piratahasInstalacionPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tfgllopis.tecnicotfg.PiratahasInstalacion[ piratahasInstalacionPK=" + piratahasInstalacionPK + " ]";
    }
    
}
