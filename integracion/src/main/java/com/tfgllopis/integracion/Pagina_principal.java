package com.tfgllopis.integracion;

import java.io.File;
import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.navigator.View;
import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;

public class Pagina_principal extends Pagina_principal_Ventana implements View
{
	@Autowired
	private PlanetaRepository planetaRepo;
	
	@Autowired
	private PlanetahasRecursoRepository planetaRecursoRepo;
	
	@Autowired
	private PlanetahasInstalacionRepository planetaInstalacionRepo;
	
	@Autowired
	private UsuarioconstruyeNaveRepository construyeRepo;
	
	@Autowired
	private PlanetaHasNaveRepository planetaNaveRepo;
	
	@Autowired
	private UsuarioHasNaveRepository usuarioNaveRepo;
	
	@Autowired
	private NavecuestaRecursoRepository naveCuestaRepo;
	
	
	public static final String VIEW_NAME = "paginaPrincipal";
		
	public Pagina_principal()
	{
		planetaRepo = ((VaadinUI) UI.getCurrent()).getInterfazPlaneta();
		planetaRecursoRepo = ((VaadinUI) UI.getCurrent()).getInterfazPlanetaRecurso();
		planetaInstalacionRepo = ((VaadinUI) UI.getCurrent()).getInterfazPlanetaInstalacion();
		construyeRepo = ((VaadinUI) UI.getCurrent()).getInterfazUsuarioConstruye();
		planetaNaveRepo = ((VaadinUI) UI.getCurrent()).getInterfazPlanetaNave();
		usuarioNaveRepo = ((VaadinUI) UI.getCurrent()).getInterfazUsuarioNave();
		naveCuestaRepo = ((VaadinUI) UI.getCurrent()).getInterfazNaveCuesta();

		
		imagenMetal.setSource(new FileResource(new File(new File("").getAbsolutePath() + "/images/" + "Metal.png")));
		imagenOro.setSource(new FileResource(new File(new File("").getAbsolutePath() + "/images/" + "Oro.png")));
		imagenPetroleo.setSource(new FileResource(new File(new File("").getAbsolutePath() + "/images/" + "Petroleo.png")));

		
		Planeta planeta = planetaRepo.findByUsuarioUsername(((VaadinUI) UI.getCurrent()).getUsuario());
		
		Pagina_principal.generarRecursos(planeta, planetaRecursoRepo, planetaInstalacionRepo);
		Nave_en_construccion.checkConstruccion(((VaadinUI) UI.getCurrent()).getUsuario(), construyeRepo, planetaRepo, planetaNaveRepo, usuarioNaveRepo, planetaRecursoRepo, naveCuestaRepo);
		
		metalL.setValue(planetaRecursoRepo.findByPlanetaRecurso(planeta.getCoordenadaX(), planeta.getCoordenadaY(), planeta.getSistemanombreSistema(), "Metal").getCantidad() + "");
		oroL.setValue(planetaRecursoRepo.findByPlanetaRecurso(planeta.getCoordenadaX(), planeta.getCoordenadaY(), planeta.getSistemanombreSistema(), "Oro").getCantidad() + "");
		petroleoL.setValue(planetaRecursoRepo.findByPlanetaRecurso(planeta.getCoordenadaX(), planeta.getCoordenadaY(), planeta.getSistemanombreSistema(), "Petroleo").getCantidad() + "");

	}
	
	
	public static String generarRecursos(Planeta planeta, PlanetahasRecursoRepository planetaRecursoRepo, PlanetahasInstalacionRepository planetaInstalacionRepo)
	{
		Timestamp fechaAhora = new Timestamp(System.currentTimeMillis());
		int generacionSegundoMetal, generacionSegundoOro, generacionSegundoPetroleo, metalGenerado, oroGenerado, petroleoGenerado, segundos;
		PlanetahasRecurso metalPlaneta, oroPlaneta, petroleoPlaneta;
		PlanetahasInstalacion instalacionMetal = planetaInstalacionRepo.findByInstalacionnamePlaneta("Mina de Metal", planeta.getCoordenadaX(), planeta.getCoordenadaY(), planeta.getSistemanombreSistema());
		PlanetahasInstalacion instalacionOro = planetaInstalacionRepo.findByInstalacionnamePlaneta("Mina de Oro", planeta.getCoordenadaX(), planeta.getCoordenadaY(), planeta.getSistemanombreSistema());
		PlanetahasInstalacion instalacionPetroleo = planetaInstalacionRepo.findByInstalacionnamePlaneta("Plataforma Petrolifera", planeta.getCoordenadaX(), planeta.getCoordenadaY(), planeta.getSistemanombreSistema());

		generacionSegundoMetal = instalacionMetal.getNivelInstalacion() * (int) instalacionMetal.getInstalacion().getGeneracionBase();
		generacionSegundoOro = instalacionOro.getNivelInstalacion() * (int) instalacionOro.getInstalacion().getGeneracionBase();
		generacionSegundoPetroleo = instalacionPetroleo.getNivelInstalacion() * (int) instalacionPetroleo.getInstalacion().getGeneracionBase();

		segundos =  (int) (fechaAhora.getTime()-instalacionMetal.getUltimaGeneracion().getTime())/1000;

		instalacionMetal.setUltimaGeneracion(fechaAhora);
		instalacionOro.setUltimaGeneracion(fechaAhora);
		instalacionPetroleo.setUltimaGeneracion(fechaAhora);
		
		metalGenerado = generacionSegundoMetal * segundos;
		oroGenerado = generacionSegundoOro * segundos;
		petroleoGenerado = generacionSegundoPetroleo * segundos;
		
		metalPlaneta = planetaRecursoRepo.findByPlanetaRecurso(planeta.getCoordenadaX(), planeta.getCoordenadaY(), planeta.getSistemanombreSistema(), "Metal");
		oroPlaneta = planetaRecursoRepo.findByPlanetaRecurso(planeta.getCoordenadaX(), planeta.getCoordenadaY(), planeta.getSistemanombreSistema(), "Oro");
		petroleoPlaneta = planetaRecursoRepo.findByPlanetaRecurso(planeta.getCoordenadaX(), planeta.getCoordenadaY(), planeta.getSistemanombreSistema(), "Petroleo");
		
		metalPlaneta.setCantidad(metalPlaneta.getCantidad() + metalGenerado);
		oroPlaneta.setCantidad(oroPlaneta.getCantidad() + oroGenerado);
		petroleoPlaneta.setCantidad(petroleoPlaneta.getCantidad() + petroleoGenerado);
		
		planetaRecursoRepo.save(metalPlaneta);
		planetaRecursoRepo.save(oroPlaneta);
		planetaRecursoRepo.save(petroleoPlaneta);
		
		planetaInstalacionRepo.save(instalacionMetal);
		planetaInstalacionRepo.save(instalacionOro);
		planetaInstalacionRepo.save(instalacionPetroleo);

		return "";
	}
}

