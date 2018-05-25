package com.tfgllopis.integracion;

import java.util.ArrayList;
import java.util.Calendar;
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
	
	public static String construirNave(Nave nave, Usuario usuario, String cantidad, UsuarioconstruyeNaveRepository construyeRepo, PlanetaRepository planetaRepo, PlanetahasRecursoRepository planetaRecursoRepo, NaveRepository naveRepo, NavecuestaRecursoRepository naveCuestaRepo, UsuarioHasNaveRepository usuarioNaveRepo)
	{
		Date fechaFinConstruccion;
		Calendar calendar = Calendar.getInstance();
		Planeta planeta = planetaRepo.findByUsuarioUsername(usuario);
		PlanetahasRecurso cantidadMetal = planetaRecursoRepo.findByPlanetaRecurso(planeta.getCoordenadaY(), planeta.getCoordenadaY(), planeta.getSistemanombreSistema(), "Metal");
		PlanetahasRecurso cantidadOro = planetaRecursoRepo.findByPlanetaRecurso(planeta.getCoordenadaY(), planeta.getCoordenadaY(), planeta.getSistemanombreSistema(), "Oro");
		PlanetahasRecurso cantidadPetroleo = planetaRecursoRepo.findByPlanetaRecurso(planeta.getCoordenadaY(), planeta.getCoordenadaY(), planeta.getSistemanombreSistema(), "Petroleo");
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
		
		cantidadMetal = planetaRecursoRepo.findByPlanetaRecurso(planeta.getCoordenadaY(), planeta.getCoordenadaY(), planeta.getSistemanombreSistema(), "Metal");
		cantidadMetal.setCantidad(cantidadMetal.getCantidad()-costeMetal);
		planetaRecursoRepo.save(cantidadMetal);
		
		cantidadOro = planetaRecursoRepo.findByPlanetaRecurso(planeta.getCoordenadaY(), planeta.getCoordenadaY(), planeta.getSistemanombreSistema(), "Oro");
		cantidadOro.setCantidad(cantidadOro.getCantidad()-costeOro);
		planetaRecursoRepo.save(cantidadOro);	
		
		cantidadPetroleo = planetaRecursoRepo.findByPlanetaRecurso(planeta.getCoordenadaY(), planeta.getCoordenadaY(), planeta.getSistemanombreSistema(), "Petroleo");
		cantidadPetroleo.setCantidad(cantidadPetroleo.getCantidad()-costePetroleo);
		planetaRecursoRepo.save(cantidadPetroleo);	
		
		naveConstruida = new UsuarioconstruyeNave(usuario.getUsername(), nave.getNombreNave(), Integer.parseInt(cantidad), fechaFinConstruccion);
		naveConstruida.setNave(nave);
		
		construyeRepo.save(naveConstruida);
		
		return "";
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
			planetaNaves.add(new PlanetaHasNave(0, 0, "Atlas", nombreNave, cantidad));
		}else
		{
			usuarioNaves.get(0).setCantidad(usuarioNaves.get(0).getCantidad() + cantidad);
			planetaNaves.get(0).setCantidad(planetaNaves.get(0).getCantidad() + cantidad);
		}
		
		usuarioNaveRepo.save(usuarioNaves.get(0));
		planetaNaveRepo.save(planetaNaves.get(0));
	}

}
