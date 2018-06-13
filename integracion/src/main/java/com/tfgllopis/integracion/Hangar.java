package com.tfgllopis.integracion;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.data.HasValue.ValueChangeEvent;
import com.vaadin.data.HasValue.ValueChangeListener;
import com.vaadin.event.MouseEvents.ClickEvent;
import com.vaadin.event.MouseEvents.ClickListener;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FileResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.Image;
import com.vaadin.ui.UI;

public class Hangar extends Hangar_Ventana implements View
{
	@Autowired
	TipoNaveRepository tipoRepo;
	
	@Autowired
	NaveRepository naveRepo;
	
	@Autowired
	NavecuestaRecursoRepository naveCuestaRepo;
	
	@Autowired
	UsuarioconstruyeNaveRepository construyeRepo;
	
	@Autowired
	PlanetaRepository planetaRepo;
	
	@Autowired
	PlanetahasRecursoRepository planetaRecursoRepo;
	
	@Autowired
	PlanetaHasNaveRepository planetaNaveRepo;
	
	@Autowired
	PlanetahasInstalacionRepository planetaInstalacionRepo;
	
	@Autowired
	UsuarioHasNaveRepository usuarioNaveRepo;
	
	public static final String VIEW_NAME = "hangar";
	private int posicion = 0;
	private ArrayList<Nave> naves;
	private boolean clickable = true;
	private Image tipoElegido;
	
	@SuppressWarnings({ "rawtypes", "unchecked", "serial" })
	public Hangar()
	{
		ArrayList<TipoNave> tipoNaves;
		Planeta planeta;
		Tipo_nave aux;
		naves = new ArrayList<>();

		naveRepo = ((VaadinUI) UI.getCurrent()).getInterfazNave();
		naveCuestaRepo = ((VaadinUI) UI.getCurrent()).getInterfazNaveCuesta();
		construyeRepo = ((VaadinUI) UI.getCurrent()).getInterfazUsuarioConstruye();
		planetaRepo = ((VaadinUI) UI.getCurrent()).getInterfazPlaneta();
		planetaRecursoRepo = ((VaadinUI) UI.getCurrent()).getInterfazPlanetaRecurso();
		planetaInstalacionRepo = ((VaadinUI) UI.getCurrent()).getInterfazPlanetaInstalacion();
		planetaNaveRepo = ((VaadinUI) UI.getCurrent()).getInterfazPlanetaNave();
		usuarioNaveRepo = ((VaadinUI) UI.getCurrent()).getInterfazUsuarioNave();
		tipoRepo = ((VaadinUI) UI.getCurrent()).getInterfazTipo();
		tipoNaves = new ArrayList<>(tipoRepo.findAll());
		flechaDer.setSource(new FileResource(new File(new File("").getAbsolutePath() + "/images/" +"Flecha_Der.png")));
		flechaIzq.setSource(new FileResource(new File(new File("").getAbsolutePath() + "/images/" +"Flecha_Izq.png")));
		
		planeta = planetaRepo.findByUsuarioUsername(((VaadinUI) UI.getCurrent()).getUsuario());
		
		Pagina_principal.generarRecursos(planeta, planetaRecursoRepo, planetaInstalacionRepo);
		Nave_en_construccion.checkConstruccion(((VaadinUI) UI.getCurrent()).getUsuario(), construyeRepo, planetaRepo, planetaNaveRepo, usuarioNaveRepo, planetaRecursoRepo, naveCuestaRepo);

		if(!construyeRepo.findByUsuariousername(((VaadinUI) UI.getCurrent()).getUsuario().getUsername()).isEmpty())
		{
			cancelarConstruccionLayout.addComponent(new Nave_en_construccion(cancelarConstruccionLayout, errorL,construyeRepo, planetaRepo, planetaNaveRepo, usuarioNaveRepo, planetaRecursoRepo, naveCuestaRepo));
		}
		
		for(int i = 0; i < tipoNaves.size(); i++)
		{
			aux = new Tipo_nave(tipoNaves.get(i), i);
			
			tipoNaveLayout.addComponent(aux);
			
			if(i == 0)
			{
				tipoElegido = aux.getImage();
				construirLayout.setVisible(true);
				nombreTipoL.setValue(tipoNaves.get(i).getNombreTipoNave());
				posicion = 0;
				naves = new ArrayList<>(naveRepo.findByNombreTipoNave(tipoNaves.get(i).getNombreTipoNave()));
				comprobarFlechaDer();
				comprobarFlechaIzq();
				naveLayout.removeAllComponents();
				naveLayout.addComponent(new Nave_hangar(naves.get(posicion), ((VaadinUI) UI.getCurrent()).getUsuario(), usuarioNaveRepo));
				actualizarDatosNave(naves.get(0));
				construirF.setValue("");
				tipoElegido.addStyleName("borde-nave");
			}
			
			aux.getImage().addClickListener(new ClickListener()
			{
				@Override
				public void click(ClickEvent event) 
				{
					tipoElegido.removeStyleName("borde-nave");
					tipoElegido = (Image) event.getSource();
					tipoElegido.addStyleName("borde-nave");
					construirLayout.setVisible(true);
					nombreTipoL.setValue(tipoNaves.get(Integer.parseInt(event.getComponent().getId())).getNombreTipoNave());
					posicion = 0;
					naves = new ArrayList<>(naveRepo.findByNombreTipoNave(tipoNaves.get(Integer.parseInt(event.getComponent().getId())).getNombreTipoNave()));
					comprobarFlechaDer();
					comprobarFlechaIzq();
					naveLayout.removeAllComponents();
					naveLayout.addComponent(new Nave_hangar(naves.get(posicion), ((VaadinUI) UI.getCurrent()).getUsuario(), usuarioNaveRepo));
					actualizarDatosNave(naves.get(0));
					construirF.setValue("");
				}				
			});
		}
		
		flechaIzq.addClickListener(new ClickListener()
		{

			@Override
			public void click(ClickEvent event) 
			{
				if(clickable) 
				{
					clickable = false;
					try 
					{
						flechaIzq.setEnabled(false);
						posicion -= 1;
						if(posicion < 0)posicion = 0;
						naveLayout.removeAllComponents();
						naveLayout.addComponent(new Nave_hangar(naves.get(posicion), ((VaadinUI) UI.getCurrent()).getUsuario(), usuarioNaveRepo));
						actualizarDatosNave(naves.get(posicion));
						construirF.setValue("");
						Thread.sleep(100);
						comprobarFlechaIzq();
						comprobarFlechaDer();	
					} catch (InterruptedException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}	
					clickable = true;
				}
			}	
		});
		
		flechaDer.addClickListener(new ClickListener()
		{

			@Override
			public void click(ClickEvent event) 
			{
				if(clickable)
				{
					clickable = false;
					try 
					{
						flechaDer.setEnabled(false);
						posicion += 1;
						if(posicion >= naves.size())posicion = naves.size()-1;
						naveLayout.removeAllComponents();
						naveLayout.addComponent(new Nave_hangar(naves.get(posicion), ((VaadinUI) UI.getCurrent()).getUsuario(), usuarioNaveRepo));
						actualizarDatosNave(naves.get(posicion));
						construirF.setValue("");
						Thread.sleep(100);
						comprobarFlechaIzq();
						comprobarFlechaDer();
					} catch (InterruptedException e) 
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					clickable = true;	
				}
			}
			
		});
		
		construirF.addValueChangeListener(new ValueChangeListener()
		{

			@Override
			public void valueChange(ValueChangeEvent event) 
			{
				if(NaveDataValidator.comprobarValorInteger(construirF.getValue()))
				{
					actualizarCostes(Integer.parseInt(construirF.getValue().replaceAll(",",".")));
				}
				
			}
			
		});
		
		construirB.addClickListener(new Button.ClickListener() 
		{
			
			@Override
			public void buttonClick(com.vaadin.ui.Button.ClickEvent event) 
			{
				String value = Hangar.construirNave(naves.get(posicion), ((VaadinUI) UI.getCurrent()).getUsuario(), construirF.getValue(), construyeRepo, planetaRepo, planetaRecursoRepo, naveRepo, naveCuestaRepo, usuarioNaveRepo);
				errorL.setValue(value);
				
				if(value.isEmpty())
				{
					errorL.setVisible(false);
					correctoL.setVisible(true);
					cancelarConstruccionLayout.addComponent(new Nave_en_construccion(cancelarConstruccionLayout, errorL,construyeRepo, planetaRepo, planetaNaveRepo, usuarioNaveRepo, planetaRecursoRepo, naveCuestaRepo));
					
				}else
				{
					correctoL.setVisible(false);
					errorL.setVisible(true);
				}
			}
		});
	}
	
	public void comprobarFlechaDer()
	{
		if(posicion >= naves.size()-1)
		{
			flechaDer.setEnabled(false);
		}else
		{
			flechaDer.setEnabled(true);
		}
	}
	
	public void comprobarFlechaIzq()
	{
		if(posicion <= 0)
		{
			flechaIzq.setEnabled(false);
		}else
		{
			flechaIzq.setEnabled(true);
		}
	}
	
	private void actualizarDatosNave(Nave nave)
	{
		saludL.setValue(nave.getHullNave() + "");
		escudoL.setValue(nave.getEscudoNave() + "");
		ataqueL.setValue(nave.getAtaqueNave() + "");
		agilidadL.setValue(nave.getAgilidadNave() + "");
		velocidadL.setValue(nave.getVelocidadNave() + "");
		cargaL.setValue(nave.getCapacidadCarga() + "");
		velocidadL.setValue(nave.getVelocidadNave() + "");
		
		metalL.setValue(naveCuestaRepo.findByRecursoname_NavenombreNave(nave.getNombreNave(), "Metal").getCantidadBase() + "");
		oroL.setValue(naveCuestaRepo.findByRecursoname_NavenombreNave(nave.getNombreNave(), "Oro").getCantidadBase() + "");
		petroleoL.setValue(naveCuestaRepo.findByRecursoname_NavenombreNave(nave.getNombreNave(), "Petroleo").getCantidadBase() + "");
	}
	
	private void actualizarCostes(int cantidad)
	{
		metalL.setValue(naveCuestaRepo.findByRecursoname_NavenombreNave(naves.get(posicion).getNombreNave(), "Metal").getCantidadBase() * cantidad + "");
		oroL.setValue(naveCuestaRepo.findByRecursoname_NavenombreNave(naves.get(posicion).getNombreNave(), "Oro").getCantidadBase() * cantidad + "");
		petroleoL.setValue(naveCuestaRepo.findByRecursoname_NavenombreNave(naves.get(posicion).getNombreNave(), "Petroleo").getCantidadBase() * cantidad + "");
	}
	
	public static String construirNave(Nave nave, Usuario usuario, String cantidad, UsuarioconstruyeNaveRepository construyeRepo, PlanetaRepository planetaRepo, PlanetahasRecursoRepository planetaRecursoRepo, NaveRepository naveRepo, NavecuestaRecursoRepository naveCuestaRepo, UsuarioHasNaveRepository usuarioNaveRepo)
	{
		Date fechaFinConstruccion;
		Calendar calendar = Calendar.getInstance();
		Planeta planeta = planetaRepo.findByUsuarioUsername(usuario);
		PlanetahasRecurso cantidadMetal = planetaRecursoRepo.findByPlanetaRecurso(planeta.getCoordenadaX(), planeta.getCoordenadaY(), planeta.getSistemanombreSistema(), "Metal");
		PlanetahasRecurso cantidadOro = planetaRecursoRepo.findByPlanetaRecurso(planeta.getCoordenadaX(), planeta.getCoordenadaY(), planeta.getSistemanombreSistema(), "Oro");
		PlanetahasRecurso cantidadPetroleo = planetaRecursoRepo.findByPlanetaRecurso(planeta.getCoordenadaX(), planeta.getCoordenadaY(), planeta.getSistemanombreSistema(), "Petroleo");
		int costeMetal = naveCuestaRepo.findByRecursoname_NavenombreNave(nave.getNombreNave(), "Metal").getCantidadBase();
		int costeOro = naveCuestaRepo.findByRecursoname_NavenombreNave(nave.getNombreNave(), "Oro").getCantidadBase();
		int costePetroleo = naveCuestaRepo.findByRecursoname_NavenombreNave(nave.getNombreNave(), "Petroleo").getCantidadBase();
		UsuarioconstruyeNave naveConstruida;
		
		if(!construyeRepo.findByUsuariousername(usuario.getUsername()).isEmpty()) return "Ya hay naves construyendose";
		if(nave.getBloqueada() == 1 && usuarioNaveRepo.findByNavenombreNaveUsuarioUsername(nave.getNombreNave(), usuario.getUsername()).isEmpty()) return "No tienes esa nave desbloqueada";
		if(!NaveDataValidator.comprobarValorInteger(cantidad)) return "La cantidad de naves introducida es incorrecta";
		
		costeMetal *= Integer.parseInt(cantidad);
		costeOro *= Integer.parseInt(cantidad);
		costePetroleo *= Integer.parseInt(cantidad);
		
		if(costeMetal > cantidadMetal.getCantidad()) return "No posees suficiente metal";
		if(costeOro > cantidadOro.getCantidad()) return "No posees suficiente oro";
		if(costePetroleo > cantidadPetroleo.getCantidad()) return "No posees suficiente petroleo";

		calendar.add(Calendar.SECOND, Integer.parseInt(cantidad) * nave.getSegundosConstruccion());
		fechaFinConstruccion = calendar.getTime();
		
		cantidadMetal = planetaRecursoRepo.findByPlanetaRecurso(planeta.getCoordenadaX(), planeta.getCoordenadaY(), planeta.getSistemanombreSistema(), "Metal");
		cantidadMetal.setCantidad(cantidadMetal.getCantidad()-costeMetal);
		planetaRecursoRepo.save(cantidadMetal);
		
		cantidadOro = planetaRecursoRepo.findByPlanetaRecurso(planeta.getCoordenadaX(), planeta.getCoordenadaY(), planeta.getSistemanombreSistema(), "Oro");
		cantidadOro.setCantidad(cantidadOro.getCantidad()-costeOro);
		planetaRecursoRepo.save(cantidadOro);	
		
		cantidadPetroleo = planetaRecursoRepo.findByPlanetaRecurso(planeta.getCoordenadaX(), planeta.getCoordenadaY(), planeta.getSistemanombreSistema(), "Petroleo");
		cantidadPetroleo.setCantidad(cantidadPetroleo.getCantidad()-costePetroleo);
		planetaRecursoRepo.save(cantidadPetroleo);	
		
		naveConstruida = new UsuarioconstruyeNave(usuario.getUsername(), nave.getNombreNave(), Integer.parseInt(cantidad), fechaFinConstruccion);
		naveConstruida.setNave(nave);
		
		construyeRepo.save(naveConstruida);
		
		return "";
	}
	

}
