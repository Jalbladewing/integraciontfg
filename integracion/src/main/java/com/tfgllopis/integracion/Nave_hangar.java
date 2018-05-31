package com.tfgllopis.integracion;

import com.vaadin.server.ClassResource;

public class Nave_hangar extends Nave_hangar_Ventana 
{
	
	public Nave_hangar(Nave nave)
	{
		nombreNaveL.setValue(nave.getNombreNave());
		imagenNave.setSource(nave.getImage().getSource());
		imagenNave.addStyleName("grey-image");
	}

}
