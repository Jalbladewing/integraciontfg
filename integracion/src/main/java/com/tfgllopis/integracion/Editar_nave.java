package com.tfgllopis.integracion;

import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.UI;
import com.vaadin.ui.Button.ClickEvent;

public class Editar_nave extends Editar_nave_Ventana implements View
{
	@Autowired
	private NaveRepository naveRepo;
	
	@Autowired
	TipoNaveRepository tipoRepo;
	
	@Autowired
	private NavecuestaRecursoRepository naveCuestaRepo;
	
	@Autowired
	private PiratahasDesbloqueoNaveRepository pirataDesbloqueoRepo;
	
	public static String VIEW_NAME = "editarNave";
	private ImageUploader uploader;
	private Nave nave;
	private String imageName, filePath;
		
	public Editar_nave()
	{
		configuracionBotones();
		naveRepo = ((VaadinUI) UI.getCurrent()).getInterfazNave();
		tipoRepo = ((VaadinUI) UI.getCurrent()).getInterfazTipo();
		naveCuestaRepo = ((VaadinUI) UI.getCurrent()).getInterfazNaveCuesta();
		pirataDesbloqueoRepo = ((VaadinUI) UI.getCurrent()).getInterfazPirataDesbloqueo();		
				
		
		
		guardarB.addClickListener(new Button.ClickListener() 
		{
			
			@Override
			public void buttonClick(ClickEvent event) 
			{
				String[] probabilidadesDesbloqueo = new String[layoutDesbloqueo.getComponentCount()];
				
				for(int i = 0; i < layoutDesbloqueo.getComponentCount(); i++)
				{
					probabilidadesDesbloqueo[i] = ((Nave_desbloqueo) layoutDesbloqueo.getComponent(i)).porcentajeF.getValue();
				}
				
				String value = CrudNave.modificarNave(nave.getNombreNave(), uploader.getFilePath() + uploader.getNombreImagen(), tipoNaveCombo.getSelectedItem().get().getNombreTipoNave(), "23", ataqueF.getValue(), saludF.getValue(), escudoF.getValue(), velocidadF.getValue(), agilidadF.getValue(), cargaF.getValue(), oroF.getValue(), metalF.getValue(), petroleoF.getValue(), bloqueadaChckBx.getValue(), probabilidadesDesbloqueo, naveRepo, tipoRepo, naveCuestaRepo, pirataDesbloqueoRepo);
				errorL.setValue(value);
				
				if(value.isEmpty())
				{
					errorL.setVisible(false);
					doNavigate(Editar_nave.VIEW_NAME  + "/" + nombreF.getValue() + "&" + "Changed");
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
				
				doNavigate(Listar_naves.VIEW_NAME);
			}
		});
		
		
		
	}
	
	@Override
	public void enter(ViewChangeEvent event) 
	{
		ArrayList<TipoNave> testo = new ArrayList<>(tipoRepo.findAll());
		ArrayList<PiratahasDesbloqueoNave> piratas;
		tipoNaveCombo.setItems(testo);
		tipoNaveCombo.setSelectedItem(testo.get(0));
		
		String[] parameters;
		if (!event.getParameters().isEmpty())
		{
			parameters = event.getParameters().split("&");
			if(parameters.length > 1 && parameters[1].equals("Changed")) correctoL.setVisible(true);
			naveRepo = ((VaadinUI) UI.getCurrent()).getInterfazNave();
			pirataDesbloqueoRepo = ((VaadinUI) UI.getCurrent()).getInterfazPirataDesbloqueo();
			
			nave = naveRepo.findByNombreNave(parameters[0]).get(0);
			
			nombreF.setValue(nave.getNombreNave());
			tipoNaveCombo.setSelectedItem(nave.getTipoNavenombreTipoNave());
			imagenNave.setSource(nave.getImage().getSource());
			saludF.setValue(nave.getHullNave()+"");
			escudoF.setValue(nave.getEscudoNave()+"");
			velocidadF.setValue(nave.getVelocidadNave()+"");
			agilidadF.setValue(nave.getAgilidadNave()+"");
			ataqueF.setValue(nave.getAtaqueNave()+"");
			cargaF.setValue(nave.getCapacidadCarga()+"");
			oroF.setValue(naveCuestaRepo.findByRecursoname_NavenombreNave(nave.getNombreNave(), "Oro").getCantidadBase()+"");
			metalF.setValue(naveCuestaRepo.findByRecursoname_NavenombreNave(nave.getNombreNave(), "Metal").getCantidadBase()+"");
			petroleoF.setValue(naveCuestaRepo.findByRecursoname_NavenombreNave(nave.getNombreNave(), "Petroleo").getCantidadBase()+"");
			bloqueadaChckBx.setValue(true);
			imageName = nave.getRutaImagenNave().split("VAADIN/")[1];
			filePath = nave.getRutaImagenNave().split("VAADIN/")[0] +"VAADIN/";
			
			uploader = new ImageUploader(imagenNave, subirImagen, errorL, filePath, imageName); 
	        subirImagen.setReceiver(uploader);
	        subirImagen.addSucceededListener(uploader);
	        subirImagen.addStartedListener(uploader);

			if(nave.getBloqueada() == 0)
			{
				bloqueadaChckBx.setValue(false);
				bloqueadaChckBx.setEnabled(false);
			}
			
			piratas = new ArrayList<>(pirataDesbloqueoRepo.findByNavenombreNave(nave.getNombreNave()));
			
			for(int i = 0; i < piratas.size(); i++)
	        {
	        	layoutDesbloqueo.addComponent(new Nave_desbloqueo(piratas.get(i).getPirataidPirata(), piratas.get(i).getProbabilidadDesbloqueo()+""));
	        }
		}
	}
	
	public void configuracionBotones()
	{
		
		caracteristicasB.addClickListener(new Button.ClickListener() 
		{
			
			@Override
			public void buttonClick(ClickEvent event) 
			{
				if(panelCaracteristicas.isVisible())
				{
					caracteristicasB.setIcon(VaadinIcons.ANGLE_RIGHT);
					panelCaracteristicas.setVisible(false);
				}else
				{
					caracteristicasB.setIcon(VaadinIcons.ANGLE_DOWN);
					panelCaracteristicas.setVisible(true);
				}
				
				
			}
		});
		
		costeB.addClickListener(new Button.ClickListener() 
		{
			
			@Override
			public void buttonClick(ClickEvent event) 
			{
				if(panelCoste.isVisible())
				{
					costeB.setIcon(VaadinIcons.ANGLE_RIGHT);
					panelCoste.setVisible(false);
				}else
				{
					costeB.setIcon(VaadinIcons.ANGLE_DOWN);
					panelCoste.setVisible(true);
				}
				
			}
		});
		
		bloqueadaChckBx.addValueChangeListener(event ->
	    panelPiratas.setVisible(bloqueadaChckBx.getValue()));
	}
	
	private void doNavigate(String viewName) 
	{
		UI.getCurrent().getNavigator().navigateTo(viewName);
	}
}
