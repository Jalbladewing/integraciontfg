package com.tfgllopis.integracion;

import java.io.File;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.vaadin.server.ClassResource;
import com.vaadin.server.FileResource;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Image;

@Entity
@Table(name = "Nave", catalog = "mydb2", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Nave.findAll", query = "SELECT n FROM Nave n")
    , @NamedQuery(name = "Nave.findByNombreNave", query = "SELECT n FROM Nave n WHERE n.nombreNave = :nombreNave")
    , @NamedQuery(name = "Nave.findByAtaqueNave", query = "SELECT n FROM Nave n WHERE n.ataqueNave = :ataqueNave")
    , @NamedQuery(name = "Nave.findByHullNave", query = "SELECT n FROM Nave n WHERE n.hullNave = :hullNave")
    , @NamedQuery(name = "Nave.findByEscudoNave", query = "SELECT n FROM Nave n WHERE n.escudoNave = :escudoNave")
    , @NamedQuery(name = "Nave.findByAgilidadNave", query = "SELECT n FROM Nave n WHERE n.agilidadNave = :agilidadNave")
    , @NamedQuery(name = "Nave.findByNombreTipoNave", query = "SELECT n FROM Nave n WHERE n.tipoNavenombreTipoNave.nombreTipoNave = :nombreTipoNave")    , @NamedQuery(name = "Nave.findByCapacidadCarga", query = "SELECT n FROM Nave n WHERE n.capacidadCarga = :capacidadCarga")
    , @NamedQuery(name = "Nave.findBySegundosConstruccion", query = "SELECT n FROM Nave n WHERE n.segundosConstruccion = :segundosConstruccion")
    , @NamedQuery(name = "Nave.findByBloqueada", query = "SELECT n FROM Nave n WHERE n.bloqueada = :bloqueada")})
public class Nave implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "nombreNave")
    private String nombreNave;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 245)
    @Column(name = "rutaImagenNave")
    private String rutaImagenNave;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ataqueNave")
    private float ataqueNave;
    @Basic(optional = false)
    @NotNull
    @Column(name = "hullNave")
    private float hullNave;
    @Basic(optional = false)
    @NotNull
    @Column(name = "escudoNave")
    private float escudoNave;
    @Basic(optional = false)
    @Column(name = "velocidadNave")
    private float velocidadNave;
    @Basic(optional = false)
    @NotNull
    @Column(name = "agilidadNave")
    private float agilidadNave;
    @Basic(optional = false)
    @NotNull
    @Column(name = "capacidadCarga")
    private float capacidadCarga;
    @Basic(optional = false)
    @NotNull
    @Column(name = "segundosConstruccion")
    private int segundosConstruccion;
    @Basic(optional = false)
    @Column(name = "bloqueada")
    private short bloqueada;
    @JoinColumn(name = "tipoNave_nombreTipoNave", referencedColumnName = "nombreTipoNave")
    @ManyToOne(optional = false)
    private TipoNave tipoNavenombreTipoNave;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "nave")
    private List<PlanetaHasNave> planetaHasNaveList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "nave")
    private List<UsuarioHasNave> usuarioHasNaveList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "nave")
    private List<NavecuestaRecurso> navecuestaRecursoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "nave")
    private List<PlanetaHasNave> planetahasNaveList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "nave")
    private List<UsuarioHasNave> usuariohasNaveList;
    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "nave")
    private List<UsuarioconstruyeNave> usuarioconstruyeNaveList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "nave")
    private List<PiratahasDesbloqueoNave> piratahasDesbloqueoNaveList;
    @OneToMany(mappedBy = "navenombreNaveDesbloqueada")
    private List<InformeBatalla> informeBatallaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "nave")
    private List<InformeBatallahasNaveDefensa> informeBatallahasNaveDefensaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "nave")
    private List<InformeBatallahasNaveAtaque> informeBatallahasNaveAtaqueList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "nave")
    private List<MovimientohasNave> movimientohasNaveList;
    
    public Nave() 
    {
    }
    
    public Nave(String nombreNave, String rutaImagenNave, float ataqueNave, float hullNave, float escudoNave, float velocidadNave, float agilidadNave, float capacidadCarga, int segundosConstruccion, short bloqueada) {
        this.nombreNave = nombreNave;
        this.rutaImagenNave = rutaImagenNave;
        this.ataqueNave = ataqueNave;
        this.hullNave = hullNave;
        this.escudoNave = escudoNave;
        this.velocidadNave = velocidadNave;
        this.agilidadNave = agilidadNave;
        this.capacidadCarga = capacidadCarga;
        this.segundosConstruccion = segundosConstruccion;
        this.bloqueada = bloqueada;
    }

    public String getNombreNave() {
        return nombreNave;
    }

    public void setNombreNave(String nombreNave) {
        this.nombreNave = nombreNave;
    }

    public String getRutaImagenNave() {
        return rutaImagenNave;
    }
    
    public Image getImage()
    {
    	Image image = new Image("", new ClassResource("/images/" + rutaImagenNave));
    	
    	return image;
    }

    public void setRutaImagenNave(String rutaImagenNave) {
        this.rutaImagenNave = rutaImagenNave;
    }

    public float getAtaqueNave() {
        return ataqueNave;
    }

    public void setAtaqueNave(float ataqueNave) {
        this.ataqueNave = ataqueNave;
    }

    public float getHullNave() {
        return hullNave;
    }

    public void setHullNave(float hullNave) {
        this.hullNave = hullNave;
    }

    public float getEscudoNave() {
        return escudoNave;
    }

    public void setEscudoNave(float escudoNave) {
        this.escudoNave = escudoNave;
    }

    public float getVelocidadNave() {
        return velocidadNave;
    }

    public void setVelocidadNave(float velocidadNave) {
        this.velocidadNave = velocidadNave;
    }

    public float getAgilidadNave() {
        return agilidadNave;
    }

    public void setAgilidadNave(float agilidadNave) {
        this.agilidadNave = agilidadNave;
    }

    public float getCapacidadCarga() {
        return capacidadCarga;
    }

    public void setCapacidadCarga(float capacidadCarga) {
        this.capacidadCarga = capacidadCarga;
    }

    public int getSegundosConstruccion() {
        return segundosConstruccion;
    }

    public void setSegundosConstruccion(int segundosConstruccion) {
        this.segundosConstruccion = segundosConstruccion;
    }

    public short getBloqueada() {
        return bloqueada;
    }

    public void setBloqueada(short bloqueada) {
        this.bloqueada = bloqueada;
    }
    
    @XmlTransient
    public List<PlanetaHasNave> getPlanetaHasNaveList() {
        return planetaHasNaveList;
    }

    public void setPlanetaHasNaveList(List<PlanetaHasNave> planetaHasNaveList) 
    {
        this.planetaHasNaveList = planetaHasNaveList;
    }

    public TipoNave getTipoNavenombreTipoNave() {
        return tipoNavenombreTipoNave;
    }

    public void setTipoNavenombreTipoNave(TipoNave tipoNavenombreTipoNave) {
        this.tipoNavenombreTipoNave = tipoNavenombreTipoNave;
    }
    
    @XmlTransient
    public List<NavecuestaRecurso> getNavecuestaRecursoList() {
        return navecuestaRecursoList;
    }

    public void setNavecuestaRecursoList(List<NavecuestaRecurso> navecuestaRecursoList) {
        this.navecuestaRecursoList = navecuestaRecursoList;
    }
    
    @XmlTransient
    public List<UsuarioHasNave> getUsuariohasNaveList() {
        return usuariohasNaveList;
    }

    public void setUsuariohasNaveList(List<UsuarioHasNave> usuariohasNaveList) {
        this.usuariohasNaveList = usuariohasNaveList;
    }
    
    @XmlTransient
    public List<UsuarioconstruyeNave> getUsuarioconstruyeNaveList() {
        return usuarioconstruyeNaveList;
    }

    public void setUsuarioconstruyeNaveList(List<UsuarioconstruyeNave> usuarioconstruyeNaveList) {
        this.usuarioconstruyeNaveList = usuarioconstruyeNaveList;
    }
    
    @XmlTransient
    public List<PiratahasDesbloqueoNave> getPiratahasDesbloqueoNaveList() {
        return piratahasDesbloqueoNaveList;
    }

    public void setPiratahasDesbloqueoNaveList(List<PiratahasDesbloqueoNave> piratahasDesbloqueoNaveList) {
        this.piratahasDesbloqueoNaveList = piratahasDesbloqueoNaveList;
    }
    
    @XmlTransient
    public List<InformeBatalla> getInformeBatallaList() {
        return informeBatallaList;
    }

    public void setInformeBatallaList(List<InformeBatalla> informeBatallaList) {
        this.informeBatallaList = informeBatallaList;
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
    
    public void guardarNave(NaveRepository repo)
    {
    	repo.save(this);
    }
    
    public static Nave cargarNave(String nombre, NaveRepository repo)
    {
    	List<Nave> nave = repo.findByNombreNave(nombre);
    	if(nave.isEmpty()) return null;
    	return nave.get(0);
    }
    
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Nave)) {
            return false;
        }
        Nave other = (Nave) object;
        if ((this.nombreNave == null && other.nombreNave != null) || (this.nombreNave != null && !this.nombreNave.equals(other.nombreNave))) {
            return false;
        }
        return true;
    }
    
}
