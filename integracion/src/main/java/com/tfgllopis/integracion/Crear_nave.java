package com.tfgllopis.integracion;

import java.io.File;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.data.HasValue.ValueChangeEvent;
import com.vaadin.data.HasValue.ValueChangeListener;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.VaadinService;
import com.vaadin.server.VaadinServlet;
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
		String filePath = new File("").getAbsolutePath() + "/images/";
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
				
				String value = Crear_nave.crearNave(nombreF.getValue(), uploader.getNombreImagen(), tipoNaveCombo.getSelectedItem().get().getNombreTipoNave(), "23", ataqueF.getValue(), saludF.getValue(), escudoF.getValue(), velocidadF.getValue(), agilidadF.getValue(), cargaF.getValue(), oroF.getValue(), metalF.getValue(), petroleoF.getValue(), bloqueadaChckBx.getValue(), probabilidadesDesbloqueo, naveRepo, tipoRepo, naveCuestaRepo, pirataDesbloqueoRepo);
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
	
	public static String crearNave(String nombre, String imagen, String tipoNave, String segundosConstruccion, String ataqueNave, String hullNave, String escudoNave, String velocidadNave, String agilidadNave, String capacidadCarga, String costeOro, String costeMetal, String costePetroleo, boolean bloqueada, String[] probabilidadesDesbloqueo, NaveRepository repo, TipoNaveRepository tipoNaveRepo, NavecuestaRecursoRepository naveCuestaRepo, PiratahasDesbloqueoNaveRepository pirataRepo)
	{
		Nave nave;
		NavecuestaRecurso naveCuesta;
		Short bloqueo = (short) 0;
		if(!NaveDataValidator.comprobarNombre(nombre)) return "Nombre inválido";
		if(!NaveDataValidator.comprobarImagen(imagen)) return "Imagen inválida";
		if(!NaveDataValidator.comprobarValorInteger(segundosConstruccion)) return "Tiempo de construcción no válido";
		if(!NaveDataValidator.comprobarValorFloat(ataqueNave)) return "Valor de ataque no válido";
		if(!NaveDataValidator.comprobarValorFloat(hullNave)) return "Valor de salud no válido";
		if(!NaveDataValidator.comprobarValorFloat(escudoNave)) return "Valor de escudo no válido";
		if(!NaveDataValidator.comprobarValorFloat(velocidadNave)) return "Valor de velocidad no válido";
		if(!NaveDataValidator.comprobarValorFloat(agilidadNave)) return "Valor de agilidad no válido";
		if(!NaveDataValidator.comprobarValorFloat(capacidadCarga)) return "Valor de carga no válido";
		if(!NaveDataValidator.comprobarValorInteger(costeOro)) return "La cantidad de oro es incorrecta";
		if(!NaveDataValidator.comprobarValorInteger(costeMetal)) return "La cantidad de metal es incorrecta";
		if(!NaveDataValidator.comprobarValorInteger(costePetroleo)) return "La cantidad de petróleo es incorrecta";
		if(!NaveDataValidator.comprobarNaveBD(nombre, repo)) return "La nave ya existe";
		if(Float.parseFloat(velocidadNave.replaceAll(",",".")) > 100) return "La velocidad máxima es 100";
		
		for(int i = 0; i < probabilidadesDesbloqueo.length; i++)
		{
			if(!NaveDataValidator.comprobarValorFloat(probabilidadesDesbloqueo[i])) return "La " + (i+1) +"ª probabilidad es inválida";
		}
		
		if(bloqueada) bloqueo = (short) 1;
		
		nave = new Nave(nombre, imagen, Float.parseFloat(ataqueNave.replaceAll(",",".")), Float.parseFloat(hullNave.replaceAll(",",".")), Float.parseFloat(escudoNave.replaceAll(",",".")), Float.parseFloat(velocidadNave.replaceAll(",",".")), Float.parseFloat(agilidadNave.replaceAll(",",".")), Float.parseFloat(capacidadCarga.replaceAll(",",".")), Integer.parseInt(segundosConstruccion.replaceAll(",",".")), bloqueo);
		nave.setTipoNavenombreTipoNave(tipoNaveRepo.findByNombreTipoNave(tipoNave).get(0));
		nave.guardarNave(repo);
		
		naveCuesta = new NavecuestaRecurso(nombre, "Oro", "Mina de Oro", Integer.parseInt(costeOro.replaceAll(",",".")));
		naveCuesta.guardarNaveCuestaRecurso(naveCuestaRepo);
		naveCuesta = new NavecuestaRecurso(nombre, "Metal", "Mina de Metal", Integer.parseInt(costeMetal.replaceAll(",",".")));
		naveCuesta.guardarNaveCuestaRecurso(naveCuestaRepo);
		naveCuesta = new NavecuestaRecurso(nombre, "Petroleo", "Plataforma Petrolifera", Integer.parseInt(costePetroleo.replaceAll(",",".")));
		naveCuesta.guardarNaveCuestaRecurso(naveCuestaRepo);
		
		if(bloqueada)
		{
			for(int i = 0; i < probabilidadesDesbloqueo.length; i++)
			{
				pirataRepo.save(new PiratahasDesbloqueoNave(i+1, nave.getNombreNave(), Float.parseFloat(probabilidadesDesbloqueo[i].replaceAll(",","."))));
			}
		}
		
		return "";
	}
}
