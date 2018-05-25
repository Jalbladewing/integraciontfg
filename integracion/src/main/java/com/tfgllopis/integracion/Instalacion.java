package com.tfgllopis.integracion;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name = "Instalacion", catalog = "mydb2", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Instalacion.findAll", query = "SELECT i FROM Instalacion i")
    , @NamedQuery(name = "Instalacion.findByName", query = "SELECT i FROM Instalacion i WHERE i.name = :name")
    , @NamedQuery(name = "Instalacion.findByGeneracionBase", query = "SELECT i FROM Instalacion i WHERE i.generacionBase = :generacionBase")})
public class Instalacion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @Column(name = "generacionBase")
    private float generacionBase;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "instalacion")
    private List<InstalacioncuestaRecurso> instalacioncuestaRecursoList;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "instalacion")
    private Recurso recurso;

    public Instalacion()
    {
    	
    }
    
    public Instalacion(String name, float generacionBase) {
        this.name = name;
        this.generacionBase = generacionBase;
    }

    public String getName() {
        return name;
    }

    public float getGeneracionBase() {
        return generacionBase;
    }
    
    public void setGeneracionBase(float generacionBase) {
        this.generacionBase = generacionBase;
    }

    @XmlTransient
    public List<InstalacioncuestaRecurso> getInstalacioncuestaRecursoList() {
        return instalacioncuestaRecursoList;
    }

    public void setInstalacioncuestaRecursoList(List<InstalacioncuestaRecurso> instalacioncuestaRecursoList) {
        this.instalacioncuestaRecursoList = instalacioncuestaRecursoList;
    }

    @XmlTransient
    public Recurso getRecurso() {
        return recurso;
    }

    public void setRecurso(Recurso recurso) {
        this.recurso = recurso;
    }
    
    public static Instalacion cargarInstalacion(String nombreInstalacion, InstalacionRepository repo)
    {
    	return repo.findByName(nombreInstalacion);
    }
    
}
