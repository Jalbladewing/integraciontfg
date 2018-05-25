package com.tfgllopis.integracion;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationForTests.class) 
public class ModuloInformacionApplicationTests 
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
	public void testTipoMensaje()
	{
		TipoMensaje tipoM = new TipoMensaje("Sistema");
		
		assertEquals("Sistema", tipoM.getName());
	}
	
	@Test
	public void testMensaje()
	{
		Date fechaAhora = new Date();
		TipoMensaje tipoM = new TipoMensaje("Sistema");
		Mensaje mensaje = new Mensaje("Mantenimiento", "Mantenimiento el dia 15", fechaAhora);
	
		mensaje.setTipoMensajename(tipoM);
		
		assertEquals("Mantenimiento", mensaje.getAsunto());
		assertEquals("Mantenimiento el dia 15", mensaje.getDescripcion());
		assertEquals(fechaAhora, mensaje.getFechaEnvio());
		assertEquals(tipoM, mensaje.getTipoMensajename());
	}
	
	@Test
	public void testUsuarioHasMensaje()
	{		
		Date fechaAhora = new Date();
		Usuario user = new Usuario("juan@gmail.com", "Pepe", "1234", true, fechaAhora, fechaAhora);
		Mensaje mensaje = new Mensaje("Mantenimiento", "Mantenimiento el dia 15", fechaAhora);
		UsuariohasMensaje usuarioMensaje = new UsuariohasMensaje("Pepe", 1, (short) 0);
				
		usuarioMensaje.setUsuario(user);
		usuarioMensaje.setMensaje(mensaje);
		
		assertEquals("Pepe", usuarioMensaje.getUsuariousername());
		assertEquals(1, usuarioMensaje.getMensajeidMensaje());
		assertEquals(0, usuarioMensaje.getDescartado());
		assertEquals(user, usuarioMensaje.getUsuario());
		assertEquals(mensaje, usuarioMensaje.getMensaje());
	}
	
	@Test
	public void testUsuarioHasMensajePK()
	{
		UsuariohasMensajePK usuarioMensajePK = new UsuariohasMensajePK("Pepe", 1);
		UsuariohasMensaje usuarioMensaje = new UsuariohasMensaje(usuarioMensajePK, (short) 1);
		
		assertEquals("Pepe", usuarioMensaje.getUsuariohasMensajePK().getUsuariousername());
		assertEquals(1, usuarioMensaje.getUsuariohasMensajePK().getMensajeidMensaje());
		assertEquals(1, usuarioMensaje.getDescartado());
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
	public void testMensajeListaUsuarioHas()
	{
		Date fechaAhora = new Date();
		Mensaje mensaje = new Mensaje("Mantenimiento", "Mantenimiento el dia 15", fechaAhora);
		UsuariohasMensaje usuarioMensaje1 = new UsuariohasMensaje("Pepe", 1, (short) 0);
		UsuariohasMensaje usuarioMensaje2 = new UsuariohasMensaje("Jose", 1, (short) 1);
		
		ArrayList<UsuariohasMensaje> listaUsuarios = new ArrayList<>();
		listaUsuarios.add(usuarioMensaje1);
		listaUsuarios.add(usuarioMensaje2);
		mensaje.setUsuariohasMensajeList(listaUsuarios);
		
		assertEquals(mensaje.getUsuariohasMensajeList().size(), 2);
		assertEquals(mensaje.getUsuariohasMensajeList().get(0), usuarioMensaje1);
		assertEquals(mensaje.getUsuariohasMensajeList().get(1), usuarioMensaje2);
	}
	
	@Test
	public void testUsuarioListaMensajeHas()
	{
		Date fechaAhora = new Date();
		Usuario user = new Usuario("juan@gmail.com", "Pepe", "1234", true, fechaAhora, fechaAhora);
		UsuariohasMensaje usuarioMensaje1 = new UsuariohasMensaje("Pepe", 1, (short) 0);
		UsuariohasMensaje usuarioMensaje2 = new UsuariohasMensaje("Jose", 1, (short) 1);
		
		ArrayList<UsuariohasMensaje> listaUsuarios = new ArrayList<>();
		listaUsuarios.add(usuarioMensaje1);
		listaUsuarios.add(usuarioMensaje2);
		user.setUsuariohasMensajeList(listaUsuarios);
		
		assertEquals(user.getUsuariohasMensajeList().size(), 2);
		assertEquals(user.getUsuariohasMensajeList().get(0), usuarioMensaje1);
		assertEquals(user.getUsuariohasMensajeList().get(1), usuarioMensaje2);
	}
}
