package com.tfgllopis.integracion;

import java.util.Date;

import com.vaadin.ui.Button;
import com.vaadin.ui.UI;
import com.vaadin.ui.Button.ClickEvent;

public class Movimiento_jugador extends Movimiento_jugador_Ventana 
{
	static final int SECOND = 1000;        // no. of ms in a second
	static final int MINUTE = SECOND * 60; // no. of ms in a minute
	static final int HOUR = MINUTE * 60;   // no. of ms in an hour
	static final int DAY = HOUR * 24;      // no. of ms in a day
	
	private Movimiento movimiento;
	
	public Movimiento_jugador(Movimiento movimiento, PlanetaRepository planetaRepo, MovimientoRepository movimientoRepo, MovimientohasNaveRepository movimientoNaveRepo)
	{
		Planeta planetaOrigen = planetaRepo.findByUsuarioUsername(movimiento.getUsuariousername());
		Planeta planetaUsuario = planetaRepo.findByUsuarioUsername(((VaadinUI) UI.getCurrent()).getUsuario());
		
		if(movimiento.getPlaneta().equals(planetaUsuario))
		{
			regresarB.setEnabled(false);
		}
		
		if(planetaOrigen.equals(movimiento.getPlaneta()))
		{
			layoutOrigen.setVisible(false);
			coordenadasOrigenL.setVisible(false);
			regresarB.setEnabled(false);
		}
		
		this.movimiento = movimiento;
		nombreOrigen.setValue(planetaOrigen.getNombrePlaneta());
		coordenadasOrigenL.setValue("(" + planetaOrigen.getCoordenadaX() + "," + planetaOrigen.getCoordenadaY() + ")");
		nombreDestino.setValue(movimiento.getPlaneta().getNombrePlaneta());
		coordenadasDestinoL.setValue("(" + movimiento.getPlaneta().getCoordenadaX() + "," + movimiento.getPlaneta().getCoordenadaY() + ")");
		setExpandRatio(movimiento);
		setTiempoRestante(movimiento);
		
		configurarBotones(movimiento, planetaOrigen, movimientoRepo, movimientoNaveRepo);
				
	}
	
	private void configurarBotones(Movimiento movimiento, Planeta planetaOrigen, MovimientoRepository movimientoRepo, MovimientohasNaveRepository movimientoNaveRepo)
	{
		verNavesB.addClickListener(new Button.ClickListener() 
		{
			
			@Override
			public void buttonClick(ClickEvent event) 
			{
				((VaadinUI) UI.getCurrent()).setMovimiento(movimiento);
				doNavigate(Ver_naves.VIEW_NAME);
			}
		});
		
		regresarB.addClickListener(new Button.ClickListener() 
		{
			
			@Override
			public void buttonClick(ClickEvent event) 
			{
				CrudNave.cancelarAtaque(((VaadinUI) UI.getCurrent()).getEntitymanager(), planetaOrigen, movimiento, movimientoRepo, movimientoNaveRepo);
				doNavigate(Ver_movimiento.VIEW_NAME);
			}
		});
	}
	
	private void setExpandRatio(Movimiento movimiento)
	{
		float expandRatio;
		Date fechaAhora = new Date();
		double secondsTotal = (movimiento.getTiempoLlegada().getTime() - movimiento.getTiempoEnvio().getTime())/1000;
		double secondsRestante = (movimiento.getTiempoLlegada().getTime() - fechaAhora.getTime())/1000;
		double seconds = ((secondsTotal - secondsRestante) / secondsTotal);
		expandRatio = (float) (100 - (seconds * 100)) / 10;
		
		if(expandRatio < 0.5) expandRatio = (float) 0.5;
		hLayout.setExpandRatio(layoutTiempoRestante, expandRatio);
	}
	
	private void setTiempoRestante(Movimiento movimiento)
	{
		Date fechaAhora = new Date();
		long tiempoRestante = movimiento.getTiempoLlegada().getTime() - fechaAhora.getTime();
		int hours   = (int)((tiempoRestante % DAY) / HOUR);
		int minutes = (int)((tiempoRestante % HOUR) / MINUTE);
		int seconds = (int)((tiempoRestante % MINUTE) / SECOND);
		tiempoRestanteL.setValue(String.format("%02d", hours) + ":" + String.format("%02d", minutes) + ":" + String.format("%02d", seconds));
	}
	
	private void doNavigate(String viewName) 
	{
		UI.getCurrent().getNavigator().navigateTo(viewName);
	}

}
