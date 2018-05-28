package com.tfgllopis.integracion;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationForTests.class) 
public class AtaqueTests 
{
	@Autowired
	private RolesRepository rolRepo;
	
	@Autowired
	private UsuarioRepository usuarioRepo;
	
	@Autowired
	private TipoNaveRepository tipoNaveRepo;
	
	@Autowired
	private NaveRepository naveRepo;
	
	@Autowired
	private SistemaRepository sistemaRepo;
	
	@Autowired 
	private PlanetaRepository planetaRepo;
	
	@Autowired
	private MovimientoRepository movimientoRepo;
	
	@Autowired
	private InformeBatallaRepository informeRepo;
	
	@Autowired
	private InformeBatallahasNaveDefensaRepository informeDefensaRepo;
	
	private Usuario usuario, usuario2, usuario3, usuario4;
	private Planeta planeta1, planeta2, planeta3, planeta4, planeta5, planeta6;
	
	@Before
	@Transactional
	public void setUp() 
	{		
		ArrayList<InformeBatalla> auxBatalla = new ArrayList<>();
		ArrayList<InformeBatallahasNaveDefensa> auxDefensa = new ArrayList<>();
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.HOUR_OF_DAY, -20);
		Date fechaAhora = new Date();
		Date fecha20Horas = calendar.getTime();
		calendar = Calendar.getInstance();
		calendar.add(Calendar.HOUR_OF_DAY, -5);
		Date fecha5Horas = calendar.getTime();
		calendar = Calendar.getInstance();
		calendar.add(Calendar.HOUR_OF_DAY, -4);
		Date fecha4Horas = calendar.getTime();
		calendar = Calendar.getInstance();
		calendar.add(Calendar.HOUR_OF_DAY, -3);
		Date fecha3Horas = calendar.getTime();
		calendar = Calendar.getInstance();
		calendar.add(Calendar.HOUR_OF_DAY, -2);
		Date fecha2Horas = calendar.getTime();
		calendar = Calendar.getInstance();
		calendar.add(Calendar.HOUR_OF_DAY, -1);
		Date fecha1Horas = calendar.getTime();
		Rol rol = new Rol("Jugador");
		usuario = new Usuario("juan@gmail.com", "Pepe", "1234", true, fechaAhora, fechaAhora);
		usuario2 = new Usuario("paco@gmail.com", "Paco", "1234", true, fechaAhora, fechaAhora);
		usuario3 = new Usuario("laurencio@gmail.com", "Laurencio", "1234", true, fechaAhora, fechaAhora);
		usuario4 = new Usuario("josefina@gmail.com", "Josefina", "1234", true, fechaAhora, fechaAhora);
		usuario.setRolName(rol);
		usuario2.setRolName(rol);
		usuario3.setRolName(rol);
		usuario4.setRolName(rol);
		
		rolRepo.save(rol);
		usuarioRepo.save(usuario);
		usuarioRepo.save(usuario2);
		usuarioRepo.save(usuario3);
		usuarioRepo.save(usuario4);

		TipoNave tipoNave = new TipoNave("Caza", "/WEB-INF/images/image.png");
		tipoNaveRepo.save(tipoNave);
		
		Nave nave = new Nave("Vulture", "/WEB-INF/images/image.png", 120, 200, 105, 20, 52, 10, 11, (short) 1);
		nave.setTipoNavenombreTipoNave(tipoNave);
		naveRepo.save(nave);
		
		Sistema sistema = new Sistema("Atlas", 0, 0);
		planeta1 = new Planeta(0, 0, "Atlas", "Planeta de Pepe", "ruta");
		planeta1.setUsuariousername(usuario);
		planeta2 = new Planeta(0, 1, "Atlas", "Planeta de Jose", "ruta");
		planeta3 = new Planeta(0, 2, "Atlas", "Planeta de Leopoldo", "ruta");
		planeta4 = new Planeta(0, 3, "Atlas", "Planeta de Paco", "ruta");
		planeta4.setUsuariousername(usuario2);
		planeta5 = new Planeta(0, 4, "Atlas", "Planeta de Laurencio", "ruta");
		planeta5.setUsuariousername(usuario3);
		planeta6 = new Planeta(0, 5, "Atlas", "Planeta de Josefina", "ruta");
		planeta6.setUsuariousername(usuario4);
		sistemaRepo.save(sistema);
		planetaRepo.save(planeta1);
		planetaRepo.save(planeta2);
		planetaRepo.save(planeta3);
		planetaRepo.save(planeta4);
		planetaRepo.save(planeta5);
		planetaRepo.save(planeta6);

		
		/*Planeta 3*/
		Movimiento movimiento = new Movimiento(fecha20Horas, fechaAhora, (short) 0);
		movimiento.setUsuariousername(usuario);
		movimiento.setPlaneta(planeta3);
		movimiento = movimientoRepo.save(movimiento);		
		
		InformeBatalla informe = new InformeBatalla();
		informe.setMovimientoidMovimiento(movimiento);
		informe = informeRepo.save(informe);
		InformeBatallahasNaveDefensa informeDefensa = new InformeBatallahasNaveDefensa(informe.getIdBatalla(),"Vulture", 20, 20);
		informeDefensaRepo.save(informeDefensa);
		
		auxDefensa.add(informeDefensa);
		informe.setInformeBatallahasNaveDefensaList(auxDefensa);
		informeRepo.save(informe);
		
		auxBatalla.add(informe);
		movimiento.setInformeBatallaList(auxBatalla);
		movimientoRepo.save(movimiento);

		planeta3.setInformeBatallaidBatalla(informe);
		planetaRepo.save(planeta3);
		
		auxBatalla = new ArrayList<>();
		auxDefensa = new ArrayList<>();
		
		/*Planeta 4*/
		planeta4.setInformeBatallaidBatalla(informe);
		planetaRepo.save(planeta4);
		
		movimiento = new Movimiento(fecha20Horas, fechaAhora, (short) 0);
		movimiento.setUsuariousername(usuario2);
		movimiento.setPlaneta(planeta4);
		movimiento = movimientoRepo.save(movimiento);		
		
		informe = new InformeBatalla();
		informe.setMovimientoidMovimiento(movimiento);
		informe = informeRepo.save(informe);
		informeDefensa = new InformeBatallahasNaveDefensa(informe.getIdBatalla(),"Vulture", 20, 20);
		informeDefensaRepo.save(informeDefensa);
		
		auxDefensa.add(informeDefensa);
		informe.setInformeBatallahasNaveDefensaList(auxDefensa);
		informeRepo.save(informe);
		
		auxBatalla.add(informe);
		movimiento.setInformeBatallaList(auxBatalla);
		movimientoRepo.save(movimiento);

		auxBatalla = new ArrayList<>();
		auxDefensa = new ArrayList<>();
		
		movimiento = new Movimiento(fecha5Horas, fechaAhora, (short) 0);
		movimiento.setUsuariousername(usuario2);
		movimiento.setPlaneta(planeta4);
		movimiento = movimientoRepo.save(movimiento);		
		
		informe = new InformeBatalla();
		informe.setMovimientoidMovimiento(movimiento);
		informe = informeRepo.save(informe);
		informeDefensa = new InformeBatallahasNaveDefensa(informe.getIdBatalla(),"Vulture", 20, 20);
		informeDefensaRepo.save(informeDefensa);
		
		auxDefensa.add(informeDefensa);
		informe.setInformeBatallahasNaveDefensaList(auxDefensa);
		informeRepo.save(informe);
		
		auxBatalla.add(informe);
		movimiento.setInformeBatallaList(auxBatalla);
		movimientoRepo.save(movimiento);
		
		planeta4.setInformeBatallaidBatalla(informe);
		planetaRepo.save(planeta4);
		
		auxBatalla = new ArrayList<>();
		auxDefensa = new ArrayList<>();
		
		/*Planeta 5*/
		planeta5.setInformeBatallaidBatalla(informe);
		planetaRepo.save(planeta5);
		
		movimiento = new Movimiento(fecha5Horas, fechaAhora, (short) 0);
		movimiento.setUsuariousername(usuario3);
		movimiento.setPlaneta(planeta5);
		movimiento = movimientoRepo.save(movimiento);		
		
		informe = new InformeBatalla();
		informe.setMovimientoidMovimiento(movimiento);
		informe = informeRepo.save(informe);
		informeDefensa = new InformeBatallahasNaveDefensa(informe.getIdBatalla(),"Vulture", 20, 20);
		informeDefensaRepo.save(informeDefensa);
		
		auxDefensa.add(informeDefensa);
		informe.setInformeBatallahasNaveDefensaList(auxDefensa);
		informeRepo.save(informe);
		
		auxBatalla.add(informe);
		movimiento.setInformeBatallaList(auxBatalla);
		movimientoRepo.save(movimiento);

		auxBatalla = new ArrayList<>();
		auxDefensa = new ArrayList<>();
				
		movimiento = new Movimiento(fecha4Horas, fechaAhora, (short) 0);
		movimiento.setUsuariousername(usuario3);
		movimiento.setPlaneta(planeta5);
		movimiento = movimientoRepo.save(movimiento);		
		
		informe = new InformeBatalla();
		informe.setMovimientoidMovimiento(movimiento);
		informe = informeRepo.save(informe);
		informeDefensa = new InformeBatallahasNaveDefensa(informe.getIdBatalla(),"Vulture", 20, 20);
		informeDefensaRepo.save(informeDefensa);
		
		auxDefensa.add(informeDefensa);
		informe.setInformeBatallahasNaveDefensaList(auxDefensa);
		informeRepo.save(informe);
		
		auxBatalla.add(informe);
		movimiento.setInformeBatallaList(auxBatalla);
		movimientoRepo.save(movimiento);

		auxBatalla = new ArrayList<>();
		auxDefensa = new ArrayList<>();
				
		movimiento = new Movimiento(fecha3Horas, fechaAhora, (short) 0);
		movimiento.setUsuariousername(usuario3);
		movimiento.setPlaneta(planeta5);
		movimiento = movimientoRepo.save(movimiento);		
		
		informe = new InformeBatalla();
		informe.setMovimientoidMovimiento(movimiento);
		informe = informeRepo.save(informe);
		informeDefensa = new InformeBatallahasNaveDefensa(informe.getIdBatalla(),"Vulture", 20, 20);
		informeDefensaRepo.save(informeDefensa);
		
		auxDefensa.add(informeDefensa);
		informe.setInformeBatallahasNaveDefensaList(auxDefensa);
		informeRepo.save(informe);
		
		auxBatalla.add(informe);
		movimiento.setInformeBatallaList(auxBatalla);
		movimientoRepo.save(movimiento);

		auxBatalla = new ArrayList<>();
		auxDefensa = new ArrayList<>();
		
		movimiento = new Movimiento(fecha2Horas, fechaAhora, (short) 0);
		movimiento.setUsuariousername(usuario3);
		movimiento.setPlaneta(planeta5);
		movimiento = movimientoRepo.save(movimiento);		
		
		informe = new InformeBatalla();
		informe.setMovimientoidMovimiento(movimiento);
		informe = informeRepo.save(informe);
		informeDefensa = new InformeBatallahasNaveDefensa(informe.getIdBatalla(),"Vulture", 20, 20);
		informeDefensaRepo.save(informeDefensa);
		
		auxDefensa.add(informeDefensa);
		informe.setInformeBatallahasNaveDefensaList(auxDefensa);
		informeRepo.save(informe);
		
		auxBatalla.add(informe);
		movimiento.setInformeBatallaList(auxBatalla);
		movimientoRepo.save(movimiento);

		auxBatalla = new ArrayList<>();
		auxDefensa = new ArrayList<>();
		
		movimiento = new Movimiento(fecha1Horas, fechaAhora, (short) 0);
		movimiento.setUsuariousername(usuario3);
		movimiento.setPlaneta(planeta5);
		movimiento = movimientoRepo.save(movimiento);		
		
		informe = new InformeBatalla();
		informe.setMovimientoidMovimiento(movimiento);
		informe = informeRepo.save(informe);
		informeDefensa = new InformeBatallahasNaveDefensa(informe.getIdBatalla(),"Vulture", 20, 20);
		informeDefensaRepo.save(informeDefensa);
		
		auxDefensa.add(informeDefensa);
		informe.setInformeBatallahasNaveDefensaList(auxDefensa);
		informeRepo.save(informe);
		
		auxBatalla.add(informe);
		movimiento.setInformeBatallaList(auxBatalla);
		movimientoRepo.save(movimiento);
		
		planeta5.setInformeBatallaidBatalla(informe);
		planetaRepo.save(planeta5);
		
		auxBatalla = new ArrayList<>();
		auxDefensa = new ArrayList<>();
		
		/*Planeta 6*/
		planeta5.setInformeBatallaidBatalla(informe);
		planetaRepo.save(planeta6);
		
		movimiento = new Movimiento(fecha5Horas, fechaAhora, (short) 0);
		movimiento.setUsuariousername(usuario4);
		movimiento.setPlaneta(planeta6);
		movimiento = movimientoRepo.save(movimiento);		
		
		informe = new InformeBatalla();
		informe.setMovimientoidMovimiento(movimiento);
		informe = informeRepo.save(informe);
		informeDefensa = new InformeBatallahasNaveDefensa(informe.getIdBatalla(),"Vulture", 20, 0);
		informeDefensaRepo.save(informeDefensa);
		
		auxDefensa.add(informeDefensa);
		informe.setInformeBatallahasNaveDefensaList(auxDefensa);
		informeRepo.save(informe);
		
		auxBatalla.add(informe);
		movimiento.setInformeBatallaList(auxBatalla);
		movimientoRepo.save(movimiento);

		auxBatalla = new ArrayList<>();
		auxDefensa = new ArrayList<>();
				
		movimiento = new Movimiento(fecha4Horas, fechaAhora, (short) 0);
		movimiento.setUsuariousername(usuario4);
		movimiento.setPlaneta(planeta6);
		movimiento = movimientoRepo.save(movimiento);		
		
		informe = new InformeBatalla();
		informe.setMovimientoidMovimiento(movimiento);
		informe = informeRepo.save(informe);
		informeDefensa = new InformeBatallahasNaveDefensa(informe.getIdBatalla(),"Vulture", 20, 20);
		informeDefensaRepo.save(informeDefensa);
		
		auxDefensa.add(informeDefensa);
		informe.setInformeBatallahasNaveDefensaList(auxDefensa);
		informeRepo.save(informe);
		
		auxBatalla.add(informe);
		movimiento.setInformeBatallaList(auxBatalla);
		movimientoRepo.save(movimiento);

		auxBatalla = new ArrayList<>();
		auxDefensa = new ArrayList<>();
				
		movimiento = new Movimiento(fecha3Horas, fechaAhora, (short) 0);
		movimiento.setUsuariousername(usuario4);
		movimiento.setPlaneta(planeta6);
		movimiento = movimientoRepo.save(movimiento);		
		
		informe = new InformeBatalla();
		informe.setMovimientoidMovimiento(movimiento);
		informe = informeRepo.save(informe);
		informeDefensa = new InformeBatallahasNaveDefensa(informe.getIdBatalla(),"Vulture", 20, 20);
		informeDefensaRepo.save(informeDefensa);
		
		auxDefensa.add(informeDefensa);
		informe.setInformeBatallahasNaveDefensaList(auxDefensa);
		informeRepo.save(informe);
		
		auxBatalla.add(informe);
		movimiento.setInformeBatallaList(auxBatalla);
		movimientoRepo.save(movimiento);

		auxBatalla = new ArrayList<>();
		auxDefensa = new ArrayList<>();
		
		movimiento = new Movimiento(fecha2Horas, fechaAhora, (short) 0);
		movimiento.setUsuariousername(usuario4);
		movimiento.setPlaneta(planeta6);
		movimiento = movimientoRepo.save(movimiento);		
		
		informe = new InformeBatalla();
		informe.setMovimientoidMovimiento(movimiento);
		informe = informeRepo.save(informe);
		informeDefensa = new InformeBatallahasNaveDefensa(informe.getIdBatalla(),"Vulture", 20, 20);
		informeDefensaRepo.save(informeDefensa);
		
		auxDefensa.add(informeDefensa);
		informe.setInformeBatallahasNaveDefensaList(auxDefensa);
		informeRepo.save(informe);
		
		auxBatalla.add(informe);
		movimiento.setInformeBatallaList(auxBatalla);
		movimientoRepo.save(movimiento);

		auxBatalla = new ArrayList<>();
		auxDefensa = new ArrayList<>();
		
		movimiento = new Movimiento(fecha1Horas, fechaAhora, (short) 0);
		movimiento.setUsuariousername(usuario4);
		movimiento.setPlaneta(planeta6);
		movimiento = movimientoRepo.save(movimiento);		
		
		informe = new InformeBatalla();
		informe.setMovimientoidMovimiento(movimiento);
		informe = informeRepo.save(informe);
		informeDefensa = new InformeBatallahasNaveDefensa(informe.getIdBatalla(),"Vulture", 20, 20);
		informeDefensaRepo.save(informeDefensa);
		
		auxDefensa.add(informeDefensa);
		informe.setInformeBatallahasNaveDefensaList(auxDefensa);
		informeRepo.save(informe);
		
		auxBatalla.add(informe);
		movimiento.setInformeBatallaList(auxBatalla);
		movimientoRepo.save(movimiento);
		
		planeta6.setInformeBatallaidBatalla(informe);
		planetaRepo.save(planeta6);
		
		auxBatalla = new ArrayList<>();
		auxDefensa = new ArrayList<>();
	}

	@Test
	public void comprobarPlanetaPropio() 
	{
		assertFalse(Galaxia.comprobarPlanetaDisponible(usuario, planeta1, movimientoRepo, informeRepo, informeDefensaRepo).isEmpty());
	}
	
	@Transactional
	@Test
	public void comprobarNoAtacado()
	{
		assertTrue(Galaxia.comprobarPlanetaDisponible(usuario, planeta2, movimientoRepo, informeRepo, informeDefensaRepo).isEmpty());
	}
	
	@Transactional
	@Test
	public void comprobarUltimoAtaqueMenos8Horas()
	{
		assertTrue(Galaxia.comprobarPlanetaDisponible(usuario, planeta3, movimientoRepo, informeRepo, informeDefensaRepo).isEmpty());
	}
	
	@Transactional
	@Test
	public void comprobarSinProteccionHoras()
	{
		assertTrue(Galaxia.comprobarPlanetaDisponible(usuario, planeta4, movimientoRepo, informeRepo, informeDefensaRepo).isEmpty());
	}
	
	@Transactional
	@Test
	public void comprobarConProteccion()
	{
		assertFalse(Galaxia.comprobarPlanetaDisponible(usuario, planeta5, movimientoRepo, informeRepo, informeDefensaRepo).isEmpty());
	}
	
	@Transactional
	@Test
	public void comprobarSinProteccionVictoria()
	{
		assertTrue(Galaxia.comprobarPlanetaDisponible(usuario, planeta6, movimientoRepo, informeRepo, informeDefensaRepo).isEmpty());
	}

}
