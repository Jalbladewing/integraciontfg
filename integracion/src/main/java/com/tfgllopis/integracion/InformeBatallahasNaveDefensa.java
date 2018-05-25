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
@Table(name = "InformeBatalla_has_Nave_Defensa")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "InformeBatallahasNaveDefensa.findAll", query = "SELECT i FROM InformeBatallahasNaveDefensa i")
    , @NamedQuery(name = "InformeBatallahasNaveDefensa.findByInformeBatallaidBatalla", query = "SELECT i FROM InformeBatallahasNaveDefensa i WHERE i.informeBatallahasNaveDefensaPK.informeBatallaidBatalla = :informeBatallaidBatalla")
    , @NamedQuery(name = "InformeBatallahasNaveDefensa.findByNavenombreNave", query = "SELECT i FROM InformeBatallahasNaveDefensa i WHERE i.informeBatallahasNaveDefensaPK.navenombreNave = :navenombreNave")
    , @NamedQuery(name = "InformeBatallahasNaveDefensa.findByCantidadEnviada", query = "SELECT i FROM InformeBatallahasNaveDefensa i WHERE i.cantidadEnviada = :cantidadEnviada")
    , @NamedQuery(name = "InformeBatallahasNaveDefensa.findByCantidadPerdida", query = "SELECT i FROM InformeBatallahasNaveDefensa i WHERE i.cantidadPerdida = :cantidadPerdida")})
public class InformeBatallahasNaveDefensa implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected InformeBatallahasNaveDefensaPK informeBatallahasNaveDefensaPK;
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

    public InformeBatallahasNaveDefensa() {
    }

    public InformeBatallahasNaveDefensa(InformeBatallahasNaveDefensaPK informeBatallahasNaveDefensaPK, int cantidadEnviada, int cantidadPerdida) {
        this.informeBatallahasNaveDefensaPK = informeBatallahasNaveDefensaPK;
        this.cantidadEnviada = cantidadEnviada;
        this.cantidadPerdida = cantidadPerdida;
    }

    public InformeBatallahasNaveDefensa(int informeBatallaidBatalla, String navenombreNave, int cantidadEnviada, int cantidadPerdida) {
        this.informeBatallahasNaveDefensaPK = new InformeBatallahasNaveDefensaPK(informeBatallaidBatalla, navenombreNave);
        this.cantidadEnviada = cantidadEnviada;
        this.cantidadPerdida = cantidadPerdida;
    }

    public InformeBatallahasNaveDefensaPK getInformeBatallahasNaveDefensaPK() {
        return informeBatallahasNaveDefensaPK;
    }
    
    public int getInformeBatallaidBatalla() {
        return informeBatallahasNaveDefensaPK.getInformeBatallaidBatalla();
    }

    public String getNavenombreNave() {
        return informeBatallahasNaveDefensaPK.getNavenombreNave();
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
        hash += (informeBatallahasNaveDefensaPK != null ? informeBatallahasNaveDefensaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InformeBatallahasNaveDefensa)) {
            return false;
        }
        InformeBatallahasNaveDefensa other = (InformeBatallahasNaveDefensa) object;
        if ((this.informeBatallahasNaveDefensaPK == null && other.informeBatallahasNaveDefensaPK != null) || (this.informeBatallahasNaveDefensaPK != null && !this.informeBatallahasNaveDefensaPK.equals(other.informeBatallahasNaveDefensaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tfgllopis.moduloNaves.InformeBatallahasNaveDefensa[ informeBatallahasNaveDefensaPK=" + informeBatallahasNaveDefensaPK + " ]";
    }
    
}
