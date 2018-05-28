package com.tfgllopis.integracion;

import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import java.sql.Timestamp;
import java.util.Date;

import com.vaadin.server.ClassResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Label;

public class Nave_en_construccion extends Nave_en_construccion_Ventana
{
	static final int SECOND = 1000;        // no. of ms in a second
	static final int MINUTE = SECOND * 60; // no. of ms in a minute
	static final int HOUR = MINUTE * 60;   // no. of ms in an hour
	static final int DAY = HOUR * 24;      // no. of ms in a day
	private VerticalLayout layoutPadre;
	private Label errorL;
	
	public Nave_en_construccion(VerticalLayout layoutPadre, Label errorL,UsuarioconstruyeNaveRepository construyeRepo, PlanetaRepository planetaRepo, PlanetaHasNaveRepository planetaNaveRepo, UsuarioHasNaveRepository usuarioNaveRepo, PlanetahasRecursoRepository planetaRecursoRepo, NavecuestaRecursoRepository naveCuestaRepo)
	{
		Date fechaAhora = new Date();
		this.layoutPadre = layoutPadre;
		this.errorL = errorL;
		UsuarioconstruyeNave naveConstruida = construyeRepo.findByUsuariousername(((VaadinUI) UI.getCurrent()).getUsuario().getUsername()).get(0);
		long tiempoRestante = (naveConstruida.getFinConstruccion().getTime()-fechaAhora.getTime());
		int hours   = (int)((tiempoRestante % DAY) / HOUR);
		int minutes = (int)((tiempoRestante % HOUR) / MINUTE);
		int seconds = (int)((tiempoRestante % MINUTE) / SECOND);
		navesConstruccionL.setValue(naveConstruida.getCantidad() + "");
		tiempoL.setValue(hours + ":" + minutes + ":" + seconds);
		imagenNave.setSource(new ClassResource("/images/" + naveConstruida.getNave().getRutaImagenNave()));
		cancelarConstruccionB.addClickListener(new Button.ClickListener() 
		{
			
			@Override
			public void buttonClick(ClickEvent event) 
			{
				String value = CrudNave.cancelarConstruccion(((VaadinUI) UI.getCurrent()).getUsuario(), construyeRepo, planetaRepo, planetaNaveRepo, usuarioNaveRepo, planetaRecursoRepo, naveCuestaRepo);
				
				errorL.setValue(value);
				
				if(value.isEmpty())
				{
					layoutPadre.removeAllComponents();

				}else
				{
					errorL.setVisible(true);
				}						
			}
		});
	}
	
	private void doNavigate(String viewName) 
	{
		UI.getCurrent().getNavigator().navigateTo(viewName);
	}

}
