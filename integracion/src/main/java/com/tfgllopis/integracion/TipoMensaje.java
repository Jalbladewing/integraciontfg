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
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name = "TipoMensaje")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoMensaje.findAll", query = "SELECT t FROM TipoMensaje t")
    , @NamedQuery(name = "TipoMensaje.findByName", query = "SELECT t FROM TipoMensaje t WHERE t.name = :name")})
public class TipoMensaje implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tipoMensajename")
    private List<Mensaje> mensajeList;

    public TipoMensaje() {
    }

    public TipoMensaje(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    /*@XmlTransient
    public List<Mensaje> getMensajeList() {
        return mensajeList;
    }

    public void setMensajeList(List<Mensaje> mensajeList) {
        this.mensajeList = mensajeList;
    }*/

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (name != null ? name.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoMensaje)) {
            return false;
        }
        TipoMensaje other = (TipoMensaje) object;
        if ((this.name == null && other.name != null) || (this.name != null && !this.name.equals(other.name))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tfgllopis.moduloNaves.TipoMensaje[ name=" + name + " ]";
    }
    
}
