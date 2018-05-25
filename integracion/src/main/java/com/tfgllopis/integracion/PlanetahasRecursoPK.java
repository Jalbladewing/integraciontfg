package com.tfgllopis.integracion;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class PlanetahasRecursoPK implements Serializable {

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
    @Column(name = "Recurso_name")
    private String recursoname;
    @Basic(optional = false)
    @Column(name = "Recurso_Instalacion_name")
    private String recursoInstalacionname;

    public PlanetahasRecursoPK() {
    }

    public PlanetahasRecursoPK(int planetacoordenadaX, int planetacoordenadaY, String planetaSistemanombreSistema, String recursoname, String recursoInstalacionname) {
        this.planetacoordenadaX = planetacoordenadaX;
        this.planetacoordenadaY = planetacoordenadaY;
        this.planetaSistemanombreSistema = planetaSistemanombreSistema;
        this.recursoname = recursoname;
        this.recursoInstalacionname = recursoInstalacionname;
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

    public String getRecursoname() {
        return recursoname;
    }

    public String getRecursoInstalacionname() {
        return recursoInstalacionname;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) planetacoordenadaX;
        hash += (int) planetacoordenadaY;
        hash += (planetaSistemanombreSistema != null ? planetaSistemanombreSistema.hashCode() : 0);
        hash += (recursoname != null ? recursoname.hashCode() : 0);
        hash += (recursoInstalacionname != null ? recursoInstalacionname.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PlanetahasRecursoPK)) {
            return false;
        }
        PlanetahasRecursoPK other = (PlanetahasRecursoPK) object;
        if (this.planetacoordenadaX != other.planetacoordenadaX) {
            return false;
        }
        if (this.planetacoordenadaY != other.planetacoordenadaY) {
            return false;
        }
        if ((this.planetaSistemanombreSistema == null && other.planetaSistemanombreSistema != null) || (this.planetaSistemanombreSistema != null && !this.planetaSistemanombreSistema.equals(other.planetaSistemanombreSistema))) {
            return false;
        }
        if ((this.recursoname == null && other.recursoname != null) || (this.recursoname != null && !this.recursoname.equals(other.recursoname))) {
            return false;
        }
        if ((this.recursoInstalacionname == null && other.recursoInstalacionname != null) || (this.recursoInstalacionname != null && !this.recursoInstalacionname.equals(other.recursoInstalacionname))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tfgllopis.moduloTecnico.PlanetahasRecursoPK[ planetacoordenadaX=" + planetacoordenadaX + ", planetacoordenadaY=" + planetacoordenadaY + ", planetaSistemanombreSistema=" + planetaSistemanombreSistema + ", recursoname=" + recursoname + ", recursoInstalacionname=" + recursoInstalacionname + " ]";
    }
    
}
