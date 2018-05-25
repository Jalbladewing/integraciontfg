package com.tfgllopis.integracion;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class UsuarioconstruyeNavePK implements Serializable {

    @Basic(optional = false)
    @Column(name = "Usuario_username")
    private String usuariousername;
    @Basic(optional = false)
    @Column(name = "Nave_nombreNave")
    private String navenombreNave;

    public UsuarioconstruyeNavePK() {
    }

    public UsuarioconstruyeNavePK(String usuariousername, String navenombreNave) {
        this.usuariousername = usuariousername;
        this.navenombreNave = navenombreNave;
    }

    public String getUsuariousername() {
        return usuariousername;
    }


    public String getNavenombreNave() {
        return navenombreNave;
    }


    @Override
    public int hashCode() {
        int hash = 0;
        hash += (usuariousername != null ? usuariousername.hashCode() : 0);
        hash += (navenombreNave != null ? navenombreNave.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UsuarioconstruyeNavePK)) {
            return false;
        }
        UsuarioconstruyeNavePK other = (UsuarioconstruyeNavePK) object;
        if ((this.usuariousername == null && other.usuariousername != null) || (this.usuariousername != null && !this.usuariousername.equals(other.usuariousername))) {
            return false;
        }
        if ((this.navenombreNave == null && other.navenombreNave != null) || (this.navenombreNave != null && !this.navenombreNave.equals(other.navenombreNave))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tfgllopis.moduloNaves.UsuarioconstruyeNavePK[ usuariousername=" + usuariousername + ", navenombreNave=" + navenombreNave + " ]";
    }
    
}
