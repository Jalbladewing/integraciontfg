package com.tfgllopis.integracion;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name = "Planeta", catalog = "mydb2", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Planeta.findAll", query = "SELECT p FROM Planeta p")
    , @NamedQuery(name = "Planeta.findByCoordenadaX", query = "SELECT p FROM Planeta p WHERE p.planetaPK.coordenadaX = :coordenadaX")
    , @NamedQuery(name = "Planeta.findByCoordenadaY", query = "SELECT p FROM Planeta p WHERE p.planetaPK.coordenadaY = :coordenadaY")
    , @NamedQuery(name = "Planeta.findPlaneta", query = "SELECT p FROM Planeta p WHERE p.planetaPK.coordenadaX = :coordenadaX AND p.planetaPK.coordenadaY = :coordenadaY AND p.planetaPK.sistemanombreSistema = :sistemanombreSistema")
    , @NamedQuery(name = "Planeta.findByNombrePlaneta", query = "SELECT p FROM Planeta p WHERE p.nombrePlaneta = :nombrePlaneta")
    , @NamedQuery(name = "Planeta.findByFinProteccion", query = "SELECT p FROM Planeta p WHERE p.finProteccion = :finProteccion")
    , @NamedQuery(name = "Planeta.findBySistemanombreSistema", query = "SELECT p FROM Planeta p WHERE p.planetaPK.sistemanombreSistema = :sistemanombreSistema")
    , @NamedQuery(name = "Planeta.findByPlanetaLibre", query = "SELECT p FROM Planeta p WHERE p.usuariousername IS NULL")
    , @NamedQuery(name = "Planeta.findByUsuarioUsername", query = "SELECT p FROM Planeta p WHERE p.usuariousername = :usuariousername")})
public class Planeta implements Serializable {

	private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected PlanetaPK planetaPK;
    @Basic(optional = false)
    @Column(name = "nombrePlaneta")
    private String nombrePlaneta;
    @Column(name = "finProteccion")
    @Temporal(TemporalType.DATE)
    private Date finProteccion;
    @Basic(optional = false)
    @Column(name = "rutaImagenPlaneta")
    private String rutaImagenPlaneta;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "planeta")
    private List<PlanetaHasNave> planetahasNaveList;
    @JoinColumn(name = "Pirata_idPirata", referencedColumnName = "idPirata")
    @ManyToOne
    private Pirata pirataidPirata;
    @JoinColumn(name = "Sistema_nombreSistema", referencedColumnName = "nombreSistema", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Sistema sistema;
    @JoinColumn(name = "Usuario_username", referencedColumnName = "username")
    @ManyToOne
    private Usuario usuariousername;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "planeta")
    private List<PlanetahasRecurso> planetahasRecursoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "planeta")
    private List<PlanetahasInstalacion> planetahasInstalacionList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "planeta")
    private List<Movimiento> movimientoList;
    @JoinColumn(name = "InformeBatalla_idBatalla", referencedColumnName = "idBatalla")
    @ManyToOne
    private InformeBatalla informeBatallaidBatalla;

    public Planeta() {
    }

    public Planeta(PlanetaPK planetaPK, String nombrePlaneta, String rutaImagenPlaneta) {
        this.planetaPK = planetaPK;
        this.nombrePlaneta = nombrePlaneta;
        this.rutaImagenPlaneta = rutaImagenPlaneta;
    }

    public Planeta(int coordenadaX, int coordenadaY, String sistemanombreSistema, String nombrePlaneta, String rutaImagenPlaneta) 
    {
        this.planetaPK = new PlanetaPK(coordenadaX, coordenadaY, sistemanombreSistema);
        this.nombrePlaneta = nombrePlaneta;
        this.rutaImagenPlaneta = rutaImagenPlaneta;
    }

    public PlanetaPK getPlanetaPK() {
        return planetaPK;
    }

    public String getNombrePlaneta() {
        return nombrePlaneta;
    }

    public Date getFinProteccion() {
        return finProteccion;
    }

    public void setFinProteccion(Date finProteccion) {
        this.finProteccion = finProteccion;
    }

    public String getRutaImagenPlaneta() {
        return rutaImagenPlaneta;
    }
    
    public int getCoordenadaX() {
        return planetaPK.getCoordenadaX();
    }

    public int getCoordenadaY() {
        return planetaPK.getCoordenadaY();
    }

    public String getSistemanombreSistema() {
        return planetaPK.getSistemanombreSistema();
    }
    
    @XmlTransient
    public List<PlanetaHasNave> getPlanetahasNaveList() {
        return planetahasNaveList;
    }

    public void setPlanetahasNaveList(List<PlanetaHasNave> planetahasNaveList) {
        this.planetahasNaveList = planetahasNaveList;
    }
    
    public Pirata getPirataidPirata() {
        return pirataidPirata;
    }

    public void setPirataidPirata(Pirata pirataidPirata) {
        this.pirataidPirata = pirataidPirata;
    }

    public Sistema getSistema() {
        return sistema;
    }

    public void setSistema(Sistema sistema) {
        this.sistema = sistema;
    }

    public Usuario getUsuariousername() {
        return usuariousername;
    }

    public void setUsuariousername(Usuario usuariousername) {
        this.usuariousername = usuariousername;
    }    

    @XmlTransient
    public List<PlanetahasRecurso> getPlanetahasRecursoList() {
        return planetahasRecursoList;
    }

    public void setPlanetahasRecursoList(List<PlanetahasRecurso> planetahasRecursoList) {
        this.planetahasRecursoList = planetahasRecursoList;
    }

    @XmlTransient
    public List<PlanetahasInstalacion> getPlanetahasInstalacionList() {
        return planetahasInstalacionList;
    }

    public void setPlanetahasInstalacionList(List<PlanetahasInstalacion> planetahasInstalacionList) {
        this.planetahasInstalacionList = planetahasInstalacionList;
    }
    
    @XmlTransient
    public List<Movimiento> getMovimientoList() {
        return movimientoList;
    }

    public void setMovimientoList(List<Movimiento> movimientoList) {
        this.movimientoList = movimientoList;
    }
    
    public InformeBatalla getInformeBatallaidBatalla() {
        return informeBatallaidBatalla;
    }

    public void setInformeBatallaidBatalla(InformeBatalla informeBatallaidBatalla) {
        this.informeBatallaidBatalla = informeBatallaidBatalla;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (planetaPK != null ? planetaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Planeta)) {
            return false;
        }
        Planeta other = (Planeta) object;
        if ((this.planetaPK == null && other.planetaPK != null) || (this.planetaPK != null && !this.planetaPK.equals(other.planetaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.teamburton.Planeta[ planetaPK=" + planetaPK + " ]";
    }
    
}
