package com.tfgllopis.integracion;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Date;

import javax.transaction.Transactional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class) 
@SpringBootTest(classes = ApplicationForTests.class)
public class ModuloNavesApplicationTests 
{
		@Test
	public void testUsuarioFull() 
	{
		Date fechaAhora = new Date();
		Usuario user = new Usuario("juan@gmail.com", "Pepe", "1234", true, fechaAhora, fechaAhora);
		
		assertEquals("juan@gmail.com", user.getEmail());
		assertEquals("Pepe", user.getUsername());
		assertEquals(true, user.getActivo());
		assertEquals(fechaAhora, user.getFechaCreacion());
		assertEquals(fechaAhora, user.getFechaUltimoAcceso());
		assertTrue(CheckPassword.verifyHash("1234", user.getPassword()));
	}
	
	@Test
	public void testUsuarioEmail() 
	{
		Date fechaAhora = new Date();
		Usuario user = new Usuario("juan@gmail.com");
		
		assertEquals("juan@gmail.com", user.getEmail());
	}
	
	@Test
	public void testUsuarioEspacios() 
	{
		Date fechaAhora = new Date();
		Usuario user = new Usuario("juan@ gmail. com", "P ep e", "1 23 4", true, fechaAhora, fechaAhora);
		
		assertEquals("juan@gmail.com", user.getEmail());
		assertEquals("Pepe", user.getUsername());
		assertEquals(true, user.getActivo());
		assertEquals(fechaAhora, user.getFechaCreacion());
		assertEquals(fechaAhora, user.getFechaUltimoAcceso());
		assertTrue(CheckPassword.verifyHash("1234", user.getPassword()));
	}
	
	@Test
	public void testRol()
	{
		Rol rol = new Rol("Administrador");
		
		assertEquals("Administrador", rol.getName());
	}
	
	@Test
	public void testNaveFull()
	{
		Nave nave = new Nave("Vulture", "/WEB-INF/images/image.png", 120, 200, 105, 20, 52, 10, 11, (short) 1);
		TipoNave tipoNave = new TipoNave("Caza", "/WEB-INF/images/image.png");
		
		nave.setTipoNavenombreTipoNave(tipoNave);
		
		assertEquals("Vulture", nave.getNombreNave());
		assertEquals("/WEB-INF/images/image.png", nave.getRutaImagenNave());
		assertEquals(120, nave.getAtaqueNave(), 0.0);
		assertEquals(200, nave.getHullNave(), 0.0);
		assertEquals(105, nave.getEscudoNave(), 0.0);
		assertEquals(20, nave.getVelocidadNave(), 0.0);
		assertEquals(52, nave.getAgilidadNave(), 0.0);
		assertEquals(10, nave.getCapacidadCarga(), 0.0);
		assertEquals(11, nave.getSegundosConstruccion(), 0.0);
		assertEquals((short) 1, nave.getBloqueada());
		assertEquals(tipoNave, nave.getTipoNavenombreTipoNave());
	}
	
	@Test
	public void testTipoNave()
	{
		TipoNave tipoNave = new TipoNave("Caza", "/WEB-INF/images/image.png");
		
		assertEquals("Caza", tipoNave.getNombreTipoNave());
		assertEquals("/WEB-INF/images/image.png", tipoNave.getRutaImagenTipoNave());
	}
	
	@Test
	public void testTipoNaveListaNave()
	{
		TipoNave tipoNave = new TipoNave("Caza", "/WEB-INF/images/image.png");
		
		Nave nave1 = new Nave("Vulture", "/WEB-INF/images/image.png", 120, 200, 105, 20, 52, 10, 11, (short) 1);
		Nave nave2 = new Nave("Eagle", "/WEB-INF/images/image.png", 120, 200, 105, 20, 52, 10, 11, (short) 1);
		
		ArrayList<Nave> listaNaves = new ArrayList<>();
		listaNaves.add(nave1);
		listaNaves.add(nave2);
		tipoNave.setNaveList(listaNaves);
		
		
		assertEquals(tipoNave.getNaveList().size(), 2);
		assertEquals(tipoNave.getNaveList().get(0), nave1);
		assertEquals(tipoNave.getNaveList().get(1), nave2);
	}
	
	@Test
	public void testUsuarioHasNave()
	{
		Date fechaAhora = new Date();
		UsuarioHasNave usuarioNave = new UsuarioHasNave("Pepe", "Vulture", 20);
		
		Nave nave = new Nave("Vulture", "/WEB-INF/images/image.png", 120, 200, 105, 20, 52, 10, 11, (short) 1);
		Usuario user = new Usuario("juan@gmail.com", "Pepe", "1234", true, fechaAhora, fechaAhora);
		
		usuarioNave.setNave(nave);
		usuarioNave.setUsuario(user);
		
		assertEquals("Pepe", usuarioNave.getUsuariousername());
		assertEquals("Vulture", usuarioNave.getNavenombreNave());
		assertEquals(20, usuarioNave.getCantidad());
		assertEquals(nave, usuarioNave.getNave());
		assertEquals(user, usuarioNave.getUsuario());
	}
	
	@Test
	public void testUsuarioHasNavePK()
	{
		UsuarioHasNavePK usuarioNavePK = new UsuarioHasNavePK("Pepe", "Vulture");
		UsuarioHasNave usuarioNave = new UsuarioHasNave(usuarioNavePK, 20);
		
		assertEquals("Pepe", usuarioNave.getUsuarioHasNavePK().getUsuariousername());
		assertEquals("Vulture", usuarioNave.getUsuarioHasNavePK().getNavenombreNave());
		assertEquals(20, usuarioNave.getCantidad());
	}
	
	@Test
	public void testUsuarioConstruyeNave()
	{
		Date fechaAhora = new Date();
		UsuarioconstruyeNave usuarioNave = new UsuarioconstruyeNave("Pepe", "Vulture", 20, fechaAhora);
		
		Nave nave = new Nave("Vulture", "/WEB-INF/images/image.png", 120, 200, 105, 20, 52, 10, 11, (short) 1);
		Usuario user = new Usuario("juan@gmail.com", "Pepe", "1234", true, fechaAhora, fechaAhora);
		
		usuarioNave.setNave(nave);
		usuarioNave.setUsuario(user);
		
		assertEquals("Pepe", usuarioNave.getUsuariousername());
		assertEquals("Vulture", usuarioNave.getNavenombreNave());
		assertEquals(20, usuarioNave.getCantidad());
		assertEquals(fechaAhora, usuarioNave.getFinConstruccion());
		assertEquals(nave, usuarioNave.getNave());
		assertEquals(user, usuarioNave.getUsuario());
	}
	
	@Test
	public void testUsuarioConstruyeNavePK()
	{
		Date fechaAhora = new Date();		
		UsuarioconstruyeNavePK usuarioNavePK = new UsuarioconstruyeNavePK("Pepe", "Vulture");
		UsuarioconstruyeNave usuarioNave = new UsuarioconstruyeNave(usuarioNavePK, 20, fechaAhora);
		
		assertEquals("Pepe", usuarioNave.getUsuarioconstruyeNavePK().getUsuariousername());
		assertEquals("Vulture", usuarioNave.getUsuarioconstruyeNavePK().getNavenombreNave());
		assertEquals(20, usuarioNave.getCantidad());
		assertEquals(fechaAhora, usuarioNave.getFinConstruccion());
	}
	
	@Test
	public void testRecursoFull()
	{
		Recurso recurso = new Recurso("Metal", "Mina");
		Instalacion instalacion = new Instalacion("Mina", 20);
		
		recurso.setInstalacion(instalacion);
	
		assertEquals("Metal", recurso.getName());
		assertEquals("Mina", recurso.getInstalacionname());
		assertEquals(instalacion, recurso.getInstalacion());
	}
	
	@Test
	public void testRecursoPK()
	{
		RecursoPK recursoPK = new RecursoPK("Metal", "Mina");
		Recurso recurso = new Recurso(recursoPK);
		
		assertEquals("Metal", recurso.getRecursoPK().getName());
		assertEquals("Mina", recurso.getRecursoPK().getInstalacionname());
	}
	
	
	@Test
	public void testInstalacionFull()
	{
		Instalacion instalacion = new Instalacion("Mina", 20);
		Recurso recurso = new Recurso("Metal", "Mina");
		
		instalacion.setRecurso(recurso);
				
		assertEquals("Mina", instalacion.getName());
		assertEquals(20, instalacion.getGeneracionBase(), 0.0);
		assertEquals(recurso, instalacion.getRecurso());
	}
	
	@Test
	public void testSistema()
	{
		Sistema sistema = new Sistema("Atlas", 0, 1);
		
		assertEquals(0, sistema.getCoordenadaX());
		assertEquals(1, sistema.getCoordenadaY());
		assertEquals("Atlas", sistema.getNombreSistema());
	}
	
	@Test
	public void testPlanetaFull()
	{
		Date fechaAhora = new Date();
		Planeta planeta = new Planeta(0, 1, "Atlas", "Planeta Centauri 1", "/ruta/imagen.png");
		
		Usuario user = new Usuario("juan@gmail.com", "Pepe", "1234", true, fechaAhora, fechaAhora);
		Sistema sistema = new Sistema("Atlas", 0, 1);
		
		planeta.setUsuariousername(user);
		planeta.setSistema(sistema);
		planeta.setFinProteccion(fechaAhora);
		
		assertEquals(0, planeta.getCoordenadaX());
		assertEquals(1, planeta.getCoordenadaY());
		assertEquals("Atlas", planeta.getSistemanombreSistema());
		assertEquals("/ruta/imagen.png", planeta.getRutaImagenPlaneta());
		assertEquals("Planeta Centauri 1", planeta.getNombrePlaneta());
		assertEquals(fechaAhora, planeta.getFinProteccion());
		assertEquals(user, planeta.getUsuariousername());
		assertEquals(sistema, planeta.getSistema());
	}
	
	@Test
	public void testPlanetaPK()
	{
		PlanetaPK planetaPK = new PlanetaPK(0, 1, "Atlas");
		Planeta planeta = new Planeta(planetaPK, "Planeta Centauri 1", "/ruta/imagen.png");
		
		assertEquals(0, planeta.getPlanetaPK().getCoordenadaX());
		assertEquals(1, planeta.getPlanetaPK().getCoordenadaY());
		assertEquals("Atlas", planeta.getPlanetaPK().getSistemanombreSistema());
		assertEquals("/ruta/imagen.png", planeta.getRutaImagenPlaneta());
		assertEquals("Planeta Centauri 1", planeta.getNombrePlaneta());
	}
	
	@Test
	public void testInstalacionCuestaRecursos()
	{
		InstalacioncuestaRecurso instalacionRecurso = new InstalacioncuestaRecurso("Mina", "Metal", "Mina",20);
		Instalacion instalacion = new Instalacion("Mina", 20);
		Recurso recurso = new Recurso("Metal", "Mina");
		
		instalacionRecurso.setInstalacion(instalacion);
		instalacionRecurso.setRecurso(recurso);
		
		assertEquals("Mina", instalacionRecurso.getInstalacionname());
		assertEquals("Metal", instalacionRecurso.getRecursoname());
		assertEquals("Mina", instalacionRecurso.getRecursoInstalacionname());
		assertEquals(20, instalacionRecurso.getCantidadBase());
		assertEquals(instalacion, instalacionRecurso.getInstalacion());
		assertEquals(recurso, instalacionRecurso.getRecurso());
	}
	
	@Test
	public void testInstalacionCuestaRecursosPK()
	{
		InstalacioncuestaRecursoPK instalacionRecursoPK = new InstalacioncuestaRecursoPK("Mina", "Metal", "Mina");
		InstalacioncuestaRecurso instalacionRecurso = new InstalacioncuestaRecurso(instalacionRecursoPK, 20);
		
		assertEquals("Mina", instalacionRecurso.getInstalacioncuestaRecursoPK().getInstalacionname());
		assertEquals("Metal", instalacionRecurso.getInstalacioncuestaRecursoPK().getRecursoname());
		assertEquals("Mina", instalacionRecurso.getInstalacioncuestaRecursoPK().getRecursoInstalacionname());
		assertEquals(20, instalacionRecurso.getCantidadBase());
	}
	
	@Test
	public void testNaveCuestaRecursos()
	{
		NavecuestaRecurso naveRecurso = new NavecuestaRecurso("Vulture", "Metal", "Mina",20);
		Recurso recurso = new Recurso("Metal", "Mina");
		Nave nave = new Nave("Vulture", "/WEB-INF/images/image.png", 120, 200, 105, 20, 52, 10, 11, (short) 1);
		
		naveRecurso.setRecurso(recurso);
		naveRecurso.setNave(nave);
		
		assertEquals("Vulture", naveRecurso.getNavenombreNave());
		assertEquals("Metal", naveRecurso.getRecursoname());
		assertEquals("Mina", naveRecurso.getRecursoInstalacionname());
		assertEquals(20, naveRecurso.getCantidadBase());
		assertEquals(recurso, naveRecurso.getRecurso());
		assertEquals(nave, naveRecurso.getNave());
	}
	
	@Test
	public void testNaveCuestaRecursosPK()
	{
		NavecuestaRecursoPK naveRecursoPK = new NavecuestaRecursoPK("Vulture", "Metal", "Mina");
		NavecuestaRecurso naveRecurso = new NavecuestaRecurso(naveRecursoPK, 20);
		
		assertEquals("Vulture", naveRecurso.getNavecuestaRecursoPK().getNavenombreNave());
		assertEquals("Metal", naveRecurso.getNavecuestaRecursoPK().getRecursoname());
		assertEquals("Mina", naveRecurso.getNavecuestaRecursoPK().getRecursoInstalacionname());
		assertEquals(20, naveRecurso.getCantidadBase());
	}
	
	@Test
	public void testPlanetaHasRecursos()
	{
		PlanetahasRecurso planetaRecurso = new PlanetahasRecurso(0, 1, "Atlas", "Metal", "Mina de Metal", 25);
		Recurso recurso = new Recurso("Metal", "Mina de Metal");
		Planeta planeta = new Planeta(0, 1, "Atlas", "Planeta Centauri 1", "/ruta/imagen.png");
		
		planetaRecurso.setRecurso(recurso);
		planetaRecurso.setPlaneta(planeta);
		
		assertEquals(0, planetaRecurso.getPlanetacoordenadaX());
		assertEquals(1, planetaRecurso.getPlanetacoordenadaY());
		assertEquals("Atlas", planetaRecurso.getPlanetaSistemanombreSistema());
		assertEquals("Metal", planetaRecurso.getRecursoname());
		assertEquals("Mina de Metal", planetaRecurso.getRecursoInstalacionname());
		assertEquals(25, planetaRecurso.getCantidad(), 0.0);
		assertEquals(recurso, planetaRecurso.getRecurso());
		assertEquals(planeta, planetaRecurso.getPlaneta());
	}
	
	@Test
	public void testPlanetaHasRecursosPK()
	{
		PlanetahasRecursoPK planetaRecursoPK = new PlanetahasRecursoPK(0, 1, "Atlas", "Metal", "Mina de Metal");
		PlanetahasRecurso planetaRecurso = new PlanetahasRecurso(planetaRecursoPK, 25);
		
		assertEquals(0, planetaRecurso.getPlanetahasRecursoPK().getPlanetacoordenadaX());
		assertEquals(1, planetaRecurso.getPlanetahasRecursoPK().getPlanetacoordenadaY());
		assertEquals("Atlas", planetaRecurso.getPlanetahasRecursoPK().getPlanetaSistemanombreSistema());
		assertEquals("Metal", planetaRecurso.getPlanetahasRecursoPK().getRecursoname());
		assertEquals("Mina de Metal", planetaRecurso.getPlanetahasRecursoPK().getRecursoInstalacionname());
		assertEquals(25, planetaRecurso.getCantidad(), 0.0);
	}
	
	@Test
	public void testPlanetaHasNave()
	{		
		Nave nave = new Nave("Vulture", "/WEB-INF/images/image.png", 120, 200, 105, 20, 52, 10, 11, (short) 1);
		Planeta planeta = new Planeta(0, 1, "Atlas", "Planeta Centauri 1", "/ruta/imagen.png");
		PlanetaHasNave planetaNave = new PlanetaHasNave(0, 1, "Atlas", "Vulture", 32);
		
		planetaNave.setNave(nave);
		planetaNave.setPlaneta(planeta);
		
		assertEquals(0, planetaNave.getPlanetacoordenadaX());
		assertEquals(1, planetaNave.getPlanetacoordenadaY());
		assertEquals("Atlas", planetaNave.getPlanetaSistemanombreSistema());
		assertEquals("Vulture", planetaNave.getNavenombreNave());
		assertEquals(32, planetaNave.getCantidad(), 0.0);
		assertEquals(nave, planetaNave.getNave());
		assertEquals(planeta, planetaNave.getPlaneta());
	}
	
	@Test
	public void testPlanetaHasNavePK()
	{
		PlanetaHasNavePK planetaNavePK = new PlanetaHasNavePK(0, 1, "Atlas", "Vulture");
		PlanetaHasNave planetaNave = new PlanetaHasNave(planetaNavePK, 32);
		
		assertEquals(0, planetaNave.getPlanetaHasNavePK().getPlanetacoordenadaX());
		assertEquals(1, planetaNave.getPlanetaHasNavePK().getPlanetacoordenadaY());
		assertEquals("Atlas", planetaNave.getPlanetaHasNavePK().getPlanetaSistemanombreSistema());
		assertEquals("Vulture", planetaNave.getPlanetaHasNavePK().getNavenombreNave());
		assertEquals(32, planetaNave.getCantidad(), 0.0);
	}
	
	@Test
	public void testPlanetaHasInstalacion()
	{		
		Date fechaAhora = new Date();
		PlanetahasInstalacion planetaInstalacion = new PlanetahasInstalacion(0, 1, "Atlas", "Mina de metal", 2, fechaAhora);
		
		Planeta planeta = new Planeta(0, 1, "Atlas", "Planeta Centauri 1", "/ruta/imagen.png");
		Instalacion instalacion = new Instalacion("Mina de metal", 40);
		
		planetaInstalacion.setPlaneta(planeta);
		planetaInstalacion.setInstalacion(instalacion);
		
		assertEquals(0, planetaInstalacion.getPlanetacoordenadaX());
		assertEquals(1, planetaInstalacion.getPlanetacoordenadaY());
		assertEquals("Atlas", planetaInstalacion.getPlanetaSistemanombreSistema());
		assertEquals("Mina de metal", planetaInstalacion.getInstalacionname());
		assertEquals(2, planetaInstalacion.getNivelInstalacion());
		assertEquals(fechaAhora, planetaInstalacion.getUltimaGeneracion());
		assertEquals(planeta, planetaInstalacion.getPlaneta());
		assertEquals(instalacion, planetaInstalacion.getInstalacion());
	}
	
	@Test
	public void testPlanetaHasInstalacionPK()
	{
		Date fechaAhora = new Date();
		PlanetahasInstalacionPK planetaInstalacionPK = new PlanetahasInstalacionPK(0, 1, "Atlas", "Vulture");
		PlanetahasInstalacion planetaInstalacion = new PlanetahasInstalacion(planetaInstalacionPK, 2, fechaAhora);
		
		assertEquals(0, planetaInstalacion.getPlanetahasInstalacionPK().getPlanetacoordenadaX());
		assertEquals(1, planetaInstalacion.getPlanetahasInstalacionPK().getPlanetacoordenadaY());
		assertEquals("Atlas", planetaInstalacion.getPlanetahasInstalacionPK().getPlanetaSistemanombreSistema());
		assertEquals("Vulture", planetaInstalacion.getPlanetahasInstalacionPK().getInstalacionname());
		assertEquals(2, planetaInstalacion.getNivelInstalacion());
		assertEquals(fechaAhora, planetaInstalacion.getUltimaGeneracion());
	}
	
	@Test
	public void testUsuarioListaNave()
	{
		Date fechaAhora = new Date();
		
		Usuario user = new Usuario("juan@gmail.com", "Pepe", "1234", true, fechaAhora, fechaAhora);
		UsuarioHasNave usuarioNave1 = new UsuarioHasNave("Pepe", "Vulture", 20);
		UsuarioHasNave usuarioNave2 = new UsuarioHasNave("Pepe", "Eagle", 20);
		
		ArrayList<UsuarioHasNave> listaNaves = new ArrayList<>();
		listaNaves.add(usuarioNave1);
		listaNaves.add(usuarioNave2);
		user.setUsuariohasNaveList(listaNaves);
		
		assertEquals(user.getUsuariohasNaveList().size(), 2);
		assertEquals(user.getUsuariohasNaveList().get(0), usuarioNave1);
		assertEquals(user.getUsuariohasNaveList().get(1), usuarioNave2);
	}
	
	@Test
	public void testNaveListaNave()
	{
		Nave nave = new Nave("Vulture", "/WEB-INF/images/image.png", 120, 200, 105, 20, 52, 10, 11, (short) 1);
		UsuarioHasNave usuarioNave1 = new UsuarioHasNave("Pepe", "Vulture", 20);
		UsuarioHasNave usuarioNave2 = new UsuarioHasNave("Jose", "Vulture", 20);
		
		ArrayList<UsuarioHasNave> listaNaves = new ArrayList<>();
		listaNaves.add(usuarioNave1);
		listaNaves.add(usuarioNave2);
		nave.setUsuariohasNaveList(listaNaves);
		
		assertEquals(nave.getUsuariohasNaveList().size(), 2);
		assertEquals(nave.getUsuariohasNaveList().get(0), usuarioNave1);
		assertEquals(nave.getUsuariohasNaveList().get(1), usuarioNave2);
	}
	
	@Test
	public void testUsuarioListaConstruye()
	{
		Date fechaAhora = new Date();
		
		Usuario user = new Usuario("juan@gmail.com", "Pepe", "1234", true, fechaAhora, fechaAhora);
		UsuarioconstruyeNave usuarioNave1 = new UsuarioconstruyeNave("Pepe", "Vulture", 20, fechaAhora);
		UsuarioconstruyeNave usuarioNave2 = new UsuarioconstruyeNave("Pepe", "Eagle", 20, fechaAhora);
		
		ArrayList<UsuarioconstruyeNave> listaNaves = new ArrayList<>();
		listaNaves.add(usuarioNave1);
		listaNaves.add(usuarioNave2);
		user.setUsuarioconstruyeNaveList(listaNaves);
		
		assertEquals(user.getUsuarioconstruyeNaveList().size(), 2);
		assertEquals(user.getUsuarioconstruyeNaveList().get(0), usuarioNave1);
		assertEquals(user.getUsuarioconstruyeNaveList().get(1), usuarioNave2);
	}
	
	@Test
	public void testNaveListaConstruye()
	{
		Date fechaAhora = new Date();
		
		Nave nave = new Nave("Vulture", "/WEB-INF/images/image.png", 120, 200, 105, 20, 52, 10, 11, (short) 1);
		UsuarioconstruyeNave usuarioNave1 = new UsuarioconstruyeNave("Pepe", "Vulture", 20, fechaAhora);
		UsuarioconstruyeNave usuarioNave2 = new UsuarioconstruyeNave("Jose", "Vulture", 20, fechaAhora);
		
		ArrayList<UsuarioconstruyeNave> listaNaves = new ArrayList<>();
		listaNaves.add(usuarioNave1);
		listaNaves.add(usuarioNave2);
		nave.setUsuarioconstruyeNaveList(listaNaves);
		
		assertEquals(nave.getUsuarioconstruyeNaveList().size(), 2);
		assertEquals(nave.getUsuarioconstruyeNaveList().get(0), usuarioNave1);
		assertEquals(nave.getUsuarioconstruyeNaveList().get(1), usuarioNave2);
	}
	
	@Test
	public void testNaveListaNaveCuesta()
	{
		Nave nave = new Nave("Vulture", "/WEB-INF/images/image.png", 120, 200, 105, 20, 52, 10, 11, (short) 1);
		NavecuestaRecurso naveRecurso1 = new NavecuestaRecurso("Vulture", "Metal", "Mina",30);
		NavecuestaRecurso naveRecurso2 = new NavecuestaRecurso("Eagle", "Metal", "Mina",10);
		
		ArrayList<NavecuestaRecurso> listaCostes = new ArrayList<>();
		listaCostes.add(naveRecurso1);
		listaCostes.add(naveRecurso2);
		nave.setNavecuestaRecursoList(listaCostes);
		
		assertEquals(nave.getNavecuestaRecursoList().size(), 2);
		assertEquals(nave.getNavecuestaRecursoList().get(0), naveRecurso1);
		assertEquals(nave.getNavecuestaRecursoList().get(1), naveRecurso2);
	}
	
	@Test
	public void testSistemaListaPlanetas()
	{
		Sistema sistema = new Sistema("Atlas", 0, 1);
		
		Planeta planeta1 = new Planeta(0, 1, "Atlas", "Planeta Centauri 1", "/ruta/imagen.png");
		Planeta planeta2 = new Planeta(0, 2, "Atlas", "Planeta Alpha 1", "/ruta/imagen.png");
		
		ArrayList<Planeta> listaPlanetas = new ArrayList<>();
		listaPlanetas.add(planeta1);
		listaPlanetas.add(planeta2);
		sistema.setPlanetaList(listaPlanetas);
		
		assertEquals(sistema.getPlanetaList().size(), 2);
		assertEquals(sistema.getPlanetaList().get(0), planeta1);
		assertEquals(sistema.getPlanetaList().get(1), planeta2);
	}
	
	@Test
	public void testRecursoListaInstalacionCuesta()
	{
		Recurso recurso = new Recurso("Metal", "Mina");
		InstalacioncuestaRecurso instalacionRecurso1 = new InstalacioncuestaRecurso("Mina", "Metal", "Mina",20);
		InstalacioncuestaRecurso instalacionRecurso2 = new InstalacioncuestaRecurso("Plataforma Petrolífera", "Metal", "Mina",60);
		
		ArrayList<InstalacioncuestaRecurso> listaInstalaciones = new ArrayList<>();
		listaInstalaciones.add(instalacionRecurso1);
		listaInstalaciones.add(instalacionRecurso2);
		recurso.setInstalacioncuestaRecursoList(listaInstalaciones);
		
		assertEquals(recurso.getInstalacioncuestaRecursoList().size(), 2);
		assertEquals(recurso.getInstalacioncuestaRecursoList().get(0), instalacionRecurso1);
		assertEquals(recurso.getInstalacioncuestaRecursoList().get(1), instalacionRecurso2);
	}
	
	@Test
	public void testRecursoListaNaveCuesta()
	{
		Recurso recurso = new Recurso("Metal", "Mina");
		NavecuestaRecurso naveRecurso1 = new NavecuestaRecurso("Vulture", "Metal", "Mina",30);
		NavecuestaRecurso naveRecurso2 = new NavecuestaRecurso("Eagle", "Metal", "Mina",10);
		
		ArrayList<NavecuestaRecurso> listaNaves = new ArrayList<>();
		listaNaves.add(naveRecurso1);
		listaNaves.add(naveRecurso2);
		recurso.setNavecuestaRecursoList(listaNaves);
		
		assertEquals(recurso.getNavecuestaRecursoList().size(), 2);
		assertEquals(recurso.getNavecuestaRecursoList().get(0), naveRecurso1);
		assertEquals(recurso.getNavecuestaRecursoList().get(1), naveRecurso2);
	}
	
	@Test
	public void testRecursoListaPlanetaHas()
	{
		Recurso recurso = new Recurso("Metal", "Mina");
		PlanetahasRecurso planetaRecurso1 = new PlanetahasRecurso(0, 1, "Atlas", "Metal", "Mina de Metal", 25);
		PlanetahasRecurso planetaRecurso2 = new PlanetahasRecurso(0, 2, "Atlas", "Metal", "Mina de Metal", 92);
		
		ArrayList<PlanetahasRecurso> listaPlanetas = new ArrayList<>();
		listaPlanetas.add(planetaRecurso1);
		listaPlanetas.add(planetaRecurso2);
		recurso.setPlanetahasRecursoList(listaPlanetas);
		
		assertEquals(recurso.getPlanetahasRecursoList().size(), 2);
		assertEquals(recurso.getPlanetahasRecursoList().get(0), planetaRecurso1);
		assertEquals(recurso.getPlanetahasRecursoList().get(1), planetaRecurso2);
	}
	
	@Test
	public void testPlanetaListaRecursoHas()
	{
		Planeta planeta = new Planeta(0, 1, "Atlas", "Planeta Centauri 1", "/ruta/imagen.png");
		PlanetahasRecurso planetaRecurso1 = new PlanetahasRecurso(0, 1, "Atlas", "Metal", "Mina de Metal", 25);
		PlanetahasRecurso planetaRecurso2 = new PlanetahasRecurso(0, 1, "Atlas", "Oro", "Mina de Oro", 92);
		
		ArrayList<PlanetahasRecurso> listaRecursos = new ArrayList<>();
		listaRecursos.add(planetaRecurso1);
		listaRecursos.add(planetaRecurso2);
		planeta.setPlanetahasRecursoList(listaRecursos);
		
		assertEquals(planeta.getPlanetahasRecursoList().size(), 2);
		assertEquals(planeta.getPlanetahasRecursoList().get(0), planetaRecurso1);
		assertEquals(planeta.getPlanetahasRecursoList().get(1), planetaRecurso2);
	}
	
	@Test
	public void testNaveListaPlanetaHas()
	{
		Nave nave = new Nave("Vulture", "/WEB-INF/images/image.png", 120, 200, 105, 20, 52, 10, 11, (short) 1);
		PlanetaHasNave planetaNave1 = new PlanetaHasNave(0, 1, "Atlas", "Vulture", 32);
		PlanetaHasNave planetaNave2 = new PlanetaHasNave(0, 2, "Atlas", "Vulture", 10);
		
		ArrayList<PlanetaHasNave> listaPlanetas = new ArrayList<>();
		listaPlanetas.add(planetaNave1);
		listaPlanetas.add(planetaNave2);
		nave.setPlanetaHasNaveList(listaPlanetas);
		
		assertEquals(nave.getPlanetaHasNaveList().size(), 2);
		assertEquals(nave.getPlanetaHasNaveList().get(0), planetaNave1);
		assertEquals(nave.getPlanetaHasNaveList().get(1), planetaNave2);
	}
	
	@Test
	public void testPlanetaListaNavesHas()
	{
		Planeta planeta = new Planeta(0, 1, "Atlas", "Planeta Centauri 1", "/ruta/imagen.png");
		PlanetaHasNave planetaNave1 = new PlanetaHasNave(0, 1, "Atlas", "Vulture", 32);
		PlanetaHasNave planetaNave2 = new PlanetaHasNave(0, 1, "Atlas", "Anaconda", 10);
		
		ArrayList<PlanetaHasNave> listaNaves = new ArrayList<>();
		listaNaves.add(planetaNave1);
		listaNaves.add(planetaNave2);
		planeta.setPlanetahasNaveList(listaNaves);
		
		assertEquals(planeta.getPlanetahasNaveList().size(), 2);
		assertEquals(planeta.getPlanetahasNaveList().get(0), planetaNave1);
		assertEquals(planeta.getPlanetahasNaveList().get(1), planetaNave2);
	}
	
	@Test
	public void testPlanetaListaInstalacionHas()
	{
		Date fechaAhora1 = new Date();
		Date fechaAhora2 = new Date();
		Planeta planeta = new Planeta(0, 1, "Atlas", "Planeta Centauri 1", "/ruta/imagen.png");
		PlanetahasInstalacion planetaInstalacion1 = new PlanetahasInstalacion(0, 1, "Atlas", "Mina de metal", 2, fechaAhora1);
		PlanetahasInstalacion planetaInstalacion2 = new PlanetahasInstalacion(0, 1, "Atlas", "Mina de oro", 3, fechaAhora2);
		
		ArrayList<PlanetahasInstalacion> listaInstalaciones = new ArrayList<>();
		listaInstalaciones.add(planetaInstalacion1);
		listaInstalaciones.add(planetaInstalacion2);
		planeta.setPlanetahasInstalacionList(listaInstalaciones);
		
		assertEquals(planeta.getPlanetahasInstalacionList().size(), 2);
		assertEquals(planeta.getPlanetahasInstalacionList().get(0), planetaInstalacion1);
		assertEquals(planeta.getPlanetahasInstalacionList().get(1), planetaInstalacion2);
	}
	
	@Test
	public void testInstalacionListaInstalacionCuesta()
	{
		Instalacion instalacion = new Instalacion("Mina", 5);
		InstalacioncuestaRecurso instalacionRecurso1 = new InstalacioncuestaRecurso("Mina", "Metal", "Mina",20);
		InstalacioncuestaRecurso instalacionRecurso2 = new InstalacioncuestaRecurso("Mina", "Petróleo", "Plataforma Petrolífera",60);
		
		ArrayList<InstalacioncuestaRecurso> listaInstalaciones = new ArrayList<>();
		listaInstalaciones.add(instalacionRecurso1);
		listaInstalaciones.add(instalacionRecurso2);
		instalacion.setInstalacioncuestaRecursoList(listaInstalaciones);
		
		assertEquals(instalacion.getInstalacioncuestaRecursoList().size(), 2);
		assertEquals(instalacion.getInstalacioncuestaRecursoList().get(0), instalacionRecurso1);
		assertEquals(instalacion.getInstalacioncuestaRecursoList().get(1), instalacionRecurso2);
	}
	
	

}
