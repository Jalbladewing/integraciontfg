package com.tfgllopis.integracion;

import java.io.File;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.navigator.View;
import com.vaadin.server.FileResource;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Button;
import com.vaadin.ui.UI;
import com.vaadin.ui.Button.ClickEvent;

public class Registrarse  extends Registrarse_Ventana implements View
{
	@Autowired
	private UsuarioRepository userRepo;
	
	@Autowired
	private RolesRepository rolRepo;
	
	@Autowired
	private PlanetaRepository planetaRepo;
	
	@Autowired
	private PlanetaHasNaveRepository planetaNaveRepo;
	
	@Autowired
	private PlanetahasInstalacionRepository planetaInstalacionRepo;
	
	@Autowired
	private PlanetahasRecursoRepository planetaRecursoRepo;
	
	public static final String VIEW_NAME = "registro";
	
	public Registrarse()
	{
		userRepo = ((VaadinUI) UI.getCurrent()).getInterfazUsuario();
		rolRepo = ((VaadinUI) UI.getCurrent()).getInterfazRol();
		planetaRepo = ((VaadinUI) UI.getCurrent()).getInterfazPlaneta();
		planetaNaveRepo = ((VaadinUI) UI.getCurrent()).getInterfazPlanetaNave();
		planetaInstalacionRepo = ((VaadinUI) UI.getCurrent()).getInterfazPlanetaInstalacion();
		planetaRecursoRepo = ((VaadinUI) UI.getCurrent()).getInterfazPlanetaRecurso();
		
		imagenLogo.setSource( new FileResource(new File(new File("").getAbsolutePath() + "/images/" + "Logo_Cabeza_Fenix_Pequeña.png")));
		imagenLogo.setWidth(100, Unit.PIXELS);
		imagenLogo.setHeight(100, Unit.PIXELS);
		
		registrarseB.addClickListener(new Button.ClickListener() 
		{
			
			@Override
			public void buttonClick(ClickEvent event) 
			{
				String value = Registrarse.registro(usuarioF.getValue(), emailF.getValue(), passwordF.getValue(), password2F.getValue(), userRepo, rolRepo);
				errorL.setValue(value);
				
				if(value.isEmpty()) 
				{
					errorL.setVisible(false);
					Registrarse.inicializarUsuario(Usuario.cargarUsuario(usuarioF.getValue().replaceAll("\\s+",""), userRepo), planetaNaveRepo, planetaInstalacionRepo, planetaRecursoRepo, planetaRepo);
					doNavigate(Iniciar_sesion.VIEW_NAME + "/" + "registrado");
				}else
				{
					errorL.setVisible(true);
				}
				
			}
		});
		
		cancelarB.addClickListener(new Button.ClickListener() 
		{
			
			@Override
			public void buttonClick(ClickEvent event) 
			{
				doNavigate(Iniciar_sesion.VIEW_NAME);
			}
		});
	}
	
	private void doNavigate(String viewName) 
	{
		UI.getCurrent().getNavigator().navigateTo(viewName);
	}
	
	public static String registro(String username, String email, String password1, String password2, UsuarioRepository repo, RolesRepository rolRepo)
	{
		Usuario user;
		Date fechaRegistro = new Date();
		//Gestor_Correos correo = new Gestor_Correos();
		if(!UserDataValidator.comprobarUser(username)) return "Usuario inválido";
		if(!UserDataValidator.comprobarEmail(email)) return "Email inválido";
		if(!UserDataValidator.comprobarPassword(password1, password2)) return "Las contraseñas no coinciden";		
		if(UserDataValidator.comprobarUsuarioBD(username, repo)) return "El usuario ya existe";
		if(UserDataValidator.comprobarEmailBD(email, repo)) return "El correo ya está en uso";
		//if(!correo.correo_registro(email)) return "Imposible acceder al correo";
		
		user = new Usuario(email, username, password1, true, fechaRegistro, fechaRegistro);
		user.setRolName(rolRepo.findByName("Jugador").get(0));
		user.guardarUsuario(repo);
		
		return "";
	}
	
	public static String inicializarUsuario(Usuario usuario, PlanetaHasNaveRepository planetaNaveRepo, PlanetahasInstalacionRepository planetaInstalacionRepo, PlanetahasRecursoRepository planetaRecursoRepo, PlanetaRepository planetaRepo)
	{
		Planeta planeta = planetaRepo.findByPlanetaLibre().get(0);
		PlanetahasInstalacion planetaInstalacionMetal = planetaInstalacionRepo.findByInstalacionnamePlaneta("Mina de Metal", planeta.getCoordenadaX(), planeta.getCoordenadaY(), planeta.getSistemanombreSistema());
		PlanetahasInstalacion planetaInstalacionOro = planetaInstalacionRepo.findByInstalacionnamePlaneta("Mina de Oro", planeta.getCoordenadaX(), planeta.getCoordenadaY(), planeta.getSistemanombreSistema());
		PlanetahasInstalacion planetaInstalacionPetroleo = planetaInstalacionRepo.findByInstalacionnamePlaneta("Plataforma Petrolifera", planeta.getCoordenadaX(), planeta.getCoordenadaY(), planeta.getSistemanombreSistema());
		PlanetahasRecurso planetaRecursoMetal = planetaRecursoRepo.findByPlanetaRecurso(planeta.getCoordenadaX(), planeta.getCoordenadaY(), planeta.getSistemanombreSistema(), "Metal");
		PlanetahasRecurso planetaRecursoOro = planetaRecursoRepo.findByPlanetaRecurso(planeta.getCoordenadaX(), planeta.getCoordenadaY(), planeta.getSistemanombreSistema(), "Oro");
		PlanetahasRecurso planetaRecursoPetroleo = planetaRecursoRepo.findByPlanetaRecurso(planeta.getCoordenadaX(), planeta.getCoordenadaY(), planeta.getSistemanombreSistema(), "Petroleo");

		planeta.setUsuariousername(usuario);
		planeta.setPirataidPirata(null);
		planeta.setInformeBatallaidBatalla(null);
		planeta.setNombrePlaneta("Planeta de " + usuario.getUsername());
		
		planetaInstalacionMetal.setUltimaGeneracion(new Date());
		planetaInstalacionMetal.resetNivelInstalacion();
		planetaInstalacionOro.setUltimaGeneracion(new Date());
		planetaInstalacionOro.resetNivelInstalacion();
		planetaInstalacionPetroleo.setUltimaGeneracion(new Date());
		planetaInstalacionPetroleo.resetNivelInstalacion();
		
		planetaRecursoMetal.setCantidad(0);
		planetaRecursoOro.setCantidad(0);
		planetaRecursoPetroleo.setCantidad(0);
		
		planetaNaveRepo.deleteAll(planetaNaveRepo.findByPlaneta(planeta.getCoordenadaX(), planeta.getCoordenadaY(), planeta.getSistemanombreSistema()));
				
		planetaRepo.save(planeta);
		
		planetaInstalacionRepo.save(planetaInstalacionMetal);
		planetaInstalacionRepo.save(planetaInstalacionOro);
		planetaInstalacionRepo.save(planetaInstalacionPetroleo);
		
		planetaRecursoRepo.save(planetaRecursoMetal);
		planetaRecursoRepo.save(planetaRecursoOro);
		planetaRecursoRepo.save(planetaRecursoPetroleo);
		
		return "";
	}
}
