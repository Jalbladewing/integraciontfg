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
@Table(name = "Instalacion_cuesta_Recurso", catalog = "mydb2", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "InstalacioncuestaRecurso.findAll", query = "SELECT i FROM InstalacioncuestaRecurso i")
    , @NamedQuery(name = "InstalacioncuestaRecurso.findByInstalacionname", query = "SELECT i FROM InstalacioncuestaRecurso i WHERE i.instalacioncuestaRecursoPK.instalacionname = :instalacionname")
    , @NamedQuery(name = "InstalacioncuestaRecurso.findByRecursoname", query = "SELECT i FROM InstalacioncuestaRecurso i WHERE i.instalacioncuestaRecursoPK.recursoname = :recursoname")
    , @NamedQuery(name = "InstalacioncuestaRecurso.findByRecursoInstalacionname", query = "SELECT i FROM InstalacioncuestaRecurso i WHERE i.instalacioncuestaRecursoPK.instalacionname = :instalacionname AND i.instalacioncuestaRecursoPK.recursoname = :recursoname")
    , @NamedQuery(name = "InstalacioncuestaRecurso.findByCantidadBase", query = "SELECT i FROM InstalacioncuestaRecurso i WHERE i.cantidadBase = :cantidadBase")})
public class InstalacioncuestaRecurso implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected InstalacioncuestaRecursoPK instalacioncuestaRecursoPK;
    @Basic(optional = false)
    @Column(name = "cantidadBase")
    private int cantidadBase;
    @JoinColumn(name = "Instalacion_name", referencedColumnName = "name", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Instalacion instalacion;
    @JoinColumns({
        @JoinColumn(name = "Recurso_name", referencedColumnName = "name", insertable = false, updatable = false)
        , @JoinColumn(name = "Recurso_Instalacion_name", referencedColumnName = "Instalacion_name", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private Recurso recurso;
    
    public InstalacioncuestaRecurso()
    {
    	
    }

    public InstalacioncuestaRecurso(InstalacioncuestaRecursoPK instalacioncuestaRecursoPK, int cantidadBase) {
        this.instalacioncuestaRecursoPK = instalacioncuestaRecursoPK;
        this.cantidadBase = cantidadBase;
    }

    public InstalacioncuestaRecurso(String instalacionname, String recursoname, String recursoInstalacionname, int cantidadBase) {
        this.instalacioncuestaRecursoPK = new InstalacioncuestaRecursoPK(instalacionname, recursoname, recursoInstalacionname);
        this.cantidadBase = cantidadBase;
    }
    
    public String getInstalacionname() 
    {
        return instalacioncuestaRecursoPK.getInstalacionname();
    }

    public String getRecursoname() 
    {
    	return instalacioncuestaRecursoPK.getRecursoname();
    }

    public String getRecursoInstalacionname() 
    {
    	return instalacioncuestaRecursoPK.getRecursoInstalacionname();
    }

    public InstalacioncuestaRecursoPK getInstalacioncuestaRecursoPK() {
        return instalacioncuestaRecursoPK;
    }

    public int getCantidadBase() {
        return cantidadBase;
    }

    public Instalacion getInstalacion() {
        return instalacion;
    }

    public void setInstalacion(Instalacion instalacion) {
        this.instalacion = instalacion;
    }

    public Recurso getRecurso() {
        return recurso;
    }

    public void setRecurso(Recurso recurso) {
        this.recurso = recurso;
    }
    
}
