package com.tfgllopis.integracion;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.navigator.View;
import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TabSheet.SelectedTabChangeEvent;
import com.vaadin.ui.TabSheet.SelectedTabChangeListener;
import com.vaadin.ui.TabSheet.Tab;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;

public class Galaxia extends Galaxia_Ventana implements View
{
	@Autowired
	private PlanetaRepository planetaRepo;
	
	@Autowired
	private SistemaRepository sistemaRepo;
	
	@Autowired
	private MovimientoRepository movimientoRepo;
	
	@Autowired
	private InformeBatallaRepository informeRepo;
		
	@Autowired
	private InformeBatallahasNaveDefensaRepository informeDefensaRepo;
	
	public static String VIEW_NAME = "galaxia";
	private List<Planeta> planetasL;
	private List<Sistema> sistemasL;
	private Button seleccionadoB;
	private Component currentComponent;
	
	public Galaxia()
	{
		VerticalLayout aux;
		planetaRepo = ((VaadinUI) UI.getCurrent()).getInterfazPlaneta();
		sistemaRepo = ((VaadinUI) UI.getCurrent()).getInterfazSistema();
		movimientoRepo = ((VaadinUI) UI.getCurrent()).getInterfazMovimiento();
		informeDefensaRepo = ((VaadinUI) UI.getCurrent()).getInterfazInformeDefensa();
		informeRepo = ((VaadinUI) UI.getCurrent()).getInterfazInformeBatalla();
		
		sistemasL = sistemaRepo.findAll();
		sistemasTab.getTab(0).getComponent().setId("0");
		sistemasTab.getTab(0).setId(sistemasL.get(0).getNombreSistema());
		sistemasTab.getTab(0).setCaption(sistemasL.get(0).getNombreSistema());
		currentComponent = sistemasTab.getSelectedTab();
		
		for(int i = 1; i < sistemasL.size(); i++)
		{
			aux = new VerticalLayout();
			aux.setId(i + "");
			
			sistemasTab.addTab(aux, sistemasL.get(i).getNombreSistema());
			sistemasTab.getTab(i).setId(sistemasL.get(i).getNombreSistema());
		}
		
		initTab(sistemasL.get(0).getNombreSistema());
		
		//Columna imagen
		planetaTabla.addComponentColumn(planeta -> {
			Image imagen = new Image();
			imagen.setSource(new FileResource(new File(new File("").getAbsolutePath() + "/images/" + "No_Image_Available.png")));
			imagen.setWidth("50px");
			imagen.setHeight("50px");
			return imagen;	
		}).setCaption("Planeta")
		.setId("imagenPlaneta");
		
		//Columna coordenadas planeta
		planetaTabla.addComponentColumn(planeta -> {
			Label coordenadaPlanetaL = new Label("(" + planeta.getCoordenadaX() + "," + planeta.getCoordenadaY() + ")");
			return coordenadaPlanetaL;
		}).setCaption("Coordenadas (Planeta)")
		  .setId("coordenadaPlaneta");
		
		//Columna coordenadas planeta
		planetaTabla.addComponentColumn(planeta -> {
			Label coordenadaSistemaL = new Label("(" + planeta.getSistema().getCoordenadaX() + "," + planeta.getSistema().getCoordenadaY() + ")");
			return coordenadaSistemaL;
		}).setCaption("Coordenadas (Sistema)")
		  .setId("coordenadaSistema");
		
		//Columna botón
		planetaTabla.addComponentColumn(planeta -> {
			HorizontalLayout hl = new HorizontalLayout();
			Button atacar = new Button("Atacar");
			//atacar.addStyleName("link");
			String value = comprobarPlanetaDisponible(((VaadinUI) UI.getCurrent()).getUsuario(), planeta, movimientoRepo, informeRepo, informeDefensaRepo);
			if(!value.isEmpty())
			{
				atacar.setDescription(value);
				atacar.setEnabled(false);				
			}
			
			atacar.addClickListener(new Button.ClickListener()
			{
						
				@Override
				public void buttonClick(ClickEvent event) 
				{
					((VaadinUI) UI.getCurrent()).setPlanetaAtaque(planeta);
					doNavigate(Ataque.VIEW_NAME);
				}
			});
			hl.addComponent(atacar);
			return hl;
		}).setCaption("-")
		  .setId("buttons");
		
		planetaTabla.setColumnOrder("imagenPlaneta", "nombrePlaneta", "coordenadaPlaneta", "sistemanombreSistema", "coordenadaSistema", "buttons");
	
		sistemasTab.addSelectedTabChangeListener(new SelectedTabChangeListener()
		{

			@Override
			public void selectedTabChange(SelectedTabChangeEvent event) 
			{
				if(event.getTabSheet().getSelectedTab() != currentComponent)
				{
					int idSeleccionada = Integer.parseInt(event.getTabSheet().getSelectedTab().getId());
					String nombreSeleccionada = event.getTabSheet().getTab(idSeleccionada).getCaption();
					Tab nuevaTab;
					VerticalLayout aux = new VerticalLayout();
					
					aux.setId(currentComponent.getId());
					event.getTabSheet().replaceComponent(currentComponent, aux);
					currentComponent.setId(idSeleccionada + "");
					
					nuevaTab = event.getTabSheet().addTab(currentComponent, nombreSeleccionada);
					event.getTabSheet().setSelectedTab(nuevaTab);
					
					event.getTabSheet().removeTab(event.getTabSheet().getTab(idSeleccionada));
					event.getTabSheet().setTabPosition(nuevaTab, idSeleccionada);	
					currentComponent = event.getTabSheet().getSelectedTab();
					
					hLayoutBotones.removeAllComponents();
					initTab(event.getTabSheet().getTab(currentComponent).getCaption());
				}				
			}			
		});
	}
	
	private void initTab(String nombreSistema)
	{
		int contadorPlanetas = 1;
		Button coordenadaYB;
		
		planetasL = planetaRepo.findBySistemanombreSistema(nombreSistema);
		
		for(int i = 0; i < planetasL.size(); i += 10)
		{
			coordenadaYB = new Button(contadorPlanetas + "");
			coordenadaYB.setId(contadorPlanetas + "");
			hLayoutBotones.addComponent(coordenadaYB);
			
			if(contadorPlanetas == 1) seleccionadoB = coordenadaYB;
			contadorPlanetas++;
			
			coordenadaYB.addClickListener(new Button.ClickListener() 
			{
				
				@Override
				public void buttonClick(ClickEvent event) 
				{
					int subListInicio = 10*(Integer.parseInt(event.getButton().getId()) - 1);
					int subListFinal = 10 +  subListInicio;
					
					seleccionadoB.removeStyleName("friendly");
					seleccionadoB = event.getButton();
					seleccionadoB.addStyleName("friendly");
					
					if(subListFinal > planetasL.size()-1) subListFinal = planetasL.size();
				
					planetaTabla.setItems(planetasL.subList(subListInicio, subListFinal));
				}
			});
			
		}
		
		
		if(planetasL.size()-1 < 10) 
		{
			planetaTabla.setItems(planetasL.subList(0, planetasL.size()));
		}else
		{
			planetaTabla.setItems(planetasL.subList(0, 10));

		}
		seleccionadoB.addStyleName("friendly");
	}
	
	public static String comprobarPlanetaDisponible(Usuario usuario, Planeta planeta, MovimientoRepository movimientoRepo, InformeBatallaRepository informeRepo, InformeBatallahasNaveDefensaRepository informeDefensaRepo)
	{
		List<InformeBatallahasNaveDefensa> aux;
		InformeBatalla informeBatalla;
		int seconds;
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.HOUR_OF_DAY, -8);
		Date fechaAhora = calendar.getTime();
		int contadorDerrotas = 0;		
		InformeBatalla ultimoInforme = planeta.getInformeBatallaidBatalla();
		List<Movimiento> movimientos = movimientoRepo.findByPlanetaDestino(planeta);

		if(usuario.equals(planeta.getUsuariousername())) return "No puedes atacarte a ti mismo";
		if(ultimoInforme == null) return "";
		
		seconds = (int) (fechaAhora.getTime() - ultimoInforme.getMovimientoidMovimiento().getTiempoLlegada().getTime())/1000;
		if(seconds >= 0) return "";
		
		for(int i = movimientos.size()-1; i > 0; i--)
		{
			calendar = Calendar.getInstance();
			calendar.setTime(movimientos.get(i).getTiempoLlegada());
			calendar.add(Calendar.HOUR_OF_DAY, -8);
			
			seconds = (int) (calendar.getTime().getTime() - movimientos.get(i-1).getTiempoLlegada().getTime())/1000;
			if(seconds >= 0) return "";
			
			informeBatalla = informeRepo.findByIdMovimiento(movimientos.get(i));
			
			if(informeBatalla == null) continue;
			
			aux = informeDefensaRepo.findByInformeBatallaidBatalla(informeBatalla.getIdBatalla());

			if(aux != null && !aux.isEmpty() && aux.get(0).getCantidadEnviada() != aux.get(0).getCantidadPerdida()) break;
			
			contadorDerrotas++;
		}
		
		if(contadorDerrotas >= 5) return "Planeta con protección";
		
		return "";
	}
	
	private void doNavigate(String viewName) 
	{
		UI.getCurrent().getNavigator().navigateTo(viewName);
	}
}
