package com.tfgllopis.integracion;

import static org.junit.Assert.*;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationForTests.class) 
public class ConstruirNave 
{
	@Autowired
	private UsuarioRepository userRepo;
	
	@Autowired
	private RolesRepository rolRepo;
	
	@Autowired
	private NaveRepository naveRepo;
	
	@Autowired
	private TipoNaveRepository tipoNaveRepo;
	
	@Autowired
	private NavecuestaRecursoRepository naveCuestaRepo;
	
	@Autowired
	private RecursoRepository recursoRepo;
	
	@Autowired
	private InstalacionRepository instalacionRepo;
	
	@Autowired
	private SistemaRepository sistemaRepo;
	
	@Autowired
	private PlanetaRepository planetaRepo;
	
	@Autowired
	private PlanetahasRecursoRepository planetaRecursoRepo;
	
	@Autowired
	private PlanetaHasNaveRepository planetaNaveRepo;
	
	@Autowired
	private UsuarioHasNaveRepository usuarioNaveRepo;
	
	@Autowired
	private UsuarioconstruyeNaveRepository construyeRepo;
	
	private Usuario usuario;
	private TipoNave tipoNave1;
	private Nave nave1, nave2, nave3;
	private Recurso recurso1, recurso2, recurso3;
	private Instalacion instalacion1, instalacion2, instalacion3;
	private PlanetahasRecurso planetaMetal, planetaOro, planetaPetroleo;
	
	@Before
	@Transactional
	public void setUp() 
	{	
		rolRepo.save(new Rol("Jugador"));
	       
	    usuario = new Usuario("juanito@ual.es", "juan","damn",true,new Date(), new Date());
	    usuario.setRolName(rolRepo.findByName("Jugador").get(0)); 
	    
	    userRepo.save(usuario);
		
		tipoNave1 = new TipoNave("Caza", "testUrl");
		 	
		tipoNaveRepo.save(tipoNave1);
		
		nave1 = new Nave("Eagle", "/WEB-INF/images/image.png", 120, 200, 105, 22, 52, 10, 11, (short) 0);
		nave1.setTipoNavenombreTipoNave(tipoNave1);
		
		nave2 = new Nave("Vulture", "/WEB-INF/images/image.png", 120, 200, 105, 22, 52, 10, 11, (short) 0);
		nave2.setTipoNavenombreTipoNave(tipoNave1);
		
		nave3 = new Nave("Anaconda", "/WEB-INF/images/image.png", 120, 200, 105, 22, 52, 10, 11, (short) 1);
		nave3.setTipoNavenombreTipoNave(tipoNave1);
		
		naveRepo.save(nave1);
		naveRepo.save(nave2);
		naveRepo.save(nave3);
		 	
		instalacion1 = new Instalacion("Mina de Metal", 20);
		instalacion2 = new Instalacion("Mina de Oro", 20);
		instalacion3 = new Instalacion("Plataforma Petrolifera", 20);
		
		instalacionRepo.save(instalacion1);
		instalacionRepo.save(instalacion2);
		instalacionRepo.save(instalacion3);
		
		recurso1 = new Recurso("Metal", "Mina de Metal");
		recurso2 = new Recurso("Oro", "Mina de Oro");
		recurso3 = new Recurso("Petroleo", "Plataforma Petrolifera");
		
		recursoRepo.save(recurso1);
		recursoRepo.save(recurso2);
		recursoRepo.save(recurso3);
		
		naveCuestaRepo.save(new NavecuestaRecurso("Eagle", "Metal", "Mina de Metal", 120));
		naveCuestaRepo.save(new NavecuestaRecurso("Eagle", "Oro", "Mina de Oro", 180));
		naveCuestaRepo.save(new NavecuestaRecurso("Eagle", "Petroleo", "Plataforma Petrolifera", 220));
		
		naveCuestaRepo.save(new NavecuestaRecurso("Vulture", "Metal", "Mina de Metal", 220));
		naveCuestaRepo.save(new NavecuestaRecurso("Vulture", "Oro", "Mina de Oro", 280));
		naveCuestaRepo.save(new NavecuestaRecurso("Vulture", "Petroleo", "Plataforma Petrolifera", 320));
		
		naveCuestaRepo.save(new NavecuestaRecurso("Anaconda", "Metal", "Mina de Metal", 320));
		naveCuestaRepo.save(new NavecuestaRecurso("Anaconda", "Oro", "Mina de Oro", 380));
		naveCuestaRepo.save(new NavecuestaRecurso("Anaconda", "Petroleo", "Plataforma Petrolifera", 420));

		Sistema sistema = new Sistema("Atlas", 0, 0);
		sistemaRepo.save(sistema);
		
		Planeta planeta = new Planeta(0, 0, "Atlas", "Planeta Pepe", "");
		planeta.setUsuariousername(usuario);
		planetaRepo.save(planeta);
		
		planetaMetal = new PlanetahasRecurso(0, 0, "Atlas", "Metal", "Mina de Metal", 2300);
		planetaRecursoRepo.save(planetaMetal);
		
		planetaOro = new PlanetahasRecurso(0, 0, "Atlas", "Oro", "Mina de Oro", 2570);
		planetaRecursoRepo.save(planetaOro);
		
		planetaPetroleo = new PlanetahasRecurso(0, 0, "Atlas", "Petroleo", "Plataforma Petrolifera", 3400);
		planetaRecursoRepo.save(planetaPetroleo);

	
	}
	
	@Transactional
	@Test
	public void testConstruirNaveCorrecto()
	{
		assertTrue(CrudNave.construirNave(nave1, usuario, "10", construyeRepo, planetaRepo, planetaRecursoRepo, naveRepo, naveCuestaRepo,usuarioNaveRepo).isEmpty());
		
		assertEquals(planetaRecursoRepo.findByPlanetaRecurso(0, 0, "Atlas", "Metal").getCantidad(), 1100);
		assertEquals(planetaRecursoRepo.findByPlanetaRecurso(0, 0, "Atlas", "Oro").getCantidad(), 770);
		assertEquals(planetaRecursoRepo.findByPlanetaRecurso(0, 0, "Atlas", "Petroleo").getCantidad(), 1200);
		assertEquals(construyeRepo.findByUsuariousername("juan").size(), 1);
		assertEquals(construyeRepo.findByUsuariousername("juan").get(0).getCantidad(), 10);
	}
	
	@Transactional
	@Test
	public void testConstruirNaveConstruyendose()
	{		
		assertTrue(CrudNave.construirNave(nave1, usuario, "10", construyeRepo, planetaRepo, planetaRecursoRepo, naveRepo, naveCuestaRepo, usuarioNaveRepo).isEmpty());
		assertFalse(CrudNave.construirNave(nave2, usuario, "5", construyeRepo, planetaRepo, planetaRecursoRepo, naveRepo, naveCuestaRepo, usuarioNaveRepo).isEmpty());
	}
	
	@Transactional
	@Test
	public void testConstruirNaveBloqueada()
	{
		assertFalse(CrudNave.construirNave(nave3, usuario, "5", construyeRepo, planetaRepo, planetaRecursoRepo, naveRepo, naveCuestaRepo, usuarioNaveRepo).isEmpty());
	}
	
	@Transactional
	@Test
	public void testConstruirNaveCantidadNegativa()
	{
		assertFalse(CrudNave.construirNave(nave1, usuario, "-10", construyeRepo, planetaRepo, planetaRecursoRepo, naveRepo, naveCuestaRepo, usuarioNaveRepo).isEmpty());
	}
	
	@Transactional
	@Test
	public void testConstruirNaveFaltaMetal()
	{
		planetaMetal.setCantidad(0);
		planetaRecursoRepo.save(planetaMetal);
		
		assertFalse(CrudNave.construirNave(nave1, usuario, "10", construyeRepo, planetaRepo, planetaRecursoRepo, naveRepo, naveCuestaRepo, usuarioNaveRepo).isEmpty());
	}
	
	@Transactional
	@Test
	public void testConstruirNaveFaltaOro()
	{
		planetaOro.setCantidad(0);
		planetaRecursoRepo.save(planetaOro);
		
		assertFalse(CrudNave.construirNave(nave1, usuario, "10", construyeRepo, planetaRepo, planetaRecursoRepo, naveRepo, naveCuestaRepo, usuarioNaveRepo).isEmpty());
	}
	
	@Transactional
	@Test
	public void testConstruirNaveFaltaPetroleo()
	{
		planetaPetroleo.setCantidad(0);
		planetaRecursoRepo.save(planetaPetroleo);
		
		assertFalse(CrudNave.construirNave(nave1, usuario, "10", construyeRepo, planetaRepo, planetaRecursoRepo, naveRepo, naveCuestaRepo, usuarioNaveRepo).isEmpty());
	}
	
	@Transactional
	@Test
	public void testCancelarConstruccionErrorNoNaves()
	{
		assertFalse(CrudNave.cancelarConstruccion(usuario, construyeRepo, planetaRepo, planetaNaveRepo, usuarioNaveRepo, planetaRecursoRepo, naveCuestaRepo).isEmpty());
	}
	
	@Transactional
	@Test
	public void testCheckConstruccionNoNaves()
	{
		assertFalse(CrudNave.checkConstruccion(usuario, construyeRepo, planetaRepo, planetaNaveRepo, usuarioNaveRepo, planetaRecursoRepo, naveCuestaRepo).isEmpty());
	}
	
	@Transactional
	@Test
	public void testCancelarConstruccionYaConstruidaVacio()
	{
		nave1.setSegundosConstruccion(0);
		naveRepo.save(nave1);

		assertTrue(CrudNave.construirNave(nave1, usuario, "10", construyeRepo, planetaRepo, planetaRecursoRepo, naveRepo, naveCuestaRepo, usuarioNaveRepo).isEmpty());
		assertFalse(CrudNave.cancelarConstruccion(usuario, construyeRepo, planetaRepo, planetaNaveRepo, usuarioNaveRepo, planetaRecursoRepo, naveCuestaRepo).isEmpty());
		
		assertEquals(10, usuarioNaveRepo.findByNavenombreNaveUsuarioUsername("Eagle", usuario.getUsername()).get(0).getCantidad());
		assertEquals(10, planetaNaveRepo.findByNavenombreNavePlaneta("Eagle", 0, 0, "Atlas").get(0).getCantidad());
	}
	
	@Transactional
	@Test
	public void testCancelarConstruccionYaConstruidaNoVacio()
	{
		nave1.setSegundosConstruccion(0);
		naveRepo.save(nave1);
		usuarioNaveRepo.save(new UsuarioHasNave("juan", "Eagle", 2));
		planetaNaveRepo.save(new PlanetaHasNave(0, 0, "Atlas", "Eagle", 5));
		
		assertTrue(CrudNave.construirNave(nave1, usuario, "10", construyeRepo, planetaRepo, planetaRecursoRepo, naveRepo, naveCuestaRepo, usuarioNaveRepo).isEmpty());
		assertFalse(CrudNave.cancelarConstruccion(usuario, construyeRepo, planetaRepo, planetaNaveRepo, usuarioNaveRepo, planetaRecursoRepo, naveCuestaRepo).isEmpty());
		
		assertEquals(12, usuarioNaveRepo.findByNavenombreNaveUsuarioUsername("Eagle", usuario.getUsername()).get(0).getCantidad());
		assertEquals(15, planetaNaveRepo.findByNavenombreNavePlaneta("Eagle", 0, 0, "Atlas").get(0).getCantidad());
	}
	
	@Transactional
	@Test
	public void testCheckConstruccionYaConstruidaVacio()
	{
		nave1.setSegundosConstruccion(0);
		naveRepo.save(nave1);

		assertTrue(CrudNave.construirNave(nave1, usuario, "10", construyeRepo, planetaRepo, planetaRecursoRepo, naveRepo, naveCuestaRepo, usuarioNaveRepo).isEmpty());
		assertFalse(CrudNave.checkConstruccion(usuario, construyeRepo, planetaRepo, planetaNaveRepo, usuarioNaveRepo, planetaRecursoRepo, naveCuestaRepo).isEmpty());
		
		assertEquals(10, usuarioNaveRepo.findByNavenombreNaveUsuarioUsername("Eagle", usuario.getUsername()).get(0).getCantidad());
		assertEquals(10, planetaNaveRepo.findByNavenombreNavePlaneta("Eagle", 0, 0, "Atlas").get(0).getCantidad());
	}
	
	@Transactional
	@Test
	public void testCheckConstruccionYaConstruidaNoVacio()
	{
		nave1.setSegundosConstruccion(0);
		naveRepo.save(nave1);
		usuarioNaveRepo.save(new UsuarioHasNave("juan", "Eagle", 2));
		planetaNaveRepo.save(new PlanetaHasNave(0, 0, "Atlas", "Eagle", 5));
		
		assertTrue(CrudNave.construirNave(nave1, usuario, "10", construyeRepo, planetaRepo, planetaRecursoRepo, naveRepo, naveCuestaRepo, usuarioNaveRepo).isEmpty());
		assertFalse(CrudNave.checkConstruccion(usuario, construyeRepo, planetaRepo, planetaNaveRepo, usuarioNaveRepo, planetaRecursoRepo, naveCuestaRepo).isEmpty());
		
		assertEquals(12, usuarioNaveRepo.findByNavenombreNaveUsuarioUsername("Eagle", usuario.getUsername()).get(0).getCantidad());
		assertEquals(15, planetaNaveRepo.findByNavenombreNavePlaneta("Eagle", 0, 0, "Atlas").get(0).getCantidad());
	}
	
	@Transactional
	@Test
	public void testCancelarConstruccionVacio()
	{
		nave1.setSegundosConstruccion(1);
		naveRepo.save(nave1);
		
		assertTrue(CrudNave.construirNave(nave1, usuario, "10", construyeRepo, planetaRepo, planetaRecursoRepo, naveRepo, naveCuestaRepo, usuarioNaveRepo).isEmpty());
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertTrue(CrudNave.cancelarConstruccion(usuario, construyeRepo, planetaRepo, planetaNaveRepo, usuarioNaveRepo, planetaRecursoRepo, naveCuestaRepo).isEmpty());
		
		assertEquals(3, usuarioNaveRepo.findByNavenombreNaveUsuarioUsername("Eagle", usuario.getUsername()).get(0).getCantidad());
		assertEquals(3, planetaNaveRepo.findByNavenombreNavePlaneta("Eagle", 0, 0, "Atlas").get(0).getCantidad());
		assertEquals(1940, planetaRecursoRepo.findByPlanetaRecurso(0, 0, "Atlas", "Metal").getCantidad());
		assertEquals(2030, planetaRecursoRepo.findByPlanetaRecurso(0, 0, "Atlas", "Oro").getCantidad());
		assertEquals(2740, planetaRecursoRepo.findByPlanetaRecurso(0, 0, "Atlas", "Petroleo").getCantidad());
		assertTrue(construyeRepo.findByUsuariousername(usuario.getUsername()).isEmpty());
	}
	
	@Transactional
	@Test
	public void testCancelarConstruccionNoVacio()
	{
		nave1.setSegundosConstruccion(1);
		naveRepo.save(nave1);
		usuarioNaveRepo.save(new UsuarioHasNave("juan", "Eagle", 2));
		planetaNaveRepo.save(new PlanetaHasNave(0, 0, "Atlas", "Eagle", 5));

		assertTrue(CrudNave.construirNave(nave1, usuario, "10", construyeRepo, planetaRepo, planetaRecursoRepo, naveRepo, naveCuestaRepo, usuarioNaveRepo).isEmpty());
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertTrue(CrudNave.cancelarConstruccion(usuario, construyeRepo, planetaRepo, planetaNaveRepo, usuarioNaveRepo, planetaRecursoRepo, naveCuestaRepo).isEmpty());
		
		assertEquals(5, usuarioNaveRepo.findByNavenombreNaveUsuarioUsername("Eagle", usuario.getUsername()).get(0).getCantidad());
		assertEquals(8, planetaNaveRepo.findByNavenombreNavePlaneta("Eagle", 0, 0, "Atlas").get(0).getCantidad());
		assertEquals(1940, planetaRecursoRepo.findByPlanetaRecurso(0, 0, "Atlas", "Metal").getCantidad());
		assertEquals(2030, planetaRecursoRepo.findByPlanetaRecurso(0, 0, "Atlas", "Oro").getCantidad());
		assertEquals(2740, planetaRecursoRepo.findByPlanetaRecurso(0, 0, "Atlas", "Petroleo").getCantidad());
		assertTrue(construyeRepo.findByUsuariousername(usuario.getUsername()).isEmpty());
	}
	
	@Transactional
	@Test
	public void testCheckConstruccionVacio()
	{
		nave1.setSegundosConstruccion(1);
		naveRepo.save(nave1);
		
		assertTrue(CrudNave.construirNave(nave1, usuario, "10", construyeRepo, planetaRepo, planetaRecursoRepo, naveRepo, naveCuestaRepo, usuarioNaveRepo).isEmpty());
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertTrue(CrudNave.checkConstruccion(usuario, construyeRepo, planetaRepo, planetaNaveRepo, usuarioNaveRepo, planetaRecursoRepo, naveCuestaRepo).isEmpty());
		
		assertEquals(3, usuarioNaveRepo.findByNavenombreNaveUsuarioUsername("Eagle", usuario.getUsername()).get(0).getCantidad());
		assertEquals(3, planetaNaveRepo.findByNavenombreNavePlaneta("Eagle", 0, 0, "Atlas").get(0).getCantidad());
	}
	
	@Transactional
	@Test
	public void testCheckConstruccionNoVacio()
	{
		nave1.setSegundosConstruccion(1);
		naveRepo.save(nave1);
		usuarioNaveRepo.save(new UsuarioHasNave("juan", "Eagle", 2));
		planetaNaveRepo.save(new PlanetaHasNave(0, 0, "Atlas", "Eagle", 5));

		assertTrue(CrudNave.construirNave(nave1, usuario, "10", construyeRepo, planetaRepo, planetaRecursoRepo, naveRepo, naveCuestaRepo, usuarioNaveRepo).isEmpty());
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertTrue(CrudNave.checkConstruccion(usuario, construyeRepo, planetaRepo, planetaNaveRepo, usuarioNaveRepo, planetaRecursoRepo, naveCuestaRepo).isEmpty());
		
		assertEquals(5, usuarioNaveRepo.findByNavenombreNaveUsuarioUsername("Eagle", usuario.getUsername()).get(0).getCantidad());
		assertEquals(8, planetaNaveRepo.findByNavenombreNavePlaneta("Eagle", 0, 0, "Atlas").get(0).getCantidad());
	}
}
