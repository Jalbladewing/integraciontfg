package com.tfgllopis.integracion;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class PiratahasDesbloqueoNavePK implements Serializable {

    @Basic(optional = false)
    @Column(name = "Pirata_idPirata")
    private int pirataidPirata;
    @Basic(optional = false)
    @Column(name = "Nave_nombreNave")
    private String navenombreNave;

    public PiratahasDesbloqueoNavePK() {
    }

    public PiratahasDesbloqueoNavePK(int pirataidPirata, String navenombreNave) {
        this.pirataidPirata = pirataidPirata;
        this.navenombreNave = navenombreNave;
    }

    public int getPirataidPirata() {
        return pirataidPirata;
    }
    
    public void setPirataidPirata(int pirataidPirata) {
        this.pirataidPirata = pirataidPirata;
    }

    public String getNavenombreNave() {
        return navenombreNave;
    }
    
    public void setNavenombreNave(String navenombreNave) {
        this.navenombreNave = navenombreNave;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) pirataidPirata;
        hash += (navenombreNave != null ? navenombreNave.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PiratahasDesbloqueoNavePK)) {
            return false;
        }
        PiratahasDesbloqueoNavePK other = (PiratahasDesbloqueoNavePK) object;
        if (this.pirataidPirata != other.pirataidPirata) {
            return false;
        }
        if ((this.navenombreNave == null && other.navenombreNave != null) || (this.navenombreNave != null && !this.navenombreNave.equals(other.navenombreNave))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tfgllopis.moduloTecnico.PiratahasDesbloqueoNavePK[ pirataidPirata=" + pirataidPirata + ", navenombreNave=" + navenombreNave + " ]";
    }
    
}
