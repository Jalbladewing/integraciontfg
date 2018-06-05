package com.tfgllopis.integracion;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.UI;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;

public class Borrar_usuario extends Borrar_usuario_Ventana implements View 
{
	@Autowired
	private UsuarioRepository userRepo;
	
	@Autowired
	private PlanetaRepository planetaRepo;
	
	@Autowired
	private PlanetaHasNaveRepository planetaNaveRepo;
	
	@Autowired
	private PlanetahasInstalacionRepository planetaInstalacionRepo;
	
	@Autowired
	private PlanetahasRecursoRepository planetaRecursoRepo;
	
	@Autowired
	private PirataRepository pirataRepo;
	
	@Autowired
	private PiratahasInstalacionRepository pirataInstalacionRepo;
	
	@Autowired
	private PiratahasNaveRepository pirataNaveRepo;
	
	public static String VIEW_NAME = "borrarUsuario";
	
	public Borrar_usuario()
	{
		eliminarB.addClickListener(new Button.ClickListener() 
		{
			
			@Override
			public void buttonClick(ClickEvent event) 
			{
				CrudUsuario.borrarUsuario(Usuario.cargarUsuario(usuarioL.getValue().replaceAll("\\s+",""), userRepo), planetaNaveRepo, planetaInstalacionRepo, planetaRecursoRepo, planetaRepo, pirataRepo, pirataInstalacionRepo, pirataNaveRepo, userRepo);
				doNavigate(Listar_usuarios.VIEW_NAME);	
			}
		});
		
		cancelarB.addClickListener(new Button.ClickListener() 
		{
			
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
		
		if (!event.getParameters().isEmpty())
		{
			userRepo = ((VaadinUI) UI.getCurrent()).getInterfazUsuario();
			planetaRepo = ((VaadinUI) UI.getCurrent()).getInterfazPlaneta();
			planetaNaveRepo = ((VaadinUI) UI.getCurrent()).getInterfazPlanetaNave();
			planetaInstalacionRepo = ((VaadinUI) UI.getCurrent()).getInterfazPlanetaInstalacion();
			planetaRecursoRepo = ((VaadinUI) UI.getCurrent()).getInterfazPlanetaRecurso();
			pirataRepo = ((VaadinUI) UI.getCurrent()).getInterfazPirata();
			pirataInstalacionRepo = ((VaadinUI) UI.getCurrent()).getInterfazPirataInstalacion();
			pirataNaveRepo = ((VaadinUI) UI.getCurrent()).getInterfazPirataNave();
			
			usuarioL.setValue(event.getParameters());
			emailL.setValue(userRepo.findByUsername(event.getParameters()).get(0).getEmail());
		}
	}
	
	private void doNavigate(String viewName) 
	{
		UI.getCurrent().getNavigator().navigateTo(viewName);
	}
}
