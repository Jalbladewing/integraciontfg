package com.tfgllopis.integracion;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class PlanetaHasNavePK implements Serializable {

    @Basic(optional = false)
    @Column(name = "Planeta_coordenadaX", nullable = false)
    private int planetacoordenadaX;
    @Basic(optional = false)
    @Column(name = "Planeta_coordenadaY", nullable = false)
    private int planetacoordenadaY;
    @Basic(optional = false)
    @Column(name = "Planeta_Sistema_nombreSistema", nullable = false, length = 50)
    private String planetaSistemanombreSistema;
    @Basic(optional = false)
    @Column(name = "Nave_nombreNave", nullable = false, length = 50)
    private String navenombreNave;

    public PlanetaHasNavePK() {
    }

    public PlanetaHasNavePK(int planetacoordenadaX, int planetacoordenadaY, String planetaSistemanombreSistema, String navenombreNave) {
        this.planetacoordenadaX = planetacoordenadaX;
        this.planetacoordenadaY = planetacoordenadaY;
        this.planetaSistemanombreSistema = planetaSistemanombreSistema;
        this.navenombreNave = navenombreNave;
    }

    public int getPlanetacoordenadaX() {
        return planetacoordenadaX;
    }
    
    public int getPlanetacoordenadaY() {
        return planetacoordenadaY;
    }

    public String getPlanetaSistemanombreSistema() {
        return planetaSistemanombreSistema;
    }

    public String getNavenombreNave() {
        return navenombreNave;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) planetacoordenadaX;
        hash += (int) planetacoordenadaY;
        hash += (planetaSistemanombreSistema != null ? planetaSistemanombreSistema.hashCode() : 0);
        hash += (navenombreNave != null ? navenombreNave.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PlanetaHasNavePK)) {
            return false;
        }
        PlanetaHasNavePK other = (PlanetaHasNavePK) object;
        if (this.planetacoordenadaX != other.planetacoordenadaX) {
            return false;
        }
        if (this.planetacoordenadaY != other.planetacoordenadaY) {
            return false;
        }
        if ((this.planetaSistemanombreSistema == null && other.planetaSistemanombreSistema != null) || (this.planetaSistemanombreSistema != null && !this.planetaSistemanombreSistema.equals(other.planetaSistemanombreSistema))) {
            return false;
        }
        if ((this.navenombreNave == null && other.navenombreNave != null) || (this.navenombreNave != null && !this.navenombreNave.equals(other.navenombreNave))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.teamburton.PlanetaHasNavePK[ planetacoordenadaX=" + planetacoordenadaX + ", planetacoordenadaY=" + planetacoordenadaY + ", planetaSistemanombreSistema=" + planetaSistemanombreSistema + ", navenombreNave=" + navenombreNave + " ]";
    }
    
}
