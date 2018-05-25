package com.tfgllopis.integracion;

import static org.junit.Assert.*;

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
public class GestionRecursos 
{
	@Autowired
	private UsuarioRepository userRepo;
	
	@Autowired
	private RolesRepository rolRepo;
	
	@Autowired
	private InstalacioncuestaRecursoRepository instalacionCuestaRepo;
	
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
	private PlanetahasInstalacionRepository planetaInstalacionRepo;
	
	private Usuario usuario;
	private Planeta planeta;
	private Recurso recurso1, recurso2, recurso3;
	private Instalacion instalacion1, instalacion2, instalacion3;
	private PlanetahasInstalacion planetaInstalacion1, planetaInstalacion2, planetaInstalacion3;
	
	@Before
	@Transactional
	public void setUp() 
	{	
		rolRepo.save(new Rol("Jugador"));
	       
	    usuario = new Usuario("juanito@ual.es", "juan","damn",true,new Date(), new Date());
	    usuario.setRolName(rolRepo.findByName("Jugador").get(0)); 
	    
	    userRepo.save(usuario);
		 	
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
		
		instalacionCuestaRepo.save(new InstalacioncuestaRecurso("Mina de Metal", "Metal", "Mina de Metal", 120));
		instalacionCuestaRepo.save(new InstalacioncuestaRecurso("Mina de Metal", "Oro", "Mina de Oro", 180));
		instalacionCuestaRepo.save(new InstalacioncuestaRecurso("Mina de Metal", "Petroleo", "Plataforma Petrolifera", 220));

		Sistema sistema = new Sistema("Atlas", 0, 0);
		sistemaRepo.save(sistema);
		
		planeta = new Planeta(0, 0, "Atlas", "Planeta Pepe", "");
		planeta.setUsuariousername(usuario);
		planetaRepo.save(planeta);
		
		planetaRecursoRepo.save(new PlanetahasRecurso(0, 0, "Atlas", "Metal", "Mina de Metal", 200));
		planetaRecursoRepo.save(new PlanetahasRecurso(0, 0, "Atlas", "Oro", "Mina de Oro", 250));
		planetaRecursoRepo.save(new PlanetahasRecurso(0, 0, "Atlas", "Petroleo", "Plataforma Petrolifera", 220));

		planetaInstalacion1 = new PlanetahasInstalacion(0, 0, "Atlas", "Mina de Metal", 1, new Date());
		planetaInstalacion1.setInstalacion(instalacion1);
		
		planetaInstalacion2 = new PlanetahasInstalacion(0, 0, "Atlas", "Mina de Oro", 1, new Date());
		planetaInstalacion2.setInstalacion(instalacion2);
		
		planetaInstalacion3 = new PlanetahasInstalacion(0, 0, "Atlas", "Plataforma Petrolifera", 1, new Date());
		planetaInstalacion3.setInstalacion(instalacion3);
		
		planetaInstalacionRepo.save(planetaInstalacion1);
		planetaInstalacionRepo.save(planetaInstalacion2);
		planetaInstalacionRepo.save(planetaInstalacion3);

	}
	
	@Transactional
	@Test
	public void testSubirNivel1Correcto()
	{
		assertTrue(CrudRecurso.subirNivel(usuario, "Mina de Metal", planetaRepo, planetaInstalacionRepo, planetaRecursoRepo, instalacionCuestaRepo).isEmpty());
		
		assertEquals(2, planetaInstalacionRepo.findByInstalacionnamePlaneta("Mina de Metal", 0, 0, "Atlas").getNivelInstalacion());
		assertEquals(80, planetaRecursoRepo.findByPlanetaRecurso(0, 0, "Atlas", "Metal").getCantidad());
		assertEquals(70, planetaRecursoRepo.findByPlanetaRecurso(0, 0, "Atlas", "Oro").getCantidad());
		assertEquals(0, planetaRecursoRepo.findByPlanetaRecurso(0, 0, "Atlas", "Petroleo").getCantidad());
	}
	
	@Transactional
	@Test
	public void testSubirNivel4Correcto()
	{
		PlanetahasRecurso auxMetal = planetaRecursoRepo.findByPlanetaRecurso(0, 0, "Atlas", "Metal");
		PlanetahasRecurso auxOro = planetaRecursoRepo.findByPlanetaRecurso(0, 0, "Atlas", "Oro");
		PlanetahasRecurso auxPetroleo = planetaRecursoRepo.findByPlanetaRecurso(0, 0, "Atlas", "Petroleo");
		auxMetal.setCantidad(480);
		auxOro.setCantidad(720);
		auxPetroleo.setCantidad(880);
		planetaRecursoRepo.save(auxMetal);
		planetaRecursoRepo.save(auxOro);
		planetaRecursoRepo.save(auxPetroleo);
		
		planetaInstalacion1.subirNivelInstalacion();
		planetaInstalacion1.subirNivelInstalacion();
		planetaInstalacion1.subirNivelInstalacion();
		
		planetaInstalacionRepo.save(planetaInstalacion1);
				
		assertTrue(CrudRecurso.subirNivel(usuario, "Mina de Metal", planetaRepo, planetaInstalacionRepo, planetaRecursoRepo, instalacionCuestaRepo).isEmpty());
		
		assertEquals(5, planetaInstalacionRepo.findByInstalacionnamePlaneta("Mina de Metal", 0, 0, "Atlas").getNivelInstalacion());
		assertEquals(0, planetaRecursoRepo.findByPlanetaRecurso(0, 0, "Atlas", "Metal").getCantidad());
		assertEquals(0, planetaRecursoRepo.findByPlanetaRecurso(0, 0, "Atlas", "Oro").getCantidad());
		assertEquals(0, planetaRecursoRepo.findByPlanetaRecurso(0, 0, "Atlas", "Petroleo").getCantidad());
	}
	
	@Transactional
	@Test
	public void testSubirNivelFaltaMetal()
	{
		PlanetahasRecurso aux = planetaRecursoRepo.findByPlanetaRecurso(0, 0, "Atlas", "Metal");
		aux.setCantidad(119);
		planetaRecursoRepo.save(aux);
		
		assertFalse(CrudRecurso.subirNivel(usuario, "Mina de Metal", planetaRepo, planetaInstalacionRepo, planetaRecursoRepo, instalacionCuestaRepo).isEmpty());
		
		assertEquals(1, planetaInstalacionRepo.findByInstalacionnamePlaneta("Mina de Metal", 0, 0, "Atlas").getNivelInstalacion());
		assertEquals(119, planetaRecursoRepo.findByPlanetaRecurso(0, 0, "Atlas", "Metal").getCantidad());
		assertEquals(250, planetaRecursoRepo.findByPlanetaRecurso(0, 0, "Atlas", "Oro").getCantidad());
		assertEquals(220, planetaRecursoRepo.findByPlanetaRecurso(0, 0, "Atlas", "Petroleo").getCantidad());
	}
	
	@Transactional
	@Test
	public void testSubirNivelFaltaOro()
	{
		PlanetahasRecurso aux = planetaRecursoRepo.findByPlanetaRecurso(0, 0, "Atlas", "Oro");
		aux.setCantidad(179);
		planetaRecursoRepo.save(aux);
		
		assertFalse(CrudRecurso.subirNivel(usuario, "Mina de Metal", planetaRepo, planetaInstalacionRepo, planetaRecursoRepo, instalacionCuestaRepo).isEmpty());
		
		assertEquals(1, planetaInstalacionRepo.findByInstalacionnamePlaneta("Mina de Metal", 0, 0, "Atlas").getNivelInstalacion());
		assertEquals(200, planetaRecursoRepo.findByPlanetaRecurso(0, 0, "Atlas", "Metal").getCantidad());
		assertEquals(179, planetaRecursoRepo.findByPlanetaRecurso(0, 0, "Atlas", "Oro").getCantidad());
		assertEquals(220, planetaRecursoRepo.findByPlanetaRecurso(0, 0, "Atlas", "Petroleo").getCantidad());
	}
	

	@Transactional
	@Test
	public void testSubirNivelFaltaPetroleo()
	{
		PlanetahasRecurso aux = planetaRecursoRepo.findByPlanetaRecurso(0, 0, "Atlas", "Petroleo");
		aux.setCantidad(210);
		planetaRecursoRepo.save(aux);
		
		assertFalse(CrudRecurso.subirNivel(usuario, "Mina de Metal", planetaRepo, planetaInstalacionRepo, planetaRecursoRepo, instalacionCuestaRepo).isEmpty());
		
		assertEquals(1, planetaInstalacionRepo.findByInstalacionnamePlaneta("Mina de Metal", 0, 0, "Atlas").getNivelInstalacion());
		assertEquals(200, planetaRecursoRepo.findByPlanetaRecurso(0, 0, "Atlas", "Metal").getCantidad());
		assertEquals(250, planetaRecursoRepo.findByPlanetaRecurso(0, 0, "Atlas", "Oro").getCantidad());
		assertEquals(210, planetaRecursoRepo.findByPlanetaRecurso(0, 0, "Atlas", "Petroleo").getCantidad());
	}
	
	@Transactional
	@Test
	public void testGenerarRecursos()
	{
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.SECOND, -280);
		Date fechaPosterior = calendar.getTime();
		int recursosMetal = (int) instalacion1.getGeneracionBase() * planetaInstalacion1.getNivelInstalacion() * 280 + planetaRecursoRepo.findByPlanetaRecurso(0, 0, "Atlas", "Metal").getCantidad();
		int recursosOro = (int) instalacion2.getGeneracionBase() * planetaInstalacion2.getNivelInstalacion() * 280 + planetaRecursoRepo.findByPlanetaRecurso(0, 0, "Atlas", "Oro").getCantidad();
		int recursosPetroleo = (int) instalacion3.getGeneracionBase() * planetaInstalacion3.getNivelInstalacion() * 280 + planetaRecursoRepo.findByPlanetaRecurso(0, 0, "Atlas", "Petroleo").getCantidad();

		planetaInstalacion1.setUltimaGeneracion(fechaPosterior);
		planetaInstalacion2.setUltimaGeneracion(fechaPosterior);
		planetaInstalacion3.setUltimaGeneracion(fechaPosterior);
		
		planetaInstalacionRepo.save(planetaInstalacion1);
		planetaInstalacionRepo.save(planetaInstalacion2);
		planetaInstalacionRepo.save(planetaInstalacion3);
		
		assertTrue(CrudRecurso.generarRecursos(planeta, planetaRecursoRepo, planetaInstalacionRepo).isEmpty());

		assertEquals(recursosMetal, planetaRecursoRepo.findByPlanetaRecurso(0, 0, "Atlas", "Metal").getCantidad());
		assertEquals(recursosOro, planetaRecursoRepo.findByPlanetaRecurso(0, 0, "Atlas", "Oro").getCantidad());
		assertEquals(recursosPetroleo, planetaRecursoRepo.findByPlanetaRecurso(0, 0, "Atlas", "Petroleo").getCantidad());
	}
	
}
