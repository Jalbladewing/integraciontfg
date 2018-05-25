package com.tfgllopis.integracion;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.data.Binder;
import com.vaadin.data.HasValue.ValueChangeEvent;
import com.vaadin.data.HasValue.ValueChangeListener;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.UI;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;

public class Recursos extends Recursos_Ventana implements View
{
	@Autowired
	private InstalacionRepository instalacionRepo;
	
	public static String VIEW_NAME = "recursos";
		
	public Recursos()
	{
		Instalacion instalacionMetal, instalacionOro, instalacionPetroleo;
		Instalacion_tecnico layoutMetal, layoutOro, layoutPetroleo;
		instalacionRepo = ((VaadinUI) UI.getCurrent()).getInterfazInstalacion();
		
		instalacionMetal = instalacionRepo.findByName("Mina de Metal");
		instalacionOro = instalacionRepo.findByName("Mina de Oro");
		instalacionPetroleo = instalacionRepo.findByName("Plataforma Petrolifera");
		
		layoutMetal = new Instalacion_tecnico("Mina de Metal", this);
		layoutOro = new Instalacion_tecnico("Mina de Oro", this);
		layoutPetroleo  = new Instalacion_tecnico("Plataforma Petrolifera", this);
		
		layoutInstalaciones.addComponent(layoutMetal);
		layoutInstalaciones.addComponent(layoutOro);
		layoutInstalaciones.addComponent(layoutPetroleo);
		
		layoutMetal.tasaInstalacionF.setValue(instalacionMetal.getGeneracionBase()+"");
		layoutOro.tasaInstalacionF.setValue(instalacionOro.getGeneracionBase()+"");
		layoutPetroleo.tasaInstalacionF.setValue(instalacionPetroleo.getGeneracionBase()+"");
		
		setRecursosInfo(instalacionMetal.getName(), instalacionMetal.getGeneracionBase());
		setRecursosInfo(instalacionOro.getName(), instalacionOro.getGeneracionBase());
		setRecursosInfo(instalacionPetroleo.getName(), instalacionPetroleo.getGeneracionBase());
		
		generacionCombo.setSelectedItem("Por Nivel");
		
		guardarB.addClickListener(new Button.ClickListener() 
		{
			
			@Override
			public void buttonClick(ClickEvent event) 
			{
				String value = Recursos.updateInstalacion(instalacionMetal, instalacionOro, instalacionPetroleo, layoutMetal.tasaInstalacionF.getValue(), layoutOro.tasaInstalacionF.getValue(), layoutPetroleo.tasaInstalacionF.getValue(), instalacionRepo);
				errorL.setValue(value);
				
				if(value.isEmpty())
				{
					errorL.setVisible(false);
					doNavigate(Recursos.VIEW_NAME + "/" + "creado");
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
				doNavigate(Pagina_principal.VIEW_NAME);
				
			}
		});
		
		
	}
	
	public void setRecursosInfo(String tipoInstalacion, float generacionBase)
	{
		if(tipoInstalacion.equals("Mina de Metal"))
		{
			metalNiv1.setValue(generacionBase + "");
			metalNiv2.setValue(generacionBase*2 + "");
			metalNiv3.setValue(generacionBase*3 + "");
			
		}else if(tipoInstalacion.equals("Mina de Oro"))
		{
			oroNiv1.setValue(generacionBase + "");
			oroNiv2.setValue(generacionBase*2 + "");
			oroNiv3.setValue(generacionBase*3 + "");
			
		}else
		{
			petroleoNiv1.setValue(generacionBase + "");
			petroleoNiv2.setValue(generacionBase*2 + "");
			petroleoNiv3.setValue(generacionBase*3 + "");
		}
	}
	
	public static String updateInstalacion(Instalacion instalacionMetal, Instalacion instalacionOro, Instalacion instalacionPetroleo, String generacionMetal, String generacionOro, String generacionPetroleo, InstalacionRepository instalacionRepo)
	{
		if(!NaveDataValidator.comprobarValorFloat(generacionMetal)) return "La generación de metal es incorrecta";
		if(!NaveDataValidator.comprobarValorFloat(generacionOro)) return "La generación de oro es incorrecta";
		if(!NaveDataValidator.comprobarValorFloat(generacionPetroleo)) return "La generación de petroleo es incorrecta";
		
		instalacionMetal.setGeneracionBase(Float.parseFloat(generacionMetal.replaceAll(",",".")));
		instalacionOro.setGeneracionBase(Float.parseFloat(generacionOro.replaceAll(",",".")));
		instalacionPetroleo.setGeneracionBase(Float.parseFloat(generacionPetroleo.replaceAll(",",".")));
		
		instalacionRepo.save(instalacionMetal);
		instalacionRepo.save(instalacionOro);
		instalacionRepo.save(instalacionPetroleo);
		
		return "";
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
}
