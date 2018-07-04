package com.tfgllopis.integracion;

import java.util.ArrayList;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.UI;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;

public class Borrar_usuario extends Borrar_usuario_Ventana implements View 
{
	@Autowired
	private UsuarioRepository userRepo;
	
	@Autowired
	private PlanetaRepository planetaRepo;
	
	@Autowired
	private PlanetaHasNaveRepository planetaNaveRepo;
	
	@Autowired
	private PlanetahasInstalacionRepository planetaInstalacionRepo;
	
	@Autowired
	private PlanetahasRecursoRepository planetaRecursoRepo;
	
	@Autowired
	private PirataRepository pirataRepo;
	
	@Autowired
	private PiratahasInstalacionRepository pirataInstalacionRepo;
	
	@Autowired
	private PiratahasNaveRepository pirataNaveRepo;
	
	public static String VIEW_NAME = "borrarUsuario";
	
	public Borrar_usuario()
	{
		eliminarB.addClickListener(new Button.ClickListener() 
		{
			
			@Override
			public void buttonClick(ClickEvent event) 
			{
				Usuario usuarioBorrado = Usuario.cargarUsuario(usuarioL.getValue().replaceAll("\\s+",""), userRepo);
				
				if(!usuarioBorrado.equals(((VaadinUI) UI.getCurrent()).getUsuario())) Borrar_usuario.borrarUsuario(usuarioBorrado, planetaNaveRepo, planetaInstalacionRepo, planetaRecursoRepo, planetaRepo, pirataRepo, pirataInstalacionRepo, pirataNaveRepo, userRepo);
								
				doNavigate(Listar_usuarios.VIEW_NAME);	
			}
		});
		
		cancelarB.addClickListener(new Button.ClickListener() 
		{
			
			@Override
			public void buttonClick(ClickEvent event) 
			{
				doNavigate(Listar_usuarios.VIEW_NAME);	
			}
		});
	}
	
	@Override
	public void enter(ViewChangeEvent event) 
	{
		
		if (!event.getParameters().isEmpty())
		{
			userRepo = ((VaadinUI) UI.getCurrent()).getInterfazUsuario();
			planetaRepo = ((VaadinUI) UI.getCurrent()).getInterfazPlaneta();
			planetaNaveRepo = ((VaadinUI) UI.getCurrent()).getInterfazPlanetaNave();
			planetaInstalacionRepo = ((VaadinUI) UI.getCurrent()).getInterfazPlanetaInstalacion();
			planetaRecursoRepo = ((VaadinUI) UI.getCurrent()).getInterfazPlanetaRecurso();
			pirataRepo = ((VaadinUI) UI.getCurrent()).getInterfazPirata();
			pirataInstalacionRepo = ((VaadinUI) UI.getCurrent()).getInterfazPirataInstalacion();
			pirataNaveRepo = ((VaadinUI) UI.getCurrent()).getInterfazPirataNave();
			
			usuarioL.setValue(event.getParameters());
			emailL.setValue(userRepo.findByUsername(event.getParameters()).get(0).getEmail());
		}
	}
	
	private void doNavigate(String viewName) 
	{
		UI.getCurrent().getNavigator().navigateTo(viewName);
	}
	
	public static String borrarUsuario(Usuario usuario, PlanetaHasNaveRepository planetaNaveRepo, PlanetahasInstalacionRepository planetaInstalacionRepo, PlanetahasRecursoRepository planetaRecursoRepo, PlanetaRepository planetaRepo, PirataRepository pirataRepo, PiratahasInstalacionRepository pirataInstalacionRepo, PiratahasNaveRepository pirataNaveRepo, UsuarioRepository usuarioRepo)
	{
		Planeta planeta = planetaRepo.findByUsuarioUsername(usuario);
		PlanetahasInstalacion planetaInstalacionMetal = planetaInstalacionRepo.findByInstalacionnamePlaneta("Mina de Metal", planeta.getCoordenadaX(), planeta.getCoordenadaY(), planeta.getSistemanombreSistema());
		PlanetahasInstalacion planetaInstalacionOro = planetaInstalacionRepo.findByInstalacionnamePlaneta("Mina de Oro", planeta.getCoordenadaX(), planeta.getCoordenadaY(), planeta.getSistemanombreSistema());
		PlanetahasInstalacion planetaInstalacionPetroleo = planetaInstalacionRepo.findByInstalacionnamePlaneta("Plataforma Petrolifera", planeta.getCoordenadaX(), planeta.getCoordenadaY(), planeta.getSistemanombreSistema());
		PlanetahasRecurso planetaRecursoMetal = planetaRecursoRepo.findByPlanetaRecurso(planeta.getCoordenadaX(), planeta.getCoordenadaY(), planeta.getSistemanombreSistema(), "Metal");
		PlanetahasRecurso planetaRecursoOro = planetaRecursoRepo.findByPlanetaRecurso(planeta.getCoordenadaX(), planeta.getCoordenadaY(), planeta.getSistemanombreSistema(), "Oro");
		PlanetahasRecurso planetaRecursoPetroleo = planetaRecursoRepo.findByPlanetaRecurso(planeta.getCoordenadaX(), planeta.getCoordenadaY(), planeta.getSistemanombreSistema(), "Petroleo");
		ArrayList<PiratahasNave> listaNavesDefecto;
		ArrayList<PlanetaHasNave> listaNaves = new ArrayList<>();
		
		planeta.setUsuariousername(null);
		planeta.setPirataidPirata(pirataRepo.findByIdPirata(planeta.getCoordenadaY()+1).get(0));
		planeta.setInformeBatallaidBatalla(null);
		planeta.setNombrePlaneta("Pirata LvL " + (planeta.getCoordenadaY()+1));
		
		planetaInstalacionMetal.setUltimaGeneracion(new Date());
		planetaInstalacionMetal.setNivelInstalacion(pirataInstalacionRepo.findByPirataidPirata_Instalacionname(planeta.getCoordenadaY()+1, "Mina de Metal").getNivelDefecto());
		planetaInstalacionOro.setUltimaGeneracion(new Date());
		planetaInstalacionOro.setNivelInstalacion(pirataInstalacionRepo.findByPirataidPirata_Instalacionname(planeta.getCoordenadaY()+1, "Mina de Oro").getNivelDefecto());
		planetaInstalacionPetroleo.setUltimaGeneracion(new Date());
		planetaInstalacionPetroleo.setNivelInstalacion(pirataInstalacionRepo.findByPirataidPirata_Instalacionname(planeta.getCoordenadaY()+1, "Plataforma Petrolifera").getNivelDefecto());
		
		planetaRecursoMetal.setCantidad(0);
		planetaRecursoOro.setCantidad(0);
		planetaRecursoPetroleo.setCantidad(0);
		
		listaNavesDefecto = new ArrayList<>(pirataNaveRepo.findByPirataidPirata(planeta.getCoordenadaY()+1));
		
		for(int i = 0; i < listaNavesDefecto.size(); i++)
		{
			listaNaves.add(new PlanetaHasNave(planeta.getCoordenadaX(), planeta.getCoordenadaY(), planeta.getSistemanombreSistema(), listaNavesDefecto.get(i).getNavenombreNave(), listaNavesDefecto.get(i).getCantidadDefecto()));
		}
		
		planetaNaveRepo.deleteAll(planetaNaveRepo.findByPlaneta(planeta.getCoordenadaX(), planeta.getCoordenadaY(), planeta.getSistemanombreSistema()));
						
		planetaRepo.save(planeta);
		
		planetaInstalacionRepo.save(planetaInstalacionMetal);
		planetaInstalacionRepo.save(planetaInstalacionOro);
		planetaInstalacionRepo.save(planetaInstalacionPetroleo);
		
		planetaRecursoRepo.save(planetaRecursoMetal);
		planetaRecursoRepo.save(planetaRecursoOro);
		planetaRecursoRepo.save(planetaRecursoPetroleo);
		
		planetaNaveRepo.saveAll(listaNaves);
		
		usuarioRepo.delete(usuario);
		
		return "";
	}
}
