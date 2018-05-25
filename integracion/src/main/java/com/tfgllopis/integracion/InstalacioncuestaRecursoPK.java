package com.tfgllopis.integracion;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class InstalacioncuestaRecursoPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "Instalacion_name")
    private String instalacionname;
    @Basic(optional = false)
    @Column(name = "Recurso_name")
    private String recursoname;
    @Basic(optional = false)
    @Column(name = "Recurso_Instalacion_name")
    private String recursoInstalacionname;

    public InstalacioncuestaRecursoPK()
    {
    	
    }
    
    public InstalacioncuestaRecursoPK(String instalacionname, String recursoname, String recursoInstalacionname) {
        this.instalacionname = instalacionname;
        this.recursoname = recursoname;
        this.recursoInstalacionname = recursoInstalacionname;
    }

    public String getInstalacionname() {
        return instalacionname;
    }

    public String getRecursoname() {
        return recursoname;
    }

    public String getRecursoInstalacionname() {
        return recursoInstalacionname;
    }
    
}
