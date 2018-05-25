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
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "Usuario_has_Nave")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UsuarioHasNave.findAll", query = "SELECT u FROM UsuarioHasNave u")
    , @NamedQuery(name = "UsuarioHasNave.findByUsuariousername", query = "SELECT u FROM UsuarioHasNave u WHERE u.usuarioHasNavePK.usuariousername = :usuariousername")
    , @NamedQuery(name = "UsuarioHasNave.findByNavenombreNave", query = "SELECT u FROM UsuarioHasNave u WHERE u.usuarioHasNavePK.navenombreNave = :navenombreNave")
    , @NamedQuery(name = "UsuarioHasNave.findByCantidad", query = "SELECT u FROM UsuarioHasNave u WHERE u.cantidad = :cantidad")
	, @NamedQuery(name = "UsuarioHasNave.findByNavenombreNaveUsuarioUsername", query = "SELECT u FROM UsuarioHasNave u WHERE u.usuarioHasNavePK.navenombreNave = :navenombreNave AND u.usuarioHasNavePK.usuariousername = :usuariousername")})
public class UsuarioHasNave implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected UsuarioHasNavePK usuarioHasNavePK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "cantidad")
    private int cantidad;
    @JoinColumn(name = "Nave_nombreNave", referencedColumnName = "nombreNave", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Nave nave;
    @JoinColumn(name = "Usuario_username", referencedColumnName = "username", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Usuario usuario;

    public UsuarioHasNave() {
    }

    public UsuarioHasNave(UsuarioHasNavePK usuarioHasNavePK, int cantidad) 
    {
        this.usuarioHasNavePK = usuarioHasNavePK;
        this.cantidad = cantidad;
    }

    public UsuarioHasNave(String usuariousername, String navenombreNave, int cantidad) 
    {
        this.usuarioHasNavePK = new UsuarioHasNavePK(usuariousername, navenombreNave);
        this.cantidad = cantidad;
    }

    public UsuarioHasNavePK getUsuarioHasNavePK() {
        return usuarioHasNavePK;
    }
    
    public String getUsuariousername() {
        return usuarioHasNavePK.getUsuariousername();
    }

    public String getNavenombreNave() {
    	return usuarioHasNavePK.getNavenombreNave();
    }

    public int getCantidad() {
        return cantidad;
    }
    
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Nave getNave() {
        return nave;
    }

    public void setNave(Nave nave) {
        this.nave = nave;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (usuarioHasNavePK != null ? usuarioHasNavePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UsuarioHasNave)) {
            return false;
        }
        UsuarioHasNave other = (UsuarioHasNave) object;
        if ((this.usuarioHasNavePK == null && other.usuarioHasNavePK != null) || (this.usuarioHasNavePK != null && !this.usuarioHasNavePK.equals(other.usuarioHasNavePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "teamburton.UsuarioHasNave[ usuarioHasNavePK=" + usuarioHasNavePK + " ]";
    }
    
}
