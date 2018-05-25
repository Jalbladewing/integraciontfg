package com.tfgllopis.integracion;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class RecursoPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @Column(name = "Instalacion_name")
    private String instalacionname;

    public RecursoPK() {
    }

    public RecursoPK(String name, String instalacionname) {
        this.name = name;
        this.instalacionname = instalacionname;
    }

    public String getName() {
        return name;
    }

    public String getInstalacionname() {
        return instalacionname;
    }
    
}
