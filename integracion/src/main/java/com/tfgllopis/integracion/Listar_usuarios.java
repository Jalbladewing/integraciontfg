package com.tfgllopis.integracion;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.navigator.View;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.renderers.ButtonRenderer;
import com.vaadin.ui.themes.ValoTheme;

public class Listar_usuarios extends Listar_usuarios_Ventana implements View
{
	@Autowired
	private UsuarioRepository userRepo;
	
	@Autowired
	private RolesRepository rolRepo;
	
	public static String VIEW_NAME = "listarUsuarios";
	private List<Usuario> usuariosL;

	public Listar_usuarios()
	{
		userRepo = ((VaadinUI) UI.getCurrent()).getInterfazUsuario();
		//rolRepo = ((VaadinUI) UI.getCurrent()).getInterfazRol();
		
		usuariosL = userRepo.findAll();
		usuariosTabla.setItems(usuariosL);
		
		usuariosTabla.addComponentColumn(user -> {
			HorizontalLayout hl = new HorizontalLayout();
			Button editar = new Button("Editar");
			editar.addStyleName("link");
			editar.addClickListener(new Button.ClickListener()
			{
				
				@Override
				public void buttonClick(ClickEvent event) 
				{
					doNavigate(Editar_usuario.VIEW_NAME + "/" + user.getUsername());
				}
			});
			hl.addComponent(editar);
			hl.addComponent(new Label(" - "));
			Button borrar = new Button("Borrar");
			borrar.addStyleName("link");
			borrar.addClickListener(new Button.ClickListener()
			{
				
				@Override
				public void buttonClick(ClickEvent event) 
				{
					doNavigate(Borrar_usuario.VIEW_NAME + "/" + user.getUsername());
				}
			});
			hl.addComponent(borrar);
			return hl;
		}).setCaption("-")
		.setId("Buttons");
	}
	
	private void doNavigate(String viewName) 
	{
		UI.getCurrent().getNavigator().navigateTo(viewName);
	}
}
