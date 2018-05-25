package com.tfgllopis.integracion;

import java.io.Serializable;
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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name = "InformeBatalla")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "InformeBatalla.findAll", query = "SELECT i FROM InformeBatalla i")
    , @NamedQuery(name = "InformeBatalla.findByIdBatalla", query = "SELECT i FROM InformeBatalla i WHERE i.idBatalla = :idBatalla")
	, @NamedQuery(name = "InformeBatalla.findByIdMovimiento", query = "SELECT i FROM InformeBatalla i WHERE i.movimientoidMovimiento = :movimientoidMovimiento")})

public class InformeBatalla implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idBatalla")
    private Integer idBatalla;
    @JoinColumn(name = "Movimiento_idMovimiento", referencedColumnName = "idMovimiento")
    @ManyToOne(optional = false)
    private Movimiento movimientoidMovimiento;
    @JoinColumn(name = "Nave_nombreNaveDesbloqueada", referencedColumnName = "nombreNave")
    @ManyToOne
    private Nave navenombreNaveDesbloqueada;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "informeBatalla")
    private List<InformeBatallahasRecurso> informeBatallahasRecursoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "informeBatalla")
    private List<InformeBatallahasNaveDefensa> informeBatallahasNaveDefensaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "informeBatalla")
    private List<InformeBatallahasNaveAtaque> informeBatallahasNaveAtaqueList;
    @OneToMany(mappedBy = "informeBatallaidBatalla")
    private List<Planeta> planetaList;
    @OneToMany(mappedBy = "informeBatallaidBatalla")
    private List<Mensaje> mensajeList;

    public InformeBatalla() {
    }

    public Integer getIdBatalla() {
        return idBatalla;
    }

    public Movimiento getMovimientoidMovimiento() {
        return movimientoidMovimiento;
    }

    public void setMovimientoidMovimiento(Movimiento movimientoidMovimiento) {
        this.movimientoidMovimiento = movimientoidMovimiento;
    }

    public Nave getNavenombreNaveDesbloqueada() {
        return navenombreNaveDesbloqueada;
    }

    public void setNavenombreNaveDesbloqueada(Nave navenombreNaveDesbloqueada) {
        this.navenombreNaveDesbloqueada = navenombreNaveDesbloqueada;
    }

    @XmlTransient
    public List<InformeBatallahasRecurso> getInformeBatallahasRecursoList() {
        return informeBatallahasRecursoList;
    }

    public void setInformeBatallahasRecursoList(List<InformeBatallahasRecurso> informeBatallahasRecursoList) {
        this.informeBatallahasRecursoList = informeBatallahasRecursoList;
    }

    @XmlTransient
    public List<InformeBatallahasNaveDefensa> getInformeBatallahasNaveDefensaList() {
        return informeBatallahasNaveDefensaList;
    }

    public void setInformeBatallahasNaveDefensaList(List<InformeBatallahasNaveDefensa> informeBatallahasNaveDefensaList) {
        this.informeBatallahasNaveDefensaList = informeBatallahasNaveDefensaList;
    }

    @XmlTransient
    public List<InformeBatallahasNaveAtaque> getInformeBatallahasNaveAtaqueList() {
        return informeBatallahasNaveAtaqueList;
    }

    public void setInformeBatallahasNaveAtaqueList(List<InformeBatallahasNaveAtaque> informeBatallahasNaveAtaqueList) {
        this.informeBatallahasNaveAtaqueList = informeBatallahasNaveAtaqueList;
    }

    @XmlTransient
    public List<Planeta> getPlanetaList() {
        return planetaList;
    }

    public void setPlanetaList(List<Planeta> planetaList) {
        this.planetaList = planetaList;
    }

    @XmlTransient
    public List<Mensaje> getMensajeList() {
        return mensajeList;
    }

    public void setMensajeList(List<Mensaje> mensajeList) {
        this.mensajeList = mensajeList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idBatalla != null ? idBatalla.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InformeBatalla)) {
            return false;
        }
        InformeBatalla other = (InformeBatalla) object;
        if ((this.idBatalla == null && other.idBatalla != null) || (this.idBatalla != null && !this.idBatalla.equals(other.idBatalla))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tfgllopis.moduloNaves.InformeBatalla[ idBatalla=" + idBatalla + " ]";
    }
    
}
