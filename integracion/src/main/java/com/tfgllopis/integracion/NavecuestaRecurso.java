package com.tfgllopis.integracion;

import java.io.Serializable;
import java.util.List;

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
@Table(name = "Nave_cuesta_Recurso", catalog = "mydb2", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "NavecuestaRecurso.findAll", query = "SELECT n FROM NavecuestaRecurso n")
    , @NamedQuery(name = "NavecuestaRecurso.findByNavenombreNave", query = "SELECT n FROM NavecuestaRecurso n WHERE n.navecuestaRecursoPK.navenombreNave = :navenombreNave")
    , @NamedQuery(name = "NavecuestaRecurso.findByRecursoname", query = "SELECT n FROM NavecuestaRecurso n WHERE n.navecuestaRecursoPK.recursoname = :recursoname")
    , @NamedQuery(name = "NavecuestaRecurso.findByRecursoInstalacionname", query = "SELECT n FROM NavecuestaRecurso n WHERE n.navecuestaRecursoPK.recursoInstalacionname = :recursoInstalacionname")
    , @NamedQuery(name = "NavecuestaRecurso.findByCantidadBase", query = "SELECT n FROM NavecuestaRecurso n WHERE n.cantidadBase = :cantidadBase")
    , @NamedQuery(name = "NavecuestaRecurso.findByRecursoname_NavenombreNave", query = "SELECT n FROM NavecuestaRecurso n WHERE n.navecuestaRecursoPK.navenombreNave = :navenombreNave AND n.navecuestaRecursoPK.recursoname = :recursoname")})
public class NavecuestaRecurso implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected NavecuestaRecursoPK navecuestaRecursoPK;
    @Basic(optional = false)
    @Column(name = "cantidadBase")
    private int cantidadBase;
    @JoinColumn(name = "Nave_nombreNave", referencedColumnName = "nombreNave", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Nave nave;
    @JoinColumns({
        @JoinColumn(name = "Recurso_name", referencedColumnName = "name", insertable = false, updatable = false)
        , @JoinColumn(name = "Recurso_Instalacion_name", referencedColumnName = "Instalacion_name", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private Recurso recurso;

    public NavecuestaRecurso()
    {
    	
    }
    
    public NavecuestaRecurso(NavecuestaRecursoPK navecuestaRecursoPK, int cantidadBase) {
        this.navecuestaRecursoPK = navecuestaRecursoPK;
        this.cantidadBase = cantidadBase;
    }

    public NavecuestaRecurso(String navenombreNave, String recursoname, String recursoInstalacionname, int cantidadBase) {
        this.navecuestaRecursoPK = new NavecuestaRecursoPK(navenombreNave, recursoname, recursoInstalacionname);
        this.cantidadBase = cantidadBase;
    }
    
    public String getNavenombreNave() 
    {
        return navecuestaRecursoPK.getNavenombreNave();
    }
    
    public void setNavenombreNave(String navenombreNave)
    {
    	this.navecuestaRecursoPK.setNavenombreNave(navenombreNave);
    }

    public String getRecursoname() 
    {
    	return navecuestaRecursoPK.getRecursoname();
    }
    
    public void setRecursoname(String recursoname)
    {
    	this.navecuestaRecursoPK.setRecursoname(recursoname);
    }

    public String getRecursoInstalacionname() 
    {
    	return navecuestaRecursoPK.getRecursoInstalacionname();
    }
    
    public void setRecursoInstalacionname(String recursoInstalacionname)
    {
    	this.navecuestaRecursoPK.setRecursoInstalacionname(recursoInstalacionname);
    }

    public NavecuestaRecursoPK getNavecuestaRecursoPK() {
        return navecuestaRecursoPK;
    }

    public int getCantidadBase() {
        return cantidadBase;
    }
    
    public void setCantidadBase(int cantidadBase) {
        this.cantidadBase = cantidadBase;
    }

    public Nave getNave() {
        return nave;
    }

    public void setNave(Nave nave) {
        this.nave = nave;
    }

    public Recurso getRecurso() {
        return recurso;
    }

    public void setRecurso(Recurso recurso) {
        this.recurso = recurso;
    }
    
    public void guardarNaveCuestaRecurso(NavecuestaRecursoRepository repo)
    {
    	repo.save(this);
    }
    
    public static int cargarCosteNave(String nombreRecurso, String nombreNave, NavecuestaRecursoRepository repo)
    {
    	int coste = repo.findByRecursoname_NavenombreNave(nombreNave, nombreRecurso).getCantidadBase();
    	return coste;
    }
    
    public static NavecuestaRecurso cargarNaveCuestaRecurso(String nombreRecurso, String nombreNave, NavecuestaRecursoRepository repo)
    {
    	return repo.findByRecursoname_NavenombreNave(nombreNave, nombreRecurso);
    }
    
}
