package com.tfgllopis.integracion;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.navigator.View;
import com.vaadin.ui.Button;
import com.vaadin.ui.UI;
import com.vaadin.ui.Button.ClickEvent;

public class Registrarse  extends Registrarse_Ventana implements View
{
	@Autowired
	private UsuarioRepository userRepo;
	
	@Autowired
	private RolesRepository rolRepo;
	
	public static final String VIEW_NAME = "registro";
	
	public Registrarse()
	{
		userRepo = ((VaadinUI) UI.getCurrent()).getInterfazUsuario();
		rolRepo = ((VaadinUI) UI.getCurrent()).getInterfazRol();
		
		registrarseB.addClickListener(new Button.ClickListener() 
		{
			
			@Override
			public void buttonClick(ClickEvent event) 
			{
				String value = CrudUsuario.registro(usuarioF.getValue(), emailF.getValue(), passwordF.getValue(), password2F.getValue(), userRepo, rolRepo);
				errorL.setValue(value);
				
				if(value.isEmpty()) 
				{
					errorL.setVisible(false);
					doNavigate(Iniciar_sesion.VIEW_NAME + "/" + "registrado");
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
				doNavigate(Iniciar_sesion.VIEW_NAME);
			}
		});
	}
	
	private void doNavigate(String viewName) 
	{
		UI.getCurrent().getNavigator().navigateTo(viewName);
	}
}
