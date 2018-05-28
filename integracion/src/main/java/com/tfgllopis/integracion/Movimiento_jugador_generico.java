package com.tfgllopis.integracion;

import java.util.Date;

import com.vaadin.ui.UI;

public class Movimiento_jugador_generico extends Movimiento_jugador_generico_Ventana 
{
	static final int SECOND = 1000;        // no. of ms in a second
	static final int MINUTE = SECOND * 60; // no. of ms in a minute
	static final int HOUR = MINUTE * 60;   // no. of ms in an hour
	static final int DAY = HOUR * 24;      // no. of ms in a day
	
	public Movimiento_jugador_generico(Movimiento movimiento, PlanetaRepository planetaRepo, MovimientoRepository movimientoRepo, MovimientohasNaveRepository movimientoNaveRepo)
	{
		Planeta planetaOrigen = planetaRepo.findByUsuarioUsername(movimiento.getUsuariousername());
		if(planetaOrigen.equals(movimiento.getPlaneta()))
		{
			layoutOrigen.setVisible(false);
			coordenadasOrigenL.setVisible(false);
		}
		
		nombreOrigen.setValue(planetaOrigen.getNombrePlaneta());
		coordenadasOrigenL.setValue("(" + planetaOrigen.getCoordenadaX() + "," + planetaOrigen.getCoordenadaY() + ")");
		nombreDestino.setValue(movimiento.getPlaneta().getNombrePlaneta());
		coordenadasDestinoL.setValue("(" + movimiento.getPlaneta().getCoordenadaX() + "," + movimiento.getPlaneta().getCoordenadaY() + ")");
		setExpandRatio(movimiento);
		setTiempoRestante(movimiento);
		
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