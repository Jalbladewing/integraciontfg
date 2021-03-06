package com.tfgllopis.integracion;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.RadioButtonGroup;
import com.vaadin.ui.UI;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;

public class Crear_usuario extends Crear_usuario_Ventana implements View 
{
	@Autowired
	private UsuarioRepository userRepo;
	
	@Autowired
	private RolesRepository rolRepo;
	
	@Autowired
	private PlanetaRepository planetaRepo;
	
	@Autowired
	private PlanetaHasNaveRepository planetaNaveRepo;
	
	@Autowired
	private PlanetahasInstalacionRepository planetaInstalacionRepo;
	
	@Autowired
	private PlanetahasRecursoRepository planetaRecursoRepo;
	
	public static String VIEW_NAME = "crearUsuario";
	
	public Crear_usuario()
	{
		userRepo = ((VaadinUI) UI.getCurrent()).getInterfazUsuario();
		rolRepo = ((VaadinUI) UI.getCurrent()).getInterfazRol();
		planetaRepo = ((VaadinUI) UI.getCurrent()).getInterfazPlaneta();
		planetaNaveRepo = ((VaadinUI) UI.getCurrent()).getInterfazPlanetaNave();
		planetaInstalacionRepo = ((VaadinUI) UI.getCurrent()).getInterfazPlanetaInstalacion();
		planetaRecursoRepo = ((VaadinUI) UI.getCurrent()).getInterfazPlanetaRecurso();
		
		guardarB.addClickListener(new Button.ClickListener() 
		{
			
			@Override
			public void buttonClick(ClickEvent event)
			{
				String value = Registrarse.comprobarPlanetaDisponible(planetaRepo);
				errorL.setValue(value);
				
				if(value.isEmpty()) 
				{
					errorL.setVisible(false);
					
					value = Crear_usuario.registroAdmin(usuarioF.getValue(), emailF.getValue(), passwordF.getValue(), password2F.getValue(), rolesRadio.getValue(), activoChckBx.getValue(), userRepo, rolRepo);
					errorL.setValue(value);
					
					if(value.isEmpty()) 
					{
						errorL.setVisible(false);
						Registrarse.inicializarUsuario(Usuario.cargarUsuario(usuarioF.getValue().replaceAll("\\s+",""), userRepo), planetaNaveRepo, planetaInstalacionRepo, planetaRecursoRepo, planetaRepo);
						doNavigate(Crear_usuario.VIEW_NAME + "/" + "creado");
					}else
					{
						correctoL.setVisible(false);
						errorL.setVisible(true);
					}
					
				}else
				{
					errorL.setVisible(true);
				}		
				
			}
		});
	}
	
	@Override
	public void enter(ViewChangeEvent event) 
	{
		 if (!event.getParameters().isEmpty()) correctoL.setVisible(true);
	}
	
	private void doNavigate(String viewName) 
	{
		UI.getCurrent().getNavigator().navigateTo(viewName);
	}

	public static String registroAdmin(String username, String email, String password1, String password2, String rol, boolean activo, UsuarioRepository repo, RolesRepository rolRepo)
	{
		Usuario user;
		Date fechaRegistro = new Date();
		Gestor_Correos correo = new Gestor_Correos();
		if(!UserDataValidator.comprobarUser(username)) return "Usuario inválido";
		if(!UserDataValidator.comprobarEmail(email)) return "Email inválido";
		if(!UserDataValidator.comprobarPassword(password1, password2)) return "Las contraseñas no coinciden";		
		if(UserDataValidator.comprobarUsuarioBD(username, repo)) return "El usuario ya existe";
		if(UserDataValidator.comprobarEmailBD(email, repo)) return "El correo ya está en uso";
		if(!correo.correo_registro(email)) return "Imposible acceder al correo";
		
		user = new Usuario(email, username, password1, activo, fechaRegistro, fechaRegistro);
		user.setRolName(rolRepo.findByName(rol).get(0));
		user.guardarUsuario(repo);
		
		return "";
	}
	
}
