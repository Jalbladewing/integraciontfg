package com.tfgllopis.integracion;

import java.io.File;

import com.vaadin.server.FileResource;

public class Recurso_obtenido extends Recurso_obtenido_Ventana
{
	public Recurso_obtenido(String nombreRecurso, int cantidad, boolean victoria)
	{
		if(!victoria) cantidad *= -1;
		imagenRecurso.setSource( new FileResource(new File(new File("").getAbsolutePath() + "/images/" + nombreRecurso + ".png")));
		nombreRecursoL.setValue(nombreRecurso);
		botinL.setValue(cantidad + "");
	}

}
