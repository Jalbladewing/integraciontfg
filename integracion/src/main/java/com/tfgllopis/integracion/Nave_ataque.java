package com.tfgllopis.integracion;

public class Nave_ataque extends Nave_ataque_Ventana
{
	private PlanetaHasNave nave;
	
	public Nave_ataque(PlanetaHasNave navePlaneta)
	{
		cantidadF.setValue("0");
		imagenNave.setSource(navePlaneta.getNave().getImage().getSource());
		disponiblesL.setValue(navePlaneta.getCantidad() + "");
		nave = navePlaneta;
	}
	
	public PlanetaHasNave getNave()
	{
		return nave;
	}

}
