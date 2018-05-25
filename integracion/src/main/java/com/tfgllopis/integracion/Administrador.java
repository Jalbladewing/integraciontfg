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
	}
}
