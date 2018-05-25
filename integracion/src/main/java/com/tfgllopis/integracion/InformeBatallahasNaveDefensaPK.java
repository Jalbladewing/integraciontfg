package com.tfgllopis.integracion;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class InformeBatallahasNaveDefensaPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "InformeBatalla_idBatalla")
    private int informeBatallaidBatalla;
    @Basic(optional = false)
    @Column(name = "Nave_nombreNave")
    private String navenombreNave;

    public InformeBatallahasNaveDefensaPK() {
    }

    public InformeBatallahasNaveDefensaPK(int informeBatallaidBatalla, String navenombreNave) {
        this.informeBatallaidBatalla = informeBatallaidBatalla;
        this.navenombreNave = navenombreNave;
    }

    public int getInformeBatallaidBatalla() {
        return informeBatallaidBatalla;
    }

    public String getNavenombreNave() {
        return navenombreNave;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) informeBatallaidBatalla;
        hash += (navenombreNave != null ? navenombreNave.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InformeBatallahasNaveDefensaPK)) {
            return false;
        }
        InformeBatallahasNaveDefensaPK other = (InformeBatallahasNaveDefensaPK) object;
        if (this.informeBatallaidBatalla != other.informeBatallaidBatalla) {
            return false;
        }
        if ((this.navenombreNave == null && other.navenombreNave != null) || (this.navenombreNave != null && !this.navenombreNave.equals(other.navenombreNave))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tfgllopis.moduloNaves.InformeBatallahasNaveDefensaPK[ informeBatallaidBatalla=" + informeBatallaidBatalla + ", navenombreNave=" + navenombreNave + " ]";
    }
    
}
