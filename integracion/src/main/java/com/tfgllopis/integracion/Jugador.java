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
		navigator.addView(Hangar.VIEW_NAME, Hangar.class);
		navigator.addView(Recursos.VIEW_NAME, Recursos.class);
		navigator.addView(Flota.VIEW_NAME, Flota.class);
		navigator.addView(Ver_movimiento.VIEW_NAME, Ver_movimiento.class);
		navigator.addView(Ver_naves.VIEW_NAME, Ver_naves.class);
		navigator.addView(Galaxia.VIEW_NAME, Galaxia.class);
		navigator.addView(Ataque.VIEW_NAME, Ataque.class);
		navigator.addView(Mensajes.VIEW_NAME,  Mensajes.class);
		navigator.addView(Mensaje_sistema.VIEW_NAME, Mensaje_sistema.class);
		navigator.addView(Mensaje_batalla.VIEW_NAME, Mensaje_batalla.class);
		
		navigator.navigateTo(Pagina_principal.VIEW_NAME);

		if (navigator.getState().isEmpty()) 
		{
			navigator.navigateTo(Pagina_principal.VIEW_NAME);
		}

	}
}
