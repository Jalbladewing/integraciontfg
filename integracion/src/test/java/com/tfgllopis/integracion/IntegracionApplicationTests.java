package com.tfgllopis.integracion;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class) 
@SpringBootTest(classes = ApplicationForTests.class)
public class IntegracionApplicationTests 
{

	@Autowired
	private UsuarioRepository repo;
	
	@Autowired
	private RolesRepository rolRepo;

	@Autowired
	private PirataRepository pirataRepo;
	
	@Autowired
	private RecursoRepository recursoRepo;
	
	@Autowired
	private InstalacionRepository instalacionRepo;
	
	@Autowired
	private TipoNaveRepository tipoNaveRepo;
	
	@Autowired
	private NaveRepository naveRepo;
	
	@Autowired
	private PiratahasInstalacionRepository pirataInstalacionRepo;
	
	@Autowired
	private PiratahasNaveRepository pirataNaveRepo;
	
	@Autowired
	private SistemaRepository sistemaRepo;
	
	@Autowired
	private PlanetaRepository planetaRepo;
	
	@Autowired
	private PlanetahasRecursoRepository planetaRecursoRepo;
	
	@Autowired
	private PlanetahasInstalacionRepository planetaInstalacionRepo;
	
	@Autowired
	private PlanetaHasNaveRepository planetaNaveRepo;
	
	private TipoNave tipoNave1;
	private Instalacion instalacion1, instalacion2, instalacion3;
	
	private Usuario usuario1, usuario2;
	private Rol rol1, rol2;
	
	private Pirata pirata;
	private PiratahasInstalacion pirataInstalacion1, pirataInstalacion2, pirataInstalacion3;
	private PiratahasNave pirataNave;
	private Planeta planeta1;
	
	
	@Before
	@Transactional
	public void setUp() 
	{
	       
		 rol1 = new Rol("admin");
		 rol2 = new Rol("Jugador");
		 	
		 	
		 rolRepo.save(rol1);
		 rolRepo.save(rol2);
		 	
		 	
	     usuario1 = new Usuario("root@ual.es", "root","1234",true,new Date(), new Date());
	     usuario1.setRolName(rolRepo.findByName("admin").get(0));
	       
	     usuario2 = new Usuario("juanito@ual.es", "juan","damn",true,new Date(), new Date());
	     usuario2.setRolName(rolRepo.findByName("Jugador").get(0)); 
	       
	     repo.deleteAll();
	     repo.save(usuario1);
	     repo.save(usuario2);
	     
	     tipoNave1 = new TipoNave("Caza", "testUrl");
		 	
	     tipoNaveRepo.save(tipoNave1);
			
	     Nave nave1 = new Nave("Eagle", "/WEB-INF/images/image.png", 120, 200, 105, 22, 52, 10, 11, (short) 1);
	     nave1.setTipoNavenombreTipoNave(tipoNave1);
			
	     Nave nave2 = new Nave("Vulture", "/WEB-INF/images/image.png", 120, 200, 105, 22, 52, 10, 11, (short) 1);
	     nave2.setTipoNavenombreTipoNave(tipoNave1);
			
	     naveRepo.save(nave1);
		 naveRepo.save(nave2);
			
		 instalacion1 = new Instalacion("Mina de Metal", 20);
		 instalacion2 = new Instalacion("Mina de Oro", 20);
		 instalacion3 = new Instalacion("Plataforma Petrolifera", 20);
		 
		 instalacionRepo.save(instalacion1);
		 instalacionRepo.save(instalacion2);
		 instalacionRepo.save(instalacion3);
		 
		 recursoRepo.save(new Recurso("Metal", "Mina de Metal"));
		 recursoRepo.save(new Recurso("Oro", "Mina de Oro"));
		 recursoRepo.save(new Recurso("Petroleo", "Plataforma Petrolifera"));

			
		 pirata = new Pirata(1);
		 
		 pirataRepo.save(pirata);
			
		 pirataInstalacion1 = new PiratahasInstalacion(1, "Mina de Metal", 3);
		 pirataInstalacion2 = new PiratahasInstalacion(1, "Mina de Oro", 9);
		 pirataInstalacion3 = new PiratahasInstalacion(1, "Plataforma Petrolifera", 3);

		 pirataInstalacionRepo.save(pirataInstalacion1);
		 pirataInstalacionRepo.save(pirataInstalacion2);
		 pirataInstalacionRepo.save(pirataInstalacion3);

		 pirataNave = new PiratahasNave("Eagle", 1, 50);
		 
		 pirataNaveRepo.save(pirataNave);
		 
		 Sistema sistema = new Sistema("Atlas", 0, 0);
		 planeta1 = new Planeta(0, 0, "Atlas", "Pirata LvL 1", "ruta");
		 planeta1.setPirataidPirata(pirata);
		 
		 sistemaRepo.save(sistema);
		 planetaRepo.save(planeta1);
		 
		 PlanetahasRecurso planetaRecurso1 = new PlanetahasRecurso(planeta1.getCoordenadaX(), planeta1.getCoordenadaY(), planeta1.getSistemanombreSistema(), "Metal", "Mina de Metal", 500);
		 PlanetahasRecurso planetaRecurso2 = new PlanetahasRecurso(planeta1.getCoordenadaX(), planeta1.getCoordenadaY(), planeta1.getSistemanombreSistema(), "Oro", "Mina de Oro", 2500);
		 PlanetahasRecurso planetaRecurso3 = new PlanetahasRecurso(planeta1.getCoordenadaX(), planeta1.getCoordenadaY(), planeta1.getSistemanombreSistema(), "Petroleo", "Plataforma Petrolifera", 500);

		 ArrayList<PlanetahasRecurso> listaRecursos = new ArrayList<>();
		 listaRecursos.add(planetaRecurso1);
		 listaRecursos.add(planetaRecurso2);
		 listaRecursos.add(planetaRecurso3);
		 
		 planeta1.setPlanetahasRecursoList(listaRecursos);
		 
		 planetaRecursoRepo.save(planetaRecurso1);
		 planetaRecursoRepo.save(planetaRecurso2);
		 planetaRecursoRepo.save(planetaRecurso3);
		 
		 PlanetahasInstalacion planetaInstalacion1 = new PlanetahasInstalacion(planeta1.getCoordenadaX(), planeta1.getCoordenadaY(), planeta1.getSistemanombreSistema(), "Mina de Metal", 5, new Date());
		 PlanetahasInstalacion planetaInstalacion2 = new PlanetahasInstalacion(planeta1.getCoordenadaX(), planeta1.getCoordenadaY(), planeta1.getSistemanombreSistema(), "Mina de Oro", 3, new Date());
		 PlanetahasInstalacion planetaInstalacion3 = new PlanetahasInstalacion(planeta1.getCoordenadaX(), planeta1.getCoordenadaY(), planeta1.getSistemanombreSistema(), "Plataforma Petrolifera", 2, new Date());

		 ArrayList<PlanetahasInstalacion> listaInstalaciones = new ArrayList<>();
		 listaInstalaciones.add(planetaInstalacion1);
		 listaInstalaciones.add(planetaInstalacion2);
		 listaInstalaciones.add(planetaInstalacion3);
		 
		 planeta1.setPlanetahasInstalacionList(listaInstalaciones);
		 
		 planetaInstalacionRepo.save(planetaInstalacion1);
		 planetaInstalacionRepo.save(planetaInstalacion2);
		 planetaInstalacionRepo.save(planetaInstalacion3);
		 
		 PlanetaHasNave planetaNave = new PlanetaHasNave(planeta1.getCoordenadaX(), planeta1.getCoordenadaY(), planeta1.getSistemanombreSistema(), nave1.getNombreNave(), 42);
		 
		 planetaNaveRepo.save(planetaNave);

		 planetaRepo.save(planeta1);
	 
	 }
	
	@Transactional
	@Test
	public void testRegistroCorrecto()
	{
		Usuario usuario;
		
		assertTrue(CrudUsuario.registro("Pepe", "juan@gmail.com", "1234", "1234", repo, rolRepo).isEmpty());
		
		usuario = Usuario.cargarUsuario("Pepe", repo);
		
		assertTrue(CrudUsuario.inicializarUsuario(usuario, planetaNaveRepo, planetaInstalacionRepo, planetaRecursoRepo, planetaRepo).isEmpty());

		planeta1 = planetaRepo.findByUsuarioUsername(usuario);
		
		assertEquals(usuario.getEmail(), "juan@gmail.com");
		assertEquals(usuario.getUsername(), "Pepe");
		assertEquals(usuario.getActivo(), true);
		assertTrue(CheckPassword.verifyHash("1234", usuario.getPassword()));
		
		assertTrue(planeta1.getPirataidPirata() == null);
		assertEquals(planeta1.getPlanetahasInstalacionList().get(0).getNivelInstalacion(), 1);
		assertEquals(planeta1.getPlanetahasInstalacionList().get(1).getNivelInstalacion(), 1);
		assertEquals(planeta1.getPlanetahasInstalacionList().get(2).getNivelInstalacion(), 1);
		assertEquals(planeta1.getPlanetahasRecursoList().get(0).getCantidad(), 0);
		assertEquals(planeta1.getPlanetahasRecursoList().get(1).getCantidad(), 0);
		assertEquals(planeta1.getPlanetahasRecursoList().get(2).getCantidad(), 0);
		assertTrue(planeta1.getPlanetahasNaveList() == null);
		assertEquals(planeta1.getNombrePlaneta(), "Planeta de " +usuario.getUsername());

	}
	
	@Transactional
	@Test
	public void testBorrarCorrecto()
	{
		Usuario usuario;
		
		assertTrue(CrudUsuario.registro("Pepe", "juan@gmail.com", "1234", "1234", repo, rolRepo).isEmpty());
		
		usuario = Usuario.cargarUsuario("Pepe", repo);
		
		assertTrue(CrudUsuario.inicializarUsuario(usuario, planetaNaveRepo, planetaInstalacionRepo, planetaRecursoRepo, planetaRepo).isEmpty());
		assertTrue(CrudUsuario.borrarUsuario(usuario, planetaNaveRepo, planetaInstalacionRepo, planetaRecursoRepo, planetaRepo, pirataRepo, pirataInstalacionRepo, pirataNaveRepo, repo).isEmpty());
		
		planeta1 = planetaRepo.findByPlanetaLibre().get(0);
		
		assertEquals(planeta1.getPirataidPirata(), pirata);
		assertEquals(planeta1.getPlanetahasInstalacionList().get(0).getNivelInstalacion(), pirataInstalacion1.getNivelDefecto());
		assertEquals(planeta1.getPlanetahasInstalacionList().get(1).getNivelInstalacion(), pirataInstalacion2.getNivelDefecto());
		assertEquals(planeta1.getPlanetahasInstalacionList().get(2).getNivelInstalacion(), pirataInstalacion3.getNivelDefecto());
		assertEquals(planeta1.getPlanetahasRecursoList().get(0).getCantidad(), 0);
		assertEquals(planeta1.getPlanetahasRecursoList().get(1).getCantidad(), 0);
		assertEquals(planeta1.getPlanetahasRecursoList().get(2).getCantidad(), 0);
		assertEquals(planetaNaveRepo.findByNavenombreNavePlaneta(pirataNave.getNavenombreNave(), planeta1.getCoordenadaX(), planeta1.getCoordenadaY(), planeta1.getSistemanombreSistema()).get(0).getCantidad(), pirataNave.getCantidadDefecto());
		assertEquals(planeta1.getNombrePlaneta(), "Pirata LvL " + pirata.getIdPirata());
	}

}
