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
public class PiratahasNavePK implements Serializable {

    @Basic(optional = false)
    @Column(name = "Nave_nombreNave")
    private String navenombreNave;
    @Basic(optional = false)
    @Column(name = "Pirata_idPirata")
    private int pirataidPirata;

    public PiratahasNavePK() {
    }

    public PiratahasNavePK(String navenombreNave, int pirataidPirata) {
        this.navenombreNave = navenombreNave;
        this.pirataidPirata = pirataidPirata;
    }

    public String getNavenombreNave() {
        return navenombreNave;
    }

    public int getPirataidPirata() {
        return pirataidPirata;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (navenombreNave != null ? navenombreNave.hashCode() : 0);
        hash += (int) pirataidPirata;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PiratahasNavePK)) {
            return false;
        }
        PiratahasNavePK other = (PiratahasNavePK) object;
        if ((this.navenombreNave == null && other.navenombreNave != null) || (this.navenombreNave != null && !this.navenombreNave.equals(other.navenombreNave))) {
            return false;
        }
        if (this.pirataidPirata != other.pirataidPirata) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tfgllopis.tecnicotfg.PiratahasNavePK[ navenombreNave=" + navenombreNave + ", pirataidPirata=" + pirataidPirata + " ]";
    }
    
}
