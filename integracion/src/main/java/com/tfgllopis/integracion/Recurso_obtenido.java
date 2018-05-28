package com.tfgllopis.integracion;

public class Recurso_obtenido extends Recurso_obtenido_Ventana
{
	public Recurso_obtenido(String nombreRecurso, int cantidad, boolean victoria)
	{
		if(!victoria) cantidad *= -1;
		nombreRecursoL.setValue(nombreRecurso);
		botinL.setValue(cantidad + "");
	}

}
