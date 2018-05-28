package com.tfgllopis.integracion;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.data.HasValue.ValueChangeEvent;
import com.vaadin.data.HasValue.ValueChangeListener;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.UI;

public class Crear_nave extends Crear_nave_Ventana implements View
{
	@Autowired
	private NaveRepository naveRepo;
	
	@Autowired
	TipoNaveRepository tipoRepo;
	
	@Autowired
	private NavecuestaRecursoRepository naveCuestaRepo;
	
	@Autowired
	private PiratahasDesbloqueoNaveRepository pirataDesbloqueoRepo;
	
	@Autowired PirataRepository pirataRepo;
	
	public static String VIEW_NAME = "crearNave";
		
	public Crear_nave()
	{
		String filePath = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath() + "/VAADIN/";
		ArrayList<Pirata> piratas;
		ArrayList<TipoNave> tiposNave;
		configuracionBotones();
		naveRepo = ((VaadinUI) UI.getCurrent()).getInterfazNave();
		tipoRepo = ((VaadinUI) UI.getCurrent()).getInterfazTipo();
		naveCuestaRepo = ((VaadinUI) UI.getCurrent()).getInterfazNaveCuesta();
		pirataDesbloqueoRepo = ((VaadinUI) UI.getCurrent()).getInterfazPirataDesbloqueo();		
		pirataRepo = ((VaadinUI) UI.getCurrent()).getInterfazPirata();
		
		tiposNave = new ArrayList<>(tipoRepo.findAll());
		tipoNaveCombo.setItems(tiposNave);
		tipoNaveCombo.setSelectedItem(tiposNave.get(0));
		
		final ImageUploader uploader = new ImageUploader(imagenNave, subirImagen, errorL, "", ""); 
        subirImagen.setReceiver(uploader);
        subirImagen.addSucceededListener(uploader);
        subirImagen.addStartedListener(uploader);
        
        piratas = new ArrayList<>(pirataRepo.findAll());
        
        for(int i = 0; i < piratas.size(); i++)
        {
        	layoutDesbloqueo.addComponent(new Nave_desbloqueo(piratas.get(i).getIdPirata(), "100"));
        }
		
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
				
				String value = CrudNave.crearNave(nombreF.getValue(), uploader.getNombreImagen(), tipoNaveCombo.getSelectedItem().get().getNombreTipoNave(), "23", ataqueF.getValue(), saludF.getValue(), escudoF.getValue(), velocidadF.getValue(), agilidadF.getValue(), cargaF.getValue(), oroF.getValue(), metalF.getValue(), petroleoF.getValue(), bloqueadaChckBx.getValue(), probabilidadesDesbloqueo, naveRepo, tipoRepo, naveCuestaRepo, pirataDesbloqueoRepo);
				errorL.setValue(value);
				
				if(value.isEmpty())
				{
					errorL.setVisible(false);
					doNavigate(Crear_nave.VIEW_NAME + "/" + "creado");
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
	
	@Override
	public void enter(ViewChangeEvent event) 
	{
		 if (!event.getParameters().isEmpty()) correctoL.setVisible(true);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked", "serial" })
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
		
		bloqueadaChckBx.addValueChangeListener(new ValueChangeListener() 
		{
			@Override
			public void valueChange(ValueChangeEvent event) 
			{
				panelPiratas.setVisible(bloqueadaChckBx.getValue());
				
			}
        });
	    
	}
	
	private void doNavigate(String viewName) 
	{
		UI.getCurrent().getNavigator().navigateTo(viewName);
	}
}
