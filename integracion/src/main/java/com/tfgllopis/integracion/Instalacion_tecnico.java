package com.tfgllopis.integracion;

import java.io.File;

import com.vaadin.data.HasValue.ValueChangeEvent;
import com.vaadin.data.HasValue.ValueChangeListener;
import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinServlet;

public class Instalacion_tecnico extends Instalacion_tecnico_Ventana
{
	private String tipoInstalacion;
	
	@SuppressWarnings({ "unchecked", "serial", "rawtypes" })
	public Instalacion_tecnico(String tipoInstalacion, Recursos_tecnico recursos)
	{
		this.tipoInstalacion = tipoInstalacion;
		imagenInstalacion.setSource( new FileResource(new File(new File("").getAbsolutePath() + "/images/" + "No_Image_Available.png")));

		
		tasaInstalacionF.addValueChangeListener(new ValueChangeListener() 
		{
			@Override
			public void valueChange(ValueChangeEvent event) 
			{
				if(NaveDataValidator.comprobarValorFloat(tasaInstalacionF.getValue()))
				{
					recursos.setRecursosInfo(tipoInstalacion, Float.parseFloat(tasaInstalacionF.getValue().replaceAll(",",".")));
				}
				
			}
        });
	}

}
