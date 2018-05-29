package com.tfgllopis.integracion;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class) 
@SpringBootTest(classes = ApplicationForTests.class)
public class ModuloAtaqueApplicationTests {

	@Test
	public void testInformeBatallaFull()
	{
		Date fechaAhora, fechaLuego;
		Calendar aux = Calendar.getInstance();
		
		aux.add(Calendar.HOUR_OF_DAY, 2);
		fechaAhora = new Date();
		fechaLuego = aux.getTime();
		
		InformeBatalla informe = new InformeBatalla();
		
		Movimiento movimiento = new Movimiento(fechaLuego, fechaAhora, (short) 0);
		Nave nave = new Nave("Vulture", "/WEB-INF/images/image.png", 120, 200, 105, 20, 52, 10, 11, (short) 1);
		
		informe.setMovimientoidMovimiento(movimiento);
		informe.setNavenombreNaveDesbloqueada(nave);
		
		assertEquals(movimiento, informe.getMovimientoidMovimiento());
		assertEquals(nave, informe.getNavenombreNaveDesbloqueada());
	}
	
	@Test
	public void testInformeListaPlaneta()
	{
		InformeBatalla informe = new InformeBatalla();
		
		ArrayList<Planeta> listaPlaneta = new ArrayList<>();
		Planeta planeta1 = new Planeta(0, 1, "Atlas", "Planeta Centauri 1", "/ruta/imagen.png");
		Planeta planeta2 = new Planeta(0, 2, "Atlas", "Planeta Centauri 2", "/ruta/imagen.png");
		listaPlaneta.add(planeta1);
		listaPlaneta.add(planeta2);
		informe.setPlanetaList(listaPlaneta);
		
		assertEquals(informe.getPlanetaList().size(), 2);
		assertEquals(informe.getPlanetaList().get(0), planeta1);
		assertEquals(informe.getPlanetaList().get(1), planeta2);
	}
	
	@Test
	public void testInformeListaMensaje()
	{
		Date fechaAhora = new Date();
		InformeBatalla informe = new InformeBatalla();
		
		ArrayList<Mensaje> listaMensaje = new ArrayList<>();
		Mensaje mensaje1 = new Mensaje("Mantenimiento", "Mantenimiento el dia 15", fechaAhora);
		Mensaje mensaje2 = new Mensaje("Mantenimiento2", "Mantenimiento el dia 16", fechaAhora);
		listaMensaje.add(mensaje1);
		listaMensaje.add(mensaje2);
		informe.setMensajeList(listaMensaje);
		
		assertEquals(informe.getMensajeList().size(), 2);
		assertEquals(informe.getMensajeList().get(0), mensaje1);
		assertEquals(informe.getMensajeList().get(1), mensaje2);
	}
	
	@Test
	public void testNaveListaInforme()
	{
		Nave nave = new Nave("Vulture", "/WEB-INF/images/image.png", 120, 200, 105, 20, 52, 10, 11, (short) 1);
	
		ArrayList<InformeBatalla> listaInformes = new ArrayList<>();
		InformeBatalla informe1 = new InformeBatalla();
		InformeBatalla informe2 = new InformeBatalla();
		listaInformes.add(informe1);
		listaInformes.add(informe2);
		nave.setInformeBatallaList(listaInformes);
		
		assertEquals(nave.getInformeBatallaList().size(), 2);
		assertEquals(nave.getInformeBatallaList().get(0), informe1);
		assertEquals(nave.getInformeBatallaList().get(1), informe2);
	}
	
	@Test
	public void testInformeBatallasHasNaveAtaqueFull()
	{
		InformeBatallahasNaveAtaque informeAtaque = new InformeBatallahasNaveAtaque(1, "Vulture", 50, 34);
		
		Nave nave = new Nave("Vulture", "/WEB-INF/images/image.png", 120, 200, 105, 20, 52, 10, 11, (short) 1);
		InformeBatalla informe = new InformeBatalla();
		
		informeAtaque.setNave(nave);
		informeAtaque.setInformeBatalla(informe);
		
		assertEquals(1, informeAtaque.getInformeBatallaidBatalla());
		assertEquals("Vulture", informeAtaque.getNavenombreNave());
		assertEquals(50, informeAtaque.getCantidadEnviada());
		assertEquals(34, informeAtaque.getCantidadPerdida());
		assertEquals(nave, informeAtaque.getNave());
		assertEquals(informe, informeAtaque.getInformeBatalla());
	}
	
	@Test
	public void testInformeBatallasHasNaveAtaquePK()
	{
		InformeBatallahasNaveAtaquePK informeAtaquePK = new InformeBatallahasNaveAtaquePK(1, "Vulture");

		InformeBatallahasNaveAtaque informeAtaque = new InformeBatallahasNaveAtaque(informeAtaquePK, 50, 34);
		
		assertEquals(1, informeAtaque.getInformeBatallahasNaveAtaquePK().getInformeBatallaidBatalla());
		assertEquals("Vulture", informeAtaque.getInformeBatallahasNaveAtaquePK().getNavenombreNave());
		assertEquals(50, informeAtaque.getCantidadEnviada());
		assertEquals(34, informeAtaque.getCantidadPerdida());
	}
	
	@Test
	public void testInformeBatallasHasRecursoFull()
	{
		InformeBatallahasRecurso informeRecurso = new InformeBatallahasRecurso(1, "Metal", "Mina de Metal", 580);
		
		Recurso recurso = new Recurso("Metal", "Mina");
		InformeBatalla informe = new InformeBatalla();
		
		informeRecurso.setRecurso(recurso);
		informeRecurso.setInformeBatalla(informe);
		
		assertEquals(1, informeRecurso.getInformeBatallaidBatalla());
		assertEquals("Metal", informeRecurso.getRecursoname());
		assertEquals("Mina de Metal", informeRecurso.getRecursoInstalacionname());
		assertEquals(580, informeRecurso.getCantidad());
		assertEquals(recurso, informeRecurso.getRecurso());
		assertEquals(informe, informeRecurso.getInformeBatalla());
	}
	
	@Test
	public void testInformeBatallasHasRecursoPK()
	{
		InformeBatallahasRecursoPK informeRecursoPK = new InformeBatallahasRecursoPK(1, "Metal", "Mina de Metal");
		InformeBatallahasRecurso informeRecurso = new InformeBatallahasRecurso(informeRecursoPK, 580);
		
		assertEquals(1, informeRecurso.getInformeBatallahasRecursoPK().getInformeBatallaidBatalla());
		assertEquals("Metal", informeRecurso.getInformeBatallahasRecursoPK().getRecursoname());
		assertEquals("Mina de Metal", informeRecurso.getInformeBatallahasRecursoPK().getRecursoInstalacionname());
		assertEquals(580, informeRecurso.getCantidad());
	}
	
	@Test
	public void testInformeBatallasHasNaveDefensaFull()
	{
		InformeBatallahasNaveDefensa informeDefensa = new InformeBatallahasNaveDefensa(1, "Vulture", 50, 34);
		
		Nave nave = new Nave("Vulture", "/WEB-INF/images/image.png", 120, 200, 105, 20, 52, 10, 11, (short) 1);
		InformeBatalla informe = new InformeBatalla();
		
		informeDefensa.setNave(nave);
		informeDefensa.setInformeBatalla(informe);
		
		assertEquals(1, informeDefensa.getInformeBatallaidBatalla());
		assertEquals("Vulture", informeDefensa.getNavenombreNave());
		assertEquals(50, informeDefensa.getCantidadEnviada());
		assertEquals(34, informeDefensa.getCantidadPerdida());
		assertEquals(nave, informeDefensa.getNave());
		assertEquals(informe, informeDefensa.getInformeBatalla());
	}
	
	@Test
	public void testInformeBatallasHasNaveDefensaPK()
	{
		InformeBatallahasNaveDefensaPK informeDefensaPK = new InformeBatallahasNaveDefensaPK(1, "Vulture");

		InformeBatallahasNaveDefensa informeDefensa = new InformeBatallahasNaveDefensa(informeDefensaPK, 50, 34);
		
		assertEquals(1, informeDefensa.getInformeBatallahasNaveDefensaPK().getInformeBatallaidBatalla());
		assertEquals("Vulture", informeDefensa.getInformeBatallahasNaveDefensaPK().getNavenombreNave());
		assertEquals(50, informeDefensa.getCantidadEnviada());
		assertEquals(34, informeDefensa.getCantidadPerdida());
	}
	
	@Test
	public void testMovimientoFull()
	{
		Date fechaAhora, fechaLuego;
		Calendar aux = Calendar.getInstance();
		
		aux.add(Calendar.HOUR_OF_DAY, 2);
		fechaAhora = new Date();
		fechaLuego = aux.getTime();
		
		Movimiento movimiento = new Movimiento(fechaLuego, fechaAhora, (short) 0);
	
		Usuario user = new Usuario("juan@gmail.com", "Pepe", "1234", true, fechaAhora, fechaAhora);
		Planeta planeta = new Planeta(0, 1, "Atlas", "Planeta Centauri 1", "/ruta/imagen.png");
		
		movimiento.setUsuariousername(user);
		movimiento.setPlaneta(planeta);
		
		assertEquals(fechaAhora, movimiento.getTiempoEnvio());
		assertEquals(fechaLuego, movimiento.getTiempoLlegada());
		assertEquals(0, movimiento.getMovimientoCancelado());
		assertEquals(user, movimiento.getUsuariousername());
		assertEquals(planeta, movimiento.getPlaneta());
	}
	
	@Test
	public void testMovimientoListaInforme()
	{
		Date fechaAhora, fechaLuego;
		Calendar aux = Calendar.getInstance();
		
		aux.add(Calendar.HOUR_OF_DAY, 2);
		fechaAhora = new Date();
		fechaLuego = aux.getTime();
		
		Movimiento movimiento = new Movimiento(fechaLuego, fechaAhora, (short) 0);
	
		ArrayList<InformeBatalla> listaInformes = new ArrayList<>();
		InformeBatalla informe1 = new InformeBatalla();
		InformeBatalla informe2 = new InformeBatalla();
		listaInformes.add(informe1);
		listaInformes.add(informe2);
		movimiento.setInformeBatallaList(listaInformes);
		
		assertEquals(movimiento.getInformeBatallaList().size(), 2);
		assertEquals(movimiento.getInformeBatallaList().get(0), informe1);
		assertEquals(movimiento.getInformeBatallaList().get(1), informe2);
	}
	
	@Test
	public void testMovimientoHasNaveFull()
	{
		Date fechaAhora, fechaLuego;
		Calendar aux = Calendar.getInstance();
		
		aux.add(Calendar.HOUR_OF_DAY, 2);
		fechaAhora = new Date();
		fechaLuego = aux.getTime();
		
		MovimientohasNave movimientoNave = new MovimientohasNave(1, "Eagle", 20);
	
		Movimiento movimiento = new Movimiento(fechaLuego, fechaAhora, (short) 0);
		Nave nave = new Nave("Vulture", "/WEB-INF/images/image.png", 120, 200, 105, 20, 52, 10, 11, (short) 1);
		
		movimientoNave.setMovimiento(movimiento);
		movimientoNave.setNave(nave);
		
		assertEquals(1, movimientoNave.getMovimientoidMovimiento());
		assertEquals("Eagle", movimientoNave.getNavenombreNave());
		assertEquals(20, movimientoNave.getCantidad());
		assertEquals(movimiento, movimientoNave.getMovimiento());
		assertEquals(nave, movimientoNave.getNave());
	}
	
	@Test
	public void testMovimientoHasNavePK()
	{
		MovimientohasNavePK movimientoNavePK = new MovimientohasNavePK(1, "Eagle");
		MovimientohasNave movimientoNave = new MovimientohasNave(movimientoNavePK, 20);
				
		assertEquals(1, movimientoNave.getMovimientohasNavePK().getMovimientoidMovimiento());
		assertEquals("Eagle", movimientoNave.getMovimientohasNavePK().getNavenombreNave());
		assertEquals(20, movimientoNave.getCantidad());
	}
	
	@Test
	public void testUsuarioListaMovimiento()
	{
		Date fechaAhora, fechaLuego;
		Calendar aux = Calendar.getInstance();
		
		aux.add(Calendar.HOUR_OF_DAY, 2);
		fechaAhora = new Date();
		fechaLuego = aux.getTime();
		
		Usuario user = new Usuario("juan@gmail.com", "Pepe", "1234", true, fechaAhora, fechaAhora);
		
		ArrayList<Movimiento> listaMovimientos = new ArrayList<>();
		Movimiento movimiento1 = new Movimiento(fechaLuego, fechaAhora, (short) 0);
		Movimiento movimiento2 = new Movimiento(fechaLuego, fechaAhora, (short) 1);
		listaMovimientos.add(movimiento1);
		listaMovimientos.add(movimiento2);
		user.setMovimientoList(listaMovimientos);
		
		assertEquals(user.getMovimientoList().size(), 2);
		assertEquals(user.getMovimientoList().get(0), movimiento1);
		assertEquals(user.getMovimientoList().get(1), movimiento2);
	}
	
	@Test
	public void testPlanetaInforme()
	{
		Planeta planeta = new Planeta(0, 1, "Atlas", "Planeta Centauri 1", "/ruta/imagen.png");

		InformeBatalla informe = new InformeBatalla();
		
		planeta.setInformeBatallaidBatalla(informe);
		
		assertEquals(informe, planeta.getInformeBatallaidBatalla());
	}
	
	@Test
	public void testPlanetaListaMovimiento()
	{
		Date fechaAhora, fechaLuego;
		Calendar aux = Calendar.getInstance();
		
		aux.add(Calendar.HOUR_OF_DAY, 2);
		fechaAhora = new Date();
		fechaLuego = aux.getTime();
		
		Planeta planeta = new Planeta(0, 1, "Atlas", "Planeta Centauri 1", "/ruta/imagen.png");
		
		ArrayList<Movimiento> listaMovimientos = new ArrayList<>();
		Movimiento movimiento1 = new Movimiento(fechaLuego, fechaAhora, (short) 0);
		Movimiento movimiento2 = new Movimiento(fechaLuego, fechaAhora, (short) 1);
		listaMovimientos.add(movimiento1);
		listaMovimientos.add(movimiento2);
		planeta.setMovimientoList(listaMovimientos);
		
		assertEquals(planeta.getMovimientoList().size(), 2);
		assertEquals(planeta.getMovimientoList().get(0), movimiento1);
		assertEquals(planeta.getMovimientoList().get(1), movimiento2);
	}
	
	@Test
	public void testMensajeInforme()
	{
		Date fechaAhora = new Date();
		Mensaje mensaje = new Mensaje("Mantenimiento", "Mantenimiento el dia 15", fechaAhora);

		InformeBatalla informe = new InformeBatalla();
		
		mensaje.setInformeBatallaidBatalla(informe);
		
		assertEquals(informe, mensaje.getInformeBatallaidBatalla());
	}
	
	@Test
	public void testInformeListaHasRecurso()
	{
		InformeBatalla informe = new InformeBatalla();

		ArrayList<InformeBatallahasRecurso> listaRecursos = new ArrayList<>();
		InformeBatallahasRecurso informeRecurso1 = new InformeBatallahasRecurso(1, "Metal", "Mina de Metal", 0);
		InformeBatallahasRecurso informeRecurso2 = new InformeBatallahasRecurso(2, "Metal", "Mina de Metal", 50);
		listaRecursos.add(informeRecurso1);
		listaRecursos.add(informeRecurso2);
		informe.setInformeBatallahasRecursoList(listaRecursos);
		
		assertEquals(informe.getInformeBatallahasRecursoList().size(), 2);
		assertEquals(informe.getInformeBatallahasRecursoList().get(0), informeRecurso1);
		assertEquals(informe.getInformeBatallahasRecursoList().get(1), informeRecurso2);
	}
	
	@Test
	public void testInformeListaHasInformeAtaque()
	{
		InformeBatalla informe = new InformeBatalla();
		
		ArrayList<InformeBatallahasNaveAtaque> listaInformes = new ArrayList<>();
		InformeBatallahasNaveAtaque informeAtaque1 = new InformeBatallahasNaveAtaque(1, "Vulture", 50, 34);
		InformeBatallahasNaveAtaque informeAtaque2 = new InformeBatallahasNaveAtaque(2, "Vulture", 60, 24);
		listaInformes.add(informeAtaque1);
		listaInformes.add(informeAtaque2);
		informe.setInformeBatallahasNaveAtaqueList(listaInformes);
		
		assertEquals(informe.getInformeBatallahasNaveAtaqueList().size(), 2);
		assertEquals(informe.getInformeBatallahasNaveAtaqueList().get(0), informeAtaque1);
		assertEquals(informe.getInformeBatallahasNaveAtaqueList().get(1), informeAtaque2);
	}
	
	@Test
	public void testInformeListaHasInformeDefensa()
	{
		InformeBatalla informe = new InformeBatalla();
		
		ArrayList<InformeBatallahasNaveDefensa> listaInformes = new ArrayList<>();
		InformeBatallahasNaveDefensa informeDefensa1 = new InformeBatallahasNaveDefensa(1, "Vulture", 600, 34);
		InformeBatallahasNaveDefensa informeDefensa2 = new InformeBatallahasNaveDefensa(2, "Vulture", 609, 24);
		listaInformes.add(informeDefensa1);
		listaInformes.add(informeDefensa2);
		informe.setInformeBatallahasNaveDefensaList(listaInformes);
		
		assertEquals(informe.getInformeBatallahasNaveDefensaList().size(), 2);
		assertEquals(informe.getInformeBatallahasNaveDefensaList().get(0), informeDefensa1);
		assertEquals(informe.getInformeBatallahasNaveDefensaList().get(1), informeDefensa2);
	}
	
	@Test
	public void testNaveListaHasInformeAtaque()
	{
		Nave nave = new Nave("Vulture", "/WEB-INF/images/image.png", 120, 200, 105, 20, 52, 10, 11, (short) 1);
	
		ArrayList<InformeBatallahasNaveAtaque> listaInformes = new ArrayList<>();
		InformeBatallahasNaveAtaque informeAtaque1 = new InformeBatallahasNaveAtaque(1, "Vulture", 50, 34);
		InformeBatallahasNaveAtaque informeAtaque2 = new InformeBatallahasNaveAtaque(2, "Vulture", 60, 24);
		listaInformes.add(informeAtaque1);
		listaInformes.add(informeAtaque2);
		nave.setInformeBatallahasNaveAtaqueList(listaInformes);
		
		assertEquals(nave.getInformeBatallahasNaveAtaqueList().size(), 2);
		assertEquals(nave.getInformeBatallahasNaveAtaqueList().get(0), informeAtaque1);
		assertEquals(nave.getInformeBatallahasNaveAtaqueList().get(1), informeAtaque2);
	}
	
	@Test
	public void testNaveListaHasInformeDefensa()
	{
		Nave nave = new Nave("Vulture", "/WEB-INF/images/image.png", 120, 200, 105, 20, 52, 10, 11, (short) 1);
	
		ArrayList<InformeBatallahasNaveDefensa> listaInformes = new ArrayList<>();
		InformeBatallahasNaveDefensa informeDefensa1 = new InformeBatallahasNaveDefensa(1, "Vulture", 600, 34);
		InformeBatallahasNaveDefensa informeDefensa2 = new InformeBatallahasNaveDefensa(2, "Vulture", 609, 24);
		listaInformes.add(informeDefensa1);
		listaInformes.add(informeDefensa2);
		nave.setInformeBatallahasNaveDefensaList(listaInformes);
		
		assertEquals(nave.getInformeBatallahasNaveDefensaList().size(), 2);
		assertEquals(nave.getInformeBatallahasNaveDefensaList().get(0), informeDefensa1);
		assertEquals(nave.getInformeBatallahasNaveDefensaList().get(1), informeDefensa2);
	}
	
	@Test
	public void testMovimientoListaHasNave()
	{
		Date fechaAhora, fechaLuego;
		Calendar aux = Calendar.getInstance();
		
		aux.add(Calendar.HOUR_OF_DAY, 2);
		fechaAhora = new Date();
		fechaLuego = aux.getTime();
		
		Movimiento movimiento = new Movimiento(fechaLuego, fechaAhora, (short) 0);
	
		ArrayList<MovimientohasNave> listaNaves = new ArrayList<>();
		MovimientohasNave movimientoNave1 = new MovimientohasNave(1, "Eagle", 20);
		MovimientohasNave movimientoNave2 = new MovimientohasNave(1, "Vulture", 50);
		listaNaves.add(movimientoNave1);
		listaNaves.add(movimientoNave2);
		movimiento.setMovimientohasNaveList(listaNaves);
		
		assertEquals(movimiento.getMovimientohasNaveList().size(), 2);
		assertEquals(movimiento.getMovimientohasNaveList().get(0), movimientoNave1);
		assertEquals(movimiento.getMovimientohasNaveList().get(1), movimientoNave2);
	}
	
	@Test
	public void testRecursoListaHasRecurso()
	{
		Recurso recurso = new Recurso("Metal", "Mina de Metal");

		ArrayList<InformeBatallahasRecurso> listaRecursos = new ArrayList<>();
		InformeBatallahasRecurso informeRecurso1 = new InformeBatallahasRecurso(1, "Metal", "Mina de Metal", 0);
		InformeBatallahasRecurso informeRecurso2 = new InformeBatallahasRecurso(2, "Metal", "Mina de Metal", 50);
		listaRecursos.add(informeRecurso1);
		listaRecursos.add(informeRecurso2);
		recurso.setInformeBatallahasRecursoList(listaRecursos);
		
		assertEquals(recurso.getInformeBatallahasRecursoList().size(), 2);
		assertEquals(recurso.getInformeBatallahasRecursoList().get(0), informeRecurso1);
		assertEquals(recurso.getInformeBatallahasRecursoList().get(1), informeRecurso2);
	}
	
}
