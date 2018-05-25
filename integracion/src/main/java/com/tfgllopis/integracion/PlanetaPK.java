package com.tfgllopis.integracion;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class PlanetaPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "coordenadaX", nullable = false)
    private int coordenadaX;
    @Basic(optional = false)
    @Column(name = "coordenadaY", nullable = false)
    private int coordenadaY;
    @Basic(optional = false)
    @Column(name = "Sistema_nombreSistema", nullable = false, length = 50)
    private String sistemanombreSistema;

    public PlanetaPK() {
    }

    public PlanetaPK(int coordenadaX, int coordenadaY, String sistemanombreSistema) {
        this.coordenadaX = coordenadaX;
        this.coordenadaY = coordenadaY;
        this.sistemanombreSistema = sistemanombreSistema;
    }

    public int getCoordenadaX() {
        return coordenadaX;
    }

    public int getCoordenadaY() {
        return coordenadaY;
    }

    public String getSistemanombreSistema() {
        return sistemanombreSistema;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) coordenadaX;
        hash += (int) coordenadaY;
        hash += (sistemanombreSistema != null ? sistemanombreSistema.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PlanetaPK)) {
            return false;
        }
        PlanetaPK other = (PlanetaPK) object;
        if (this.coordenadaX != other.coordenadaX) {
            return false;
        }
        if (this.coordenadaY != other.coordenadaY) {
            return false;
        }
        if ((this.sistemanombreSistema == null && other.sistemanombreSistema != null) || (this.sistemanombreSistema != null && !this.sistemanombreSistema.equals(other.sistemanombreSistema))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.teamburton.PlanetaPK[ coordenadaX=" + coordenadaX + ", coordenadaY=" + coordenadaY + ", sistemanombreSistema=" + sistemanombreSistema + " ]";
    }
    
}
