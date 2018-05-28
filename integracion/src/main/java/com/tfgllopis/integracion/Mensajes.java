package com.tfgllopis.integracion;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.navigator.View;
import com.vaadin.ui.UI;

public class Mensajes extends Mensajes_Ventana implements View
{
	@Autowired
	private UsuariohasMensajeRepository usuarioMensajeRepo;
	
	public static String VIEW_NAME = "mensajes";
	private List<UsuariohasMensaje> mensajesL;
	
	public Mensajes()
	{
		usuarioMensajeRepo = ((VaadinUI) UI.getCurrent()).getInterfazUsuarioMensaje();
		mensajesL = usuarioMensajeRepo.findByUsuariousernameNoDescartado(((VaadinUI) UI.getCurrent()).getUsuario().getUsername());
	
		for(int i = 0; i < mensajesL.size(); i++)
		{
			mensajesLayout.addComponent(new Mensaje_recibido(mensajesL.get(i).getMensaje()));
		}
	
	}
}
