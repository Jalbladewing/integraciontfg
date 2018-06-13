package com.tfgllopis.integracion;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.navigator.View;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;

public class Flota extends Flota_Ventana implements View
{
	@Autowired
	private UsuarioHasNaveRepository usuarioNaveRepo;
	
	@Autowired
	private PlanetaRepository planetaRepo;
	
	@Autowired
	private PlanetaHasNaveRepository planetaNaveRepo;
	
	@Autowired
	private UsuarioconstruyeNaveRepository construyeRepo;
	
	@Autowired
	private NavecuestaRecursoRepository naveCuestaRepo;
	
	@Autowired
	private PlanetahasRecursoRepository planetaRecursoRepo;
	
	public static String VIEW_NAME = "flota";
	private List<UsuarioHasNave> navesL;
	
	public Flota()
	{
		Usuario usuario = ((VaadinUI) UI.getCurrent()).getUsuario();
		Planeta planeta;
		usuarioNaveRepo = ((VaadinUI) UI.getCurrent()).getInterfazUsuarioNave();
		planetaRepo = ((VaadinUI) UI.getCurrent()).getInterfazPlaneta();
		planetaNaveRepo = ((VaadinUI) UI.getCurrent()).getInterfazPlanetaNave();
		construyeRepo = ((VaadinUI) UI.getCurrent()).getInterfazUsuarioConstruye();
		naveCuestaRepo = ((VaadinUI) UI.getCurrent()).getInterfazNaveCuesta();
		planetaRecursoRepo = ((VaadinUI) UI.getCurrent()).getInterfazPlanetaRecurso();

		Nave_en_construccion.checkConstruccion(((VaadinUI) UI.getCurrent()).getUsuario(), construyeRepo, planetaRepo, planetaNaveRepo, usuarioNaveRepo, planetaRecursoRepo, naveCuestaRepo);
		
		navesL = usuarioNaveRepo.findByUsuarioUsername(usuario.getUsername());
		planeta = planetaRepo.findByUsuarioUsername(usuario);
		
		naveTabla.setItems(navesL);
		
		//Columna imagen
		naveTabla.addComponentColumn(nave -> {
			Image imagen = new Image();
			imagen.setSource(nave.getNave().getImage().getSource());
			imagen.setWidth(50, Unit.PIXELS);
			imagen.setHeight(35, Unit.PIXELS);
			return imagen;	
		}).setCaption("Naves")
		.setId("imagenPlaneta");
		
		//Columna Espera
		naveTabla.addComponentColumn(nave -> {
			Label esperaL = new Label();
			int navesPlaneta = planetaNaveRepo.findByNavenombreNavePlaneta(nave.getNavenombreNave(), planeta.getCoordenadaX(), planeta.getCoordenadaY(), planeta.getSistemanombreSistema()).get(0).getCantidad();
			esperaL.setValue(navesPlaneta + "");
			return esperaL;
		}).setCaption("En espera")
		.setId("navesEspera");
		
		//Columna Movimiento
		naveTabla.addComponentColumn(nave -> {
			Label movimientoL = new Label();
			int navesPlaneta = planetaNaveRepo.findByNavenombreNavePlaneta(nave.getNavenombreNave(), planeta.getCoordenadaX(), planeta.getCoordenadaY(), planeta.getSistemanombreSistema()).get(0).getCantidad();
			movimientoL.setValue(nave.getCantidad() -  navesPlaneta + "");
			return movimientoL;
		}).setCaption("En movimiento")
		.setId("navesMovimiento");
		
		//Columna Movimiento
		naveTabla.addComponentColumn(nave -> {
			Label totalL = new Label(nave.getCantidad() + "");
			return totalL;
		}).setCaption("Total")
		.setId("navesTotal");
		
		movimientoB.addClickListener(new Button.ClickListener() 
		{
			
			@Override
			public void buttonClick(ClickEvent event) 
			{
				doNavigate(Ver_movimiento.VIEW_NAME);				
			}
		});
	}
	
	private void doNavigate(String viewName) 
	{
		UI.getCurrent().getNavigator().navigateTo(viewName);
	}

}
