package com.tfgllopis.integracion;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.navigator.View;
import com.vaadin.server.FileResource;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Button;
import com.vaadin.ui.UI;
import com.vaadin.ui.Button.ClickEvent;

public class Registrarse  extends Registrarse_Ventana implements View
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
	
	public static final String VIEW_NAME = "registro";
	
	public Registrarse()
	{
		userRepo = ((VaadinUI) UI.getCurrent()).getInterfazUsuario();
		rolRepo = ((VaadinUI) UI.getCurrent()).getInterfazRol();
		planetaRepo = ((VaadinUI) UI.getCurrent()).getInterfazPlaneta();
		planetaNaveRepo = ((VaadinUI) UI.getCurrent()).getInterfazPlanetaNave();
		planetaInstalacionRepo = ((VaadinUI) UI.getCurrent()).getInterfazPlanetaInstalacion();
		planetaRecursoRepo = ((VaadinUI) UI.getCurrent()).getInterfazPlanetaRecurso();
		
		imagenLogo.setSource( new FileResource(new File(new File("").getAbsolutePath() + "/images/" + "Logo_Cabeza_Fenix_Pequeña.png")));
		imagenLogo.setWidth(100, Unit.PIXELS);
		imagenLogo.setHeight(100, Unit.PIXELS);
		
		registrarseB.addClickListener(new Button.ClickListener() 
		{
			
			@Override
			public void buttonClick(ClickEvent event) 
			{
				String value = CrudUsuario.registro(usuarioF.getValue(), emailF.getValue(), passwordF.getValue(), password2F.getValue(), userRepo, rolRepo);
				errorL.setValue(value);
				
				if(value.isEmpty()) 
				{
					errorL.setVisible(false);
					CrudUsuario.inicializarUsuario(Usuario.cargarUsuario(usuarioF.getValue().replaceAll("\\s+",""), userRepo), planetaNaveRepo, planetaInstalacionRepo, planetaRecursoRepo, planetaRepo);
					doNavigate(Iniciar_sesion.VIEW_NAME + "/" + "registrado");
				}else
				{
					errorL.setVisible(true);
				}
				
			}
		});
		
		cancelarB.addClickListener(new Button.ClickListener() 
		{
			
			@Override
			public void buttonClick(ClickEvent event) 
			{
				doNavigate(Iniciar_sesion.VIEW_NAME);
			}
		});
	}
	
	private void doNavigate(String viewName) 
	{
		UI.getCurrent().getNavigator().navigateTo(viewName);
	}
}
