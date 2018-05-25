package com.tfgllopis.integracion;

public class Nave_desbloqueo extends Nave_desbloqueo_Ventana
{
	private int idPirata;
	
	public Nave_desbloqueo(int idPirata, String porcentaje)
	{
		this.idPirata = idPirata;
		
		tituloL.setValue("Pirata LvL " +idPirata);
		porcentajeF.setValue(porcentaje);
	}

}
