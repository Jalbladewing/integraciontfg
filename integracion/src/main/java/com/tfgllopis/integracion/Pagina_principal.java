package com.tfgllopis.integracion;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.navigator.View;
import com.vaadin.ui.UI;

public class Pagina_principal extends Pagina_principal_Ventana implements View
{
	@Autowired
	PlanetaRepository planetaRepo;
	
	@Autowired
	PlanetahasRecursoRepository planetaRecursoRepo;
	
	@Autowired
	PlanetahasInstalacionRepository planetaInstalacionRepo;
	
	public static final String VIEW_NAME = "paginaPrincipal";
		
	public Pagina_principal()
	{
		planetaRepo = ((VaadinUI) UI.getCurrent()).getInterfazPlaneta();
		planetaRecursoRepo = ((VaadinUI) UI.getCurrent()).getInterfazPlanetaRecurso();
		planetaInstalacionRepo = ((VaadinUI) UI.getCurrent()).getInterfazPlanetaInstalacion();
		
		Planeta planeta = planetaRepo.findByUsuarioUsername(((VaadinUI) UI.getCurrent()).getUsuario());
		
		CrudRecurso.generarRecursos(planeta, planetaRecursoRepo, planetaInstalacionRepo);
		
		metalL.setValue(planetaRecursoRepo.findByPlanetaRecurso(planeta.getCoordenadaX(), planeta.getCoordenadaY(), planeta.getSistemanombreSistema(), "Metal").getCantidad() + "");
		oroL.setValue(planetaRecursoRepo.findByPlanetaRecurso(planeta.getCoordenadaX(), planeta.getCoordenadaY(), planeta.getSistemanombreSistema(), "Oro").getCantidad() + "");
		petroleoL.setValue(planetaRecursoRepo.findByPlanetaRecurso(planeta.getCoordenadaX(), planeta.getCoordenadaY(), planeta.getSistemanombreSistema(), "Petroleo").getCantidad() + "");

	}
}

