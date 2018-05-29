package com.tfgllopis.integracion;

import java.io.File;
import java.text.SimpleDateFormat;
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
		
		imagenPlanetaOrigen.setSource( new FileResource(new File(new File("").getAbsolutePath() + "/images/" + "No_Image_Available.png")));
		imagenPlanetaDestino.setSource( new FileResource(new File(new File("").getAbsolutePath() + "/images/" + "No_Image_Available.png")));
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
		double tiempoViaje = CrudNave.calcularDistancia(planetaUsuario, planetaAtaque, (Iterator<Nave_ataque>)(Object) layoutNaves.iterator());
		fechaLlegada = CrudNave.calcularFechaViaje(tiempoViaje);
		fechaVuelta = CrudNave.calcularFechaViaje(tiempoViaje*2);
		tiempoIdaL.setValue(new SimpleDateFormat("HH:mm:ss - dd/MM/YY", Locale.ENGLISH).format(fechaLlegada));
		tiempoVueltaL.setValue(new SimpleDateFormat("HH:mm:ss - dd/MM/YY", Locale.ENGLISH).format(fechaVuelta));
	}
}
