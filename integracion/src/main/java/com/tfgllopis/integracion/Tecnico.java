package com.tfgllopis.integracion;

import com.vaadin.navigator.Navigator;

public class Tecnico extends Tecnico_Ventana
{
	public Tecnico()
	{
		cabeceraLayout.addComponent(new Cabecera_tecnico());
		menuLayout.addComponent(new Menu());
		
		Navigator navigator = new Navigator(VaadinUI.getCurrent(), principalLayout);
		
		navigator.addView(Hangar.VIEW_NAME, Hangar.class);
		navigator.addView(Recursos.VIEW_NAME, Recursos.class);
		navigator.addView(Flota.VIEW_NAME, Flota.class);
		navigator.addView(Ver_movimiento.VIEW_NAME, Ver_movimiento.class);
		navigator.addView(Ver_naves.VIEW_NAME, Ver_naves.class);
		navigator.addView(Galaxia.VIEW_NAME, Galaxia.class);
		navigator.addView(Ataque.VIEW_NAME, Ataque.class);
		navigator.addView(Mensajes_tecnico.VIEW_NAME,  Mensajes_tecnico.class);
		navigator.addView(Mensaje_sistema.VIEW_NAME, Mensaje_sistema.class);
		navigator.addView(Mensaje_batalla.VIEW_NAME, Mensaje_batalla.class);
		navigator.addView(Crear_mensaje.VIEW_NAME, Crear_mensaje.class);
		navigator.addView(Perfil.VIEW_NAME, Perfil.class);
		navigator.addView(Crear_nave.VIEW_NAME, Crear_nave.class);
		navigator.addView(Listar_naves.VIEW_NAME, Listar_naves.class);
		navigator.addView(Editar_nave.VIEW_NAME, Editar_nave.class);
		navigator.addView(Recursos_tecnico.VIEW_NAME, Recursos_tecnico.class);
		navigator.addView(Piratas.VIEW_NAME, Piratas.class);
		navigator.addView(Editar_pirata.VIEW_NAME, Editar_pirata.class);
		navigator.addView(Pagina_principal.VIEW_NAME, Pagina_principal.class);

		
		navigator.navigateTo(Pagina_principal.VIEW_NAME);

		if (navigator.getState().isEmpty()) 
		{
			navigator.navigateTo(Pagina_principal.VIEW_NAME);
		}
		
		
	}

}
