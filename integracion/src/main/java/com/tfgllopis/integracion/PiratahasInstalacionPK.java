package com.tfgllopis.integracion;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author JALLOPISE
 */
@Embeddable
public class PiratahasInstalacionPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "Pirata_idPirata")
    private int pirataidPirata;
    @Basic(optional = false)
    @Column(name = "Instalacion_name")
    private String instalacionname;

    public PiratahasInstalacionPK() {
    }

    public PiratahasInstalacionPK(int pirataidPirata, String instalacionname) {
        this.pirataidPirata = pirataidPirata;
        this.instalacionname = instalacionname;
    }

    public int getPirataidPirata() {
        return pirataidPirata;
    }

    public String getInstalacionname() {
        return instalacionname;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) pirataidPirata;
        hash += (instalacionname != null ? instalacionname.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PiratahasInstalacionPK)) {
            return false;
        }
        PiratahasInstalacionPK other = (PiratahasInstalacionPK) object;
        if (this.pirataidPirata != other.pirataidPirata) {
            return false;
        }
        if ((this.instalacionname == null && other.instalacionname != null) || (this.instalacionname != null && !this.instalacionname.equals(other.instalacionname))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tfgllopis.tecnicotfg.PiratahasInstalacionPK[ pirataidPirata=" + pirataidPirata + ", instalacionname=" + instalacionname + " ]";
    }
    
}
