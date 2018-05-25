package com.tfgllopis.integracion;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class InformeBatallahasNaveAtaquePK implements Serializable {

    @Basic(optional = false)
    @Column(name = "InformeBatalla_idBatalla")
    private int informeBatallaidBatalla;
    @Basic(optional = false)
    @Column(name = "Nave_nombreNave")
    private String navenombreNave;

    public InformeBatallahasNaveAtaquePK() {
    }

    public InformeBatallahasNaveAtaquePK(int informeBatallaidBatalla, String navenombreNave) {
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
        if (!(object instanceof InformeBatallahasNaveAtaquePK)) {
            return false;
        }
        InformeBatallahasNaveAtaquePK other = (InformeBatallahasNaveAtaquePK) object;
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
        return "com.tfgllopis.moduloNaves.InformeBatallahasNaveAtaquePK[ informeBatallaidBatalla=" + informeBatallaidBatalla + ", navenombreNave=" + navenombreNave + " ]";
    }
    
}
