package com.tfgllopis.integracion;

import java.util.ArrayList;
import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.UI;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Component;

public class Editar_pirata extends Editar_pirata_Ventana implements View
{
	@Autowired
	private NaveRepository naveRepo;
	
	@Autowired
	private PiratahasInstalacionRepository pirataInstalacionRepo;
	
	@Autowired
	private PiratahasNaveRepository pirataNaveRepo;
	
	@Autowired
	private PlanetaRepository planetaRepo;
	
	@Autowired
	private PlanetaHasNaveRepository planetaNaveRepo;
	
	public static String VIEW_NAME = "editarPirata";
	
	private int idPirata;
	private PiratahasInstalacion instalacionMetal, instalacionOro, instalacionPetroleo;
	private Instalacion_pirata layoutMetal, layoutOro, layoutPetroleo;
	
	public Editar_pirata()
	{		
		pirataNaveRepo = ((VaadinUI) UI.getCurrent()).getInterfazPirataNave();	
		
		guardarB.addClickListener(new Button.ClickListener() 
		{
			
			@Override
			public void buttonClick(ClickEvent event) 
			{
				String value = Editar_pirata.updatePirataInstalacion(instalacionMetal, instalacionOro, instalacionPetroleo, layoutMetal.nivelF.getValue(), layoutOro.nivelF.getValue(), layoutPetroleo.nivelF.getValue(), pirataInstalacionRepo);
				errorL.setValue(value);
				
				if(value.isEmpty())
				{
					errorL.setVisible(false);
					
					value = updatePirataNave(idPirata, (Iterator<Nave_pirata>)(Object) layoutNaves.iterator(), pirataNaveRepo, planetaRepo, planetaNaveRepo);
					errorL.setValue(value);
					
					if(value.isEmpty())
					{
						doNavigate(Editar_pirata.VIEW_NAME  + "/" + instalacionMetal.getPirataidPirata() + "&" + "Changed");

					}else
					{
						correctoL.setVisible(false);
						errorL.setVisible(true);
					}
					
				}else
				{
					correctoL.setVisible(false);
					errorL.setVisible(true);
				}
				
			}
		});
		
		cancelarB.addClickListener(new Button.ClickListener() 
		{
			
			@Override
			public void buttonClick(ClickEvent event) 
			{
				doNavigate(Piratas.VIEW_NAME);
			}
		});
	}
	
	public static String updatePirataInstalacion(PiratahasInstalacion instalacionMetal, PiratahasInstalacion instalacionOro, PiratahasInstalacion instalacionPetroleo, String nivelMetal, String nivelOro, String nivelPetroleo, PiratahasInstalacionRepository pirataInstalacionRepo)
	{
		if(!NaveDataValidator.comprobarValorInteger(nivelMetal)) return "El nivel para la mina de metal introducido es incorrecto";
		if(!NaveDataValidator.comprobarValorInteger(nivelOro)) return "El nivel para la mina de oro introducido es incorrecto";
		if(!NaveDataValidator.comprobarValorInteger(nivelPetroleo)) return "El nivel para la plataforma petrolifera introducido es incorrecto";
		
		instalacionMetal.setNivelDefecto(Integer.parseInt(nivelMetal.replaceAll(",",".")));
		instalacionOro.setNivelDefecto(Integer.parseInt(nivelOro.replaceAll(",",".")));
		instalacionPetroleo.setNivelDefecto(Integer.parseInt(nivelPetroleo.replaceAll(",",".")));
		
		pirataInstalacionRepo.save(instalacionMetal);
		pirataInstalacionRepo.save(instalacionOro);
		pirataInstalacionRepo.save(instalacionPetroleo);
		
		return "";
	}
	
	public static String updatePirataNave(int idPirata, Iterator<Nave_pirata> iterator, PiratahasNaveRepository pirataNaveRepo, PlanetaRepository planetaRepo, PlanetaHasNaveRepository planetaNave)
	{
		ArrayList<Planeta> planetas = new ArrayList<>(planetaRepo.findByPirataId(idPirata));
		ArrayList<Nave_pirata> naves = new ArrayList<>();
		ArrayList<PlanetaHasNave> planetaNaves = new ArrayList<>();
		Nave_pirata aux;
		
		//Comprobacion
		while(iterator.hasNext())
		{
			aux = iterator.next();
			if(!NaveDataValidator.comprobarValorInteger(aux.cantidadF.getValue())) return "Cantidad de " + aux.getNave().getNavenombreNave() +" incorrecta";
			aux.getNave().setCantidadDefecto(Integer.parseInt(aux.cantidadF.getValue()));
			naves.add(aux);
		}
		
		//Guardado
		for(int i = 0; i < naves.size(); i++)
		{
			pirataNaveRepo.save(naves.get(i).getNave());
		}
		
		//Actualizar naves de piratas
		for(int i = 0; i < planetas.size(); i++)
		{
			planetaNaves.clear();
			
			for(int j = 0; j < naves.size(); j++)
			{
				planetaNaves.add(new PlanetaHasNave(planetas.get(i).getCoordenadaX(), planetas.get(i).getCoordenadaY(), planetas.get(i).getSistemanombreSistema(),naves.get(j).getNave().getNavenombreNave(), 0));
			}
			
			planetaNave.saveAll(planetaNaves);
		}
		
		return "";
	}
	
	@Override
	public void enter(ViewChangeEvent event) 
	{
		String[] parameters;
		ArrayList<Nave> naves;
		Nave_pirata aux;
				
		if (!event.getParameters().isEmpty())
		{
			parameters = event.getParameters().split("&");
			if(parameters.length > 1 && parameters[1].equals("Changed")) correctoL.setVisible(true);
			
			naveRepo = ((VaadinUI) UI.getCurrent()).getInterfazNave();
			pirataInstalacionRepo = ((VaadinUI) UI.getCurrent()).getInterfazPirataInstalacion();
			planetaRepo = ((VaadinUI) UI.getCurrent()).getInterfazPlaneta();
			planetaNaveRepo = ((VaadinUI) UI.getCurrent()).getInterfazPlanetaNave();
			idPirata = Integer.parseInt(parameters[0]);
			tituloL.setValue("Pirata LvL " +idPirata);
			
			//Instalaciones
			instalacionMetal = pirataInstalacionRepo.findByPirataidPirata_Instalacionname(idPirata, "Mina de Metal");
			instalacionOro = pirataInstalacionRepo.findByPirataidPirata_Instalacionname(idPirata, "Mina de Oro");
			instalacionPetroleo = pirataInstalacionRepo.findByPirataidPirata_Instalacionname(idPirata, "Plataforma Petrolifera");
		
			layoutMetal = new Instalacion_pirata("Mina de Metal");
			layoutOro = new Instalacion_pirata("Mina de Oro");
			layoutPetroleo  = new Instalacion_pirata("Plataforma Petrolifera");
			
			layoutInstalaciones.addComponent(layoutMetal);
			layoutInstalaciones.addComponent(layoutOro);
			layoutInstalaciones.addComponent(layoutPetroleo);
			
			layoutMetal.nivelF.setValue(instalacionMetal.getNivelDefecto() +"");
			layoutOro.nivelF.setValue(instalacionOro.getNivelDefecto() +"");
			layoutPetroleo.nivelF.setValue(instalacionPetroleo.getNivelDefecto() +"");
			
			//Naves
			naves = new ArrayList<>(naveRepo.findAll());
			layoutNaves.setColumns(3);
			
			if(naves.isEmpty())
			{
				layoutNaves.setRows(1);
				
			}else
			{
				layoutNaves.setRows((int) Math.ceil((double)naves.size()/4) * 4);
			}
			
			for(int i = 0; i < naves.size(); i++)
			{
				aux = new Nave_pirata(naves.get(i).getImage(), naves.get(i).getNombreNave(), idPirata);
				layoutNaves.addComponent(aux);
			}
		}
	}
	
	private void doNavigate(String viewName) 
	{
		UI.getCurrent().getNavigator().navigateTo(viewName);
	}

}
