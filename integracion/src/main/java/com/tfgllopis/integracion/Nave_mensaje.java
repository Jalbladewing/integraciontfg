package com.tfgllopis.integracion;

public class Nave_mensaje extends Nave_mensaje_Ventana
{
	
	public Nave_mensaje(InformeBatallahasNaveAtaque nave)
	{
		imagenNave.setSource(nave.getNave().getImage().getSource());
		nombreNaveL.setValue(nave.getNavenombreNave());
		enviadasL.setValue(nave.getCantidadEnviada() + "");
		perdidasL.setValue(nave.getCantidadPerdida() + "");
	}
	
	public Nave_mensaje(InformeBatallahasNaveDefensa nave)
	{
		imagenNave.setSource(nave.getNave().getImage().getSource());
		nombreNaveL.setValue(nave.getNavenombreNave());
		enviadasL.setValue(nave.getCantidadEnviada() + "");
		perdidasL.setValue(nave.getCantidadPerdida() + "");
	}

}
