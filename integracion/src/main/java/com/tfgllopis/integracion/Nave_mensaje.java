package com.tfgllopis.integracion;

import com.vaadin.server.Sizeable.Unit;

public class Nave_mensaje extends Nave_mensaje_Ventana
{
	
	public Nave_mensaje(InformeBatallahasNaveAtaque nave)
	{
		imagenNave.setSource(nave.getNave().getImage().getSource());
		imagenNave.setWidth(50, Unit.PIXELS);
		imagenNave.setHeight(50, Unit.PIXELS);
		nombreNaveL.setValue(nave.getNavenombreNave());
		enviadasL.setValue(nave.getCantidadEnviada() + "");
		perdidasL.setValue(nave.getCantidadPerdida() + "");
	}
	
	public Nave_mensaje(InformeBatallahasNaveDefensa nave)
	{
		imagenNave.setSource(nave.getNave().getImage().getSource());
		imagenNave.setWidth(50, Unit.PIXELS);
		imagenNave.setHeight(50, Unit.PIXELS);
		nombreNaveL.setValue(nave.getNavenombreNave());
		enviadasL.setValue(nave.getCantidadEnviada() + "");
		perdidasL.setValue(nave.getCantidadPerdida() + "");
	}

}
