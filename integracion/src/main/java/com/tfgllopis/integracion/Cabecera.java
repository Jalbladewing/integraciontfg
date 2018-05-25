package com.tfgllopis.integracion;

import com.vaadin.server.VaadinService;
import com.vaadin.ui.Button;
import com.vaadin.ui.UI;
import com.vaadin.ui.Button.ClickEvent;

public class Cabecera extends Cabecera_Ventana 
{
	public Cabecera()
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
