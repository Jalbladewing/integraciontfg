package com.tfgllopis.integracion;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.navigator.View;
import com.vaadin.ui.UI;

public class Ver_movimiento extends Ver_movimiento_Ventana implements View
{
	@Autowired
	PlanetaRepository planetaRepo;
	
	@Autowired
	private MovimientoRepository movimientoRepo;
	
	@Autowired
	private MovimientohasNaveRepository movimientoNaveRepo;
	
	public static String VIEW_NAME = "verMovimiento";
	private List<Movimiento> movimientosL;
	
	public Ver_movimiento()
	{
		Planeta planetaUsuario;
		planetaRepo = ((VaadinUI) UI.getCurrent()).getInterfazPlaneta();
		movimientoRepo = ((VaadinUI) UI.getCurrent()).getInterfazMovimiento();
		movimientoNaveRepo = ((VaadinUI) UI.getCurrent()).getInterfazMovimientoNave();
		planetaUsuario = planetaRepo.findByUsuarioUsername(((VaadinUI) UI.getCurrent()).getUsuario());
		movimientosL = movimientoRepo.findByMovimientosUsuario(planetaUsuario, ((VaadinUI) UI.getCurrent()).getUsuario());
		
		for(int i = 0; i < movimientosL.size(); i++)
		{
			movimientosLayout.addComponent(new Movimiento_jugador(movimientosL.get(i), planetaRepo, movimientoRepo, movimientoNaveRepo));
		}
		
		
	}

}
