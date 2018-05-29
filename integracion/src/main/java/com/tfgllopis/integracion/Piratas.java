package com.tfgllopis.integracion;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.navigator.View;
import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.Button.ClickEvent;

public class Piratas extends Piratas_Ventana implements View 
{
	@Autowired
	private PirataRepository pirataRepo;
	
	@Autowired
	private PiratahasInstalacionRepository pirataInstalacionRepo;
	
	public static String VIEW_NAME = "piratas";
	private List<Pirata> piratasTecnicoL;
	
	public Piratas()
	{
		pirataRepo = ((VaadinUI) UI.getCurrent()).getInterfazPirata();
		pirataInstalacionRepo = ((VaadinUI) UI.getCurrent()).getInterfazPirataInstalacion();
		
		piratasTecnicoL = pirataRepo.findAll();
		piratasTabla.setItems(piratasTecnicoL);

		//Columna imagen
		piratasTabla.addComponentColumn(pirata -> {
			Image imagen = new Image();
			imagen.setSource(new FileResource(new File(new File("").getAbsolutePath() + "/images/" + "No_Image_Available.png")));
			imagen.setWidth("50px");
			imagen.setHeight("50px");
			return imagen;	
		}).setCaption("")
		.setId("imagenPlaneta");
		
		//Columna nombre
		piratasTabla.addComponentColumn(pirata -> {
			return new Label("Pirata LvL " + pirata.getIdPirata());
		}).setCaption("Nombre")
		.setId("Nombre");
		
		//Columna nivel metal
		piratasTabla.addComponentColumn(pirata -> {
			return new Label(pirataInstalacionRepo.findByPirataidPirata_Instalacionname(pirata.getIdPirata(), "Mina de Metal").getNivelDefecto() + "");
		}).setCaption("Nivel Mina de Metal")
		.setId("NivMetal");
		
		//Columna nivel oro
		piratasTabla.addComponentColumn(pirata -> {
			return new Label(pirataInstalacionRepo.findByPirataidPirata_Instalacionname(pirata.getIdPirata(), "Mina de Oro").getNivelDefecto() + "");
		}).setCaption("Nivel Mina de Oro")
		.setId("NivOro");
				
		//Columna nivel petroleo
		piratasTabla.addComponentColumn(pirata -> {
			return new Label(pirataInstalacionRepo.findByPirataidPirata_Instalacionname(pirata.getIdPirata(), "Plataforma Petrolifera").getNivelDefecto() + "");
		}).setCaption("Nivel Plataforma Petrolifera")
		.setId("NivPetroleo");
		
		//Columna botón
		piratasTabla.addComponentColumn(pirata -> {
			HorizontalLayout hl = new HorizontalLayout();
			Button editar = new Button("Editar");
			editar.addStyleName("link");
			editar.addClickListener(new Button.ClickListener()
			{
						
				@Override
				public void buttonClick(ClickEvent event) 
				{
					doNavigate(Editar_pirata.VIEW_NAME  + "/" + pirata.getIdPirata());
				}
			});
			hl.addComponent(editar);
			return hl;
		}).setCaption("-")
		.setId("Buttons");
				
	}
	
	private void doNavigate(String viewName) 
	{
		UI.getCurrent().getNavigator().navigateTo(viewName);
	}
	
	
}
