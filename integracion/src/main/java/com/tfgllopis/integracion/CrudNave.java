package com.tfgllopis.integracion;

import java.util.ArrayList;
import java.util.Date;

import com.vaadin.server.VaadinService;
import com.vaadin.ui.UI;

public class CrudNave
{	
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
