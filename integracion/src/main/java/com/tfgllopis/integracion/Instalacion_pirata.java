package com.tfgllopis.integracion;

import java.io.File;

import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinServlet;

public class Instalacion_pirata extends Instalacion_pirata_Ventana
{
	public Instalacion_pirata(String tipoInstalacion)
	{
		nombreInstalacion.setValue(tipoInstalacion);
		imagenInstalacion.setSource( new FileResource(new File(new File("").getAbsolutePath() + "/images/" + tipoInstalacion +".png")));
	}

}
