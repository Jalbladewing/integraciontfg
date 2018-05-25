package com.tfgllopis.integracion;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name = "Mensaje")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Mensaje.findAll", query = "SELECT m FROM Mensaje m")
    , @NamedQuery(name = "Mensaje.findByIdMensaje", query = "SELECT m FROM Mensaje m WHERE m.idMensaje = :idMensaje")
    , @NamedQuery(name = "Mensaje.findByAsunto", query = "SELECT m FROM Mensaje m WHERE m.asunto = :asunto")
    , @NamedQuery(name = "Mensaje.findByDescripcion", query = "SELECT m FROM Mensaje m WHERE m.descripcion = :descripcion")
    , @NamedQuery(name = "Mensaje.findByFechaEnvio", query = "SELECT m FROM Mensaje m WHERE m.fechaEnvio = :fechaEnvio")})
public class Mensaje implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idMensaje")
    private Integer idMensaje;
    @Basic(optional = false)
    @Column(name = "asunto")
    private String asunto;
    @Basic(optional = false)
    @Column(name = "descripcion")
    private String descripcion;
    @Basic(optional = false)
    @Column(name = "fechaEnvio")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaEnvio;
    @JoinColumn(name = "TipoMensaje_name", referencedColumnName = "name")
    @ManyToOne(optional = false)
    private TipoMensaje tipoMensajename;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "mensaje")
    private List<UsuariohasMensaje> usuariohasMensajeList;
    @JoinColumn(name = "InformeBatalla_idBatalla", referencedColumnName = "idBatalla")
    @ManyToOne
    private InformeBatalla informeBatallaidBatalla;

    public Mensaje() {
    }

    public Mensaje(String asunto, String descripcion, Date fechaEnvio) 
    {
        this.asunto = asunto;
        this.descripcion = descripcion;
        this.fechaEnvio = fechaEnvio;
    }

    public Integer getIdMensaje() {
        return idMensaje;
    }
    
    public String getAsunto() {
        return asunto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Date getFechaEnvio() {
        return fechaEnvio;
    }

    public TipoMensaje getTipoMensajename() {
        return tipoMensajename;
    }

    public void setTipoMensajename(TipoMensaje tipoMensajename) {
        this.tipoMensajename = tipoMensajename;
    }

    @XmlTransient
    public List<UsuariohasMensaje> getUsuariohasMensajeList() {
        return usuariohasMensajeList;
    }

    public void setUsuariohasMensajeList(List<UsuariohasMensaje> usuariohasMensajeList) {
        this.usuariohasMensajeList = usuariohasMensajeList;
    }
    
    public InformeBatalla getInformeBatallaidBatalla() {
        return informeBatallaidBatalla;
    }

    public void setInformeBatallaidBatalla(InformeBatalla informeBatallaidBatalla) {
        this.informeBatallaidBatalla = informeBatallaidBatalla;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idMensaje != null ? idMensaje.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Mensaje)) {
            return false;
        }
        Mensaje other = (Mensaje) object;
        if ((this.idMensaje == null && other.idMensaje != null) || (this.idMensaje != null && !this.idMensaje.equals(other.idMensaje))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tfgllopis.moduloNaves.Mensaje[ idMensaje=" + idMensaje + " ]";
    }
    
}
