package com.tfgllopis.integracion;

import java.io.File;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.vaadin.server.FileResource;
import com.vaadin.ui.Image;

@Entity
@Table(name = "TipoNave", catalog = "mydb2", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoNave.findAll", query = "SELECT t FROM TipoNave t")
    , @NamedQuery(name = "v.findByNombreTipoNave", query = "SELECT t FROM TipoNave t WHERE t.nombreTipoNave = :nombreTipoNave")})
public class TipoNave implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "nombreTipoNave")
    private String nombreTipoNave;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "rutaImagenTipoNave")
    private String rutaImagenTipoNave;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tipoNavenombreTipoNave")
    private List<Nave> naveList;

    public TipoNave() {
    }

    public TipoNave(String nombreTipoNave) {
        this.nombreTipoNave = nombreTipoNave;
    }

    public TipoNave(String nombreTipoNave, String rutaImagenTipoNave) {
        this.nombreTipoNave = nombreTipoNave;
        this.rutaImagenTipoNave = rutaImagenTipoNave;
    }

    public String getNombreTipoNave() {
        return nombreTipoNave;
    }

    public void setNombreTipoNave(String nombreTipoNave) {
        this.nombreTipoNave = nombreTipoNave;
    }

    public String getRutaImagenTipoNave() {
        return rutaImagenTipoNave;
    }

    public void setRutaImagenTipoNave(String rutaImagenTipoNave) {
        this.rutaImagenTipoNave = rutaImagenTipoNave;
    }
    
    public Image getImage()
    {
    	FileResource resource = new FileResource(new File(rutaImagenTipoNave));
    	Image image = new Image("", resource);
    	
    	return image;
    }

    @XmlTransient
    public List<Nave> getNaveList() {
        return naveList;
    }

    public void setNaveList(List<Nave> naveList) {
        this.naveList = naveList;
    }

   /* @Override
    public int hashCode() {
        int hash = 0;
        hash += (nombreTipoNave != null ? nombreTipoNave.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tiponave)) {
            return false;
        }
        Tiponave other = (Tiponave) object;
        if ((this.nombreTipoNave == null && other.nombreTipoNave != null) || (this.nombreTipoNave != null && !this.nombreTipoNave.equals(other.nombreTipoNave))) {
            return false;
        }
        return true;
    }*/

    @Override
    public String toString() 
    {
        return nombreTipoNave;
    }
    
}
