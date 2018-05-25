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
@Table(name = "Movimiento_has_Nave")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MovimientohasNave.findAll", query = "SELECT m FROM MovimientohasNave m")
    , @NamedQuery(name = "MovimientohasNave.findByMovimientoidMovimiento", query = "SELECT m FROM MovimientohasNave m WHERE m.movimientohasNavePK.movimientoidMovimiento = :movimientoidMovimiento")
    , @NamedQuery(name = "MovimientohasNave.findByNavenombreNave", query = "SELECT m FROM MovimientohasNave m WHERE m.movimientohasNavePK.navenombreNave = :navenombreNave")
    , @NamedQuery(name = "MovimientohasNave.findByCantidad", query = "SELECT m FROM MovimientohasNave m WHERE m.cantidad = :cantidad")})
public class MovimientohasNave implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected MovimientohasNavePK movimientohasNavePK;
    @Basic(optional = false)
    @Column(name = "cantidad")
    private int cantidad;
    @JoinColumn(name = "Movimiento_idMovimiento", referencedColumnName = "idMovimiento", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Movimiento movimiento;
    @JoinColumn(name = "Nave_nombreNave", referencedColumnName = "nombreNave", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Nave nave;

    public MovimientohasNave() {
    }

    public MovimientohasNave(MovimientohasNavePK movimientohasNavePK, int cantidad) {
        this.movimientohasNavePK = movimientohasNavePK;
        this.cantidad = cantidad;
    }

    public MovimientohasNave(int movimientoidMovimiento, String navenombreNave, int cantidad) {
        this.movimientohasNavePK = new MovimientohasNavePK(movimientoidMovimiento, navenombreNave);
        this.cantidad = cantidad;
    }

    public MovimientohasNavePK getMovimientohasNavePK() {
        return movimientohasNavePK;
    }
    
    public int getMovimientoidMovimiento() {
        return movimientohasNavePK.getMovimientoidMovimiento();
    }

    public String getNavenombreNave() {
        return movimientohasNavePK.getNavenombreNave();
    }

    public int getCantidad() {
        return cantidad;
    }

    public Movimiento getMovimiento() {
        return movimiento;
    }

    public void setMovimiento(Movimiento movimiento) {
        this.movimiento = movimiento;
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
        hash += (movimientohasNavePK != null ? movimientohasNavePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MovimientohasNave)) {
            return false;
        }
        MovimientohasNave other = (MovimientohasNave) object;
        if ((this.movimientohasNavePK == null && other.movimientohasNavePK != null) || (this.movimientohasNavePK != null && !this.movimientohasNavePK.equals(other.movimientohasNavePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tfgllopis.moduloNaves.MovimientohasNave[ movimientohasNavePK=" + movimientohasNavePK + " ]";
    }
    
}
