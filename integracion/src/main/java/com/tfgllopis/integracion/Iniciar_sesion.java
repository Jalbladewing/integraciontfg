package com.tfgllopis.integracion;

import java.io.File;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinService;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.UI;

public class Iniciar_sesion extends Iniciar_sesion_Ventana implements View 
{
	@Autowired
	private UsuarioRepository userRepo;
	
	public static final String VIEW_NAME = "login";
	
	public Iniciar_sesion()
	{

		userRepo = ((VaadinUI) UI.getCurrent()).getInterfazUsuario();
		
		imagenLogo.setSource( new FileResource(new File(new File("").getAbsolutePath() + "/images/" + "Logo_Cabeza_Fenix_Pequeña.png")));
		imagenLogo.setWidth(100, Unit.PIXELS);
		imagenLogo.setHeight(100, Unit.PIXELS);

		loginB.addClickListener(new Button.ClickListener() 
		{
			
			@Override
			public void buttonClick(ClickEvent event) 
			{
				String value = Iniciar_sesion.login(usuarioF.getValue(), passwordF.getValue(), userRepo);
				Usuario usuario;
				errorL.setValue(value);
				
				if(value.isEmpty())
				{
					errorL.setVisible(false);
					usuario = userRepo.findByUsername(usuarioF.getValue()).get(0);
					
					UI.getCurrent().getNavigator().destroy();
					((VaadinUI) UI.getCurrent()).setUsuario(usuario);
					VaadinService.getCurrentRequest().getWrappedSession().setAttribute("currentUser", usuario);
					
					if(usuario.getRolName().getName().equals("Jugador"))
					{
						VaadinUI.getCurrent().setContent(new Jugador());

					}else if(usuario.getRolName().getName().equals("Administrador"))
					{
						VaadinUI.getCurrent().setContent(new Administrador());

					}else if(usuario.getRolName().getName().equals("Tecnico"))
					{
						VaadinUI.getCurrent().setContent(new Tecnico());

					}
					
				}else
				{
					correctoL.setVisible(false);
					errorL.setVisible(true);
				}
			}
		});
		
		registrarseB.addClickListener(new Button.ClickListener() 
		{
			
			@Override
			public void buttonClick(ClickEvent event) 
			{
				doNavigate(Registrarse.VIEW_NAME);
			}
		});
	}
	
	private void doNavigate(String viewName) 
	{
		UI.getCurrent().getNavigator().navigateTo(viewName);
	}
	
	@Override
	public void enter(ViewChangeEvent event) 
	{
		 if (!event.getParameters().isEmpty()) correctoL.setVisible(true);
	}
	
	public static String login(String username, String password, UsuarioRepository repo)
	{		
		
		
		if(!UserDataValidator.comprobarUsuarioBD(username, repo)) return "Usuario no registrado";
		if(!UserDataValidator.comprobarPassword(username, password, repo)) return "Datos incorrectos";
		if(!UserDataValidator.comprobarActivo(username, repo)) return "Usuario bloqueado";
		
		actualizarFechaAcceso(username, repo);
		return "";
	}
	
	private static void actualizarFechaAcceso(String username, UsuarioRepository repo)
	{
		Usuario user = Usuario.cargarUsuario(username, repo);
		user.setFechaUltimoAcceso(new Date());
		user.guardarUsuario(repo);
	}
	

}
