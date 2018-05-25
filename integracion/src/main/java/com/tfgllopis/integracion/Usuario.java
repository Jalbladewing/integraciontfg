package com.tfgllopis.integracion;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name = "Usuario", catalog = "mydb2", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Usuario.findAll", query = "SELECT u FROM Usuario u")
    , @NamedQuery(name = "Usuario.findByEmail", query = "SELECT u FROM Usuario u WHERE u.email = :email")
    , @NamedQuery(name = "Usuario.findByUsername", query = "SELECT u FROM Usuario u WHERE u.username = :username")
    , @NamedQuery(name = "Usuario.findByPassword", query = "SELECT u FROM Usuario u WHERE u.password = :password")
    , @NamedQuery(name = "Usuario.findByActivo", query = "SELECT u FROM Usuario u WHERE u.activo = :activo")
    , @NamedQuery(name = "Usuario.findByFechaCreacion", query = "SELECT u FROM Usuario u WHERE u.fechaCreacion = :fechaCreacion")
    , @NamedQuery(name = "Usuario.findByFechaUltimoAcceso", query = "SELECT u FROM Usuario u WHERE u.fechaUltimoAcceso = :fechaUltimoAcceso")})
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "email")
    private String email;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "username")
    private String username;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 245)
    @Column(name = "password")
    private String password;
    @Basic(optional = false)
    @NotNull
    @Column(name = "activo")
    private boolean activo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fechaCreacion")
    @Temporal(TemporalType.DATE)
    private Date fechaCreacion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fechaUltimoAcceso")
    @Temporal(TemporalType.DATE)
    private Date fechaUltimoAcceso;
    @JoinColumn(name = "Rol_name", referencedColumnName = "name")
    @ManyToOne(optional = false)
    private Rol Rol_Name;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuario")
    private List<UsuarioHasNave> usuariohasNaveList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuario")
    private List<UsuariohasMensaje> usuariohasMensajeList;
    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "usuario")
    private List<UsuarioconstruyeNave> usuarioconstruyeNaveList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuariousername")
    private List<Movimiento> movimientoList;
    
    public Usuario() {
    }

    public Usuario(String email) {
        this.email = email.replaceAll("\\s+","");
    }

    public Usuario(String email, String username, String password, boolean activo, Date fechaCreacion, Date fechaUltimoAcceso) {
        this.email = email.replaceAll("\\s+","");
        this.username = username.replaceAll("\\s+","");
        this.password = CheckPassword.hash(password.replaceAll("\\s+",""));
        this.activo = activo;
        this.fechaCreacion = fechaCreacion;
        this.fechaUltimoAcceso = fechaUltimoAcceso;
    }

    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email)
    {
    	this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password)
    {
    	this.password = CheckPassword.hash(password.replaceAll("\\s+",""));
    }
    
    public boolean getActivo() {
        return activo;
    }
    
    public void setActivo(boolean activo) 
    {
        this.activo = activo;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public Date getFechaUltimoAcceso() {
        return fechaUltimoAcceso;
    }

    public void setFechaUltimoAcceso(Date fechaUltimoAcceso) {
        this.fechaUltimoAcceso = fechaUltimoAcceso;
    }

    public Rol getRolName() {
        return Rol_Name;
    }
    
    public void setRolName(Rol rolName) {
        this.Rol_Name = rolName;
    }
    
    @XmlTransient
    public List<UsuarioHasNave> getUsuariohasNaveList() {
        return usuariohasNaveList;
    }

    public void setUsuariohasNaveList(List<UsuarioHasNave> usuariohasNaveList) {
        this.usuariohasNaveList = usuariohasNaveList;
    }
    
    @XmlTransient
    public List<UsuariohasMensaje> getUsuariohasMensajeList() {
        return usuariohasMensajeList;
    }

    public void setUsuariohasMensajeList(List<UsuariohasMensaje> usuariohasMensajeList) {
        this.usuariohasMensajeList = usuariohasMensajeList;
    }
    
    @XmlTransient
    public List<UsuarioconstruyeNave> getUsuarioconstruyeNaveList() {
        return usuarioconstruyeNaveList;
    }

    public void setUsuarioconstruyeNaveList(List<UsuarioconstruyeNave> usuarioconstruyeNaveList) {
        this.usuarioconstruyeNaveList = usuarioconstruyeNaveList;
    }
    
    @XmlTransient
    public List<Movimiento> getMovimientoList() {
        return movimientoList;
    }

    public void setMovimientoList(List<Movimiento> movimientoList) {
        this.movimientoList = movimientoList;
    }
    
    public void guardarUsuario(UsuarioRepository repo)
    {
    	repo.save(this);
    }
    
    public static Usuario cargarUsuario(String username, UsuarioRepository repo)
    {
    	List<Usuario> user = repo.findByUsername(username);
    	if(user.isEmpty()) return null;
    	return user.get(0);
    }
    
    public static Usuario cargarUsuarioEmail(String email, UsuarioRepository repo)
    {
    	List<Usuario> user = repo.findByEmailLikeIgnoreCase(email);
    	if(user.isEmpty()) return null;
    	return user.get(0);
    }
    
    public static void borrarUsuario(String username, UsuarioRepository repo)
    {
    	repo.delete(repo.findByUsername(username).get(0));
    }
    
    public static void borrarUsuarioEmail(String email, UsuarioRepository repo)
    {
    	repo.delete(repo.findByEmailLikeIgnoreCase(email).get(0));
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (username != null ? username.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Usuario)) {
            return false;
        }
        Usuario other = (Usuario) object;
        if ((this.username == null && other.username != null) || (this.username != null && !this.username.equals(other.username))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tfgllopis.moduloTecnico.Usuario[ username=" + username + " ]";
    }
    
}
