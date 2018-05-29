package com.tfgllopis.integracion;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.ui.UI;
import com.vaadin.server.ClassResource;
import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Label;

public class Instalacion_jugador extends Instalacion_jugador_Ventana
{
	@Autowired
	private InstalacioncuestaRecursoRepository instalacionRecursoRepo;
	
	@Autowired
	private PlanetaRepository planetaRepo;
	
	@Autowired
	private PlanetahasInstalacionRepository planetaInstalacionRepo;
	
	@Autowired
	private PlanetahasRecursoRepository planetaRecursoRepo;
	
	public Instalacion_jugador(PlanetahasInstalacion instalacion, Label errorL, Label correctoL)
	{
		instalacionRecursoRepo = ((VaadinUI) UI.getCurrent()).getInterfazInstalacionRecurso();
		planetaRepo = ((VaadinUI) UI.getCurrent()).getInterfazPlaneta();
		planetaInstalacionRepo = ((VaadinUI) UI.getCurrent()).getInterfazPlanetaInstalacion();
		planetaRecursoRepo = ((VaadinUI) UI.getCurrent()).getInterfazPlanetaRecurso();
		FileResource resource = new FileResource(new File(new File("").getAbsolutePath() + "/images/" + "No_Image_Available.png"));
		
		instalacionImagen.setSource(resource);
		produccionL.setValue(instalacion.getInstalacion().getGeneracionBase() * instalacion.getNivelInstalacion() + "");
		nivelL.setValue(instalacion.getNivelInstalacion() + "");
		metalL.setValue(instalacionRecursoRepo.findByRecursoInstalacionname(instalacion.getInstalacionname(), "Metal").getCantidadBase() * instalacion.getNivelInstalacion() + "");
		oroL.setValue(instalacionRecursoRepo.findByRecursoInstalacionname(instalacion.getInstalacionname(), "Oro").getCantidadBase() * instalacion.getNivelInstalacion() + "");
		petroleoL.setValue(instalacionRecursoRepo.findByRecursoInstalacionname(instalacion.getInstalacionname(), "Petroleo").getCantidadBase() * instalacion.getNivelInstalacion() + "");
		
		subirNivelB.addClickListener(new Button.ClickListener() 
		{
			
			@Override
			public void buttonClick(ClickEvent event) 
			{
				String value = CrudRecurso.subirNivel(((VaadinUI) UI.getCurrent()).getUsuario(), instalacion.getInstalacionname(), planetaRepo, planetaInstalacionRepo, planetaRecursoRepo, instalacionRecursoRepo);
				errorL.setValue(value);
				
				if(value.isEmpty())
				{
					doNavigate(Recursos.VIEW_NAME  + "/" + instalacion.getInstalacionname());
					
				}else
				{
					correctoL.setVisible(false);
					errorL.setVisible(true);
				}
			}
		});
	}
	
	private void doNavigate(String viewName) 
	{
		UI.getCurrent().getNavigator().navigateTo(viewName);
	}
}
