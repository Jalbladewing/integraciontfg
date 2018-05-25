package com.tfgllopis.integracion;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Embeddable
public class UsuarioHasNavePK implements Serializable {

	@Basic(optional = false)
    @Column(name = "Usuario_username")
    private String usuariousername;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "Nave_nombreNave")
    private String navenombreNave;

    public UsuarioHasNavePK() {
    }

    public UsuarioHasNavePK(String usuariousername, String navenombreNave) {
        this.usuariousername  = usuariousername ;
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
        if (!(object instanceof UsuarioHasNavePK)) {
            return false;
        }
        UsuarioHasNavePK other = (UsuarioHasNavePK) object;
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
        return "teamburton.UsuarioHasNavePK[ usuarioemail=" + usuariousername + ", navenombreNave=" + navenombreNave + " ]";
    }
    
}
