package com.tfgllopis.integracion;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "Planeta_has_Nave", catalog = "mydb2", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PlanetaHasNave.findAll", query = "SELECT p FROM PlanetaHasNave p")
    , @NamedQuery(name = "PlanetaHasNave.findByPlanetacoordenadaX", query = "SELECT p FROM PlanetaHasNave p WHERE p.planetaHasNavePK.planetacoordenadaX = :planetacoordenadaX")
    , @NamedQuery(name = "PlanetaHasNave.findByPlanetacoordenadaY", query = "SELECT p FROM PlanetaHasNave p WHERE p.planetaHasNavePK.planetacoordenadaY = :planetacoordenadaY")
    , @NamedQuery(name = "PlanetaHasNave.findByPlanetaSistemanombreSistema", query = "SELECT p FROM PlanetaHasNave p WHERE p.planetaHasNavePK.planetaSistemanombreSistema = :planetaSistemanombreSistema")
    , @NamedQuery(name = "PlanetaHasNave.findByNavenombreNave", query = "SELECT p FROM PlanetaHasNave p WHERE p.planetaHasNavePK.navenombreNave = :navenombreNave")
    , @NamedQuery(name = "PlanetaHasNave.findByCantidad", query = "SELECT p FROM PlanetaHasNave p WHERE p.cantidad = :cantidad")
    , @NamedQuery(name = "PlanetaHasNave.findByNavenombreNavePlaneta", query = "SELECT p FROM PlanetaHasNave p WHERE p.planetaHasNavePK.navenombreNave = :navenombreNave AND p.planetaHasNavePK.planetacoordenadaX = :planetacoordenadaX AND p.planetaHasNavePK.planetacoordenadaY = :planetacoordenadaY AND p.planetaHasNavePK.planetaSistemanombreSistema = :planetaSistemanombreSistema")
	, @NamedQuery(name = "PlanetaHasNave.findByPlaneta", query = "SELECT p FROM PlanetaHasNave p WHERE p.planetaHasNavePK.planetacoordenadaX = :planetacoordenadaX AND p.planetaHasNavePK.planetacoordenadaY = :planetacoordenadaY AND p.planetaHasNavePK.planetaSistemanombreSistema = :planetaSistemanombreSistema")})

public class PlanetaHasNave implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected PlanetaHasNavePK planetaHasNavePK;
    @Basic(optional = false)
    @Column(name = "cantidad", nullable = false)
    private int cantidad;
    @JoinColumn(name = "Nave_nombreNave", referencedColumnName = "nombreNave", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Nave nave;
    @JoinColumns({
        @JoinColumn(name = "Planeta_coordenadaX", referencedColumnName = "coordenadaX", nullable = false, insertable = false, updatable = false)
        , @JoinColumn(name = "Planeta_coordenadaY", referencedColumnName = "coordenadaY", nullable = false, insertable = false, updatable = false)
        , @JoinColumn(name = "Planeta_Sistema_nombreSistema", referencedColumnName = "Sistema_nombreSistema", nullable = false, insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private Planeta planeta;

    public PlanetaHasNave() {
    }

    public PlanetaHasNave(PlanetaHasNavePK planetaHasNavePK, int cantidad) 
    {
        this.planetaHasNavePK = planetaHasNavePK;
        this.cantidad = cantidad;
    }

    public PlanetaHasNave(int planetacoordenadaX, int planetacoordenadaY, String planetaSistemanombreSistema, String navenombreNave, int cantidad) 
    {
        this.planetaHasNavePK = new PlanetaHasNavePK(planetacoordenadaX, planetacoordenadaY, planetaSistemanombreSistema, navenombreNave);
        this.cantidad = cantidad;
    }

    public PlanetaHasNavePK getPlanetaHasNavePK() {
        return planetaHasNavePK;
    }

    public int getCantidad() {
        return cantidad;
    }
    
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
    
    public int getPlanetacoordenadaX() {
        return planetaHasNavePK.getPlanetacoordenadaX();
    }
    
    public int getPlanetacoordenadaY() {
    	return planetaHasNavePK.getPlanetacoordenadaY();
    }

    public String getPlanetaSistemanombreSistema() {
    	return planetaHasNavePK.getPlanetaSistemanombreSistema();
    }

    public String getNavenombreNave() {
    	return planetaHasNavePK.getNavenombreNave();
    }
    
    public Nave getNave() {
        return nave;
    }

    public void setNave(Nave nave) {
        this.nave = nave;
    }

    public Planeta getPlaneta() {
        return planeta;
    }

    public void setPlaneta(Planeta planeta) {
        this.planeta = planeta;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (planetaHasNavePK != null ? planetaHasNavePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PlanetaHasNave)) {
            return false;
        }
        PlanetaHasNave other = (PlanetaHasNave) object;
        if ((this.planetaHasNavePK == null && other.planetaHasNavePK != null) || (this.planetaHasNavePK != null && !this.planetaHasNavePK.equals(other.planetaHasNavePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.teamburton.PlanetaHasNave[ planetaHasNavePK=" + planetaHasNavePK + " ]";
    }
    
}
