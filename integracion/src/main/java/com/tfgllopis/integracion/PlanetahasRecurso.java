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
@Table(name = "Planeta_has_Recurso", catalog = "mydb2", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PlanetahasRecurso.findAll", query = "SELECT p FROM PlanetahasRecurso p")
    , @NamedQuery(name = "PlanetahasRecurso.findByPlanetacoordenadaX", query = "SELECT p FROM PlanetahasRecurso p WHERE p.planetahasRecursoPK.planetacoordenadaX = :planetacoordenadaX")
    , @NamedQuery(name = "PlanetahasRecurso.findByPlanetacoordenadaY", query = "SELECT p FROM PlanetahasRecurso p WHERE p.planetahasRecursoPK.planetacoordenadaY = :planetacoordenadaY")
    , @NamedQuery(name = "PlanetahasRecurso.findByPlanetaSistemanombreSistema", query = "SELECT p FROM PlanetahasRecurso p WHERE p.planetahasRecursoPK.planetaSistemanombreSistema = :planetaSistemanombreSistema")
    , @NamedQuery(name = "PlanetahasRecurso.findByRecursoname", query = "SELECT p FROM PlanetahasRecurso p WHERE p.planetahasRecursoPK.recursoname = :recursoname")
    , @NamedQuery(name = "PlanetahasRecurso.findByRecursoInstalacionname", query = "SELECT p FROM PlanetahasRecurso p WHERE p.planetahasRecursoPK.recursoInstalacionname = :recursoInstalacionname")
    , @NamedQuery(name = "PlanetahasRecurso.findByCantidad", query = "SELECT p FROM PlanetahasRecurso p WHERE p.cantidad = :cantidad")
    , @NamedQuery(name = "PlanetahasRecurso.findByPlanetaRecurso", query = "SELECT p FROM PlanetahasRecurso p WHERE p.planetahasRecursoPK.planetacoordenadaX = :planetacoordenadaX AND p.planetahasRecursoPK.planetacoordenadaY = :planetacoordenadaY AND p.planetahasRecursoPK.planetaSistemanombreSistema = :planetaSistemanombreSistema AND p.planetahasRecursoPK.recursoname = :recursoname")})
public class PlanetahasRecurso implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected PlanetahasRecursoPK planetahasRecursoPK;
    @Basic(optional = false)
    @Column(name = "cantidad")
    private int cantidad;
    @JoinColumns({
        @JoinColumn(name = "Planeta_coordenadaX", referencedColumnName = "coordenadaX", insertable = false, updatable = false)
        , @JoinColumn(name = "Planeta_coordenadaY", referencedColumnName = "coordenadaY", insertable = false, updatable = false)
        , @JoinColumn(name = "Planeta_Sistema_nombreSistema", referencedColumnName = "Sistema_nombreSistema", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private Planeta planeta;
    @JoinColumns({
        @JoinColumn(name = "Recurso_name", referencedColumnName = "name", insertable = false, updatable = false)
        , @JoinColumn(name = "Recurso_Instalacion_name", referencedColumnName = "Instalacion_name", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private Recurso recurso;

    public PlanetahasRecurso() {
    }

    public PlanetahasRecurso(PlanetahasRecursoPK planetahasRecursoPK, int cantidad) 
    {
        this.planetahasRecursoPK = planetahasRecursoPK;
        this.cantidad = cantidad;
    }

    public PlanetahasRecurso(int planetacoordenadaX, int planetacoordenadaY, String planetaSistemanombreSistema, String recursoname, String recursoInstalacionname, int cantidad) 
    {
        this.planetahasRecursoPK = new PlanetahasRecursoPK(planetacoordenadaX, planetacoordenadaY, planetaSistemanombreSistema, recursoname, recursoInstalacionname);
        this.cantidad = cantidad;
    }

    public PlanetahasRecursoPK getPlanetahasRecursoPK() {
        return planetahasRecursoPK;
    }

    public int getCantidad() {
        return cantidad;
    }
    
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Planeta getPlaneta() {
        return planeta;
    }

    public void setPlaneta(Planeta planeta) {
        this.planeta = planeta;
    }

    public Recurso getRecurso() {
        return recurso;
    }

    public void setRecurso(Recurso recurso) {
        this.recurso = recurso;
    }
    
    public int getPlanetacoordenadaX() {
        return planetahasRecursoPK.getPlanetacoordenadaX();
    }

    public int getPlanetacoordenadaY() {
    	 return planetahasRecursoPK.getPlanetacoordenadaY();
    }

    public String getPlanetaSistemanombreSistema() {
    	 return planetahasRecursoPK.getPlanetaSistemanombreSistema();
    }

    public String getRecursoname() {
    	 return planetahasRecursoPK.getRecursoname();
    }

    public String getRecursoInstalacionname() {
    	 return planetahasRecursoPK.getRecursoInstalacionname();
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (planetahasRecursoPK != null ? planetahasRecursoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PlanetahasRecurso)) {
            return false;
        }
        PlanetahasRecurso other = (PlanetahasRecurso) object;
        if ((this.planetahasRecursoPK == null && other.planetahasRecursoPK != null) || (this.planetahasRecursoPK != null && !this.planetahasRecursoPK.equals(other.planetahasRecursoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tfgllopis.moduloTecnico.PlanetahasRecurso[ planetahasRecursoPK=" + planetahasRecursoPK + " ]";
    }
    
}
