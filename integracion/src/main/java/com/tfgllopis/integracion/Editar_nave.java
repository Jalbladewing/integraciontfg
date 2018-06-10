package com.tfgllopis.integracion;

import java.io.File;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.VaadinServlet;
import com.vaadin.server.Sizeable.Unit;
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
				
				String value = Editar_nave.modificarNave(nave.getNombreNave(), uploader.getNombreImagen(), tipoNaveCombo.getSelectedItem().get().getNombreTipoNave(), "23", ataqueF.getValue(), saludF.getValue(), escudoF.getValue(), velocidadF.getValue(), agilidadF.getValue(), cargaF.getValue(), oroF.getValue(), metalF.getValue(), petroleoF.getValue(), bloqueadaChckBx.getValue(), probabilidadesDesbloqueo, naveRepo, tipoRepo, naveCuestaRepo, pirataDesbloqueoRepo);
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
			imagenNave.setWidth(150, Unit.PIXELS);
			imagenNave.setHeight(100, Unit.PIXELS);
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
			imageName = nave.getRutaImagenNave();
			filePath = new File("").getAbsolutePath() + "/images/";
			
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
	
	public static String modificarNave(String nombre, String imagen, String tipoNave, String segundosConstruccion, String ataqueNave, String hullNave, String escudoNave, String velocidadNave,String agilidadNave, String capacidadCarga, String costeOro, String costeMetal, String costePetroleo, boolean bloqueada, String[] probabilidadesDesbloqueo, NaveRepository repo, TipoNaveRepository tipoNaveRepo, NavecuestaRecursoRepository naveCuestaRepo, PiratahasDesbloqueoNaveRepository pirataRepo)
	{
		Nave nave = Nave.cargarNave(nombre, repo);
		NavecuestaRecurso naveCuestaOro;
		NavecuestaRecurso naveCuestaMetal;
		NavecuestaRecurso naveCuestaPetroleo;
		ArrayList<PiratahasDesbloqueoNave> porcentajesDesbloqueo;
		
		Short bloqueo = (short) 0;
		if(!NaveDataValidator.comprobarImagen(imagen)) return "Imagen inválida";
		if(!NaveDataValidator.comprobarValorInteger(segundosConstruccion)) return "Tiempo de construcción no válido";
		if(!NaveDataValidator.comprobarValorFloat(ataqueNave)) return "Valor de ataque no válido";
		if(!NaveDataValidator.comprobarValorFloat(hullNave)) return "Valor de hull no válido";
		if(!NaveDataValidator.comprobarValorFloat(escudoNave)) return "Valor de escudo no válido";
		if(!NaveDataValidator.comprobarValorFloat(velocidadNave)) return "Valor de velocidad no válido";
		if(!NaveDataValidator.comprobarValorFloat(agilidadNave)) return "Valor de agilidad no válido";
		if(!NaveDataValidator.comprobarValorFloat(capacidadCarga)) return "Valor de carga no válido";
		if(!NaveDataValidator.comprobarValorInteger(costeOro)) return "La cantidad de oro es incorrecta";
		if(!NaveDataValidator.comprobarValorInteger(costeMetal)) return "La cantidad de metal es incorrecta";
		if(!NaveDataValidator.comprobarValorInteger(costePetroleo)) return "La cantidad de petróleo es incorrecta";
				
		for(int i = 0; i < probabilidadesDesbloqueo.length; i++)
		{
			if(!NaveDataValidator.comprobarValorFloat(probabilidadesDesbloqueo[i])) return "La " + (i+1) +"ª probabilidad es inválida";
		}
		
		if(bloqueada) bloqueo = (short) 1;
		
		nave.setRutaImagenNave(imagen);
		nave.setSegundosConstruccion(Integer.parseInt(segundosConstruccion.replaceAll(",",".")));
		nave.setAtaqueNave(Float.parseFloat(ataqueNave.replaceAll(",",".")));
		nave.setHullNave(Float.parseFloat(hullNave.replaceAll(",",".")));
		nave.setEscudoNave(Float.parseFloat(escudoNave.replaceAll(",",".")));
		nave.setVelocidadNave(Float.parseFloat(velocidadNave.replaceAll(",",".")));
		nave.setAgilidadNave(Float.parseFloat(agilidadNave.replaceAll(",",".")));
		nave.setCapacidadCarga(Float.parseFloat(capacidadCarga.replaceAll(",",".")));
		nave.setBloqueada(bloqueo);
		nave.setTipoNavenombreTipoNave(tipoNaveRepo.findByNombreTipoNave(tipoNave).get(0));
		nave.guardarNave(repo);
		
		naveCuestaOro = NavecuestaRecurso.cargarNaveCuestaRecurso("Oro", nombre, naveCuestaRepo);
		naveCuestaOro.setRecursoname("Oro");
		naveCuestaOro.setNavenombreNave(nombre);
		naveCuestaOro.setRecursoInstalacionname("Mina de Oro");
		naveCuestaOro.setCantidadBase(Integer.parseInt(costeOro.replaceAll(",",".")));
		naveCuestaOro.guardarNaveCuestaRecurso(naveCuestaRepo);
		
		naveCuestaMetal = NavecuestaRecurso.cargarNaveCuestaRecurso("Metal", nombre, naveCuestaRepo);
		naveCuestaMetal.setRecursoname("Metal");
		naveCuestaMetal.setNavenombreNave(nombre);
		naveCuestaMetal.setRecursoInstalacionname("Mina de Metal");
		naveCuestaMetal.setCantidadBase(Integer.parseInt(costeMetal.replaceAll(",",".")));
		naveCuestaMetal.guardarNaveCuestaRecurso(naveCuestaRepo);
		
		naveCuestaPetroleo = NavecuestaRecurso.cargarNaveCuestaRecurso("Petroleo", nombre, naveCuestaRepo);
		naveCuestaPetroleo.setRecursoname("Petroleo");
		naveCuestaPetroleo.setNavenombreNave(nombre);
		naveCuestaPetroleo.setRecursoInstalacionname("Plataforma Petrolifera");
		naveCuestaPetroleo.setCantidadBase(Integer.parseInt(costePetroleo.replaceAll(",",".")));
		naveCuestaPetroleo.guardarNaveCuestaRecurso(naveCuestaRepo);
		
		if(bloqueada)
		{
			porcentajesDesbloqueo = new ArrayList<>(PiratahasDesbloqueoNave.cargarDesbloqueosNave(nombre, pirataRepo));
			for(int i = 0; i < probabilidadesDesbloqueo.length; i++)
			{
				porcentajesDesbloqueo.get(i).setNavenombreNave(nombre);
				porcentajesDesbloqueo.get(i).setProbabilidadDesbloqueo( Float.parseFloat(probabilidadesDesbloqueo[i].replaceAll(",",".")));
				pirataRepo.save(porcentajesDesbloqueo.get(i));
			}
		}
		
		return "";
	}
}
