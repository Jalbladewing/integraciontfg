package com.tfgllopis.integracion;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.UI;
import com.vaadin.ui.Button.ClickEvent;

public class Mensaje_batalla extends Mensaje_batalla_Ventana implements View 
{
	@Autowired
	private UsuariohasMensajeRepository usuarioMensajeRepo;
	
	@Autowired
	private InformeBatallahasNaveAtaqueRepository informeAtaqueRepo;
	
	@Autowired
	private InformeBatallahasNaveDefensaRepository informeDefensaRepo;
	
	@Autowired
	private InformeBatallahasRecursoRepository informeRecursoRepo;
	
	public static String VIEW_NAME = "mensajeBatalla";
	private UsuariohasMensaje mensaje;
	
	public Mensaje_batalla()
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
			InformeBatalla informe;
			ArrayList<InformeBatallahasNaveAtaque> navesAtaque;
			ArrayList<InformeBatallahasNaveDefensa> navesDefensa;
			ArrayList<InformeBatallahasRecurso> recursos;
			boolean victoria = true;
			usuarioMensajeRepo = ((VaadinUI) UI.getCurrent()).getInterfazUsuarioMensaje();
			informeAtaqueRepo = ((VaadinUI) UI.getCurrent()).getInterfazInformeAtaque();
			informeDefensaRepo = ((VaadinUI) UI.getCurrent()).getInterfazInformeDefensa();
			informeRecursoRepo = ((VaadinUI) UI.getCurrent()).getInterfazInformeRecurso();
			mensaje = usuarioMensajeRepo.findByMensajeidMensajeUsuariousernameNoDescartado(Integer.parseInt(event.getParameters()), ((VaadinUI) UI.getCurrent()).getUsuario().getUsername());
			if(mensaje == null) return;
			
			if(mensaje.getMensaje().getAsunto().substring(0, 7).equals("Derrota")) victoria = false;
			
			tipoL.setValue("Mensaje del " + mensaje.getMensaje().getTipoMensajename().getName());
			asuntoF.setValue(mensaje.getMensaje().getAsunto());
			navesAtaque = new ArrayList<>(informeAtaqueRepo.findByInformeBatallaidBatalla(mensaje.getMensaje().getInformeBatallaidBatalla().getIdBatalla()));
			navesDefensa = new ArrayList<>(informeDefensaRepo.findByInformeBatallaidBatalla(mensaje.getMensaje().getInformeBatallaidBatalla().getIdBatalla()));
			recursos = new ArrayList<>(informeRecursoRepo.findByInformeBatallaidBatalla(mensaje.getMensaje().getInformeBatallaidBatalla().getIdBatalla()));
			
			for(int i = 0; i < navesAtaque.size(); i++)
			{
				atacantesLayout.addComponent(new Nave_mensaje(navesAtaque.get(i)));
			}
			
			for(int i = 0; i < navesDefensa.size(); i++)
			{
				defensoresLayout.addComponent(new Nave_mensaje(navesDefensa.get(i)));
			}
			
			for(int i = 0; i < recursos.size(); i++)
			{
				recursosLayout.addComponent(new Recurso_obtenido(recursos.get(i).getRecursoname(), recursos.get(i).getCantidad(), victoria));
			}
			
			if(mensaje.getMensaje().getInformeBatallaidBatalla().getNavenombreNaveDesbloqueada() != null)
			{
				navesDesbloqueadasLayout.addComponent(new Nave_desbloqueada(mensaje.getMensaje().getInformeBatallaidBatalla().getNavenombreNaveDesbloqueada()));
			}
		}
	}
	
	private void doNavigate(String viewName) 
	{
		UI.getCurrent().getNavigator().navigateTo(viewName);
	}
}
