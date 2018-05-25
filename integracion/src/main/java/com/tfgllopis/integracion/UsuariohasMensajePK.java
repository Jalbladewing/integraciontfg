package com.tfgllopis.integracion;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class UsuariohasMensajePK implements Serializable {

    @Basic(optional = false)
    @Column(name = "Usuario_username")
    private String usuariousername;
    @Basic(optional = false)
    @Column(name = "Mensaje_idMensaje")
    private int mensajeidMensaje;

    public UsuariohasMensajePK() {
    }

    public UsuariohasMensajePK(String usuariousername, int mensajeidMensaje) {
        this.usuariousername = usuariousername;
        this.mensajeidMensaje = mensajeidMensaje;
    }

    public String getUsuariousername() {
        return usuariousername;
    }

    public int getMensajeidMensaje() {
        return mensajeidMensaje;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (usuariousername != null ? usuariousername.hashCode() : 0);
        hash += (int) mensajeidMensaje;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UsuariohasMensajePK)) {
            return false;
        }
        UsuariohasMensajePK other = (UsuariohasMensajePK) object;
        if ((this.usuariousername == null && other.usuariousername != null) || (this.usuariousername != null && !this.usuariousername.equals(other.usuariousername))) {
            return false;
        }
        if (this.mensajeidMensaje != other.mensajeidMensaje) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tfgllopis.moduloNaves.UsuariohasMensajePK[ usuariousername=" + usuariousername + ", mensajeidMensaje=" + mensajeidMensaje + " ]";
    }
    
}
