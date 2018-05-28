package com.tfgllopis.integracion;

public class Nave_desbloqueada extends Nave_desbloqueada_Ventana
{
	public Nave_desbloqueada(Nave nave)
	{
		imagenNave.setSource(nave.getImage().getSource());
		nombreNaveL.setValue(nave.getNombreNave());
	}

}
