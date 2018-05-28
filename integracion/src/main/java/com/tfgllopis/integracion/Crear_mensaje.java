package com.tfgllopis.integracion;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.navigator.View;
import com.vaadin.ui.Button;
import com.vaadin.ui.UI;
import com.vaadin.ui.Button.ClickEvent;

public class Crear_mensaje extends Crear_mensaje_Ventana implements View
{
	@Autowired
	private UsuarioRepository usuarioRepo;
	
	@Autowired
	private MensajeRepository mensajeRepo;
	
	@Autowired
	private UsuariohasMensajeRepository usuarioMensajeRepo;
	
	@Autowired
	private TipoMensajeRepository tipoMensajeRepo;
	
	public static String VIEW_NAME = "crearMensaje";
	
	public Crear_mensaje()
	{
		usuarioRepo = ((VaadinUI) UI.getCurrent()).getInterfazUsuario();
		mensajeRepo = 	((VaadinUI) UI.getCurrent()).getInterfazMensaje();
		usuarioMensajeRepo = ((VaadinUI) UI.getCurrent()).getInterfazUsuarioMensaje();
		tipoMensajeRepo = ((VaadinUI) UI.getCurrent()).getInterfazTipoMensaje();
		
		enviarB.addClickListener(new Button.ClickListener() 
		{
			
			@Override
			public void buttonClick(ClickEvent event) 
			{
				String value = crearMensaje(asuntoF.getValue(), descripcionF.getValue(), usuarioRepo, mensajeRepo, tipoMensajeRepo, usuarioMensajeRepo);
				errorL.setValue(value);
				
				if(value.isEmpty())
				{
					if(((VaadinUI) UI.getCurrent()).getUsuario().getRolName().getName().equals("Administrador"))
					{
						doNavigate(Mensajes_administrador.VIEW_NAME + "/" + "success");
					}else
					{
						doNavigate(Mensajes_tecnico.VIEW_NAME + "/" + "success");					
					}
				}else
				{
					errorL.setVisible(true);
				}
			
			}
		});
		
		cancelarB.addClickListener(new Button.ClickListener() 
		{
			
			@Override
			public void buttonClick(ClickEvent event) 
			{
				if(((VaadinUI) UI.getCurrent()).getUsuario().getRolName().getName().equals("Administrador"))
				{
					doNavigate(Mensajes_administrador.VIEW_NAME);
				}else
				{
					doNavigate(Mensajes_tecnico.VIEW_NAME);	
				}
				
			}
		});
	}
	
	public static String crearMensaje(String asunto, String descripcion, UsuarioRepository usuarioRepo, MensajeRepository mensajeRepo, TipoMensajeRepository tipoMensajeRepo, UsuariohasMensajeRepository usuarioMensajeRepo)
	{
		Mensaje mensaje, mensajeEnviado;
			
		if(!MensajeDataValidator.comprobarMensajeVacio(asunto)) return "Usuario vacío";
		if(!MensajeDataValidator.comprobarMensajeVacio(descripcion)) return "Descripción vacía";
		
		mensaje = new Mensaje(asunto, descripcion, new Date());
		mensaje.setTipoMensajename(tipoMensajeRepo.findByName("Sistema"));
		mensajeEnviado = mensajeRepo.save(mensaje);

		return broadcastMensaje(mensajeEnviado, usuarioRepo, usuarioMensajeRepo);
	}
	
	private static String broadcastMensaje(Mensaje mensaje, UsuarioRepository usuarioRepo, UsuariohasMensajeRepository usuarioMensajeRepo)
	{
		List<UsuariohasMensaje> usuariosMensajeL = new ArrayList<>();
		List<Usuario> usuariosL = usuarioRepo.findAll();
		
		for(int i = 0; i < usuariosL.size(); i++)
		{
			if(!usuariosMensajeL.add(new UsuariohasMensaje(usuariosL.get(i).getUsername(), mensaje.getIdMensaje(), (short) 0))) return "Error al crear el mensaje para " + usuariosL.get(i).getUsername();
		}
		
		usuarioMensajeRepo.saveAll(usuariosMensajeL);
		
		return "";
	}
	
	private void doNavigate(String viewName) 
	{
		UI.getCurrent().getNavigator().navigateTo(viewName);
	}

}
