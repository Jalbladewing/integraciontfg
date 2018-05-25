package com.tfgllopis.integracion;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class InformeBatallahasRecursoPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "InformeBatalla_idBatalla")
    private int informeBatallaidBatalla;
    @Basic(optional = false)
    @Column(name = "Recurso_name")
    private String recursoname;
    @Basic(optional = false)
    @Column(name = "Recurso_Instalacion_name")
    private String recursoInstalacionname;

    public InformeBatallahasRecursoPK() {
    }

    public InformeBatallahasRecursoPK(int informeBatallaidBatalla, String recursoname, String recursoInstalacionname) {
        this.informeBatallaidBatalla = informeBatallaidBatalla;
        this.recursoname = recursoname;
        this.recursoInstalacionname = recursoInstalacionname;
    }

    public int getInformeBatallaidBatalla() {
        return informeBatallaidBatalla;
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
        hash += (int) informeBatallaidBatalla;
        hash += (recursoname != null ? recursoname.hashCode() : 0);
        hash += (recursoInstalacionname != null ? recursoInstalacionname.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InformeBatallahasRecursoPK)) {
            return false;
        }
        InformeBatallahasRecursoPK other = (InformeBatallahasRecursoPK) object;
        if (this.informeBatallaidBatalla != other.informeBatallaidBatalla) {
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
        return "com.tfgllopis.moduloNaves.InformeBatallahasRecursoPK[ informeBatallaidBatalla=" + informeBatallaidBatalla + ", recursoname=" + recursoname + ", recursoInstalacionname=" + recursoInstalacionname + " ]";
    }
    
}
