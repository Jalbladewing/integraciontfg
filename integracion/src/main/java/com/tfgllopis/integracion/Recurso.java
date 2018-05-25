package com.tfgllopis.integracion;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name = "Recurso", catalog = "mydb2", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Recurso.findAll", query = "SELECT r FROM Recurso r")
    , @NamedQuery(name = "Recurso.findByName", query = "SELECT r FROM Recurso r WHERE r.recursoPK.name = :name")
    , @NamedQuery(name = "Recurso.findByInstalacionname", query = "SELECT r FROM Recurso r WHERE r.recursoPK.instalacionname = :instalacionname")})
public class Recurso implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected RecursoPK recursoPK;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "recurso")
    private List<NavecuestaRecurso> navecuestaRecursoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "recurso")
    private List<InstalacioncuestaRecurso> instalacioncuestaRecursoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "recurso")
    private List<PlanetahasRecurso> planetahasRecursoList;
    @JoinColumn(name = "Instalacion_name", referencedColumnName = "name", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Instalacion instalacion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "recurso")
    private List<InformeBatallahasRecurso> informeBatallahasRecursoList;

    public Recurso()
    {
    	
    }
    
    public Recurso(RecursoPK recursoPK) {
        this.recursoPK = recursoPK;
    }

    public Recurso(String name, String instalacionname) {
        this.recursoPK = new RecursoPK(name, instalacionname);
    }

    public RecursoPK getRecursoPK() {
        return recursoPK;
    }

    public String getName() {
        return recursoPK.getName();
    }

    public String getInstalacionname() {
        return recursoPK.getInstalacionname();
    }

    @XmlTransient
    public List<NavecuestaRecurso> getNavecuestaRecursoList() {
        return navecuestaRecursoList;
    }

    public void setNavecuestaRecursoList(List<NavecuestaRecurso> navecuestaRecursoList) {
        this.navecuestaRecursoList = navecuestaRecursoList;
    }

    @XmlTransient
    public List<InstalacioncuestaRecurso> getInstalacioncuestaRecursoList() {
        return instalacioncuestaRecursoList;
    }

    public void setInstalacioncuestaRecursoList(List<InstalacioncuestaRecurso> instalacioncuestaRecursoList) {
        this.instalacioncuestaRecursoList = instalacioncuestaRecursoList;
    }
    
    @XmlTransient
    public List<PlanetahasRecurso> getPlanetahasRecursoList() {
        return planetahasRecursoList;
    }

    public void setPlanetahasRecursoList(List<PlanetahasRecurso> planetahasRecursoList) {
        this.planetahasRecursoList = planetahasRecursoList;
    }

    public Instalacion getInstalacion() {
        return instalacion;
    }

    public void setInstalacion(Instalacion instalacion) {
        this.instalacion = instalacion;
    }
    
    @XmlTransient
    public List<InformeBatallahasRecurso> getInformeBatallahasRecursoList() {
        return informeBatallahasRecursoList;
    }

    public void setInformeBatallahasRecursoList(List<InformeBatallahasRecurso> informeBatallahasRecursoList) {
        this.informeBatallahasRecursoList = informeBatallahasRecursoList;
    }
    
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Recurso)) {
            return false;
        }
        Recurso other = (Recurso) object;
        if ((this.recursoPK == null && other.recursoPK != null) || (this.recursoPK != null && !this.recursoPK.equals(other.recursoPK))) {
            return false;
        }
        return true;
    }
    
}
