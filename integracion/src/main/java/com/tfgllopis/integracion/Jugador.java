package com.tfgllopis.integracion;

import com.vaadin.navigator.Navigator;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class Jugador extends Jugador_Ventana 
{

	public Jugador() 
	{
		cabeceraLayout.addComponent(new Cabecera());
		menuLayout.addComponent(new Menu());
		
		Navigator navigator = new Navigator(VaadinUI.getCurrent(), principalLayout);
		
		navigator.addView(Pagina_principal.VIEW_NAME, Pagina_principal.class);
		navigator.addView(Perfil.VIEW_NAME, Perfil.class);
		
		navigator.navigateTo(Pagina_principal.VIEW_NAME);

		if (navigator.getState().isEmpty()) 
		{
			navigator.navigateTo(Pagina_principal.VIEW_NAME);
		}

	}
}
