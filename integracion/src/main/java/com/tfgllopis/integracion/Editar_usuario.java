package com.tfgllopis.integracion;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.UI;
import com.vaadin.ui.Button.ClickEvent;

public class Editar_usuario extends Editar_usuario_Ventana implements View 
{
	@Autowired
	private UsuarioRepository userRepo;
	
	@Autowired
	private RolesRepository rolRepo;
	
	public static String VIEW_NAME = "editarUsuario";
	private Usuario user;
	
	public Editar_usuario()
	{
		rolRepo = ((VaadinUI) UI.getCurrent()).getInterfazRol();
		
		guardarB.addClickListener(new Button.ClickListener() 
		{
			
			@Override
			public void buttonClick(ClickEvent event) 
			{
				String value = Editar_usuario.modificarPerfil(usuarioF.getValue(), user.getEmail(), emailF.getValue(), passwordF.getValue(), password2F.getValue(), rolesRadio.getValue(), activoChckBx.getValue(), userRepo, rolRepo);
				errorL.setValue(value);
				if(value.isEmpty()) 
				{
					errorL.setVisible(false);
					doNavigate(Editar_usuario.VIEW_NAME + "/" + user.getUsername() + "&" + "Changed");	
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
				doNavigate(Listar_usuarios.VIEW_NAME);				
			}
		});
	}
	
	@Override
	public void enter(ViewChangeEvent event) 
	{
		String[] parameters;
		if (!event.getParameters().isEmpty())
		{
			parameters = event.getParameters().split("&");
			if(parameters.length > 1 && parameters[1].equals("Changed")) correctoL.setVisible(true);
			userRepo = ((VaadinUI) UI.getCurrent()).getInterfazUsuario();
			user = userRepo.findByUsername(parameters[0]).get(0);
			
			usuarioF.setValue(user.getUsername());
			emailF.setValue(user.getEmail());
			rolesRadio.setValue(user.getRolName().getName());
			if(user.getActivo()) activoChckBx.setValue(true);
			if(!((VaadinUI) UI.getCurrent()).getUsuario().equals(user.getUsername())) rolesRadio.setEnabled(true);
		}
	}
	
	private void doNavigate(String viewName) 
	{
		UI.getCurrent().getNavigator().navigateTo(viewName);
	}
	
	public static String modificarPerfil(String username, String emailOriginal,String emailNuevo, String password1, String password2, String rol, boolean activo, UsuarioRepository repo, RolesRepository rolRepo)
	{
		Usuario user = Usuario.cargarUsuario(username, repo);
		if(!UserDataValidator.comprobarEmail(emailNuevo)) return "Email inválido";		
		
		if(!emailOriginal.equals(emailNuevo))
		{
			if(UserDataValidator.comprobarEmailBD(emailNuevo, repo)) return "El correo ya está en uso";
			user.setEmail(emailNuevo);
		}
		
		if(!password1.isEmpty())
		{
			if(!UserDataValidator.comprobarPassword(password1, password2)) return "Las contraseñas no coinciden";
			user.setPassword(password1);
		}
		
		user.setActivo(activo);
		user.setRolName(rolRepo.findByName(rol).get(0));
		user.guardarUsuario(repo);
		
		return "";
	}
}
