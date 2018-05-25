package com.tfgllopis.integracion;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class NavecuestaRecursoPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "Nave_nombreNave")
    private String navenombreNave;
    @Basic(optional = false)
    @Column(name = "Recurso_name")
    private String recursoname;
    @Basic(optional = false)
    @Column(name = "Recurso_Instalacion_name")
    private String recursoInstalacionname;

    public NavecuestaRecursoPK()
    {
    	
    }
    
    public NavecuestaRecursoPK(String navenombreNave, String recursoname, String recursoInstalacionname) {
        this.navenombreNave = navenombreNave;
        this.recursoname = recursoname;
        this.recursoInstalacionname = recursoInstalacionname;
    }

    public String getNavenombreNave() {
        return navenombreNave;
    }
    
    public void setNavenombreNave(String navenombreNave)
    {
    	this.navenombreNave = navenombreNave;
    }

    public String getRecursoname() {
        return recursoname;
    }
    
    public void setRecursoname(String recursoname)
    {
    	this.recursoname = recursoname;
    }

    public String getRecursoInstalacionname() {
        return recursoInstalacionname;
    }
    
    public void setRecursoInstalacionname(String recursoInstalacionname)
    {
    	this.recursoInstalacionname = recursoInstalacionname;
    }
    
}
