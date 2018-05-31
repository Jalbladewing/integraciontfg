package com.tfgllopis.integracion;

import java.util.Date;

import com.vaadin.server.VaadinService;
import com.vaadin.ui.UI;

public class CrudUsuario 
{
	public static String login(String username, String password, UsuarioRepository repo)
	{		
		
		
		if(!UserDataValidator.comprobarUsuarioBD(username, repo)) return "Usuario no registrado";
		if(!UserDataValidator.comprobarPassword(username, password, repo)) return "Datos incorrectos";
		if(!UserDataValidator.comprobarActivo(username, repo)) return "Usuario bloqueado";
		
		actualizarFechaAcceso(username, repo);
		return "";
	}
	
	private static void actualizarFechaAcceso(String username, UsuarioRepository repo)
	{
		Usuario user = Usuario.cargarUsuario(username, repo);
		user.setFechaUltimoAcceso(new Date());
		user.guardarUsuario(repo);
	}
	
	public static String registro(String username, String email, String password1, String password2, UsuarioRepository repo, RolesRepository rolRepo)
	{
		Usuario user;
		Date fechaRegistro = new Date();
		//Gestor_Correos correo = new Gestor_Correos();
		if(!UserDataValidator.comprobarUser(username)) return "Usuario inválido";
		if(!UserDataValidator.comprobarEmail(email)) return "Email inválido";
		if(!UserDataValidator.comprobarPassword(password1, password2)) return "Las contraseñas no coinciden";		
		if(UserDataValidator.comprobarUsuarioBD(username, repo)) return "El usuario ya existe";
		if(UserDataValidator.comprobarEmailBD(email, repo)) return "El correo ya está en uso";
		//if(!correo.correo_registro(email)) return "Imposible acceder al correo";
		
		user = new Usuario(email, username, password1, true, fechaRegistro, fechaRegistro);
		user.setRolName(rolRepo.findByName("Jugador").get(0));
		user.guardarUsuario(repo);
		
		return "";
	}
	
	public static String registroAdmin(String username, String email, String password1, String password2, String rol, boolean activo, UsuarioRepository repo, RolesRepository rolRepo)
	{
		Usuario user;
		Date fechaRegistro = new Date();
		//Gestor_Correos correo = new Gestor_Correos();
		if(!UserDataValidator.comprobarUser(username)) return "Usuario inválido";
		if(!UserDataValidator.comprobarEmail(email)) return "Email inválido";
		if(!UserDataValidator.comprobarPassword(password1, password2)) return "Las contraseñas no coinciden";		
		if(UserDataValidator.comprobarUsuarioBD(username, repo)) return "El usuario ya existe";
		if(UserDataValidator.comprobarEmailBD(email, repo)) return "El correo ya está en uso";
		//if(!correo.correo_registro(email)) return "Imposible acceder al correo";
		
		user = new Usuario(email, username, password1, activo, fechaRegistro, fechaRegistro);
		user.setRolName(rolRepo.findByName(rol).get(0));
		user.guardarUsuario(repo);
		
		return "";
	}
	
	public static String modificarPerfil(String username, String emailOriginal, String emailNuevo, String password1, String password2, UsuarioRepository repo)
	{
		Usuario user = Usuario.cargarUsuario(username, repo);
		Date fechaRegistro = new Date();
		if(!UserDataValidator.comprobarEmail(emailNuevo)) return "Email inválido";	
		
		if(!emailOriginal.equals(emailNuevo))
		{
			if(UserDataValidator.comprobarEmailBD(emailNuevo, repo)) return "El correo ya está en uso";
			user.setEmail(emailNuevo);
		}
		
		if(!password1.isEmpty())
		{
			if(!UserDataValidator.comprobarPassword(password1, password2)) return "Las contraseñas no coinciden";
			user.setPassword(password1);
		}
		
		user.guardarUsuario(repo);
		
		return "";
	}
	
	public static String modificarPerfil(String username, String emailOriginal,String emailNuevo, String password1, String password2, String rol, boolean activo, UsuarioRepository repo, RolesRepository rolRepo)
	{
		Usuario user = Usuario.cargarUsuario(username, repo);
		if(!UserDataValidator.comprobarEmail(emailNuevo)) return "Email inválido";		
		
		if(!emailOriginal.equals(emailNuevo))
		{
			if(UserDataValidator.comprobarEmailBD(emailNuevo, repo)) return "El correo ya está en uso";
			user.setEmail(emailNuevo);
		}
		
		if(!password1.isEmpty())
		{
			if(!UserDataValidator.comprobarPassword(password1, password2)) return "Las contraseñas no coinciden";
			user.setPassword(password1);
		}
		
		user.setActivo(activo);
		user.setRolName(rolRepo.findByName(rol).get(0));
		user.guardarUsuario(repo);
		
		return "";
	}
	
	public static String inicializarUsuario(Usuario usuario, UsuarioHasNaveRepository usuarioNaveRepo, PlanetaHasNaveRepository planetaNaveRepo, PlanetahasInstalacionRepository planetaInstalacionRepo, PlanetahasRecursoRepository planetaRecursoRepo, PlanetaRepository planetaRepo)
	{
		Planeta planeta = planetaRepo.findByPlanetaLibre().get(0);
		PlanetahasInstalacion planetaInstalacionMetal = planetaInstalacionRepo.findByInstalacionnamePlaneta("Mina de Metal", planeta.getCoordenadaX(), planeta.getCoordenadaY(), planeta.getSistemanombreSistema());
		PlanetahasInstalacion planetaInstalacionOro = planetaInstalacionRepo.findByInstalacionnamePlaneta("Mina de Oro", planeta.getCoordenadaX(), planeta.getCoordenadaY(), planeta.getSistemanombreSistema());
		PlanetahasInstalacion planetaInstalacionPetroleo = planetaInstalacionRepo.findByInstalacionnamePlaneta("Plataforma Petrolifera", planeta.getCoordenadaX(), planeta.getCoordenadaY(), planeta.getSistemanombreSistema());
		PlanetahasRecurso planetaRecursoMetal = planetaRecursoRepo.findByPlanetaRecurso(planeta.getCoordenadaX(), planeta.getCoordenadaY(), planeta.getSistemanombreSistema(), "Metal");
		PlanetahasRecurso planetaRecursoOro = planetaRecursoRepo.findByPlanetaRecurso(planeta.getCoordenadaX(), planeta.getCoordenadaY(), planeta.getSistemanombreSistema(), "Oro");
		PlanetahasRecurso planetaRecursoPetroleo = planetaRecursoRepo.findByPlanetaRecurso(planeta.getCoordenadaX(), planeta.getCoordenadaY(), planeta.getSistemanombreSistema(), "Petroleo");

		planeta.setUsuariousername(usuario);
		planeta.setPirataidPirata(null);
		
		planetaInstalacionMetal.setUltimaGeneracion(new Date());
		planetaInstalacionMetal.resetNivelInstalacion();
		planetaInstalacionOro.setUltimaGeneracion(new Date());
		planetaInstalacionOro.resetNivelInstalacion();
		planetaInstalacionPetroleo.setUltimaGeneracion(new Date());
		planetaInstalacionPetroleo.resetNivelInstalacion();
		
		planetaRecursoMetal.setCantidad(0);
		planetaRecursoOro.setCantidad(0);
		planetaRecursoPetroleo.setCantidad(0);
				
		planetaRepo.save(planeta);
		
		planetaInstalacionRepo.save(planetaInstalacionMetal);
		planetaInstalacionRepo.save(planetaInstalacionOro);
		planetaInstalacionRepo.save(planetaInstalacionPetroleo);
		
		planetaRecursoRepo.save(planetaRecursoMetal);
		planetaRecursoRepo.save(planetaRecursoOro);
		planetaRecursoRepo.save(planetaRecursoPetroleo);
		
		return "";
	}

}
