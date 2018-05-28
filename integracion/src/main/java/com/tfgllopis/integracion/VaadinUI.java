package com.tfgllopis.integracion;

import com.vaadin.annotations.PreserveOnRefresh;
import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinService;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.UI;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;

@PreserveOnRefresh
@SpringUI
@Theme("valo")
public class VaadinUI extends UI {

	@Autowired
	private UsuarioRepository userRepo;
	
	@Autowired
	private RolesRepository rolRepo;
	
	@Autowired
	private NaveRepository naveRepo;
	
	@Autowired
	private TipoNaveRepository tipoRepo;
	
	@Autowired
	private NavecuestaRecursoRepository naveCuestaRepo;
	
	@Autowired
	private PirataRepository pirataRepo;
	
	@Autowired
	private PiratahasDesbloqueoNaveRepository pirataDesbloqueoRepo;
	
	@Autowired
	private PiratahasInstalacionRepository pirataInstalacionRepo;
	
	@Autowired
	private PiratahasNaveRepository pirataNaveRepo;
	
	@Autowired
	private InstalacionRepository instalacionRepo;
	
	@Autowired
	PlanetaRepository planetaRepo;
	
	@Autowired
	PlanetahasRecursoRepository planetaRecursoRepo;
	
	@Autowired
	PlanetahasInstalacionRepository planetaInstalacionRepo;
	
	@Autowired
	PlanetaHasNaveRepository planetaNaveRepo;
	
	@Autowired
	private InstalacioncuestaRecursoRepository instalacionRecursoRepo;
	
	@Autowired
	UsuarioconstruyeNaveRepository construyeRepo;
	
	@Autowired
	UsuarioHasNaveRepository usuarioNaveRepo;
	
	@Autowired
	private UsuariohasMensajeRepository usuarioMensajeRepo;
	
	@Autowired
	private SistemaRepository sistemaRepo;
	
	@Autowired
	private MensajeRepository mensajeRepo;
	
	@Autowired
	private TipoMensajeRepository tipoMensajeRepo;
	
	@Autowired
	private MovimientoRepository movimientoRepo;

	@Autowired
	private MovimientohasNaveRepository movimientoNaveRepo;
	
	@Autowired
	private InformeBatallaRepository informeRepo;
	
	@Autowired
	private InformeBatallahasNaveAtaqueRepository informeAtaqueRepo;
	
	@Autowired
	private InformeBatallahasNaveDefensaRepository informeDefensaRepo;
	
	@Autowired
	private InformeBatallahasRecursoRepository informeRecursoRepo;
		
	@PersistenceContext
	EntityManager em;
	
	private Usuario usuario;
	private Planeta planetaAtaque;
	private Movimiento movimiento;

	@Override
	protected void init(VaadinRequest vaadinRequest) 
	{
		Usuario session = (Usuario) VaadinService.getCurrentRequest().getWrappedSession().getAttribute("currentUser");
		
		if(session == null)
		{
			usuario = null;
			setContent(new Cibernauta());
			
		}else if(session.getRolName().getName().equals("Jugador"))
		{
			usuario = session;
			setContent(new Jugador());
			
		}else if(session.getRolName().getName().equals("Administrador"))
		{
			usuario = session;
			setContent(new Administrador());
			
		}else if(session.getRolName().getName().equals("Tecnico"))
		{
			usuario = session;
			setContent(new Tecnico());
		}
		
	}
	
	public UsuarioRepository getInterfazUsuario()
	{
		return userRepo;
	}
	
	public RolesRepository getInterfazRol()
	{
		return rolRepo;
	}
	
	public TipoNaveRepository getInterfazTipo()
	{
		return tipoRepo;
	}
	
	public NaveRepository getInterfazNave()
	{
		return naveRepo;
	}
	
	public NavecuestaRecursoRepository getInterfazNaveCuesta()
	{
		return naveCuestaRepo;
	}
	
	public PirataRepository getInterfazPirata()
	{
		return pirataRepo;
	}
	
	public PiratahasDesbloqueoNaveRepository getInterfazPirataDesbloqueo()
	{
		return pirataDesbloqueoRepo;
	}
	
	public PiratahasInstalacionRepository getInterfazPirataInstalacion()
	{
		return pirataInstalacionRepo;
	}
	
	public PiratahasNaveRepository getInterfazPirataNave()
	{
		return pirataNaveRepo;
	}
	
	public InstalacionRepository getInterfazInstalacion()
	{
		return instalacionRepo;
	}
	
	public PlanetaRepository getInterfazPlaneta()
	{
		return planetaRepo;
	}
	
	public PlanetahasRecursoRepository getInterfazPlanetaRecurso()
	{
		return planetaRecursoRepo;
	}
	
	public PlanetahasInstalacionRepository getInterfazPlanetaInstalacion()
	{
		return planetaInstalacionRepo;
	}
	
	public PlanetaHasNaveRepository getInterfazPlanetaNave()

	{
		return planetaNaveRepo;
	}
	
	public InstalacioncuestaRecursoRepository getInterfazInstalacionRecurso()
	{
		return instalacionRecursoRepo;
	}
	
	public UsuarioconstruyeNaveRepository getInterfazUsuarioConstruye()
	{
		return construyeRepo;
	}
	
	public UsuarioHasNaveRepository getInterfazUsuarioNave()
	{
		return usuarioNaveRepo;
	}
	
	public UsuariohasMensajeRepository getInterfazUsuarioMensaje()
	{
		return usuarioMensajeRepo;
	}
	
	public SistemaRepository getInterfazSistema()
	{
		return sistemaRepo;
	}
	
	public MensajeRepository getInterfazMensaje()
	{
		return mensajeRepo;
	}
	
	public TipoMensajeRepository getInterfazTipoMensaje()
	{
		return tipoMensajeRepo;
	}
	
	public MovimientoRepository getInterfazMovimiento()
	{
		return movimientoRepo;
	}
	
	public MovimientohasNaveRepository getInterfazMovimientoNave()
	{
		return movimientoNaveRepo;
	}
	
	public InformeBatallaRepository getInterfazInformeBatalla()
	{
		return informeRepo;
	}
	
	public InformeBatallahasNaveAtaqueRepository getInterfazInformeAtaque()
	{
		return informeAtaqueRepo;
	}
	
	public InformeBatallahasNaveDefensaRepository getInterfazInformeDefensa()
	{
		return informeDefensaRepo;
	}
	
	public InformeBatallahasRecursoRepository getInterfazInformeRecurso()
	{
		return informeRecursoRepo;
	}
	
	
	public Usuario getUsuario() 
	{
		return usuario;
	}
	
	public void setUsuario(Usuario usuario) 
	{
		this.usuario = usuario;
	}
	
	public Planeta getPlanetaAtaque()
	{
		return planetaAtaque;
	}
	
	public void setPlanetaAtaque(Planeta planeta)

	{
		planetaAtaque = planeta;
	}
	
	public Movimiento getMovimiento()
	{
		return movimiento;
	}
	
	public void setMovimiento(Movimiento movimiento)

	{
		this.movimiento = movimiento;
	}
	
	public EntityManager getEntitymanager()
	{
		return em;
	}

}
