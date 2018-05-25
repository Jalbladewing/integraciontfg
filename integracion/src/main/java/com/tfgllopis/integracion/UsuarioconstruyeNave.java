package com.tfgllopis.integracion;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "Usuario_construye_Nave")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UsuarioconstruyeNave.findAll", query = "SELECT u FROM UsuarioconstruyeNave u")
    , @NamedQuery(name = "UsuarioconstruyeNave.findByUsuariousername", query = "SELECT u FROM UsuarioconstruyeNave u WHERE u.usuarioconstruyeNavePK.usuariousername = :usuariousername")
    , @NamedQuery(name = "UsuarioconstruyeNave.findByNavenombreNave", query = "SELECT u FROM UsuarioconstruyeNave u WHERE u.usuarioconstruyeNavePK.navenombreNave = :navenombreNave")
    , @NamedQuery(name = "UsuarioconstruyeNave.findByCantidad", query = "SELECT u FROM UsuarioconstruyeNave u WHERE u.cantidad = :cantidad")
    , @NamedQuery(name = "UsuarioconstruyeNave.findByFinConstruccion", query = "SELECT u FROM UsuarioconstruyeNave u WHERE u.finConstruccion = :finConstruccion")})
public class UsuarioconstruyeNave implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected UsuarioconstruyeNavePK usuarioconstruyeNavePK;
    @Basic(optional = false)
    @Column(name = "cantidad")
    private int cantidad;
    @Basic(optional = false)
    @Column(name = "finConstruccion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date finConstruccion;
    @JoinColumn(name = "Nave_nombreNave", referencedColumnName = "nombreNave", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Nave nave;
    @JoinColumn(name = "Usuario_username", referencedColumnName = "username", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Usuario usuario;

    public UsuarioconstruyeNave() {
    }


    public UsuarioconstruyeNave(UsuarioconstruyeNavePK usuarioconstruyeNavePK, int cantidad, Date finConstruccion) {
        this.usuarioconstruyeNavePK = usuarioconstruyeNavePK;
        this.cantidad = cantidad;
        this.finConstruccion = finConstruccion;
    }

    public UsuarioconstruyeNave(String usuariousername, String navenombreNave, int cantidad, Date finConstruccion) {
        this.usuarioconstruyeNavePK = new UsuarioconstruyeNavePK(usuariousername, navenombreNave);
        this.cantidad = cantidad;
        this.finConstruccion = finConstruccion;
    }

    public UsuarioconstruyeNavePK getUsuarioconstruyeNavePK() {
        return usuarioconstruyeNavePK;
    }
    
    public String getUsuariousername() {
        return usuarioconstruyeNavePK.getUsuariousername();
    }


    public String getNavenombreNave() {
        return usuarioconstruyeNavePK.getNavenombreNave();
    }

    public int getCantidad() {
        return cantidad;
    }
    
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Date getFinConstruccion() {
        return finConstruccion;
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
        hash += (usuarioconstruyeNavePK != null ? usuarioconstruyeNavePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UsuarioconstruyeNave)) {
            return false;
        }
        UsuarioconstruyeNave other = (UsuarioconstruyeNave) object;
        if ((this.usuarioconstruyeNavePK == null && other.usuarioconstruyeNavePK != null) || (this.usuarioconstruyeNavePK != null && !this.usuarioconstruyeNavePK.equals(other.usuarioconstruyeNavePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tfgllopis.moduloNaves.UsuarioconstruyeNave[ usuarioconstruyeNavePK=" + usuarioconstruyeNavePK + " ]";
    }
    
}
