package com.tfgllopis.integracion;

import com.vaadin.ui.Button;
import com.vaadin.ui.UI;
import com.vaadin.ui.Button.ClickEvent;

public class Mensaje_recibido extends Mensaje_recibido_Ventana
{
	public Mensaje_recibido(Mensaje mensaje)
	{
		tipoL.setValue("Mensaje del " + mensaje.getTipoMensajename().getName());
		asuntoL.setValue(mensaje.getAsunto());
		fechaL.setValue(mensaje.getFechaEnvio().toString());
		
		elegirB.addClickListener(new Button.ClickListener() 
		{
			
			@Override
			public void buttonClick(ClickEvent event) 
			{
				if(mensaje.getTipoMensajename().getName().equals("Batalla"))
				{
					doNavigate(Mensaje_batalla.VIEW_NAME + "/" +mensaje.getIdMensaje());				
				}else
				{
					doNavigate(Mensaje_sistema.VIEW_NAME + "/" +mensaje.getIdMensaje());				
				}
			}
		});
	}
	
	private void doNavigate(String viewName) 
	{
		UI.getCurrent().getNavigator().navigateTo(viewName);
	}

}
