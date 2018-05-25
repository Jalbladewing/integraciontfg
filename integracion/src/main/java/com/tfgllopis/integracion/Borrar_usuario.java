package com.tfgllopis.integracion;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.UI;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;

public class Borrar_usuario extends Borrar_usuario_Ventana implements View 
{
	@Autowired
	private UsuarioRepository userRepo;
	
	public static String VIEW_NAME = "borrarUsuario";
	
	public Borrar_usuario()
	{
		eliminarB.addClickListener(new Button.ClickListener() 
		{
			
			@Override
			public void buttonClick(ClickEvent event) 
			{
				Usuario.borrarUsuario(usuarioL.getValue(), userRepo);
				doNavigate(Listar_usuarios.VIEW_NAME);	
			}
		});
		
		cancelarB.addClickListener(new Button.ClickListener() 
		{
			
			@Override
			public void buttonClick(ClickEvent event) 
			{
				doNavigate(Listar_usuarios.VIEW_NAME);	
			}
		});
	}
	
	@Override
	public void enter(ViewChangeEvent event) 
	{
		
		if (!event.getParameters().isEmpty())
		{
			userRepo = ((VaadinUI) UI.getCurrent()).getInterfazUsuario();
			
			usuarioL.setValue(event.getParameters());
			emailL.setValue(userRepo.findByUsername(event.getParameters()).get(0).getEmail());
		}
	}
	
	private void doNavigate(String viewName) 
	{
		UI.getCurrent().getNavigator().navigateTo(viewName);
	}
}
