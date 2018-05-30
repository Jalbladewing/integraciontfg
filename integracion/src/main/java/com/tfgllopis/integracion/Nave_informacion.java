package com.tfgllopis.integracion;

import com.vaadin.server.Sizeable.Unit;

public class Nave_informacion extends Nave_informacion_Ventana
{
	private MovimientohasNave nave;
	
	public Nave_informacion(MovimientohasNave naveMovimiento)
	{
		cantidadF.setValue(naveMovimiento.getCantidad() + "");
		imagenNave.setSource(naveMovimiento.getNave().getImage().getSource());
		imagenNave.setWidth(50, Unit.PIXELS);
		imagenNave.setHeight(50, Unit.PIXELS);
		nave = naveMovimiento;
	}
	
	public MovimientohasNave getNave()
	{
		return nave;
	}

}
