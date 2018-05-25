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

@Entity
@Table(name = "Usuario_has_Mensaje")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UsuariohasMensaje.findAll", query = "SELECT u FROM UsuariohasMensaje u")
    , @NamedQuery(name = "UsuariohasMensaje.findByUsuariousername", query = "SELECT u FROM UsuariohasMensaje u WHERE u.usuariohasMensajePK.usuariousername = :usuariousername")
    , @NamedQuery(name = "UsuariohasMensaje.findByUsuariousernameNoDescartado", query = "SELECT u FROM UsuariohasMensaje u WHERE u.usuariohasMensajePK.usuariousername = :usuariousername AND u.descartado = 0")
    , @NamedQuery(name = "UsuariohasMensaje.findByMensajeidMensaje", query = "SELECT u FROM UsuariohasMensaje u WHERE u.usuariohasMensajePK.mensajeidMensaje = :mensajeidMensaje")
    , @NamedQuery(name = "UsuariohasMensaje.findByMensajeidMensajeUsuariousernameNoDescartado", query = "SELECT u FROM UsuariohasMensaje u WHERE u.usuariohasMensajePK.mensajeidMensaje = :mensajeidMensaje AND u.usuariohasMensajePK.usuariousername = :usuariousername AND u.descartado = 0")
    , @NamedQuery(name = "UsuariohasMensaje.findByDescartado", query = "SELECT u FROM UsuariohasMensaje u WHERE u.descartado = :descartado")})
public class UsuariohasMensaje implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected UsuariohasMensajePK usuariohasMensajePK;
    @Basic(optional = false)
    @Column(name = "descartado")
    private short descartado;
    @JoinColumn(name = "Mensaje_idMensaje", referencedColumnName = "idMensaje", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Mensaje mensaje;
    @JoinColumn(name = "Usuario_username", referencedColumnName = "username", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Usuario usuario;

    public UsuariohasMensaje() {
    }

    public UsuariohasMensaje(UsuariohasMensajePK usuariohasMensajePK, short descartado) {
        this.usuariohasMensajePK = usuariohasMensajePK;
        this.descartado = descartado;
    }

    public UsuariohasMensaje(String usuariousername, int mensajeidMensaje, short descartado) {
        this.usuariohasMensajePK = new UsuariohasMensajePK(usuariousername, mensajeidMensaje);
        this.descartado = descartado;
    }

    public UsuariohasMensajePK getUsuariohasMensajePK() {
        return usuariohasMensajePK;
    }
    
    public String getUsuariousername() {
        return usuariohasMensajePK.getUsuariousername();
    }

    public int getMensajeidMensaje() {
        return usuariohasMensajePK.getMensajeidMensaje();
    }

    public short getDescartado() {
        return descartado;
    }
    
    public void descartar() 
    {
        descartado = 1;
    }

    public Mensaje getMensaje() {
        return mensaje;
    }

    public void setMensaje(Mensaje mensaje) {
        this.mensaje = mensaje;
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
        hash += (usuariohasMensajePK != null ? usuariohasMensajePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UsuariohasMensaje)) {
            return false;
        }
        UsuariohasMensaje other = (UsuariohasMensaje) object;
        if ((this.usuariohasMensajePK == null && other.usuariohasMensajePK != null) || (this.usuariohasMensajePK != null && !this.usuariohasMensajePK.equals(other.usuariohasMensajePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tfgllopis.moduloNaves.UsuariohasMensaje[ usuariohasMensajePK=" + usuariohasMensajePK + " ]";
    }
    
}
