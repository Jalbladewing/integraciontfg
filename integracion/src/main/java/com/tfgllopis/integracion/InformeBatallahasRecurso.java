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
@Table(name = "InformeBatalla_has_Recurso")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "InformeBatallahasRecurso.findAll", query = "SELECT i FROM InformeBatallahasRecurso i")
    , @NamedQuery(name = "InformeBatallahasRecurso.findByInformeBatallaidBatalla", query = "SELECT i FROM InformeBatallahasRecurso i WHERE i.informeBatallahasRecursoPK.informeBatallaidBatalla = :informeBatallaidBatalla")
    , @NamedQuery(name = "InformeBatallahasRecurso.findByRecursoname", query = "SELECT i FROM InformeBatallahasRecurso i WHERE i.informeBatallahasRecursoPK.recursoname = :recursoname")
    , @NamedQuery(name = "InformeBatallahasRecurso.findByRecursoInstalacionname", query = "SELECT i FROM InformeBatallahasRecurso i WHERE i.informeBatallahasRecursoPK.recursoInstalacionname = :recursoInstalacionname")
    , @NamedQuery(name = "InformeBatallahasRecurso.findByCantidad", query = "SELECT i FROM InformeBatallahasRecurso i WHERE i.cantidad = :cantidad")})
public class InformeBatallahasRecurso implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected InformeBatallahasRecursoPK informeBatallahasRecursoPK;
    @Basic(optional = false)
    @Column(name = "cantidad")
    private int cantidad;
    @JoinColumn(name = "InformeBatalla_idBatalla", referencedColumnName = "idBatalla", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private InformeBatalla informeBatalla;
    @JoinColumns({
        @JoinColumn(name = "Recurso_name", referencedColumnName = "name", insertable = false, updatable = false)
        , @JoinColumn(name = "Recurso_Instalacion_name", referencedColumnName = "Instalacion_name", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private Recurso recurso;

    public InformeBatallahasRecurso() {
    }

    public InformeBatallahasRecurso(InformeBatallahasRecursoPK informeBatallahasRecursoPK, int cantidad) {
        this.informeBatallahasRecursoPK = informeBatallahasRecursoPK;
        this.cantidad = cantidad;
    }

    public InformeBatallahasRecurso(int informeBatallaidBatalla, String recursoname, String recursoInstalacionname, int cantidad) {
        this.informeBatallahasRecursoPK = new InformeBatallahasRecursoPK(informeBatallaidBatalla, recursoname, recursoInstalacionname);
        this.cantidad = cantidad;
    }

    public InformeBatallahasRecursoPK getInformeBatallahasRecursoPK() {
        return informeBatallahasRecursoPK;
    }

    public int getInformeBatallaidBatalla() {
        return informeBatallahasRecursoPK.getInformeBatallaidBatalla();
    }

    public String getRecursoname() {
        return informeBatallahasRecursoPK.getRecursoname();
    }

    public String getRecursoInstalacionname() {
        return informeBatallahasRecursoPK.getRecursoInstalacionname();
    }
    
    public int getCantidad() {
        return cantidad;
    }

    public InformeBatalla getInformeBatalla() {
        return informeBatalla;
    }

    public void setInformeBatalla(InformeBatalla informeBatalla) {
        this.informeBatalla = informeBatalla;
    }

    public Recurso getRecurso() {
        return recurso;
    }

    public void setRecurso(Recurso recurso) {
        this.recurso = recurso;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (informeBatallahasRecursoPK != null ? informeBatallahasRecursoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InformeBatallahasRecurso)) {
            return false;
        }
        InformeBatallahasRecurso other = (InformeBatallahasRecurso) object;
        if ((this.informeBatallahasRecursoPK == null && other.informeBatallahasRecursoPK != null) || (this.informeBatallahasRecursoPK != null && !this.informeBatallahasRecursoPK.equals(other.informeBatallahasRecursoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tfgllopis.moduloNaves.InformeBatallahasRecurso[ informeBatallahasRecursoPK=" + informeBatallahasRecursoPK + " ]";
    }
    
}
