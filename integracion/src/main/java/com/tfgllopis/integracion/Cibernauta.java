package com.tfgllopis.integracion;

import com.vaadin.navigator.Navigator;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class Cibernauta extends Cibernauta_Ventana {
	VerticalLayout vLayout;

	public Cibernauta() {
		vLayout = new VerticalLayout();
		this.addComponent(vLayout);
		

		Navigator navigator = new Navigator(UI.getCurrent(), vLayout);

		navigator.addView(Iniciar_sesion.VIEW_NAME, Iniciar_sesion.class);
		navigator.addView(Registrarse.VIEW_NAME, Registrarse.class);
		
		navigator.navigateTo(Iniciar_sesion.VIEW_NAME);

		if (navigator.getState().isEmpty()) 
		{
			navigator.navigateTo(Iniciar_sesion.VIEW_NAME);
		}

	}
}
