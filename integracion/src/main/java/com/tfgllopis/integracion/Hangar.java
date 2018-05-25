package com.tfgllopis.integracion;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.data.HasValue.ValueChangeEvent;
import com.vaadin.data.HasValue.ValueChangeListener;
import com.vaadin.event.MouseEvents.ClickEvent;
import com.vaadin.event.MouseEvents.ClickListener;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
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
		
		planeta = planetaRepo.findByUsuarioUsername(((VaadinUI) UI.getCurrent()).getUsuario());
		
		CrudRecurso.generarRecursos(planeta, planetaRecursoRepo, planetaInstalacionRepo);
		CrudNave.checkConstruccion(((VaadinUI) UI.getCurrent()).getUsuario(), construyeRepo, planetaRepo, planetaNaveRepo, usuarioNaveRepo, planetaRecursoRepo, naveCuestaRepo);

		if(!construyeRepo.findByUsuariousername(((VaadinUI) UI.getCurrent()).getUsuario().getUsername()).isEmpty())
		{
			cancelarConstruccionLayout.addComponent(new Nave_en_construccion(cancelarConstruccionLayout, errorL,construyeRepo, planetaRepo, planetaNaveRepo, usuarioNaveRepo, planetaRecursoRepo, naveCuestaRepo));
		}
		
		for(int i = 0; i < tipoNaves.size(); i++)
		{
			aux = new Tipo_nave(tipoNaves.get(i), i);
			
			tipoNaveLayout.addComponent(aux);
			
			aux.getImage().addClickListener(new ClickListener()
			{
				@Override
				public void click(ClickEvent event) 
				{
					construirLayout.setVisible(true);
					nombreTipoL.setValue(tipoNaves.get(Integer.parseInt(event.getComponent().getId())).getNombreTipoNave());
					posicion = 0;
					naves = new ArrayList<>(naveRepo.findByNombreTipoNave(tipoNaves.get(Integer.parseInt(event.getComponent().getId())).getNombreTipoNave()));
					comprobarFlechaDer();
					comprobarFlechaIzq();
					naveLayout.removeAllComponents();
					naveLayout.addComponent(new Nave_hangar(naves.get(posicion)));
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
				posicion -= 1;
				if(posicion < 0)posicion = 0;
				naveLayout.removeAllComponents();
				naveLayout.addComponent(new Nave_hangar(naves.get(posicion)));
				comprobarFlechaIzq();
				comprobarFlechaDer();
				actualizarDatosNave(naves.get(posicion));
				construirF.setValue("");
			}
			
		});
		
		flechaDer.addClickListener(new ClickListener()
		{

			@Override
			public void click(ClickEvent event) 
			{
				posicion += 1;
				if(posicion > naves.size())posicion = naves.size();
				naveLayout.removeAllComponents();
				naveLayout.addComponent(new Nave_hangar(naves.get(posicion)));
				comprobarFlechaIzq();
				comprobarFlechaDer();
				actualizarDatosNave(naves.get(posicion));
				construirF.setValue("");
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
				String value = CrudNave.construirNave(naves.get(posicion), ((VaadinUI) UI.getCurrent()).getUsuario(), construirF.getValue(), construyeRepo, planetaRepo, planetaRecursoRepo, naveRepo, naveCuestaRepo, usuarioNaveRepo);
				errorL.setValue(value);
				
				if(value.isEmpty())
				{
					errorL.setVisible(false);
					correctoL.setVisible(true);
					//doNavigate(Hangar.VIEW_NAME  + "/Changed");
					
				}else
				{
					correctoL.setVisible(false);
					errorL.setVisible(true);
				}
			}
		});
	}
	
	private void comprobarFlechaDer()
	{
		if(posicion+1 >= naves.size())
		{
			flechaDer.setEnabled(false);
		}else
		{
			flechaDer.setEnabled(true);
		}
	}
	
	private void comprobarFlechaIzq()
	{
		if(posicion == 0)
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

}
