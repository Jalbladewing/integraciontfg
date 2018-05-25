package com.tfgllopis.integracion;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class PlanetahasInstalacionPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "Planeta_coordenadaX")
    private int planetacoordenadaX;
    @Basic(optional = false)
    @Column(name = "Planeta_coordenadaY")
    private int planetacoordenadaY;
    @Basic(optional = false)
    @Column(name = "Planeta_Sistema_nombreSistema")
    private String planetaSistemanombreSistema;
    @Basic(optional = false)
    @Column(name = "Instalacion_name")
    private String instalacionname;

    public PlanetahasInstalacionPK() {
    }

    public PlanetahasInstalacionPK(int planetacoordenadaX, int planetacoordenadaY, String planetaSistemanombreSistema, String instalacionname) {
        this.planetacoordenadaX = planetacoordenadaX;
        this.planetacoordenadaY = planetacoordenadaY;
        this.planetaSistemanombreSistema = planetaSistemanombreSistema;
        this.instalacionname = instalacionname;
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

    public String getInstalacionname() {
        return instalacionname;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) planetacoordenadaX;
        hash += (int) planetacoordenadaY;
        hash += (planetaSistemanombreSistema != null ? planetaSistemanombreSistema.hashCode() : 0);
        hash += (instalacionname != null ? instalacionname.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PlanetahasInstalacionPK)) {
            return false;
        }
        PlanetahasInstalacionPK other = (PlanetahasInstalacionPK) object;
        if (this.planetacoordenadaX != other.planetacoordenadaX) {
            return false;
        }
        if (this.planetacoordenadaY != other.planetacoordenadaY) {
            return false;
        }
        if ((this.planetaSistemanombreSistema == null && other.planetaSistemanombreSistema != null) || (this.planetaSistemanombreSistema != null && !this.planetaSistemanombreSistema.equals(other.planetaSistemanombreSistema))) {
            return false;
        }
        if ((this.instalacionname == null && other.instalacionname != null) || (this.instalacionname != null && !this.instalacionname.equals(other.instalacionname))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tfgllopis.moduloTecnico.PlanetahasInstalacionPK[ planetacoordenadaX=" + planetacoordenadaX + ", planetacoordenadaY=" + planetacoordenadaY + ", planetaSistemanombreSistema=" + planetaSistemanombreSistema + ", instalacionname=" + instalacionname + " ]";
    }
    
}
