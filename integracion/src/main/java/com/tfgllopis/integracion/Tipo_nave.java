package com.tfgllopis.integracion;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.event.MouseEvents.ClickEvent;
import com.vaadin.event.MouseEvents.ClickListener;
import com.vaadin.ui.Image;
import com.vaadin.ui.UI;

public class Tipo_nave extends Tipo_nave_Ventana 
{
	
	
	private TipoNave tipoNave;
	
	public Tipo_nave(TipoNave tipoNave, int id)
	{
		this.tipoNave = tipoNave;
		nombreTipoL.setValue(tipoNave.getNombreTipoNave());
		imagenTipo.setId(id +"");
		//imagenTipo.setSource(tipoNave.getImage().getSource());
			
	}
	
	public TipoNave getTipoNave()
	{
		return tipoNave;
	}
	
	public Image getImage()
	{
		return imagenTipo;
	}

}
