package com.tfgllopis.integracion;

import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

import com.vaadin.server.ClassResource;
import com.vaadin.server.Sizeable.Unit;
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
		imagenNave.setSource(naveConstruida.getNave().getImage().getSource());
		imagenNave.setWidth(50, Unit.PIXELS);
		imagenNave.setHeight(50, Unit.PIXELS);
		cancelarConstruccionB.addClickListener(new Button.ClickListener() 
		{
			
			@Override
			public void buttonClick(ClickEvent event) 
			{
				String value = Nave_en_construccion.cancelarConstruccion(((VaadinUI) UI.getCurrent()).getUsuario(), construyeRepo, planetaRepo, planetaNaveRepo, usuarioNaveRepo, planetaRecursoRepo, naveCuestaRepo);
				
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
	
	public static String cancelarConstruccion(Usuario usuario, UsuarioconstruyeNaveRepository construyeRepo, PlanetaRepository planetaRepo, PlanetaHasNaveRepository planetaNaveRepo,UsuarioHasNaveRepository usuarioNaveRepo, PlanetahasRecursoRepository planetaRecursoRepo, NavecuestaRecursoRepository naveCuestaRepo)
	{
		Date fechaAhora = new Date();
		int seconds, segundosConstruccion, navesConstruidas, beneficiosMetal, beneficiosOro, beneficiosPetroleo, costeMetal, costeOro, costePetroleo;
		ArrayList<UsuarioconstruyeNave> naveConstruyendo = new ArrayList<>(construyeRepo.findByUsuariousername(usuario.getUsername()));
		Planeta planeta = planetaRepo.findByUsuarioUsername(usuario);
		PlanetahasRecurso recursoMetal, recursoOro, recursoPetroleo;
		
		if(naveConstruyendo.isEmpty()) return "No hay naves construyendose";
		if(naveConstruyendo.get(0).getFinConstruccion().before(fechaAhora))
		{
			añadirNaves(usuario, naveConstruyendo.get(0).getNavenombreNave(), naveConstruyendo.get(0).getCantidad(), planeta, planetaNaveRepo, usuarioNaveRepo);
			construyeRepo.delete(naveConstruyendo.get(0));
			return "Las naves ya terminaron de construirse";
		}
		
		seconds = (int) (naveConstruyendo.get(0).getFinConstruccion().getTime()-fechaAhora.getTime())/1000;
		segundosConstruccion = naveConstruyendo.get(0).getNave().getSegundosConstruccion();
		navesConstruidas = ((segundosConstruccion * naveConstruyendo.get(0).getCantidad()) - seconds)/segundosConstruccion;
		añadirNaves(usuario, naveConstruyendo.get(0).getNavenombreNave(), navesConstruidas, planeta, planetaNaveRepo, usuarioNaveRepo);

		recursoMetal = planetaRecursoRepo.findByPlanetaRecurso(planeta.getCoordenadaX(), planeta.getCoordenadaY(), planeta.getSistemanombreSistema(), "Metal");
		recursoOro = planetaRecursoRepo.findByPlanetaRecurso(planeta.getCoordenadaX(), planeta.getCoordenadaY(), planeta.getSistemanombreSistema(), "Oro");
		recursoPetroleo = planetaRecursoRepo.findByPlanetaRecurso(planeta.getCoordenadaX(), planeta.getCoordenadaY(), planeta.getSistemanombreSistema(), "Petroleo");

		costeMetal = naveCuestaRepo.findByRecursoname_NavenombreNave(naveConstruyendo.get(0).getNavenombreNave(), "Metal").getCantidadBase();
		costeOro = naveCuestaRepo.findByRecursoname_NavenombreNave(naveConstruyendo.get(0).getNavenombreNave(), "Oro").getCantidadBase();
		costePetroleo = naveCuestaRepo.findByRecursoname_NavenombreNave(naveConstruyendo.get(0).getNavenombreNave(), "Petroleo").getCantidadBase();

		beneficiosMetal = (naveConstruyendo.get(0).getCantidad() - navesConstruidas) * costeMetal;
		beneficiosOro = (naveConstruyendo.get(0).getCantidad() - navesConstruidas) * costeOro;
		beneficiosPetroleo = (naveConstruyendo.get(0).getCantidad() - navesConstruidas) * costePetroleo;

		recursoMetal.setCantidad(recursoMetal.getCantidad() + beneficiosMetal);
		recursoOro.setCantidad(recursoOro.getCantidad() + beneficiosOro);
		recursoPetroleo.setCantidad(recursoPetroleo.getCantidad() + beneficiosPetroleo);
		
		planetaRecursoRepo.save(recursoMetal);
		planetaRecursoRepo.save(recursoOro);
		planetaRecursoRepo.save(recursoPetroleo);
		
		construyeRepo.delete(naveConstruyendo.get(0));
		return "";
	}
	
	public static String checkConstruccion(Usuario usuario, UsuarioconstruyeNaveRepository construyeRepo, PlanetaRepository planetaRepo, PlanetaHasNaveRepository planetaNaveRepo,UsuarioHasNaveRepository usuarioNaveRepo, PlanetahasRecursoRepository planetaRecursoRepo, NavecuestaRecursoRepository naveCuestaRepo)
	{
		Date fechaAhora = new Date();
		int seconds, segundosConstruccion, navesConstruidas;
		ArrayList<UsuarioconstruyeNave> naveConstruyendo = new ArrayList<>(construyeRepo.findByUsuariousername(usuario.getUsername()));
		Planeta planeta = planetaRepo.findByUsuarioUsername(usuario);
		
		if(naveConstruyendo.isEmpty()) return "No hay naves construyendose";
		if(naveConstruyendo.get(0).getFinConstruccion().before(fechaAhora))
		{
			añadirNaves(usuario, naveConstruyendo.get(0).getNavenombreNave(), naveConstruyendo.get(0).getCantidad(), planeta, planetaNaveRepo, usuarioNaveRepo);
			construyeRepo.delete(naveConstruyendo.get(0));
			return "Naves ya construidas";
		}
		
		seconds = (int) (naveConstruyendo.get(0).getFinConstruccion().getTime()-fechaAhora.getTime())/1000;
		segundosConstruccion = naveConstruyendo.get(0).getNave().getSegundosConstruccion();
		navesConstruidas = ((segundosConstruccion * naveConstruyendo.get(0).getCantidad()) - seconds)/segundosConstruccion;
		añadirNaves(usuario, naveConstruyendo.get(0).getNavenombreNave(), navesConstruidas, planeta, planetaNaveRepo, usuarioNaveRepo);
		
		naveConstruyendo.get(0).setCantidad(naveConstruyendo.get(0).getCantidad() - navesConstruidas);
		construyeRepo.save(naveConstruyendo.get(0));
		
		return "";
	}
	
	private static void añadirNaves(Usuario usuario, String nombreNave, int cantidad, Planeta planeta, PlanetaHasNaveRepository planetaNaveRepo,UsuarioHasNaveRepository usuarioNaveRepo)
	{
		ArrayList<PlanetaHasNave> planetaNaves = new ArrayList<>(planetaNaveRepo.findByNavenombreNavePlaneta(nombreNave, planeta.getCoordenadaX(), planeta.getCoordenadaY(), planeta.getSistemanombreSistema()));
		ArrayList<UsuarioHasNave> usuarioNaves = new ArrayList<>(usuarioNaveRepo.findByNavenombreNaveUsuarioUsername(nombreNave, usuario.getUsername()));
	
		if(usuarioNaves.isEmpty())
		{
			usuarioNaves.add(new UsuarioHasNave(usuario.getUsername(), nombreNave, cantidad));
			planetaNaves.add(new PlanetaHasNave(planeta.getCoordenadaX(), planeta.getCoordenadaY(), planeta.getSistemanombreSistema(), nombreNave, cantidad));
		}else
		{
			usuarioNaves.get(0).setCantidad(usuarioNaves.get(0).getCantidad() + cantidad);
			planetaNaves.get(0).setCantidad(planetaNaves.get(0).getCantidad() + cantidad);
		}
		
		usuarioNaveRepo.save(usuarioNaves.get(0));
		planetaNaveRepo.save(planetaNaves.get(0));
	}

}
