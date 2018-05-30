package com.tfgllopis.integracion;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.ui.Image;
import com.vaadin.ui.UI;

public class Nave_pirata extends Nave_pirata_Ventana 
{
	@Autowired
	private PiratahasNaveRepository pirataNaveRepo;
	
	private PiratahasNave nave;
	
	public Nave_pirata(Image imagenNave, String nombreNave, int idPirata)
	{		
		this.imagenNave.setSource(imagenNave.getSource());
		this.imagenNave.setWidth(50, Unit.PIXELS);
		this.imagenNave.setHeight(50, Unit.PIXELS);
		
		cantidadF.setValue("0");
	
		pirataNaveRepo = ((VaadinUI) UI.getCurrent()).getInterfazPirataNave();
		nave = pirataNaveRepo.findByPirataidPirata_NavenombreNave(idPirata, nombreNave);
		
		if(nave != null)
		{
			cantidadF.setValue(nave.getCantidadDefecto() + "");
		}else
		{
			nave = new PiratahasNave(nombreNave, idPirata, 0);
		}
		
	}
	
	public PiratahasNave getNave()
	{
		return nave;
	}

}
