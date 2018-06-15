package com.tfgllopis.integracion;

import com.vaadin.navigator.Navigator;

public class Administrador extends Administrador_Ventana
{
	public Administrador()
	{
		cabeceraLayout.addComponent(new Cabecera_administrador());
		menuLayout.addComponent(new Menu());

		Navigator navigator = new Navigator(VaadinUI.getCurrent(), principalLayout);
		
		navigator.addView(Perfil.VIEW_NAME, Perfil.class);
		navigator.addView(Hangar.VIEW_NAME, Hangar.class);
		navigator.addView(Recursos.VIEW_NAME, Recursos.class);
		navigator.addView(Flota.VIEW_NAME, Flota.class);
		navigator.addView(Ver_movimiento.VIEW_NAME, Ver_movimiento.class);
		navigator.addView(Ver_naves.VIEW_NAME, Ver_naves.class);
		navigator.addView(Galaxia.VIEW_NAME, Galaxia.class);
		navigator.addView(Ataque.VIEW_NAME, Ataque.class);
		navigator.addView(Mensajes_administrador.VIEW_NAME,  Mensajes_administrador.class);
		navigator.addView(Mensaje_sistema.VIEW_NAME, Mensaje_sistema.class);
		navigator.addView(Mensaje_batalla.VIEW_NAME, Mensaje_batalla.class);
		navigator.addView(Crear_mensaje.VIEW_NAME, Crear_mensaje.class);
		navigator.addView(Listar_usuarios.VIEW_NAME, Listar_usuarios.class);
		navigator.addView(Editar_usuario.VIEW_NAME, Editar_usuario.class);
		navigator.addView(Borrar_usuario.VIEW_NAME, Borrar_usuario.class);
		navigator.addView(Crear_usuario.VIEW_NAME, Crear_usuario.class);
		navigator.addView(Pagina_principal.VIEW_NAME, Pagina_principal.class);
		
		navigator.navigateTo(Pagina_principal.VIEW_NAME);

		if (navigator.getState().isEmpty()) 
		{
			navigator.navigateTo(Pagina_principal.VIEW_NAME);
		}
		
		navigator.setErrorView(Pagina_principal.class);
	}
}
