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
@Table(name = "InformeBatalla_has_Nave_Ataque")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "InformeBatallahasNaveAtaque.findAll", query = "SELECT i FROM InformeBatallahasNaveAtaque i")
    , @NamedQuery(name = "InformeBatallahasNaveAtaque.findByInformeBatallaidBatalla", query = "SELECT i FROM InformeBatallahasNaveAtaque i WHERE i.informeBatallahasNaveAtaquePK.informeBatallaidBatalla = :informeBatallaidBatalla")
    , @NamedQuery(name = "InformeBatallahasNaveAtaque.findByNavenombreNave", query = "SELECT i FROM InformeBatallahasNaveAtaque i WHERE i.informeBatallahasNaveAtaquePK.navenombreNave = :navenombreNave")
    , @NamedQuery(name = "InformeBatallahasNaveAtaque.findByCantidadEnviada", query = "SELECT i FROM InformeBatallahasNaveAtaque i WHERE i.cantidadEnviada = :cantidadEnviada")
    , @NamedQuery(name = "InformeBatallahasNaveAtaque.findByCantidadPerdida", query = "SELECT i FROM InformeBatallahasNaveAtaque i WHERE i.cantidadPerdida = :cantidadPerdida")})
public class InformeBatallahasNaveAtaque implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected InformeBatallahasNaveAtaquePK informeBatallahasNaveAtaquePK;
    @Basic(optional = false)
    @Column(name = "cantidadEnviada")
    private int cantidadEnviada;
    @Basic(optional = false)
    @Column(name = "cantidadPerdida")
    private int cantidadPerdida;
    @JoinColumn(name = "InformeBatalla_idBatalla", referencedColumnName = "idBatalla", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private InformeBatalla informeBatalla;
    @JoinColumn(name = "Nave_nombreNave", referencedColumnName = "nombreNave", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Nave nave;

    public InformeBatallahasNaveAtaque() {
    }

    public InformeBatallahasNaveAtaque(InformeBatallahasNaveAtaquePK informeBatallahasNaveAtaquePK, int cantidadEnviada, int cantidadPerdida) {
        this.informeBatallahasNaveAtaquePK = informeBatallahasNaveAtaquePK;
        this.cantidadEnviada = cantidadEnviada;
        this.cantidadPerdida = cantidadPerdida;
    }

    public InformeBatallahasNaveAtaque(int informeBatallaidBatalla, String navenombreNave, int cantidadEnviada, int cantidadPerdida) {
        this.informeBatallahasNaveAtaquePK = new InformeBatallahasNaveAtaquePK(informeBatallaidBatalla, navenombreNave);
        this.cantidadEnviada = cantidadEnviada;
        this.cantidadPerdida = cantidadPerdida;
    }

    public InformeBatallahasNaveAtaquePK getInformeBatallahasNaveAtaquePK() {
        return informeBatallahasNaveAtaquePK;
    }

    public int getInformeBatallaidBatalla() {
        return informeBatallahasNaveAtaquePK.getInformeBatallaidBatalla();
    }

    public String getNavenombreNave() {
        return informeBatallahasNaveAtaquePK.getNavenombreNave();
    }

    public int getCantidadEnviada() {
        return cantidadEnviada;
    }

    public int getCantidadPerdida() {
        return cantidadPerdida;
    }

    public InformeBatalla getInformeBatalla() {
        return informeBatalla;
    }

    public void setInformeBatalla(InformeBatalla informeBatalla) {
        this.informeBatalla = informeBatalla;
    }

    public Nave getNave() {
        return nave;
    }

    public void setNave(Nave nave) {
        this.nave = nave;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (informeBatallahasNaveAtaquePK != null ? informeBatallahasNaveAtaquePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InformeBatallahasNaveAtaque)) {
            return false;
        }
        InformeBatallahasNaveAtaque other = (InformeBatallahasNaveAtaque) object;
        if ((this.informeBatallahasNaveAtaquePK == null && other.informeBatallahasNaveAtaquePK != null) || (this.informeBatallahasNaveAtaquePK != null && !this.informeBatallahasNaveAtaquePK.equals(other.informeBatallahasNaveAtaquePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tfgllopis.moduloNaves.InformeBatallahasNaveAtaque[ informeBatallahasNaveAtaquePK=" + informeBatallahasNaveAtaquePK + " ]";
    }
    
}
