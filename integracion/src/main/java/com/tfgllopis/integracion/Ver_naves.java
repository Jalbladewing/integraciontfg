package com.tfgllopis.integracion;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.navigator.View;
import com.vaadin.ui.UI;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;

public class Ver_naves extends Ver_naves_Ventana implements View
{
	@Autowired
	PlanetaRepository planetaRepo;
	
	@Autowired
	MovimientohasNaveRepository movimientoNaveRepo;
	
	public static String VIEW_NAME = "verNaves";
	private List<MovimientohasNave> navesMovimientoL;
	
	public Ver_naves()
	{
		Movimiento movimiento = ((VaadinUI) UI.getCurrent()).getMovimiento();
		((VaadinUI) UI.getCurrent()).setMovimiento(null);
		if(movimiento == null) return;
		
		movimientoL.setValue("Movimiento a " + movimiento.getPlaneta().getNombrePlaneta());
		
		planetaRepo = ((VaadinUI) UI.getCurrent()).getInterfazPlaneta();
		movimientoNaveRepo = ((VaadinUI) UI.getCurrent()).getInterfazMovimientoNave();
		navesMovimientoL = movimientoNaveRepo.findByMovimientoidMovimiento(movimiento.getIdMovimiento());
		layoutInformacion.addComponent(new Movimiento_jugador_generico(movimiento, planetaRepo, null, null));
	
		for(int i = 0; i < navesMovimientoL.size(); i++)
		{
			layoutNaves.addComponent(new Nave_informacion(navesMovimientoL.get(i)));
		}
		
		atrasB.addClickListener(new Button.ClickListener() 
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
