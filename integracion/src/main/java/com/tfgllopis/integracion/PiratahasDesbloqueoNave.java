package com.tfgllopis.integracion;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "Pirata_has_Desbloqueo_Nave", catalog = "mydb2", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PiratahasDesbloqueoNave.findAll", query = "SELECT p FROM PiratahasDesbloqueoNave p")
    , @NamedQuery(name = "PiratahasDesbloqueoNave.findByPirataidPirata", query = "SELECT p FROM PiratahasDesbloqueoNave p WHERE p.piratahasDesbloqueoNavePK.pirataidPirata = :pirataidPirata")
    , @NamedQuery(name = "PiratahasDesbloqueoNave.findByNavenombreNave", query = "SELECT p FROM PiratahasDesbloqueoNave p WHERE p.piratahasDesbloqueoNavePK.navenombreNave = :navenombreNave")
    , @NamedQuery(name = "PiratahasDesbloqueoNave.findByProbabilidadDesbloqueo", query = "SELECT p FROM PiratahasDesbloqueoNave p WHERE p.probabilidadDesbloqueo = :probabilidadDesbloqueo")})
public class PiratahasDesbloqueoNave implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected PiratahasDesbloqueoNavePK piratahasDesbloqueoNavePK;
    @Basic(optional = false)
    @Column(name = "probabilidadDesbloqueo")
    private float probabilidadDesbloqueo;
    @JoinColumn(name = "Nave_nombreNave", referencedColumnName = "nombreNave", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Nave nave;
    @JoinColumn(name = "Pirata_idPirata", referencedColumnName = "idPirata", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Pirata pirata;

    public PiratahasDesbloqueoNave() {
    }

    public PiratahasDesbloqueoNave(PiratahasDesbloqueoNavePK piratahasDesbloqueoNavePK, float probabilidadDesbloqueo) {
        this.piratahasDesbloqueoNavePK = piratahasDesbloqueoNavePK;
        this.probabilidadDesbloqueo = probabilidadDesbloqueo;
    }

    public PiratahasDesbloqueoNave(int pirataidPirata, String navenombreNave, float probabilidadDesbloqueo) {
        this.piratahasDesbloqueoNavePK = new PiratahasDesbloqueoNavePK(pirataidPirata, navenombreNave);
        this.probabilidadDesbloqueo = probabilidadDesbloqueo;
    }

    public PiratahasDesbloqueoNavePK getPiratahasDesbloqueoNavePK() {
        return piratahasDesbloqueoNavePK;
    }
    
    public int getPirataidPirata() {
        return piratahasDesbloqueoNavePK.getPirataidPirata();
    }
    
    public void setPirataidPirata(int pirataidPirata) 
    {
        this.piratahasDesbloqueoNavePK.setPirataidPirata(pirataidPirata);
    }

    public String getNavenombreNave() {
        return piratahasDesbloqueoNavePK.getNavenombreNave();
    }
    
    public void setNavenombreNave(String navenombreNave) 
    {
    	this.piratahasDesbloqueoNavePK.setNavenombreNave(navenombreNave);
    }

    public float getProbabilidadDesbloqueo() {
        return probabilidadDesbloqueo;
    }
    
    public void setProbabilidadDesbloqueo(float probabilidadDesbloqueo)
    {
    	this.probabilidadDesbloqueo = probabilidadDesbloqueo;
    }

    public Nave getNave() {
        return nave;
    }

    public void setNave(Nave nave) {
        this.nave = nave;
    }

    public Pirata getPirata() {
        return pirata;
    }

    public void setPirata(Pirata pirata) {
        this.pirata = pirata;
    }
    
    public static List<PiratahasDesbloqueoNave> cargarDesbloqueosNave(String nombreNave, PiratahasDesbloqueoNaveRepository repo)
    {
    	List<PiratahasDesbloqueoNave> desbloqueos = repo.findByNavenombreNave(nombreNave);
    	return desbloqueos;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (piratahasDesbloqueoNavePK != null ? piratahasDesbloqueoNavePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PiratahasDesbloqueoNave)) {
            return false;
        }
        PiratahasDesbloqueoNave other = (PiratahasDesbloqueoNave) object;
        if ((this.piratahasDesbloqueoNavePK == null && other.piratahasDesbloqueoNavePK != null) || (this.piratahasDesbloqueoNavePK != null && !this.piratahasDesbloqueoNavePK.equals(other.piratahasDesbloqueoNavePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tfgllopis.moduloTecnico.PiratahasDesbloqueoNave[ piratahasDesbloqueoNavePK=" + piratahasDesbloqueoNavePK + " ]";
    }
    
}
