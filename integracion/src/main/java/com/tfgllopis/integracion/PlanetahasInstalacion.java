package com.tfgllopis.integracion;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "Planeta_has_Instalacion", catalog = "mydb2", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PlanetahasInstalacion.findAll", query = "SELECT p FROM PlanetahasInstalacion p")
    , @NamedQuery(name = "PlanetahasInstalacion.findByPlanetacoordenadaX", query = "SELECT p FROM PlanetahasInstalacion p WHERE p.planetahasInstalacionPK.planetacoordenadaX = :planetacoordenadaX")
    , @NamedQuery(name = "PlanetahasInstalacion.findByPlanetacoordenadaY", query = "SELECT p FROM PlanetahasInstalacion p WHERE p.planetahasInstalacionPK.planetacoordenadaY = :planetacoordenadaY")
    , @NamedQuery(name = "PlanetahasInstalacion.findByPlanetaSistemanombreSistema", query = "SELECT p FROM PlanetahasInstalacion p WHERE p.planetahasInstalacionPK.planetaSistemanombreSistema = :planetaSistemanombreSistema")
    , @NamedQuery(name = "PlanetahasInstalacion.findByInstalacionname", query = "SELECT p FROM PlanetahasInstalacion p WHERE p.planetahasInstalacionPK.instalacionname = :instalacionname")
    , @NamedQuery(name = "PlanetahasInstalacion.findByNivelInstalacion", query = "SELECT p FROM PlanetahasInstalacion p WHERE p.nivelInstalacion = :nivelInstalacion")
    , @NamedQuery(name = "PlanetahasInstalacion.findByUltimaGeneracion", query = "SELECT p FROM PlanetahasInstalacion p WHERE p.ultimaGeneracion = :ultimaGeneracion")
    , @NamedQuery(name = "PlanetahasInstalacion.findByInstalacionnamePlaneta", query = "SELECT p FROM PlanetahasInstalacion p WHERE p.planetahasInstalacionPK.instalacionname = :instalacionname AND p.planetahasInstalacionPK.planetacoordenadaX = :planetacoordenadaX AND p.planetahasInstalacionPK.planetacoordenadaY = :planetacoordenadaY AND p.planetahasInstalacionPK.planetaSistemanombreSistema = :planetaSistemanombreSistema")})
public class PlanetahasInstalacion implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected PlanetahasInstalacionPK planetahasInstalacionPK;
    @Basic(optional = false)
    @Column(name = "nivelInstalacion")
    private int nivelInstalacion;
    @Basic(optional = false)
    @Column(name = "ultimaGeneracion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ultimaGeneracion;
    @JoinColumn(name = "Instalacion_name", referencedColumnName = "name", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Instalacion instalacion;
    @JoinColumns({
        @JoinColumn(name = "Planeta_coordenadaX", referencedColumnName = "coordenadaX", insertable = false, updatable = false)
        , @JoinColumn(name = "Planeta_coordenadaY", referencedColumnName = "coordenadaY", insertable = false, updatable = false)
        , @JoinColumn(name = "Planeta_Sistema_nombreSistema", referencedColumnName = "Sistema_nombreSistema", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private Planeta planeta;

    public PlanetahasInstalacion() {
    }

    public PlanetahasInstalacion(PlanetahasInstalacionPK planetahasInstalacionPK, int nivelInstalacion, Date ultimaGeneracion) {
        this.planetahasInstalacionPK = planetahasInstalacionPK;
        this.nivelInstalacion = nivelInstalacion;
        this.ultimaGeneracion = ultimaGeneracion;
    }

    public PlanetahasInstalacion(int planetacoordenadaX, int planetacoordenadaY, String planetaSistemanombreSistema, String instalacionname, int nivelInstalacion, Date ultimaGeneracion) {
        this.planetahasInstalacionPK = new PlanetahasInstalacionPK(planetacoordenadaX, planetacoordenadaY, planetaSistemanombreSistema, instalacionname);
        this.nivelInstalacion = nivelInstalacion;
        this.ultimaGeneracion = ultimaGeneracion;
    }

    public PlanetahasInstalacionPK getPlanetahasInstalacionPK() {
        return planetahasInstalacionPK;
    }

    public int getNivelInstalacion() {
        return nivelInstalacion;
    }
    
    public void subirNivelInstalacion()
    {
    	this.nivelInstalacion += 1;
    }
    
    public void resetNivelInstalacion()
    {
    	this.nivelInstalacion = 0;
    }

    public Date getUltimaGeneracion() {
        return ultimaGeneracion;
    }
    
    public void setUltimaGeneracion(Date fechaAhora) {
        ultimaGeneracion = fechaAhora;
    }
    
    public int getPlanetacoordenadaX() {
        return planetahasInstalacionPK.getPlanetacoordenadaX();
    }

    public int getPlanetacoordenadaY() {
    	return planetahasInstalacionPK.getPlanetacoordenadaY();
    }

    public String getPlanetaSistemanombreSistema() {
    	return planetahasInstalacionPK.getPlanetaSistemanombreSistema();
    }

    public String getInstalacionname() {
        return planetahasInstalacionPK.getInstalacionname();
    }

    public Instalacion getInstalacion() {
        return instalacion;
    }

    public void setInstalacion(Instalacion instalacion) {
        this.instalacion = instalacion;
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
        hash += (planetahasInstalacionPK != null ? planetahasInstalacionPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PlanetahasInstalacion)) {
            return false;
        }
        PlanetahasInstalacion other = (PlanetahasInstalacion) object;
        if ((this.planetahasInstalacionPK == null && other.planetahasInstalacionPK != null) || (this.planetahasInstalacionPK != null && !this.planetahasInstalacionPK.equals(other.planetahasInstalacionPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tfgllopis.moduloTecnico.PlanetahasInstalacion[ planetahasInstalacionPK=" + planetahasInstalacionPK + " ]";
    }
    
}
