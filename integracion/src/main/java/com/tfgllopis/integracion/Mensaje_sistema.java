package com.tfgllopis.integracion;

import org.apache.commons.validator.routines.IntegerValidator;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.UI;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;

public class Mensaje_sistema extends Mensaje_sistema_Ventana implements View
{
	@Autowired
	private UsuariohasMensajeRepository usuarioMensajeRepo;
	
	public static String VIEW_NAME = "mensajeSistema";
	private UsuariohasMensaje mensaje;
	
	public Mensaje_sistema()
	{
		borrarB.addClickListener(new Button.ClickListener() 
		{
			
			@Override
			public void buttonClick(ClickEvent event) 
			{
				borrarMensaje();
			}
		});
	}

	private void borrarMensaje()
	{
		if(mensaje != null)
		{
			mensaje.descartar();
			usuarioMensajeRepo.save(mensaje);
			
			if(((VaadinUI) UI.getCurrent()).getUsuario().getRolName().getName().equals("Jugador"))
			{
				doNavigate(Mensajes.VIEW_NAME);
				
			}else if(((VaadinUI) UI.getCurrent()).getUsuario().getRolName().getName().equals("Administrador"))
			{
				doNavigate(Mensajes_administrador.VIEW_NAME);
			}else
			{
				doNavigate(Mensajes_tecnico.VIEW_NAME);					
			}
			
			
		
		}
	}
	
	@Override
	public void enter(ViewChangeEvent event) 
	{
		if (!event.getParameters().isEmpty() && MensajeDataValidator.comprobarValorInteger(event.getParameters()))
		{
			usuarioMensajeRepo = ((VaadinUI) UI.getCurrent()).getInterfazUsuarioMensaje();
			mensaje = usuarioMensajeRepo.findByMensajeidMensajeUsuariousernameNoDescartado(Integer.parseInt(event.getParameters()), ((VaadinUI) UI.getCurrent()).getUsuario().getUsername());
			if(mensaje == null) return;
			
			tipoL.setValue("Mensaje del " + mensaje.getMensaje().getTipoMensajename().getName());
			asuntoF.setValue(mensaje.getMensaje().getAsunto());
			descripcionF.setValue(mensaje.getMensaje().getDescripcion());
		}
	}
	
	private void doNavigate(String viewName) 
	{
		UI.getCurrent().getNavigator().navigateTo(viewName);
	}
}
