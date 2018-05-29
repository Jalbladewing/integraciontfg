package com.tfgllopis.integracion;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class) 
@SpringBootTest(classes = ApplicationForTests.class)
public class EditarNave 
{

	@Autowired
	private NaveRepository repo;
	
	@Autowired
	private TipoNaveRepository tipoNaveRepo;
	
	@Autowired
	private NavecuestaRecursoRepository naveCuestaRepo;
	
	@Autowired
	private RecursoRepository recursoRepo;
	
	@Autowired
	private InstalacionRepository instalacionRepo;
	
	@Autowired
	private PirataRepository pirataRepo;
	
	@Autowired
	private PiratahasDesbloqueoNaveRepository pirataDesbloqueoRepo;
	
	private TipoNave tipoNave1;
	private Recurso recurso1, recurso2, recurso3;
	private Instalacion instalacion1, instalacion2, instalacion3;
	
	@Before
	@Transactional
	public void setUp() 
	{
		String[] probabilidadesDesbloqueo = new String[]{"10","15", "30", "60"};
	       
		tipoNave1 = new TipoNave("Caza", "testUrl");
		 	
		tipoNaveRepo.save(tipoNave1);
		
		Nave nave = new Nave("Eagle", "/WEB-INF/images/image.png", 120, 200, 105, 22, 52, 10, 11, (short) 1);
		nave.setTipoNavenombreTipoNave(tipoNave1);
		repo.save(nave);
		 	
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
				
		pirataRepo.save(new Pirata(1));
		pirataRepo.save(new Pirata(2));
		pirataRepo.save(new Pirata(3));
		pirataRepo.save(new Pirata(4));
		
		CrudNave.crearNave("Type-6", "imagenUrl", "Caza", "15", "20.3", "10.2", "50.4", "22.4","3.1", "80.9", "120", "140", "180", true, probabilidadesDesbloqueo, repo, tipoNaveRepo, naveCuestaRepo, pirataDesbloqueoRepo);	 
	 }
	
	@Transactional
	@Test
	public void testModificarNaveCorrectoDesbloqueada()
	{
		Nave aux;
		ArrayList<PiratahasDesbloqueoNave> desbloqueos;
		String[] probabilidadesDesbloqueo = new String[]{"2","8","1","9"};
		
		assertTrue(CrudNave.modificarNave("Type-6", "imagenUrl", "Caza", "15", "20.3", "10.2", "50.4", "22.4","3.1", "80.9", "120", "140", "180", false, probabilidadesDesbloqueo, repo, tipoNaveRepo, naveCuestaRepo, pirataDesbloqueoRepo).isEmpty());
		
		aux = Nave.cargarNave("Type-6", repo);
		desbloqueos = new ArrayList<>(PiratahasDesbloqueoNave.cargarDesbloqueosNave("Type-6", pirataDesbloqueoRepo));
		
		assertEquals(aux.getNombreNave(), "Type-6");
		assertEquals(aux.getRutaImagenNave(), "imagenUrl");
		assertEquals(aux.getTipoNavenombreTipoNave().getNombreTipoNave(), "Caza");
		assertEquals(aux.getSegundosConstruccion(), 15, 0.0);
		assertEquals(aux.getAtaqueNave(), 20.3, 0.0001);
		assertEquals(aux.getHullNave(), 10.2, 0.0001);
		assertEquals(aux.getEscudoNave(), 50.4, 0.0001);
		assertEquals(aux.getVelocidadNave(), 22.4, 0.0001);
		assertEquals(aux.getAgilidadNave(), 3.1, 0.0001);
		assertEquals(aux.getCapacidadCarga(), 80.9, 0.0001);
		assertEquals(aux.getBloqueada(), 0);
	}
	
	@Transactional
	@Test
	public void testModificarNaveCorrectoBloqueada()
	{
		Nave aux;
		ArrayList<PiratahasDesbloqueoNave> desbloqueos;
		String[] probabilidadesDesbloqueo = new String[]{"2","8","1","9"};
		
		assertTrue(CrudNave.modificarNave("Type-6", "imagenUrl", "Caza", "15", "20.3", "10.2", "50.4", "22.4", "3.1", "80.9", "120", "140", "180", true, probabilidadesDesbloqueo, repo, tipoNaveRepo, naveCuestaRepo, pirataDesbloqueoRepo).isEmpty());
		
		aux = Nave.cargarNave("Type-6", repo);
		desbloqueos = new ArrayList<>(PiratahasDesbloqueoNave.cargarDesbloqueosNave("Type-6", pirataDesbloqueoRepo));
		
		assertEquals(aux.getNombreNave(), "Type-6");
		assertEquals(aux.getRutaImagenNave(), "imagenUrl");
		assertEquals(aux.getTipoNavenombreTipoNave().getNombreTipoNave(), "Caza");
		assertEquals(aux.getSegundosConstruccion(), 15, 0.0);
		assertEquals(aux.getAtaqueNave(), 20.3, 0.0001);
		assertEquals(aux.getHullNave(), 10.2, 0.0001);
		assertEquals(aux.getEscudoNave(), 50.4, 0.0001);
		assertEquals(aux.getVelocidadNave(), 22.4, 0.0001);
		assertEquals(aux.getAgilidadNave(), 3.1, 0.0001);
		assertEquals(aux.getCapacidadCarga(), 80.9, 0.0001);
		assertEquals(aux.getBloqueada(), 1);
		assertEquals(desbloqueos.size(), 4);
		assertEquals(desbloqueos.get(2).getProbabilidadDesbloqueo(), 1, 0.0);
		
	}
	
	@Transactional
	@Test
	public void testModificarNaveCostesCorrectos()
	{
		Nave aux;
		int costeOro, costeMetal, costePetroleo;
		String[] probabilidadesDesbloqueo = new String[]{"10","15", "30", "60"};
		
		assertTrue(CrudNave.modificarNave("Type-6", "imagenUrl", "Caza", "15", "20.3", "10.2", "50.4", "22.4","3.1", "80.9", "100", "104", "280", false, probabilidadesDesbloqueo, repo, tipoNaveRepo, naveCuestaRepo, pirataDesbloqueoRepo).isEmpty());
		
		aux = Nave.cargarNave("Type-6", repo);
		costeOro = NavecuestaRecurso.cargarCosteNave("Oro", "Type-6", naveCuestaRepo);
		costeMetal = NavecuestaRecurso.cargarCosteNave("Metal", "Type-6", naveCuestaRepo);
		costePetroleo = NavecuestaRecurso.cargarCosteNave("Petroleo", "Type-6", naveCuestaRepo);
		
		assertEquals(aux.getNombreNave(), "Type-6");
		assertEquals(aux.getRutaImagenNave(), "imagenUrl");
		assertEquals(aux.getTipoNavenombreTipoNave().getNombreTipoNave(), "Caza");
		assertEquals(aux.getSegundosConstruccion(), 15, 0.0);
		assertEquals(aux.getAtaqueNave(), 20.3, 0.0001);
		assertEquals(aux.getHullNave(), 10.2, 0.0001);
		assertEquals(aux.getEscudoNave(), 50.4, 0.0001);
		assertEquals(aux.getVelocidadNave(), 22.4, 0.0001);
		assertEquals(aux.getAgilidadNave(), 3.1, 0.0001);
		assertEquals(aux.getCapacidadCarga(), 80.9, 0.0001);
		assertEquals(costeOro, 100);
		assertEquals(costeMetal, 104);
		assertEquals(costePetroleo, 280);
		
	}
	
	@Transactional
	@Test
	public void testModificarNaveImagenVacio()
	{
		String[] probabilidadesDesbloqueo = new String[]{"10","15", "30", "60"};
		
		assertFalse(CrudNave.modificarNave("Type-6", "  ", "Caza", "15", "20.3", "10.2", "50.4", "22.4","3.1", "80.9", "100", "104", "280", false, probabilidadesDesbloqueo, repo, tipoNaveRepo, naveCuestaRepo, pirataDesbloqueoRepo).isEmpty());
	}
	
	@Transactional
	@Test
	public void testModificarNaveSegundosIncorrectos()
	{
		String[] probabilidadesDesbloqueo = new String[]{"10","15", "30", "60"};
		
		assertFalse(CrudNave.modificarNave("Type-6", "imagenUrl", "Caza", "-15", "20.3", "10.2", "50.4", "22.4","3.1", "80.9", "100", "104", "280", false, probabilidadesDesbloqueo, repo, tipoNaveRepo, naveCuestaRepo, pirataDesbloqueoRepo).isEmpty());
	}
	
	@Transactional
	@Test
	public void testModificarNaveCaracteristicasIncorrectas()
	{
		String[] probabilidadesDesbloqueo = new String[]{"10","15", "30", "60"};
		
		assertFalse(CrudNave.modificarNave("Type-6", "imagenUrl", "Caza", "15", "20.3a", "10.2", "50.4", "22.4","3.1", "80.9", "100", "104", "280", false, probabilidadesDesbloqueo, repo, tipoNaveRepo, naveCuestaRepo, pirataDesbloqueoRepo).isEmpty());
		assertFalse(CrudNave.modificarNave("Type-6", "imagenUrl", "Caza", "15", "20.3", "10.2`", "50.4", "22.4","3.1", "80.9", "100", "104", "280", false, probabilidadesDesbloqueo, repo, tipoNaveRepo, naveCuestaRepo, pirataDesbloqueoRepo).isEmpty());
		assertFalse(CrudNave.modificarNave("Type-6", "imagenUrl", "Caza", "15", "20.3", "10.2", "adwaad", "22.4","3.1", "80.9", "100", "104", "280", false, probabilidadesDesbloqueo, repo, tipoNaveRepo, naveCuestaRepo, pirataDesbloqueoRepo).isEmpty());
		assertFalse(CrudNave.modificarNave("Type-6", "imagenUrl", "Caza", "15", "20.3", "10.2", "50.4", "2+2*4","3.1", "80.9", "100", "104", "280", false, probabilidadesDesbloqueo, repo, tipoNaveRepo, naveCuestaRepo, pirataDesbloqueoRepo).isEmpty());
		assertFalse(CrudNave.modificarNave("Type-6", "imagenUrl", "Caza", "15", "20.3", "10.2", "50.4", "22.4","   ", "80.9", "100", "104", "280", false, probabilidadesDesbloqueo, repo, tipoNaveRepo, naveCuestaRepo, pirataDesbloqueoRepo).isEmpty());
		assertFalse(CrudNave.modificarNave("Type-6", "imagenUrl", "Caza", "15", "20.3", "10.2", "50.4", "22.4","3.1", "8adwada.9", "100", "104", "280", false, probabilidadesDesbloqueo, repo, tipoNaveRepo, naveCuestaRepo, pirataDesbloqueoRepo).isEmpty());
	}
	
	@Transactional
	@Test
	public void testModificarNaveRecursosIncorrectos()
	{
		String[] probabilidadesDesbloqueo = new String[]{"10","15", "30", "60"};
		
		assertFalse(CrudNave.modificarNave("Type-6", "imagenUrl", "Caza", "15", "20.3", "10.2", "50.4", "22.4","3.1", "80.9", "  ", "104", "280", false, probabilidadesDesbloqueo, repo, tipoNaveRepo, naveCuestaRepo, pirataDesbloqueoRepo).isEmpty());
		assertFalse(CrudNave.modificarNave("Type-6", "imagenUrl", "Caza", "15", "20.3", "10.2", "50.4", "22.4","3.1", "80.9", "100", "asdaz", "280", false, probabilidadesDesbloqueo, repo, tipoNaveRepo, naveCuestaRepo, pirataDesbloqueoRepo).isEmpty());
		assertFalse(CrudNave.modificarNave("Type-6", "imagenUrl", "Caza", "15", "20.3", "10.2", "50.4", "22.4","3.1", "80.9", "100", "104", "2!80", false, probabilidadesDesbloqueo, repo, tipoNaveRepo, naveCuestaRepo, pirataDesbloqueoRepo).isEmpty());
	}
	
	@Transactional
	@Test
	public void testModificarNaveProbabilidadesIncorrectas()
	{
		String[] probabilidadesDesbloqueo = new String[]{"10","15", "30", "60a"};
		
		assertFalse(CrudNave.modificarNave("Type-6", "imagenUrl", "Caza", "15", "20.3", "10.2", "50.4", "22.4","3.1", "80.9", "100", "104", "280", false, probabilidadesDesbloqueo, repo, tipoNaveRepo, naveCuestaRepo, pirataDesbloqueoRepo).isEmpty());
	}
}
