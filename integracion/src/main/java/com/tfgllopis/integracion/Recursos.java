package com.tfgllopis.integracion;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.UI;

public class Recursos extends Recursos_Ventana implements View
{
	@Autowired
	private PlanetaRepository planetaRepo;
	
	@Autowired
	private PlanetahasInstalacionRepository planetaInstalacionRepo;
	
	public static final String VIEW_NAME = "recursos";
	
	public Recursos()
	{
		Planeta planeta;
		planetaRepo = ((VaadinUI) UI.getCurrent()).getInterfazPlaneta();
		planetaInstalacionRepo = ((VaadinUI) UI.getCurrent()).getInterfazPlanetaInstalacion();
		planeta = planetaRepo.findByUsuarioUsername(((VaadinUI) UI.getCurrent()).getUsuario());
		
		layoutInstalaciones.addComponent(new Instalacion_jugador(planetaInstalacionRepo.findByInstalacionnamePlaneta("Mina de Metal", planeta.getCoordenadaX(), planeta.getCoordenadaY(), planeta.getSistemanombreSistema()), errorL, correctoL));
		layoutInstalaciones.addComponent(new Instalacion_jugador(planetaInstalacionRepo.findByInstalacionnamePlaneta("Mina de Oro", planeta.getCoordenadaX(), planeta.getCoordenadaY(), planeta.getSistemanombreSistema()), errorL, correctoL));
		layoutInstalaciones.addComponent(new Instalacion_jugador(planetaInstalacionRepo.findByInstalacionnamePlaneta("Plataforma Petrolifera", planeta.getCoordenadaX(), planeta.getCoordenadaY(), planeta.getSistemanombreSistema()), errorL, correctoL));

	}
	

	@Override
	public void enter(ViewChangeEvent event) 
	{
		if (!event.getParameters().isEmpty())
		{
			correctoL.setValue(event.getParameters() + " subida de nivel correctamente.");
			correctoL.setVisible(true);
		}
	}

}
