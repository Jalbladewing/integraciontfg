package com.tfgllopis.integracion;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;

import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.GridLayout;

public class Informacion_movimiento extends Informacion_movimiento_Ventana
{
	private Date fechaLlegada;
	private Date fechaVuelta;
	private Planeta planetaUsuario;
	private Planeta planetaAtaque;
	
	public Informacion_movimiento(Planeta planetaUsuario, Planeta planetaAtaque)
	{
		this.planetaUsuario = planetaUsuario;
		this.planetaAtaque = planetaAtaque;
		
		imagenPlanetaOrigen.setSource( new FileResource(new File(new File("").getAbsolutePath() + "/images/" + "Planeta.png")));
		imagenPlanetaDestino.setSource( new FileResource(new File(new File("").getAbsolutePath() + "/images/" + "Planeta.png")));
		nombreOrigen.setValue(planetaUsuario.getNombrePlaneta());
		nombreDestino.setValue(planetaAtaque.getNombrePlaneta());
		coordenadasOrigenL.setValue("(" + planetaUsuario.getCoordenadaX() + "," + planetaUsuario.getCoordenadaY() + ")");
		coordenadasDestinoL.setValue("(" + planetaAtaque.getCoordenadaX() + "," + planetaAtaque.getCoordenadaY() + ")");
	}
	
	public Date getFechaLlegada()
	{
		return fechaLlegada;
	}
	
	public Date getFechaVuelta()
	{
		return fechaVuelta;
	}
	
	public void actualizarTiempo(GridLayout layoutNaves)
	{
		double tiempoViaje = Informacion_movimiento.calcularDistancia(planetaUsuario, planetaAtaque, (Iterator<Nave_ataque>)(Object) layoutNaves.iterator());
		fechaLlegada = Informacion_movimiento.calcularFechaViaje(tiempoViaje);
		fechaVuelta = Informacion_movimiento.calcularFechaViaje(tiempoViaje*2);
		tiempoIdaL.setValue(new SimpleDateFormat("HH:mm:ss - dd/MM/YY", Locale.ENGLISH).format(fechaLlegada));
		tiempoVueltaL.setValue(new SimpleDateFormat("HH:mm:ss - dd/MM/YY", Locale.ENGLISH).format(fechaVuelta));
	}
	
	public static double calcularDistancia(Planeta planetaOrigen, Planeta planetaDestino, Iterator<Nave_ataque> iterator)
	{
		Nave_ataque aux;
		int cantidadTotal = 0;
		double distanciaSistemaX = Math.pow(planetaDestino.getSistema().getCoordenadaX() - planetaOrigen.getSistema().getCoordenadaX(), 2);
		double distanciaSistemaY = Math.pow(planetaDestino.getSistema().getCoordenadaY() - planetaOrigen.getSistema().getCoordenadaY(), 2);
		double distanciaSistemas = Math.sqrt(distanciaSistemaX + distanciaSistemaY);
		double distanciaPlanetaX = Math.pow(planetaDestino.getCoordenadaX() - planetaOrigen.getCoordenadaX(), 2);
		double distanciaPlanetaY = Math.pow(planetaDestino.getCoordenadaY() - planetaOrigen.getCoordenadaY(), 2);
		double distanciaPlanetas = Math.sqrt(distanciaPlanetaX + distanciaPlanetaY);
		if(distanciaSistemas > 0) distanciaPlanetas += 10;
		distanciaPlanetas *= distanciaSistemas + 2;
		float velocidadMinima = 100;
		double tiempoLlegada;
		
		while(iterator.hasNext())
		{
			aux = iterator.next();
			if(!NaveDataValidator.comprobarValorInteger(aux.cantidadF.getValue()) || Integer.parseInt(aux.cantidadF.getValue()) <= 0) continue;
			if(aux.getNave().getNave().getVelocidadNave() < velocidadMinima) velocidadMinima = aux.getNave().getNave().getVelocidadNave();
			cantidadTotal += Integer.parseInt(aux.cantidadF.getValue());
		}
		
		if(cantidadTotal <= 0) return 0;
		
		tiempoLlegada = distanciaPlanetas/(velocidadMinima/100);
		
		return tiempoLlegada;		
	}
	
	public static Date calcularFechaViaje(double tiempoLlegada)
	{
		Calendar calendar;
		int minutos, segundos;
		
		minutos = (int) tiempoLlegada;
		segundos = (int) ((tiempoLlegada - minutos) * 100);
		
		calendar = Calendar.getInstance();
		calendar.add(Calendar.MINUTE, minutos);
		calendar.add(Calendar.SECOND, segundos);
		
		return calendar.getTime();	
	}
}
