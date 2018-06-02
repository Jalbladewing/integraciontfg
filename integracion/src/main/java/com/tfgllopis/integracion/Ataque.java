package com.tfgllopis.integracion;

import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.data.HasValue.ValueChangeEvent;
import com.vaadin.data.HasValue.ValueChangeListener;
import com.vaadin.navigator.View;
import com.vaadin.ui.UI;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.TextField;

public class Ataque extends Ataque_Ventana implements View
{
	@Autowired
	PlanetaRepository planetaRepo;
	
	@Autowired
	PlanetaHasNaveRepository planetaNaveRepo;
	
	@Autowired
	private MovimientoRepository movimientoRepo;
	
	@Autowired
	private MovimientohasNaveRepository movimientoNaveRepo;
	
	private EntityManager em;
	
	public static String VIEW_NAME = "ataque";
		
	@SuppressWarnings("unchecked")
	public Ataque()
	{
		Informacion_movimiento infoMovimiento;
		Nave_ataque aux;
		List<PlanetaHasNave> planetaNavesL;
		Planeta planetaUsuario;
		Planeta planetaAtaque = ((VaadinUI) UI.getCurrent()).getPlanetaAtaque();
		
		if(planetaAtaque == null) return;
		
		tituloAtaque.setValue("Ataque Vs " +planetaAtaque.getNombrePlaneta());
		
		em = ((VaadinUI) UI.getCurrent()).getEntitymanager();
		((VaadinUI) UI.getCurrent()).setPlanetaAtaque(null);
		planetaRepo = ((VaadinUI) UI.getCurrent()).getInterfazPlaneta();
		planetaNaveRepo = ((VaadinUI) UI.getCurrent()).getInterfazPlanetaNave();
		movimientoRepo = ((VaadinUI) UI.getCurrent()).getInterfazMovimiento();
		movimientoNaveRepo = ((VaadinUI) UI.getCurrent()).getInterfazMovimientoNave();
		planetaUsuario = planetaRepo.findByUsuarioUsername(((VaadinUI) UI.getCurrent()).getUsuario());
		planetaNavesL = planetaNaveRepo.findByPlaneta(planetaUsuario.getCoordenadaX(), planetaUsuario.getCoordenadaY(), planetaUsuario.getSistemanombreSistema());
		
		infoMovimiento = new Informacion_movimiento(planetaUsuario, planetaAtaque);
		layoutInformacion.addComponent(infoMovimiento);		
		
		for(int i = 0; i < planetaNavesL.size(); i++)
		{
			aux = new Nave_ataque(planetaNavesL.get(i));
			
			aux.cantidadF.addValueChangeListener(new ValueChangeListener() 
			{
				@SuppressWarnings("rawtypes")
				@Override
				public void valueChange(ValueChangeEvent event) 
				{
					TextField aux = (TextField) event.getComponent();
					if(NaveDataValidator.comprobarValorInteger(aux.getValue()))
					{
						infoMovimiento.actualizarTiempo(layoutNaves);
					}
					
				}
	        });
			
			layoutNaves.addComponent(aux);
		}
		
		atacarB.addClickListener(new Button.ClickListener() 
		{
			

			@Override
			public void buttonClick(ClickEvent event) 
			{
				String value = CrudNave.confirmarAtaque((Iterator<Nave_ataque>)(Object) layoutNaves.iterator(), infoMovimiento.getFechaLlegada(), infoMovimiento.getFechaVuelta(), ((VaadinUI) UI.getCurrent()).getUsuario(), planetaUsuario, planetaAtaque, movimientoRepo, movimientoNaveRepo, planetaNaveRepo, em);
				errorL.setValue(value);
				
				if(value.isEmpty())
				{
					errorL.setVisible(false);
					doNavigate(Ver_naves.VIEW_NAME);
					
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
				doNavigate(Galaxia.VIEW_NAME);				
			}
		});
		
	}
	
	private void doNavigate(String viewName) 
	{
		UI.getCurrent().getNavigator().navigateTo(viewName);
	}
	
}
