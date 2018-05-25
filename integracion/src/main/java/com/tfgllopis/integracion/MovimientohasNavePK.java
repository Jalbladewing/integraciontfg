package com.tfgllopis.integracion;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class MovimientohasNavePK implements Serializable {

    @Basic(optional = false)
    @Column(name = "Movimiento_idMovimiento")
    private int movimientoidMovimiento;
    @Basic(optional = false)
    @Column(name = "Nave_nombreNave")
    private String navenombreNave;

    public MovimientohasNavePK() {
    }

    public MovimientohasNavePK(int movimientoidMovimiento, String navenombreNave) {
        this.movimientoidMovimiento = movimientoidMovimiento;
        this.navenombreNave = navenombreNave;
    }

    public int getMovimientoidMovimiento() {
        return movimientoidMovimiento;
    }

    public String getNavenombreNave() {
        return navenombreNave;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) movimientoidMovimiento;
        hash += (navenombreNave != null ? navenombreNave.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MovimientohasNavePK)) {
            return false;
        }
        MovimientohasNavePK other = (MovimientohasNavePK) object;
        if (this.movimientoidMovimiento != other.movimientoidMovimiento) {
            return false;
        }
        if ((this.navenombreNave == null && other.navenombreNave != null) || (this.navenombreNave != null && !this.navenombreNave.equals(other.navenombreNave))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tfgllopis.moduloNaves.MovimientohasNavePK[ movimientoidMovimiento=" + movimientoidMovimiento + ", navenombreNave=" + navenombreNave + " ]";
    }
    
}
