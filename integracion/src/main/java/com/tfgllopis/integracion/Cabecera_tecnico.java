package com.tfgllopis.integracion;

import com.vaadin.server.VaadinService;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.UI;

public class Cabecera_tecnico extends Cabecera_tecnico_Ventana 
{
	public Cabecera_tecnico()
	{
		usernameL.setValue(((VaadinUI) UI.getCurrent()).getUsuario().getUsername());
		
		perfilB.addClickListener(new Button.ClickListener() 
		{
			
			@Override
			public void buttonClick(ClickEvent event) 
			{	
				doNavigate(Perfil.VIEW_NAME);
			}
		});
		
		mensajesB.addClickListener(new Button.ClickListener() 
		{
			
			@Override
			public void buttonClick(ClickEvent event) 
			{
				doNavigate(Mensajes_tecnico.VIEW_NAME);				
			}
		});
		
		crearNaveB.addClickListener(new Button.ClickListener() 
		{
			
			@Override
			public void buttonClick(ClickEvent event) 
			{	
				doNavigate(Crear_nave.VIEW_NAME);
			}
		});
		
		listarNavesB.addClickListener(new Button.ClickListener() 
		{
			
			@Override
			public void buttonClick(ClickEvent event) 
			{
				doNavigate(Listar_naves.VIEW_NAME);				
			}
		});
		
		listarPiratasB.addClickListener(new Button.ClickListener() 
		{
			
			@Override
			public void buttonClick(ClickEvent event) 
			{
				doNavigate(Piratas.VIEW_NAME);
				
			}
		});
		

		recursosB.addClickListener(new Button.ClickListener() 
		{
			
			@Override
			public void buttonClick(ClickEvent event) 
			{
				doNavigate(Recursos_tecnico.VIEW_NAME);
				
			}
		});
		

		desconectarB.addClickListener(new Button.ClickListener() 
		{
			
			@Override
			public void buttonClick(ClickEvent event) 
			{
				UI.getCurrent().getNavigator().destroy();
				((VaadinUI) UI.getCurrent()).setUsuario(null);
				VaadinService.getCurrentRequest().getWrappedSession().removeAttribute("currentUser");
				VaadinUI.getCurrent().setContent(new Cibernauta());
				
			}
		});
	}
	
	private void doNavigate(String viewName) 
	{
		UI.getCurrent().getNavigator().navigateTo(viewName);
	}
}
