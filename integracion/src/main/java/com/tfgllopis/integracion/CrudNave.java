package com.tfgllopis.integracion;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.vaadin.server.VaadinService;
import com.vaadin.ui.UI;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

import javax.persistence.EntityManager;
import org.springframework.transaction.annotation.Transactional;

import org.hibernate.Session;
import org.hibernate.jdbc.Work;

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
			planetaNaves.add(new PlanetaHasNave(planeta.getCoordenadaX(), planeta.getCoordenadaY(), planeta.getSistemanombreSistema(), nombreNave, cantidad));
		}else
		{
			usuarioNaves.get(0).setCantidad(usuarioNaves.get(0).getCantidad() + cantidad);
			planetaNaves.get(0).setCantidad(planetaNaves.get(0).getCantidad() + cantidad);
		}
		
		usuarioNaveRepo.save(usuarioNaves.get(0));
		planetaNaveRepo.save(planetaNaves.get(0));
	}
	
	public static String confirmarAtaque(Iterator<Nave_ataque> iterator, Date fechaLlegada, Date fechaVuelta, Usuario usuario, Planeta planetaOrigen, Planeta planetaDestino, MovimientoRepository movimientoRepo, MovimientohasNaveRepository movimientoNaveRepo, PlanetaHasNaveRepository planetaNaveRepo, EntityManager em)
	{
		ArrayList<Nave_ataque> naves = new ArrayList<>();
		ArrayList<MovimientohasNave> navesMovimiento = new ArrayList<>();
		ArrayList<PlanetaHasNave> navesPlaneta = new ArrayList<>();
		Nave_ataque aux;
		PlanetaHasNave auxPlaneta;
		Movimiento movimiento;
		String setNaves = "";
		int cantidadTotal = 0;
		
		//Comprobacion
		while(iterator.hasNext())
		{
			aux = iterator.next();
			if(!NaveDataValidator.comprobarValorInteger(aux.cantidadF.getValue())) return "Cantidad de " + aux.getNave().getNavenombreNave() +" incorrecta";
			if(Integer.parseInt(aux.cantidadF.getValue()) > aux.getNave().getCantidad()) return "No puedes enviar más naves de las que tienes";
			cantidadTotal += Integer.parseInt(aux.cantidadF.getValue());
				
			naves.add(aux);
		}
		
		if(cantidadTotal <= 0) return "Debes de enviar alguna nave";
		
		movimiento = new Movimiento(fechaLlegada, new Date(), (short) 0);
		movimiento.setPlaneta(planetaDestino);
		movimiento.setUsuariousername(usuario);
		movimiento = movimientoRepo.save(movimiento);
		
		for(int i = 0; i < naves.size(); i++)
		{			
			if(Integer.parseInt(naves.get(i).cantidadF.getValue()) > 0)
			{
				navesMovimiento.add(new MovimientohasNave(movimiento.getIdMovimiento(), naves.get(i).getNave().getNavenombreNave(), Integer.parseInt(naves.get(i).cantidadF.getValue())));
				auxPlaneta = planetaNaveRepo.findByNavenombreNavePlaneta(naves.get(i).getNave().getNavenombreNave(), planetaOrigen.getCoordenadaX(), planetaOrigen.getCoordenadaX(), planetaOrigen.getSistemanombreSistema()).get(0);
				auxPlaneta.setCantidad(auxPlaneta.getCantidad() - Integer.parseInt(naves.get(i).cantidadF.getValue()));
				navesPlaneta.add(auxPlaneta);
				setNaves += "UPDATE Planeta_has_Nave\nSET cantidad = cantidad+" +naves.get(i).getNave().getCantidad()
						+"\nWHERE Nave_NombreNave = '" +naves.get(i).getNave().getNavenombreNave() +"';\n";
			}
		}
		
		movimientoNaveRepo.saveAll(navesMovimiento);
		planetaNaveRepo.saveAll(navesPlaneta);
		
		//Crear el cronjob tocho
		createEventScheduler(em, new Timestamp(fechaLlegada.getTime()), new Timestamp(fechaVuelta.getTime()), usuario, planetaOrigen, planetaDestino, movimiento, setNaves);
		((VaadinUI) UI.getCurrent()).setMovimiento(movimiento);
		return "";
	}
	
	public static double calcularDistancia(Planeta planetaOrigen, Planeta planetaDestino, Iterator<Nave_ataque> iterator)
	{
		Nave_ataque aux;
		int cantidadTotal = 0;
		double distanciaSistemaX = Math.pow(planetaDestino.getSistema().getCoordenadaX() - planetaOrigen.getSistema().getCoordenadaX(), 2);
		double distanciaSistemaY = Math.pow(planetaDestino.getSistema().getCoordenadaY() - planetaOrigen.getSistema().getCoordenadaY(), 2);
		double distanciaSistemas = Math.sqrt(distanciaSistemaX + distanciaSistemaY);
		double distanciaPlanetaX = Math.pow(planetaDestino.getCoordenadaX() - planetaOrigen.getCoordenadaX(), 2);
		double distanciaPlanetaY = Math.pow(planetaDestino.getCoordenadaY() - planetaOrigen.getCoordenadaY(), 2);
		double distanciaPlanetas = Math.sqrt(distanciaPlanetaX + distanciaPlanetaY);
		if(distanciaSistemas > 0) distanciaPlanetas += 10;
		distanciaPlanetas *= distanciaSistemas + 2;
		float velocidadMinima = 100;
		double tiempoLlegada;
		
		while(iterator.hasNext())
		{
			aux = iterator.next();
			if(!NaveDataValidator.comprobarValorInteger(aux.cantidadF.getValue()) || Integer.parseInt(aux.cantidadF.getValue()) <= 0) continue;
			if(aux.getNave().getNave().getVelocidadNave() < velocidadMinima) velocidadMinima = aux.getNave().getNave().getVelocidadNave();
			cantidadTotal += Integer.parseInt(aux.cantidadF.getValue());
		}
		
		if(cantidadTotal <= 0) return 0;
		
		tiempoLlegada = distanciaPlanetas/(velocidadMinima/100);
		
		return tiempoLlegada;		
	}
	
	public static Date calcularFechaViaje(double tiempoLlegada)
	{
		Calendar calendar;
		int minutos, segundos;
		
		minutos = (int) tiempoLlegada;
		segundos = (int) ((tiempoLlegada - minutos) * 100);
		
		calendar = Calendar.getInstance();
		calendar.add(Calendar.MINUTE, minutos);
		calendar.add(Calendar.SECOND, segundos);
		
		return calendar.getTime();	
	}
	
	@Transactional
	public static void cancelarAtaque(EntityManager em, Planeta planetaOrigen, Movimiento antiguoMovimiento, MovimientoRepository movimientoRepo, MovimientohasNaveRepository movimientoNaveRepo)
	{
		Movimiento movimiento;
		List<MovimientohasNave> naves = movimientoNaveRepo.findByMovimientoidMovimiento(antiguoMovimiento.getIdMovimiento());
		ArrayList<MovimientohasNave> nuevoNavesMovimiento = new ArrayList<>();
		
		Date fechaAhora = new Date();
		Timestamp fechaEnvio = new Timestamp(fechaAhora.getTime());
		Calendar nuevaFechaC = Calendar.getInstance();
		int seconds = (int) ((fechaEnvio.getTime() - antiguoMovimiento.getTiempoEnvio().getTime())/1000);
		nuevaFechaC.add(Calendar.SECOND, seconds);
		Timestamp nuevaFecha = new Timestamp(nuevaFechaC.getTime().getTime());

		em = em.getEntityManagerFactory().createEntityManager();
		em.getTransaction().begin();
		Session session = em.unwrap(Session.class);
		String sentencia = "ALTER EVENT movimiento_" +antiguoMovimiento.getIdMovimiento() +"_ships ON SCHEDULE AT ? ;";
		String eventoVuelta = "DROP EVENT movimiento_" +antiguoMovimiento.getIdMovimiento() +"_return;";
		String cancelarEventoBatalla = "DROP EVENT movimiento_" +antiguoMovimiento.getIdMovimiento() + "_battleShips;";
		
		movimiento = new Movimiento(nuevaFecha, fechaEnvio, (short) 0);
		movimiento.setUsuariousername(planetaOrigen.getUsuariousername());
		movimiento.setPlaneta(planetaOrigen);
		movimiento = movimientoRepo.save(movimiento);
		
		antiguoMovimiento.cancelarMovimiento();
		movimientoRepo.save(antiguoMovimiento);
		
		for(int i = 0; i < naves.size(); i++)
		{
			nuevoNavesMovimiento.add(new MovimientohasNave(movimiento.getIdMovimiento(), naves.get(i).getNavenombreNave(), naves.get(i).getCantidad()));
		}
		
		movimientoNaveRepo.saveAll(nuevoNavesMovimiento);
		
		session.doWork(new Work()
		{

			@Override
			public void execute(Connection conn) throws SQLException 
			{
				PreparedStatement stmt = null;
				
				try
				{		
					stmt = conn.prepareStatement(sentencia);
					stmt.setInt(1, 0);
					stmt.setTimestamp(1, nuevaFecha);
					stmt.executeUpdate();
					
					stmt = conn.prepareStatement(eventoVuelta);
					stmt.executeUpdate();
					
					stmt = conn.prepareStatement(cancelarEventoBatalla);
					stmt.executeUpdate();
					
				}catch(Exception e)
				{
					System.out.println(e);
				}finally
				{
					stmt.close();

				}
						
			}
			
		});
		
		em.getTransaction().commit();
		session.close();
	}
	
	@Transactional
	private static void createEventScheduler(EntityManager em, Timestamp fechaLlegada, Timestamp fechaVuelta, Usuario usuario, Planeta planetaOrigen, Planeta planetaDestino, Movimiento movimiento, String setNaves)
	{
		//Comprobar si el usuario del destino es null y ponerle un valor para luego el string
		String usuarioDestino = null;
		int idPirata = -1;
		int idBatalla = -1;
		if(planetaDestino.getUsuariousername() != null) usuarioDestino = "'" +planetaDestino.getUsuariousername().getUsername() +"'";
		if(planetaDestino.getPirataidPirata() != null) idPirata = planetaDestino.getPirataidPirata().getIdPirata();
		if(planetaDestino.getInformeBatallaidBatalla() != null) idBatalla = planetaDestino.getInformeBatallaidBatalla().getIdBatalla();
		
		em = em.getEntityManagerFactory().createEntityManager();
		em.getTransaction().begin();
		Session session = em.unwrap(Session.class);
		String sentencia = "CREATE EVENT IF NOT EXISTS movimiento_" +movimiento.getIdMovimiento() +"_return" +"\nON SCHEDULE AT '" +fechaLlegada +"'\nDO BEGIN\n"
				+ "DECLARE done INT DEFAULT FALSE;\n"
				+ "DECLARE idMov INT;\n"
				+ "DECLARE v1 INT DEFAULT 0;\n"
				+ "DECLARE victoria BOOLEAN DEFAULT FALSE;\n"
				+ "DECLARE nom Varchar(45);\n"
				+ "DECLARE randomNumber INT DEFAULT 0;\n"
				+ "DECLARE cantidad_aux INT;\n"
				+ "DECLARE cantidad_enviada INT;\n"
				+ "DECLARE atAl FLOAT;\n"
				+ "DECLARE nomEnem Varchar(45);\n"
				+ "DECLARE atEnem FLOAT;\n"
				+ "DECLARE shieldEnem FLOAT;\n"
				+ "DECLARE hullEnem FLOAT;\n"
				+ "DECLARE agEnem FLOAT;\n"
				+ "DECLARE cantEnem INT;\n"
				+ "DECLARE atOne FLOAT;\n"
				+ "DECLARE shieldOne FLOAT;\n"
				+ "DECLARE hullOne FLOAT;\n"
				+ "DECLARE agOne FLOAT;\n"
				+ "DECLARE navesRestantes INT;\n"
				+ "DECLARE idBat INT;\n"
				+ "DECLARE idMen INT;\n"
				+ "DECLARE metalObtenido INT;\n"
				+ "DECLARE oroObtenido INT;\n"
				+ "DECLARE petroleoObtenido INT;\n"
				+ "DECLARE offsetRecursos INT;\n"
				+ "DECLARE totalCargo INT DEFAULT 0;\n"
				+ "DECLARE naveDesbloqueada Varchar(45);\n"
				+ "DECLARE naveYaDesbloqueada INT;\n"
				+ "DECLARE randomNumberFloat FLOAT;\n"
				+ "DECLARE tiempoUltimoAtaque TIMESTAMP;\n"
				+ "DECLARE horasUltimoAtaque INT;\n"
				+ "DECLARE atacanteNom CURSOR FOR SELECT Nave_nombreNave "
					+ "FROM Movimiento_has_Nave "
					+ "WHERE Movimiento_idMovimiento =" +movimiento.getIdMovimiento() +";\n"
				+ "DECLARE defensorNom CURSOR FOR SELECT Nave_nombreNave "
					+ "FROM Planeta_has_Nave "
					+ "WHERE Planeta_coordenadaX =" +planetaDestino.getCoordenadaX() 
					+" AND Planeta_coordenadaY=" +planetaDestino.getCoordenadaY() +";\n"
				+ "DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;\n"
				+ "CREATE TEMPORARY TABLE IF NOT EXISTS NaveCombate ("
					+ "nombreNave VARCHAR(45) NOT NULL,"
					+ "ataqueNave FLOAT NOT NULL,hullNave FLOAT NOT NULL,"
					+ "escudoNave FLOAT NOT NULL,velocidadNave FLOAT NOT NULL,"
					+ "agilidadNave FLOAT NOT NULL,"
					+ "capacidadCarga FLOAT NOT NULL,"
					+ "cantidad INT,"
					+ "PRIMARY KEY (nombreNave),"
					+ "UNIQUE INDEX nombreNave_UNIQUE (nombreNave ASC));\n"
				+ "CREATE TEMPORARY TABLE IF NOT EXISTS NaveDefensa ("
					+ "nombreNave VARCHAR(45) NOT NULL,"
					+ "ataqueNave FLOAT NOT NULL,hullNave FLOAT NOT NULL,"
					+ "escudoNave FLOAT NOT NULL,velocidadNave FLOAT NOT NULL,"
					+ "agilidadNave FLOAT NOT NULL,"
					+ "capacidadCarga FLOAT NOT NULL,"
					+ "cantidad INT,"
					+ "PRIMARY KEY (nombreNave),"
					+ "UNIQUE INDEX nombreNave_UNIQUE (nombreNave ASC));\n"
				+ "SELECT NOW() INTO @fecha;\n"
				+ "IF " + idPirata  +" > 0 AND " +idBatalla + " > 0 THEN\n"
	   				+"SELECT tiempoLlegada INTO tiempoUltimoAtaque FROM Movimiento\n"
	   					+"WHERE idMovimiento = (SELECT Movimiento_idMovimiento \n"
	   					+"FROM InformeBatalla\n"
	   					+"WHERE idBatalla = (SELECT InformeBatalla_idBatalla\n" 
										+"FROM Planeta \n"
										+ " WHERE coordenadaX = " +planetaDestino.getCoordenadaX()
							   			+ " AND coordenadaY = " +planetaDestino.getCoordenadaY()
							   			+ " AND Sistema_nombreSistema= '" +planetaDestino.getSistemanombreSistema() +"'));\n"
						+ "SELECT TIMESTAMPDIFF(HOUR, tiempoUltimoAtaque,NOW()) INTO horasUltimoAtaque;\n"
						+ "IF horasUltimoAtaque >= 5 THEN\n"
							+ "UPDATE Planeta_has_Nave planeta\n"
								+ "LEFT JOIN Pirata_has_Nave pirata ON planeta.Nave_nombreNave = pirata.Nave_nombreNave\n"
								+ "SET planeta.cantidad = pirata.cantidadDefecto\n"
								+ "WHERE pirata.Pirata_idPirata =" + idPirata + "\n"
								+ " AND Planeta_coordenadaX = " +planetaDestino.getCoordenadaX()
								+ " AND Planeta_coordenadaY = " +planetaDestino.getCoordenadaY()
								+ " AND Planeta_Sistema_nombreSistema= '" +planetaDestino.getSistemanombreSistema() +"';\n"
						+ "END IF;\n"
				+ "END IF;\n"
				+ "SET @ultimaGeneracion= '2018-05-23 18:45:20';"
				+ "SET @nivelInstalacion= 0;"
				+ "SET @generacionBase= 2.4;"
				+ "SET @segundosGenRecursos= 0;"
				+ "SELECT nivelInstalacion, ultimaGeneracion INTO @nivelInstalacion, @ultimaGeneracion "
					+ "FROM Planeta_has_Instalacion "
					+ "WHERE Planeta_coordenadaX =" +planetaDestino.getCoordenadaX()
	   				+ " AND Planeta_coordenadaY =" +planetaDestino.getCoordenadaY()
	   				+ " AND Planeta_Sistema_nombreSistema ='" +planetaDestino.getSistemanombreSistema()  
	   				+ "' AND Instalacion_name='Mina de Metal';\n"
	   			+ "IF " + idPirata  +" > 0 THEN\n"
	   				+ "SELECT nivelDefecto INTO @nivelInstalacion "
	   					+ "FROM Pirata_has_Instalacion "
	   					+ "WHERE Pirata_idPirata =" +idPirata
	   					+ " AND Instalacion_name='Mina de Metal';\n"
	   			+ "END IF;\n"
	   			+ "SELECT generacionBase INTO @generacionBase "
	   				+ "FROM Instalacion "
	   				+ "WHERE name = 'Mina de Metal';\n"
	   			+ "SELECT TIMESTAMPDIFF(SECOND, @ultimaGeneracion,NOW()) INTO @segundosGenRecursos;\n"
	   			+ "UPDATE Planeta_has_Instalacion "
	   				+ "SET ultimaGeneracion = NOW() "
	   				+ "WHERE Planeta_coordenadaX =" +planetaDestino.getCoordenadaX()
	   				+ " AND Planeta_coordenadaY =" +planetaDestino.getCoordenadaY()
	   				+ " AND Planeta_Sistema_nombreSistema ='" +planetaDestino.getSistemanombreSistema()  
	   				+ "' AND Instalacion_name='Mina de Metal';\n"
	   			+"UPDATE  Planeta_has_Recurso "
			   				+ "SET cantidad = cantidad + ((@nivelInstalacion * @generacionBase) * @segundosGenRecursos) "
			   				+ "WHERE Recurso_name = 'Metal' "
			   				+ "AND Recurso_instalacion_name = 'Mina de Metal' "
			   				+ "AND Planeta_coordenadaX = " + planetaDestino.getCoordenadaX()
			   				+ " AND Planeta_coordenadaY = " + planetaDestino.getCoordenadaY()
			   				+ " AND Planeta_Sistema_nombreSistema = '" + planetaDestino.getSistemanombreSistema() + "';\n"
			   	+ "SELECT nivelInstalacion, ultimaGeneracion INTO @nivelInstalacion, @ultimaGeneracion "
			   				+ "FROM Planeta_has_Instalacion "
			   				+ "WHERE Planeta_coordenadaX =" +planetaDestino.getCoordenadaX()
			   		   		+ " AND Planeta_coordenadaY =" +planetaDestino.getCoordenadaY()
			   		   		+ " AND Planeta_Sistema_nombreSistema ='" +planetaDestino.getSistemanombreSistema()  
			   		   		+ "' AND Instalacion_name='Mina de Oro';\n"
			    + "IF " + idPirata  +" > 0 THEN\n"
	   				+ "SELECT nivelDefecto INTO @nivelInstalacion "
	   					+ "FROM Pirata_has_Instalacion "
	   					+ "WHERE Pirata_idPirata =" +idPirata
	   					+ " AND Instalacion_name='Mina de Oro';\n"
	   			+ "END IF;\n"
			   	+ "SELECT generacionBase INTO @generacionBase "
			   		   		+ "FROM Instalacion "
			   		   		+ "WHERE name = 'Mina de Oro';\n"
			   	+ "SELECT TIMESTAMPDIFF(SECOND, @ultimaGeneracion,NOW()) INTO @segundosGenRecursos;\n"
			   	+ "UPDATE Planeta_has_Instalacion "
			   		   		+ "SET ultimaGeneracion = NOW() "
			   		   		+ "WHERE Planeta_coordenadaX =" +planetaDestino.getCoordenadaX()
			   		   		+ " AND Planeta_coordenadaY =" +planetaDestino.getCoordenadaY()
			   		   		+ " AND Planeta_Sistema_nombreSistema ='" +planetaDestino.getSistemanombreSistema()  
			   		   		+ "' AND Instalacion_name='Mina de Oro';\n"
			   	+"UPDATE  Planeta_has_Recurso "
			   				+ "SET cantidad = cantidad + ((@nivelInstalacion * @generacionBase) * @segundosGenRecursos) "
			   				+ "WHERE Recurso_name = 'Oro' "
			   				+ "AND Recurso_instalacion_name = 'Mina de Oro' "
			   				+ "AND Planeta_coordenadaX = " + planetaDestino.getCoordenadaX()
			   				+ " AND Planeta_coordenadaY = " + planetaDestino.getCoordenadaY()
			   				+ " AND Planeta_Sistema_nombreSistema = '" + planetaDestino.getSistemanombreSistema() + "';\n"
			   	+ "SELECT nivelInstalacion, ultimaGeneracion INTO @nivelInstalacion, @ultimaGeneracion "
					   		+ "FROM Planeta_has_Instalacion "
					   		+ "WHERE Planeta_coordenadaX =" +planetaDestino.getCoordenadaX()
					   		+ " AND Planeta_coordenadaY =" +planetaDestino.getCoordenadaY()
					   		+ " AND Planeta_Sistema_nombreSistema ='" +planetaDestino.getSistemanombreSistema()  
					   		+ "' AND Instalacion_name='Plataforma Petrolifera';\n"
				 + "IF " + idPirata  +" > 0 THEN\n"
	   				+ "SELECT nivelDefecto INTO @nivelInstalacion "
	   					+ "FROM Pirata_has_Instalacion "
	   					+ "WHERE Pirata_idPirata =" +idPirata
	   					+ " AND Instalacion_name='Plataforma Petrolifera';\n"
	   			+ "END IF;\n"
				+ "SELECT generacionBase INTO @generacionBase "
					   		+ "FROM Instalacion "
					   		+ "WHERE name = 'Plataforma Petrolifera';\n"
				+ "SELECT TIMESTAMPDIFF(SECOND, @ultimaGeneracion,NOW()) INTO @segundosGenRecursos;\n"
				+ "UPDATE Planeta_has_Instalacion "
					   		+ "SET ultimaGeneracion = NOW() "
					   		+ "WHERE Planeta_coordenadaX =" +planetaDestino.getCoordenadaX()
					   		+ " AND Planeta_coordenadaY =" +planetaDestino.getCoordenadaY()
					   		+ " AND Planeta_Sistema_nombreSistema ='" +planetaDestino.getSistemanombreSistema()  
					   		+ "' AND Instalacion_name='Plataforma Petrolifera';\n"
			    +"UPDATE  Planeta_has_Recurso "
					   		+ "SET cantidad = cantidad + ((@nivelInstalacion * @generacionBase) * @segundosGenRecursos) "
					   		+ "WHERE Recurso_name = 'Petroleo' "
					   		+ "AND Recurso_instalacion_name = 'Plataforma Petrolifera' "
					   		+ "AND Planeta_coordenadaX = " + planetaDestino.getCoordenadaX()
					   		+ " AND Planeta_coordenadaY = " + planetaDestino.getCoordenadaY()
					   		+ " AND Planeta_Sistema_nombreSistema = '" + planetaDestino.getSistemanombreSistema() + "';\n"
				+ "OPEN atacanteNom;\n"
				+ "atacantes_loopInicio: LOOP\n"
					+ "Fetch atacanteNom INTO nom;\n"
					+ "IF done THEN\n"
						+ "LEAVE atacantes_loopInicio;\n"
					+ "END IF;\n"
					+ "INSERT INTO NaveCombate (nombreNave, ataqueNave, hullNave, escudoNave, velocidadNave, agilidadNave, capacidadCarga)\n"
					+ "SELECT nombreNave, ataqueNave, hullNave, escudoNave, velocidadNave, agilidadNave, capacidadCarga\n"
						+ "FROM Nave WHERE nombreNave = nom;\n"
					+ "SELECT cantidad INTO cantidad_aux "
						+ "FROM Movimiento_has_Nave "
						+ "WHERE Movimiento_idMovimiento =" +movimiento.getIdMovimiento()
						+ " AND Nave_nombreNave = nom;\n"
					+"UPDATE NaveCombate "
						+"SET "
							+ "ataqueNave = ataqueNave * cantidad_aux,"
							+ "hullNave = hullNave * cantidad_aux,"
							+ "escudoNave = escudoNave * cantidad_aux,"
							+ "agilidadNave = agilidadNave * cantidad_aux,"
							+ "cantidad = cantidad_aux "
						+ "WHERE nombreNave = nom;\n"
				+ "END LOOP;\n"
				+ "SET done = FALSE;\n"
				+ "CLOSE atacanteNom;\n"
				+ "OPEN defensorNom;\n"
				+ "defensores_loopInicio: LOOP\n"
					+ "Fetch defensorNom INTO nom;\n"
					+ "IF done THEN\n"
						+ "LEAVE defensores_loopInicio;\n"
					+ "END IF;\n"
					+ "INSERT INTO NaveDefensa (nombreNave, ataqueNave, hullNave, escudoNave, velocidadNave, agilidadNave, capacidadCarga)\n"
					+ "SELECT nombreNave, ataqueNave, hullNave, escudoNave, velocidadNave, agilidadNave, capacidadCarga\n"
						+ "FROM Nave WHERE nombreNave = nom;\n"
					+ "SELECT cantidad INTO cantidad_aux " 
						+ "FROM Planeta_has_Nave "
						+ "WHERE Planeta_coordenadaX =" +planetaDestino.getCoordenadaX()
						+ " AND Planeta_coordenadaY =" +planetaDestino.getCoordenadaY()
						+ " AND Nave_nombreNave = nom;\n"
					+ "UPDATE NaveDefensa "
						+ "SET ataqueNave = ataqueNave * cantidad_aux,"
							+ "hullNave = hullNave * cantidad_aux, "
							+ "escudoNave = escudoNave * cantidad_aux,"
							+ "agilidadNave = agilidadNave * cantidad_aux, "
							+ "cantidad = cantidad_aux "
						+ "WHERE nombreNave = nom;\n"
				+ "END LOOP;\n"
				+ "SET done = FALSE;\n"
				+ "CLOSE defensorNom;\n"
				+ "loopPrincipal: WHILE v1 < 6 DO\n"
					+ "SELECT COUNT(*) INTO navesRestantes "
						+ "FROM NaveCombate "
						+ "WHERE cantidad > 0;\n"
					+ "IF navesRestantes <= 0 THEN\n"
			   			+ "SET victoria = FALSE;\n"
			   			+ "SET v1 = 6;\n"
			   			+ "leave loopPrincipal;\n"
			   		+ "END IF;\n"
			   		+ "SELECT COUNT(*) INTO navesRestantes "
			   			+ "FROM NaveDefensa "
			   			+ "WHERE cantidad > 0;\n"
			   		+ "IF navesRestantes <= 0 THEN\n"
			   			+ "SET victoria = TRUE;\n"
			   			+ "SET v1 = 6;\n"
			   			+ "leave loopPrincipal;\n"
			   		+ "END IF;\n"
			   		+ "OPEN atacanteNom;\n"
			   		+ "atacantes_loop: LOOP\n"
			   			+ "Fetch atacanteNom INTO nom;\n"
			   			+ "IF done THEN\n"
			   				+ "LEAVE atacantes_loop;\n"
			   				+ "CLOSE atacanteNom;\n"
			   			+ "END IF;\n"
			   			+ "SELECT COUNT(*) INTO @rank "
			   				+ "FROM NaveDefensa "
			   				+ "WHERE cantidad > 0;\n"
			   			+ "SET randomNumber = 0;\n"
			   			+ "SELECT FLOOR(RAND() * (@rank - 1 + 1)) + 1 INTO randomNumber;\n"
			   			+ "SET @rank = 0;\n"
			   			+ "SELECT "
			   				+ "nombreNave,"
			   				+ "ataqueNave,"
			   				+ "hullNave,"
			   				+ "escudoNave,"
			   				+ "agilidadNave,"
			   				+ "cantidad	"
			   				+ "INTO nomEnem , atEnem , hullEnem , shieldEnem , agEnem , cantEnem FROM "
			   				+ "("
			   					+ "SELECT @rank := @rank + 1 AS posicion,"
			   						+ "nombreNave,"
			   						+ "ataqueNave,"
			   						+ "hullNave,"
			   						+ "escudoNave,"
			   						+ "agilidadNave,"
			   						+ "cantidad "
			   						+ "FROM NaveDefensa	"
			   						+ "WHERE cantidad > 0"
			   				+ ") AS RankTable "
			   				+ "WHERE posicion = randomNumber;\n"
			   			+ "SELECT ataqueNave INTO atAl "
			   				+ "FROM NaveCombate "
			   				+ "WHERE nombreNave = nom;\n"
			   			+ "IF atAl > shieldEnem THEN\n"
			   				+ "SET hullEnem = hullEnem - (atAl - shieldEnem);\n"
			   				+ "SET shieldEnem = 0;\n"
			   			+ "ELSE\n"
			   				+ "SET shieldEnem = shieldEnem - atAl;\n"
			   			+ "END IF;\n"
			   			+ "IF hullEnem <= 0 THEN\n"
			   				+ "UPDATE NaveDefensa SET cantidad = 0 WHERE nombreNave = nomEnem;\n"
			   			+ "ELSE\n"
			   				+ "SELECT hullNave, escudoNave, ataqueNave, agilidadNave INTO hullOne, shieldOne, atOne, agOne "
			   				+ "FROM Nave "
			   				+ "WHERE nombreNave = nomEnem;\n"
			   				+ "SET cantidad_aux  = FLOOR(hullEnem/hullOne);\n"
			   				+ "UPDATE NaveDefensa "
			   					+ "SET "
			   						+ "cantidad = cantidad_aux, "
			   						+ "hullNave = hullEnem,"
			   						+ "escudoNave = shieldEnem,"
			   						+ "ataqueNave = atOne * cantidad_aux,"
			   						+ "agilidadNave = agOne * cantidad_aux "
			   					+ "WHERE nombreNave = nomEnem;\n"
					   	+ "END IF;\n"
			   		+ "END LOOP;\n"
			   		+ "SET done = FALSE;\n"
			   		+ "CLOSE atacanteNom;\n"
			   		+ "SELECT COUNT(*) INTO navesRestantes "
			   			+ "FROM NaveDefensa "
			   			+ "WHERE cantidad > 0;\n"
			   		+ "IF navesRestantes <= 0 THEN\n"
			   			+ "SET victoria = TRUE;\n"
			   		    + "SET v1 = 6;\n"
			   		    + "leave loopPrincipal;\n"
			   		+ "END IF;\n"
			   		+ "OPEN defensorNom;\n"
			   		+ "defensores_loop: LOOP\n"
		   				+ "Fetch defensorNom INTO nom;\n"
		   				+ "IF done THEN\n"
		   					+ "LEAVE defensores_loop;\n"
		   					+ "CLOSE defensorNom;\n"
		   				+ "END IF;\n"
		   				+ "SELECT COUNT(*) INTO @rank "
		   					+ "FROM NaveCombate "
		   					+ "WHERE cantidad > 0;\n"
		   				+ "SET randomNumber = 0;\n"
		   				+ "SELECT FLOOR(RAND() * (@rank - 1 + 1)) + 1 INTO randomNumber;\n"
		   				+ "SET @rank = 0;\n"
		   				+ "SELECT "
		   					+ "nombreNave,"
		   					+ "ataqueNave,"
		   					+ "hullNave,"
		   					+ "escudoNave,"
		   					+ "agilidadNave,"
		   					+ "cantidad	"
		   					+ "INTO nomEnem , atEnem , hullEnem , shieldEnem , agEnem , cantEnem FROM "
		   					+ "("
		   						+ "SELECT @rank := @rank + 1 AS posicion,"
		   							+ "nombreNave,"
		   							+ "ataqueNave,"
		   							+ "hullNave,"
		   							+ "escudoNave,"
		   							+ "agilidadNave,"
		   							+ "cantidad "
		   							+ "FROM NaveCombate	"
		   							+ "WHERE cantidad > 0"
		   					+ ") AS RankTable "
		   					+ "WHERE posicion = randomNumber;\n"
		   				+ "SELECT ataqueNave INTO atAl "
		   					+ "FROM NaveDefensa "
		   					+ "WHERE nombreNave = nom;\n"
		   				+ "IF atAl > shieldEnem THEN\n"
		   					+ "SET hullEnem = hullEnem - (atAl - shieldEnem);\n"
		   					+ "SET shieldEnem = 0;\n"
		   				+ "ELSE\n"
		   					+ "SET shieldEnem = shieldEnem - atAl;\n"
		   				+ "END IF;\n"
		   				+ "IF hullEnem <= 0 THEN\n"
		   					+ "UPDATE NaveCombate SET cantidad = 0 WHERE nombreNave = nomEnem;\n"
		   				+ "ELSE\n"
		   					+ "SELECT hullNave, escudoNave, ataqueNave, agilidadNave INTO hullOne, shieldOne, atOne, agOne "
		   						+ "FROM Nave "
		   						+ "WHERE nombreNave = nomEnem;\n"
		   					+ "SET cantidad_aux  = CEIL(hullEnem/hullOne);\n"
		   					+ "UPDATE NaveCombate "
		   						+ "SET "
		   							+ "cantidad = cantidad_aux, "
		   							+ "hullNave = hullEnem,"
		   							+ "escudoNave = shieldEnem,"
		   							+ "ataqueNave = atOne * cantidad_aux,"
		   							+ "agilidadNave = agOne * cantidad_aux "
		   						+ "WHERE nombreNave = nomEnem;\n"
		   				+ "END IF;\n"
		   			+ "END LOOP;\n"
		   			+ "SET done = FALSE;\n"
		   			+ "CLOSE defensorNom;\n"
		   			+ "SET v1 = v1 +1;\n"
		   		+ "END WHILE;\n"
		   		+ "INSERT INTO InformeBatalla (Movimiento_idMovimiento) VALUES(" +movimiento.getIdMovimiento() +");\n"
		   		+ "SELECT idBatalla INTO idBat FROM InformeBatalla WHERE Movimiento_idMovimiento = " +movimiento.getIdMovimiento() +";\n"
		   		+ "UPDATE Planeta"
		   			+ " SET InformeBatalla_idBatalla = idBat"
		   			+ " WHERE coordenadaX = " +planetaDestino.getCoordenadaX()
		   			+ " AND coordenadaY = " +planetaDestino.getCoordenadaY()
		   			+ " AND Sistema_nombreSistema= '" +planetaDestino.getSistemanombreSistema() +"';\n"
		   		+ "OPEN defensorNom;\n"
		   		+ "defensores_loopResultado: LOOP\n"
		   			+ "Fetch defensorNom INTO nom;\n"
		   			+ "IF done THEN\n"
		   				+ "LEAVE defensores_loopResultado;\n"
		   			+ "END IF;\n"
		   			+ "SELECT cantidad INTO cantidad_aux "
		   				+ "FROM NaveDefensa "
		   				+ "WHERE nombreNave = nom;\n"
		   			+ "SELECT cantidad INTO cantidad_enviada "
		   				+ "FROM Planeta_has_Nave "
		   				+ "WHERE Nave_nombreNave = nom "
		   				+ "AND Planeta_coordenadaX =" +planetaDestino.getCoordenadaX()
		   				+ " AND Planeta_coordenadaY =" +planetaDestino.getCoordenadaY()
		   				+ " AND Planeta_Sistema_nombreSistema ='" +planetaDestino.getSistemanombreSistema()  +"';\n"
		   			+ "UPDATE Planeta_has_Nave "
		   				+ "SET cantidad = cantidad_aux  "
		   				+ "WHERE Nave_nombreNave = nom "
		   				+ "AND Planeta_coordenadaX =" +planetaDestino.getCoordenadaX()
		   				+ " AND Planeta_coordenadaY =" +planetaDestino.getCoordenadaY()
		   				+ " AND Planeta_Sistema_nombreSistema ='" +planetaDestino.getSistemanombreSistema()  +"';\n"
		   			+ "IF " + usuarioDestino + " is not null THEN\n"
		   				+ "UPDATE Usuario_has_Nave\n"
		   				+ "SET cantidad = cantidad - (cantidad_enviada - cantidad_aux)\n"
		   				+"WHERE Usuario_username = " + usuarioDestino
		   				+ " AND Nave_nombreNave = nom;\n"
		   			+ "END IF;\n"
		   			+ "INSERT INTO InformeBatalla_has_Nave_Defensa VALUES(idBat, nom, cantidad_enviada, cantidad_enviada - cantidad_aux);\n"
		   		+ "END LOOP;\n"
		   		+ "SET done = FALSE;\n"
		   		+ "CLOSE defensorNom;\n"
		   		+ "DROP EVENT movimiento_" +movimiento.getIdMovimiento() + "_ships;\n"
		   		+ "IF victoria THEN\n"
		   			+ "INSERT into Movimiento (tiempoLlegada, tiempoEnvio,Usuario_username, "
		   				+ "Planeta_coordenadaX,Planeta_coordenadaY,Planeta_Sistema_nombreSistema,movimientoCancelado) Values(?,?,?,?,?,?,0);\n"
					+ "Select MAX(idMovimiento) INTO idMov "
						+ "FROM Movimiento"
						+" WHERE Planeta_coordenadaX=" + planetaOrigen.getCoordenadaX()
						+" AND Planeta_coordenadaY=" + planetaOrigen.getCoordenadaY()
						+" AND Planeta_Sistema_nombreSistema='" + planetaOrigen.getSistemanombreSistema() + "';\n"
					+ "OPEN atacanteNom;\n"
		   			+ "atacantes_loopResultado: LOOP\n"
		   				+ "Fetch atacanteNom INTO nom;\n"
		   				+ "IF done THEN\n"
		   					+ "LEAVE atacantes_loopResultado;\n"
		   				+ "END IF;\n"
		   				+ "SELECT cantidad INTO cantidad_aux "
		   					+ "FROM NaveCombate  "
		   					+ "WHERE nombreNave = nom;\n"
		   				+" SET totalCargo = totalCargo + (SELECT capacidadCarga*cantidad FROM NaveCombate WHERE nombreNave = nom);\n"
		   				+"SELECT FLOOR(cantidad * 0.3) INTO metalObtenido "
		   					+ "FROM Planeta_has_Recurso "
		   					+ "WHERE Recurso_name = 'Metal' "
		   					+ "AND Recurso_instalacion_name = 'Mina de Metal' "
		   					+ "AND Planeta_coordenadaX = " + planetaDestino.getCoordenadaX()
		   					+ " AND Planeta_coordenadaY = " + planetaDestino.getCoordenadaY()
		   					+ " AND Planeta_Sistema_nombreSistema = '" + planetaDestino.getSistemanombreSistema() + "';\n"
		   				+"SELECT FLOOR(cantidad * 0.3) INTO oroObtenido "
				   			+ "FROM Planeta_has_Recurso "
				   			+ "WHERE Recurso_name = 'Oro' "
				   			+ "AND Recurso_instalacion_name = 'Mina de Oro' "
				   			+ "AND Planeta_coordenadaX = " + planetaDestino.getCoordenadaX()
				   			+ " AND Planeta_coordenadaY = " + planetaDestino.getCoordenadaY()
				   			+ " AND Planeta_Sistema_nombreSistema = '" + planetaDestino.getSistemanombreSistema() + "';\n"
				   		+"SELECT FLOOR(cantidad * 0.3) INTO petroleoObtenido "
				   			+ "FROM Planeta_has_Recurso "
				   			+ "WHERE Recurso_name = 'Petroleo' "
				   			+ "AND Recurso_instalacion_name = 'Plataforma Petrolifera' "
				   			+ "AND Planeta_coordenadaX = " + planetaDestino.getCoordenadaX()
				   			+ " AND Planeta_coordenadaY = " + planetaDestino.getCoordenadaY()
				   			+ " AND Planeta_Sistema_nombreSistema = '" + planetaDestino.getSistemanombreSistema() + "';\n"
				   		+ "SET offsetRecursos = 0;\n"
				   		+ "IF metalObtenido < totalCargo/3 THEN\n"
				   			+ "SET offsetRecursos = FLOOR(totalCargo/3 - metalObtenido);\n"
				   		+ "ELSE\n"
				   			+ "SET metalObtenido = FLOOR(totalCargo/3);\n"
				   		+ "END IF;\n"
				   		+ "IF oroObtenido < (totalCargo/3 + offsetRecursos) THEN\n"
			   				+ "SET offsetRecursos = FLOOR((totalCargo/3 + offsetRecursos) - oroObtenido);\n"
			   			+ "ELSE\n"
			   				+ "SET oroObtenido = (totalCargo/3 + offsetRecursos);\n"
			   			+ "END IF;\n"
			   			+ "IF petroleoObtenido >= (totalCargo/3 + offsetRecursos) THEN\n"
			   				+ "SET petroleoObtenido = (totalCargo/3 + offsetRecursos);\n"
			   			+ "END IF;\n"
			   			+"UPDATE  Planeta_has_Recurso "
			   				+ "SET cantidad = cantidad - metalObtenido "
			   				+ "WHERE Recurso_name = 'Metal' "
			   				+ "AND Recurso_instalacion_name = 'Mina de Metal' "
			   				+ "AND Planeta_coordenadaX = " + planetaDestino.getCoordenadaX()
			   				+ " AND Planeta_coordenadaY = " + planetaDestino.getCoordenadaY()
			   				+ " AND Planeta_Sistema_nombreSistema = '" + planetaDestino.getSistemanombreSistema() + "';\n"
			   			+"UPDATE  Planeta_has_Recurso "
					   		+ "SET cantidad = cantidad - oroObtenido "
					   		+ "WHERE Recurso_name = 'Oro' "
					   		+ "AND Recurso_instalacion_name = 'Mina de Oro' "
					   		+ "AND Planeta_coordenadaX = " + planetaDestino.getCoordenadaX()
					   		+ " AND Planeta_coordenadaY = " + planetaDestino.getCoordenadaY()
					   		+ " AND Planeta_Sistema_nombreSistema = '" + planetaDestino.getSistemanombreSistema() + "';\n"
					   	+"UPDATE  Planeta_has_Recurso "
					   		+ "SET cantidad = cantidad - petroleoObtenido "
					   		+ "WHERE Recurso_name = 'Petroleo' "
					   		+ "AND Recurso_instalacion_name = 'Plataforma Petrolifera' "
					   		+ "AND Planeta_coordenadaX = " + planetaDestino.getCoordenadaX()
					   		+ " AND Planeta_coordenadaY = " + planetaDestino.getCoordenadaY()
					   		+ " AND Planeta_Sistema_nombreSistema = '" + planetaDestino.getSistemanombreSistema() + "';\n"
		   				+ "INSERT INTO Movimiento_has_Nave (Movimiento_idMovimiento, Nave_nombreNave, cantidad) VALUES (idMov, nom, cantidad_aux);\n"
		   				+ "SELECT cantidad INTO cantidad_enviada "
		   					+ "FROM Movimiento_has_Nave "
		   					+ "WHERE Movimiento_idMovimiento = " +movimiento.getIdMovimiento()
		   					+ " AND Nave_nombreNave = nom;\n"
		   				+ "UPDATE Usuario_has_Nave\n"
		   					+ "SET cantidad = cantidad - (cantidad_enviada - cantidad_aux)\n"
		   					+"WHERE Usuario_username = '" + planetaOrigen.getUsuariousername().getUsername()
							+ "' AND Nave_nombreNave = nom;\n"
						+ "INSERT INTO InformeBatalla_has_Nave_Ataque VALUES(idBat, nom, cantidad_enviada, cantidad_enviada - cantidad_aux);\n"
		   			+ "END LOOP;\n"
		   			+ "SET done = FALSE;\n"
		   			+ "CLOSE atacanteNom;\n"
		   			+ "ALTER EVENT movimiento_" +movimiento.getIdMovimiento() + "_battleShips ON SCHEDULE AT '" +fechaVuelta +"';\n"
				   	+ "INSERT INTO InformeBatalla_has_Recurso VALUES(idBat, 'Metal', 'Mina de Metal', metalObtenido);\n"
				   	+ "INSERT INTO InformeBatalla_has_Recurso VALUES(idBat, 'Oro', 'Mina de Oro', oroObtenido);\n"
		   			+ "INSERT INTO InformeBatalla_has_Recurso VALUES(idBat, 'Petroleo', 'Plataforma petrolifera', petroleoObtenido);\n"
		   			+ "INSERT INTO Mensaje (asunto, descripcion, TipoMensaje_name, fechaEnvio, InformeBatalla_idBatalla) VALUES ('Victoria Vs "+ movimiento.getPlaneta().getNombrePlaneta()+"', 'Victoria', 'Batalla', @fecha, idBat);\n"
		   			+ "IF " + idPirata  +" > 0 THEN\n"
		   				+ "SET randomNumberFloat = 0;\n"
			   			+ "SELECT RAND() * (100 - 1 + 1) + 1 INTO randomNumberFloat;\n"
			   			+ "SELECT Nave_nombreNave INTO naveDesbloqueada"
			   				+ " FROM Pirata_has_Desbloqueo_Nave\n" 
			   				+ "WHERE Pirata_idPirata = " + idPirata +"\n"  
			   				+ "AND probabilidadDesbloqueo > randomNumberFloat\n"
			   				+ "AND Nave_nombreNave not IN (SELECT Nave_nombreNave as nombreNave\n" 
														  	+ "FROM Usuario_has_Nave\n"
															+ "WHERE Usuario_username = '" +planetaOrigen.getUsuariousername().getUsername()  +"')\n" 
			   				+ "ORDER BY probabilidadDesbloqueo\n" 
			   				+ "LIMIT 1;\n"
		   				+ "IF naveDesbloqueada is not null THEN\n"
		   					+ "UPDATE InformeBatalla "
		   						+ "SET Nave_nombreNaveDesbloqueada = naveDesbloqueada "
		   						+ "WHERE idBatalla = idBat;\n"
		   					+ "INSERT INTO Usuario_has_Nave (Usuario_username, Nave_nombreNave, cantidad) VALUES('" +planetaOrigen.getUsuariousername().getUsername() +"', naveDesbloqueada, 0);\n"
				   			+ "INSERT INTO Planeta_has_Nave (Planeta_coordenadaX, Planeta_coordenadaY, Planeta_Sistema_nombreSistema, Nave_nombreNave, cantidad) VALUES('" +planetaOrigen.getCoordenadaX() +"', '" +planetaOrigen.getCoordenadaY() +"', '" +planetaOrigen.getSistemanombreSistema() +"', naveDesbloqueada, 0);\n"
		   				+ "END IF;\n"
		   			+ "END IF;\n"
		   			+ "SELECT LAST_INSERT_ID() INTO idMen;\n"
				   	+ "INSERT INTO Usuario_has_Mensaje (Usuario_username, Mensaje_idMensaje, descartado) VALUES ('" +planetaOrigen.getUsuariousername().getUsername() + "', idMen, 0);\n"
				   	+ "IF " + usuarioDestino + " is not null THEN\n"
		   				+ "INSERT INTO Mensaje (asunto, descripcion, TipoMensaje_name, fechaEnvio, InformeBatalla_idBatalla) VALUES ('Derrota Vs "+ planetaOrigen.getNombrePlaneta()+"', 'Derrota', 'Batalla', @fecha, idBat);\n"
		   				+ "SELECT LAST_INSERT_ID() INTO idMen;\n"
		   				+ "INSERT INTO Usuario_has_Mensaje (Usuario_username, Mensaje_idMensaje, descartado) VALUES (" +usuarioDestino + ", idMen, 0);\n"
		   			+ "END IF;\n"
		   		+ "ELSE\n"
		   			+ "DROP EVENT movimiento_" +movimiento.getIdMovimiento() + "_battleShips;\n"
		   			+ "OPEN atacanteNom;\n"
		   			+ "atacantes_loopActualizarNaves: LOOP\n"
		   				+ "Fetch atacanteNom INTO nom;\n"
		   				+ "IF done THEN\n"
		   					+ "LEAVE atacantes_loopActualizarNaves;\n"
		   				+ "END IF;\n"
		   				+ "SELECT cantidad INTO cantidad_enviada "
	   						+ "FROM Movimiento_has_Nave "
	   						+ "WHERE Movimiento_idMovimiento = " +movimiento.getIdMovimiento()
	   						+ " AND Nave_nombreNave = nom;\n"
		   				+ "UPDATE Usuario_has_Nave\n"
		   					+ "SET cantidad = cantidad - cantidad_enviada\n"
		   					+ "WHERE Usuario_username = '" + movimiento.getUsuariousername().getUsername()
		   					+ "' AND Nave_nombreNave = nom;\n"
		   				+ "INSERT INTO InformeBatalla_has_Nave_Ataque VALUES(idBat, nom, cantidad_enviada, cantidad_enviada);\n"
		   			+ "END LOOP;\n"
		   			+ "SET done = FALSE;\n"
		   			+ "CLOSE atacanteNom;\n"
		   			+ "INSERT INTO Mensaje (asunto, descripcion, TipoMensaje_name, fechaEnvio, InformeBatalla_idBatalla) VALUES ('Derrota Vs "+ movimiento.getPlaneta().getNombrePlaneta() +"', 'Derrota', 'Batalla', @fecha, idBat);\n"
					+ "SELECT idMensaje INTO idMen FROM Mensaje WHERE InformeBatalla_idBatalla = idBat;\n"
				   	+ "INSERT INTO Usuario_has_Mensaje (Usuario_username, Mensaje_idMensaje, descartado) VALUES ('" +planetaOrigen.getUsuariousername().getUsername() + "', idMen, 0);\n"
				   	+ "IF " + usuarioDestino + " is not null THEN\n"
			   			+ "INSERT INTO Mensaje (asunto, descripcion, TipoMensaje_name, fechaEnvio, InformeBatalla_idBatalla) VALUES ('Victoria Vs "+ planetaOrigen.getNombrePlaneta()+"', 'Victoria', 'Batalla', @fecha, idBat);\n"
			   			+ "SELECT LAST_INSERT_ID() INTO idMen;\n"
			   			+ "INSERT INTO Usuario_has_Mensaje (Usuario_username, Mensaje_idMensaje, descartado) VALUES (" +usuarioDestino + ", idMen, 0);\n"
			   		+ "END IF;\n"
				+ "END IF;\n"
		   		+ "DROP TABLE NaveCombate;\n"
		   		+ "DROP TABLE NaveDefensa;\n"
		   		+ "END;";
			
		String recuperarNavesTrasBatalla = "CREATE EVENT movimiento_" +movimiento.getIdMovimiento() + "_battleShips ON SCHEDULE AT '2037-12-12 23:59:59' DO BEGIN\n"
												+ "DECLARE nom Varchar(45);\n"
												+ "DECLARE cantidad_aux INT;\n"
												+ "DECLARE done INT DEFAULT FALSE;\n"
												+ "DECLARE atacanteNom CURSOR FOR SELECT Nave_nombreNave "
													+ "FROM Movimiento_has_Nave, (SELECT MAX(idMovimiento) AS maximo "
																					+ "FROM Movimiento "
																					+ "WHERE Planeta_coordenadaX = " +planetaOrigen.getCoordenadaX()
																					+ " AND Planeta_coordenadaY = " +planetaOrigen.getCoordenadaY()
																					+ " AND Planeta_Sistema_nombreSistema = '" + planetaOrigen.getSistemanombreSistema()+ "' ) as max_table "
													+ "WHERE Movimiento_idMovimiento = max_table.maximo;\n"
												+ "DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;\n"
												+ "OPEN atacanteNom;\n"
												+ "atacantes_loopResultado: LOOP\n"
	   												+ "Fetch atacanteNom INTO nom;\n"
	   												+ "IF done THEN\n"
	   													+ "LEAVE atacantes_loopResultado;\n"
	   												+ "END IF;\n"
	   												+"SELECT cantidad INTO cantidad_aux "
	   													+ "FROM Movimiento_has_Nave,  (SELECT MAX(idMovimiento) AS maximo "
	   																				+ "FROM Movimiento "  
	   																				+ "WHERE Planeta_coordenadaX = " +planetaOrigen.getCoordenadaX() 
	   																				+ " AND Planeta_coordenadaY = " +planetaOrigen.getCoordenadaY()
	   																				+ " AND Planeta_Sistema_nombreSistema ='" +planetaOrigen.getSistemanombreSistema() +"') as max_table "  
	   													+ "WHERE Movimiento_idMovimiento = max_table.maximo"
	   													+ " AND Nave_nombreNave = nom;\n"
	   												+ "UPDATE Planeta_has_Nave\n"
	   													+ "SET cantidad = cantidad + cantidad_aux\n"
	   													+"WHERE Nave_NombreNave = nom"
	   													+ " AND Planeta_coordenadaX = " +planetaOrigen.getCoordenadaX() 
														+ " AND Planeta_coordenadaY = " +planetaOrigen.getCoordenadaY()
														+ " AND Planeta_Sistema_nombreSistema ='" +planetaOrigen.getSistemanombreSistema() +"';\n"
	   											+ "END LOOP;\n"
												+ "CLOSE atacanteNom;\n"
												+ "SET done = FALSE;\n"
												+ "END;";
		String recuperarNaves = "CREATE EVENT IF NOT EXISTS movimiento_" +movimiento.getIdMovimiento() +"_ships" +"\nON SCHEDULE AT '2037-12-12 23:59:59'\nDO BEGIN\n" +setNaves +"END;";
		
		session.doWork(new Work()
		{

			@Override
			public void execute(Connection conn) throws SQLException 
			{
				PreparedStatement stmt = null;
				
				try
				{		
					stmt = conn.prepareStatement(sentencia);
					stmt.setInt(1, 0);
					stmt.setTimestamp(1, fechaVuelta);
					stmt.setTimestamp(2, fechaLlegada);
					stmt.setString(3, planetaOrigen.getUsuariousername().getUsername());
					stmt.setInt(4, planetaOrigen.getCoordenadaX());
					stmt.setInt(5, planetaOrigen.getCoordenadaY());
					stmt.setString(6, planetaOrigen.getSistemanombreSistema());
					stmt.executeUpdate();
					
					stmt = conn.prepareStatement(recuperarNavesTrasBatalla);
					stmt.executeUpdate();
					
					stmt = conn.prepareStatement(recuperarNaves);
					stmt.executeUpdate();
					
				}catch(Exception e)
				{
					System.out.println(e);
				}finally
				{
					stmt.close();
				}
						
			}
			
		});
		
		em.getTransaction().commit();
		session.close();
	}

}
