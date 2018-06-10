package com.tfgllopis.integracion;

import com.vaadin.server.Sizeable.Unit;

public class Nave_ataque extends Nave_ataque_Ventana
{
	private PlanetaHasNave nave;
	
	public Nave_ataque(PlanetaHasNave navePlaneta)
	{
		cantidadF.setValue("0");
		imagenNave.setSource(navePlaneta.getNave().getImage().getSource());
		imagenNave.setWidth(150, Unit.PIXELS);
		imagenNave.setHeight(100, Unit.PIXELS);
		disponiblesL.setValue(navePlaneta.getCantidad() + "");
		nave = navePlaneta;
	}
	
	public PlanetaHasNave getNave()
	{
		return nave;
	}

}
