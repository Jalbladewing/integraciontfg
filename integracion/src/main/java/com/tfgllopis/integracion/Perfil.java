package com.tfgllopis.integracion;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.UI;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;

public class Perfil extends Perfil_Ventana implements View
{
	@Autowired
	private UsuarioRepository userRepo;
	
	public static String VIEW_NAME = "perfil";
	
	public Perfil()
	{	
		Usuario user;
		
		userRepo = ((VaadinUI) UI.getCurrent()).getInterfazUsuario();
		user = ((VaadinUI) UI.getCurrent()).getUsuario();
		usuarioF.setValue(user.getUsername());
		emailF.setValue(user.getEmail());
		
		guardarB.addClickListener(new Button.ClickListener() 
		{
			
			@Override
			public void buttonClick(ClickEvent event) 
			{
				String value = CrudUsuario.modificarPerfil(usuarioF.getValue(), user.getEmail(), emailF.getValue(), passwordF.getValue(), password2F.getValue(), userRepo);
				errorL.setValue(value);
				if(value.isEmpty()) 
				{
					errorL.setVisible(false);
					doNavigate(Perfil.VIEW_NAME + "/" + "Changed");	
				}else
				{
					correctoL.setVisible(false);
					errorL.setVisible(true);
				}
			}
		});
		
		cancelarB.addClickListener(new Button.ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) 
			{
				doNavigate(Pagina_principal.VIEW_NAME);				
			}
		});
	}
	
	@Override
	public void enter(ViewChangeEvent event) 
	{
		 if (!event.getParameters().isEmpty()) correctoL.setVisible(true);
	}
	
	private void doNavigate(String viewName) 
	{
		UI.getCurrent().getNavigator().navigateTo(viewName);
	}
}
