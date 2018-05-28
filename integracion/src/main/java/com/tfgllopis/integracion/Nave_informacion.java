package com.tfgllopis.integracion;

public class Nave_informacion extends Nave_informacion_Ventana
{
	private MovimientohasNave nave;
	
	public Nave_informacion(MovimientohasNave naveMovimiento)
	{
		cantidadF.setValue(naveMovimiento.getCantidad() + "");
		imagenNave.setSource(naveMovimiento.getNave().getImage().getSource());
		nave = naveMovimiento;
	}
	
	public MovimientohasNave getNave()
	{
		return nave;
	}

}
