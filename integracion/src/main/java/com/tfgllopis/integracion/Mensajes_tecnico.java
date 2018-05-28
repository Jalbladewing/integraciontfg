package com.tfgllopis.integracion;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.UI;
import com.vaadin.ui.Button.ClickEvent;

public class Mensajes_tecnico extends Mensajes_tecnico_Ventana implements View
{
	@Autowired
	private UsuariohasMensajeRepository usuarioMensajeRepo;
	
	public static String VIEW_NAME = "mensajesTecnico";
	private List<UsuariohasMensaje> mensajesL;
	
	public Mensajes_tecnico()
	{
		usuarioMensajeRepo = ((VaadinUI) UI.getCurrent()).getInterfazUsuarioMensaje();
		mensajesL = usuarioMensajeRepo.findByUsuariousernameNoDescartado(((VaadinUI) UI.getCurrent()).getUsuario().getUsername());
	
		for(int i = 0; i < mensajesL.size(); i++)
		{
			mensajesLayout.addComponent(new Mensaje_recibido(mensajesL.get(i).getMensaje()));
		}
		
		crearMensajeB.addClickListener(new Button.ClickListener() 
		{
			
			@Override
			public void buttonClick(ClickEvent event) 
			{
				doNavigate(Crear_mensaje.VIEW_NAME);				
			}
		});
	
	}
	
	@Override
	public void enter(ViewChangeEvent event) 
	{
		if (!event.getParameters().isEmpty())
		{
			correctoL.setVisible(true);
		}
	}
	
	private void doNavigate(String viewName) 
	{
		UI.getCurrent().getNavigator().navigateTo(viewName);
	}
}
