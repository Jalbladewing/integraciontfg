package com.tfgllopis.integracion;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.navigator.View;
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

		
		Planeta planeta = planetaRepo.findByUsuarioUsername(((VaadinUI) UI.getCurrent()).getUsuario());
		
		CrudRecurso.generarRecursos(planeta, planetaRecursoRepo, planetaInstalacionRepo);
		CrudNave.checkConstruccion(((VaadinUI) UI.getCurrent()).getUsuario(), construyeRepo, planetaRepo, planetaNaveRepo, usuarioNaveRepo, planetaRecursoRepo, naveCuestaRepo);
		
		metalL.setValue(planetaRecursoRepo.findByPlanetaRecurso(planeta.getCoordenadaX(), planeta.getCoordenadaY(), planeta.getSistemanombreSistema(), "Metal").getCantidad() + "");
		oroL.setValue(planetaRecursoRepo.findByPlanetaRecurso(planeta.getCoordenadaX(), planeta.getCoordenadaY(), planeta.getSistemanombreSistema(), "Oro").getCantidad() + "");
		petroleoL.setValue(planetaRecursoRepo.findByPlanetaRecurso(planeta.getCoordenadaX(), planeta.getCoordenadaY(), planeta.getSistemanombreSistema(), "Petroleo").getCantidad() + "");
System.out.println(VaadinServlet.getCurrent().getServletContext().getRealPath("/"));
System.out.println(new File("").getAbsolutePath());

	}
}

