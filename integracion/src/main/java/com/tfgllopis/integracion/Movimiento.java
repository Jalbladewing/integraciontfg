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
import javax.persistence.JoinColumns;
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
@Table(name = "Movimiento")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Movimiento.findAll", query = "SELECT m FROM Movimiento m")
    , @NamedQuery(name = "Movimiento.findByIdMovimiento", query = "SELECT m FROM Movimiento m WHERE m.idMovimiento = :idMovimiento")
    , @NamedQuery(name = "Movimiento.findByTiempoLlegada", query = "SELECT m FROM Movimiento m WHERE m.tiempoLlegada = :tiempoLlegada")
    , @NamedQuery(name = "Movimiento.findByTiempoEnvio", query = "SELECT m FROM Movimiento m WHERE m.tiempoEnvio = :tiempoEnvio")
    , @NamedQuery(name = "Movimiento.findByMovimientoCancelado", query = "SELECT m FROM Movimiento m WHERE m.movimientoCancelado = :movimientoCancelado")
    , @NamedQuery(name = "Movimiento.findByPlanetaDestino", query = "SELECT m FROM Movimiento m WHERE m.planeta = :planeta")
    , @NamedQuery(name = "Movimiento.findByMovimientosUsuario", query = "SELECT m FROM Movimiento m WHERE m.tiempoLlegada > NOW() AND m.movimientoCancelado = 0 AND (m.planeta = :planeta OR m.usuariousername = :usuariousername)")})
public class Movimiento implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idMovimiento")
    private Integer idMovimiento;
    @Basic(optional = false)
    @Column(name = "tiempoLlegada")
    @Temporal(TemporalType.TIMESTAMP)
    private Date tiempoLlegada;
    @Basic(optional = false)
    @Column(name = "tiempoEnvio")
    @Temporal(TemporalType.TIMESTAMP)
    private Date tiempoEnvio;
    @Basic(optional = false)
    @Column(name = "movimientoCancelado")
    private short movimientoCancelado;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "movimientoidMovimiento")
    private List<InformeBatalla> informeBatallaList;
    @JoinColumns({
        @JoinColumn(name = "Planeta_coordenadaX", referencedColumnName = "coordenadaX")
        , @JoinColumn(name = "Planeta_coordenadaY", referencedColumnName = "coordenadaY")
        , @JoinColumn(name = "Planeta_Sistema_nombreSistema", referencedColumnName = "Sistema_nombreSistema")})
    @ManyToOne(optional = false)
    private Planeta planeta;
    @JoinColumn(name = "Usuario_username", referencedColumnName = "username")
    @ManyToOne(optional = false)
    private Usuario usuariousername;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "movimiento")
    private List<MovimientohasNave> movimientohasNaveList;
    @JoinColumn(name = "Movimiento_ida", referencedColumnName = "idMovimiento")
    @ManyToOne
    private Movimiento movimientoIda;

    public Movimiento() {
    }

    public Movimiento(Date tiempoLlegada, Date tiempoEnvio, short movimientoCancelado) {
        this.tiempoLlegada = tiempoLlegada;
        this.tiempoEnvio = tiempoEnvio;
        this.movimientoCancelado = movimientoCancelado;
    }

    public Integer getIdMovimiento() {
        return idMovimiento;
    }

    public Date getTiempoLlegada() {
        return tiempoLlegada;
    }

    public Date getTiempoEnvio() {
        return tiempoEnvio;
    }

    public short getMovimientoCancelado() {
        return movimientoCancelado;
    }
    
    public void cancelarMovimiento()
    {
    	this.movimientoCancelado = 1;
    }

    @XmlTransient
    public List<InformeBatalla> getInformeBatallaList() {
        return informeBatallaList;
    }

    public void setInformeBatallaList(List<InformeBatalla> informeBatallaList) {
        this.informeBatallaList = informeBatallaList;
    }

    public Planeta getPlaneta() {
        return planeta;
    }

    public void setPlaneta(Planeta planeta) {
        this.planeta = planeta;
    }

    public Usuario getUsuariousername() {
        return usuariousername;
    }

    public void setUsuariousername(Usuario usuariousername) {
        this.usuariousername = usuariousername;
    }

    @XmlTransient
    public List<MovimientohasNave> getMovimientohasNaveList() {
        return movimientohasNaveList;
    }

    public void setMovimientohasNaveList(List<MovimientohasNave> movimientohasNaveList) {
        this.movimientohasNaveList = movimientohasNaveList;
    }
    
    public Movimiento getMovimientoIda() {
        return movimientoIda;
    }

    public void setMovimientoIda(Movimiento movimientoIda) {
        this.movimientoIda = movimientoIda;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idMovimiento != null ? idMovimiento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Movimiento)) {
            return false;
        }
        Movimiento other = (Movimiento) object;
        if ((this.idMovimiento == null && other.idMovimiento != null) || (this.idMovimiento != null && !this.idMovimiento.equals(other.idMovimiento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tfgllopis.moduloNaves.Movimiento[ idMovimiento=" + idMovimiento + " ]";
    }
    
}
