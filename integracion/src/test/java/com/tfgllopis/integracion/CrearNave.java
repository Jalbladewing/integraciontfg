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
public class CrearNave 
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
	
	private Nave nave1, nave2;
	private TipoNave tipoNave1;
	private Recurso recurso1, recurso2, recurso3;
	private Instalacion instalacion1, instalacion2, instalacion3;
	
	@Before
	@Transactional
	public void setUp() 
	{
		Nave nave = new Nave("Eagle", "/WEB-INF/images/image.png", 120, 200, 105, 22, 52, 10, 11, (short) 1);
	       
		tipoNave1 = new TipoNave("Caza", "testUrl");
		 	
		tipoNaveRepo.save(tipoNave1);
		
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
	 
	 }
	
	@Test
	public void testNombreCorrecto()
	{
		String nombre = "Vulture";
		assertTrue(NaveDataValidator.comprobarNombre(nombre));
	}
	
	@Test
	public void testNombreVacio()
	{
		String nombre = "    ";
		assertFalse(NaveDataValidator.comprobarNombre(nombre));
	}
	
	@Test
	public void testImagenCorrecto()
	{
		String imagen = "/WEB-INF/images/image.png";
		assertTrue(NaveDataValidator.comprobarImagen(imagen));
	}
	
	@Test
	public void testImagenVacio()
	{
		String imagen = "    ";
		assertFalse(NaveDataValidator.comprobarImagen(imagen));
	}

	@Test
	public void testNumeroLetra()
	{
		String numeroLetra = " 2.2a";
		
		assertFalse(NaveDataValidator.comprobarValorFloat(numeroLetra));
	}
	
	@Test
	public void testNumeroEntero()
	{
		String numeroEntero = "2 ";
		
		assertTrue(NaveDataValidator.comprobarValorFloat(numeroEntero));
	}
	
	@Test
	public void testNumeroFloat()
	{
		String numeroFloat = " 2.8";
		
		assertTrue(NaveDataValidator.comprobarValorFloat(numeroFloat));
	}
	
	@Test
	public void testNumeroFloatComa()
	{
		String numeroFloat = " 2,8";
		
		assertTrue(NaveDataValidator.comprobarValorFloat(numeroFloat));
	}
	
	@Test
	public void testNumeroVacio()
	{
		String numeroVacio = "   ";
		
		assertFalse(NaveDataValidator.comprobarValorFloat(numeroVacio));
	}
	
	@Test
	public void testNaveExistente()
	{
		String nombreNave = "Eagle";
		
		assertFalse(NaveDataValidator.comprobarNaveBD(nombreNave, repo));
	}
	
	@Transactional
	@Test
	public void testCrearNaveCorrectoDesbloqueada()
	{
		Nave aux;
		ArrayList<PiratahasDesbloqueoNave> desbloqueos;
		String[] probabilidadesDesbloqueo = new String[]{};
		
		assertTrue(CrudNave.crearNave("Type-6", "imagenUrl", "Caza", "15", "20.3", "10.2", "50.4", "22.4","3.1", "80.9", "120", "140", "180", false, probabilidadesDesbloqueo, repo, tipoNaveRepo, naveCuestaRepo, pirataDesbloqueoRepo).isEmpty());
		
		aux = Nave.cargarNave("Type-6", repo);
		desbloqueos = new ArrayList<>(PiratahasDesbloqueoNave.cargarDesbloqueosNave("Type", pirataDesbloqueoRepo));
		
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
		assertTrue(desbloqueos.isEmpty());
	}
	
	@Transactional
	@Test
	public void testCrearNaveCorrectoBloqueada()
	{
		Nave aux;
		ArrayList<PiratahasDesbloqueoNave> desbloqueos;
		String[] probabilidadesDesbloqueo = new String[]{"10","15", "30", "60"};
		
		assertTrue(CrudNave.crearNave("Viper", "imagenUrl", "Caza", "15", "20.3", "10.2", "50.4", "22.4", "3.1", "80.9", "120", "140", "180", true, probabilidadesDesbloqueo, repo, tipoNaveRepo, naveCuestaRepo, pirataDesbloqueoRepo).isEmpty());
		
		aux = Nave.cargarNave("Viper", repo);
		desbloqueos = new ArrayList<>(PiratahasDesbloqueoNave.cargarDesbloqueosNave("Viper", pirataDesbloqueoRepo));
		
		assertEquals(aux.getNombreNave(), "Viper");
		assertEquals(aux.getRutaImagenNave(), "imagenUrl");
		assertEquals(aux.getTipoNavenombreTipoNave().getNombreTipoNave(), "Caza");
		assertEquals(aux.getSegundosConstruccion(), 15, 0.0);
		assertEquals(aux.getAtaqueNave(), 20.3, 0.0001);
		assertEquals(aux.getHullNave(), 10.2, 0.0001);
		assertEquals(aux.getEscudoNave(), 50.4, 0.0001);
		assertEquals(aux.getVelocidadNave(), 22.4, 0.0001);
		assertEquals(aux.getAgilidadNave(), 3.1, 0.0001);
		assertEquals(aux.getCapacidadCarga(), 80.9, 0.0001);
		assertEquals(desbloqueos.size(), 4);
		assertEquals(desbloqueos.get(2).getProbabilidadDesbloqueo(), 30, 0.0);
		
	}
	
	@Transactional
	@Test
	public void testCrearNaveCostesCorrectos()
	{
		Nave aux;
		int costeOro, costeMetal, costePetroleo;
		String[] probabilidadesDesbloqueo = new String[]{"10","15", "30", "60"};
		
		assertTrue(CrudNave.crearNave("Anaconda", "imagenUrl", "Caza", "15", "20.3", "10.2", "50.4", "22.4", "3.1", "80.9", "120", "140", "180", true, probabilidadesDesbloqueo, repo, tipoNaveRepo, naveCuestaRepo, pirataDesbloqueoRepo).isEmpty());
		
		aux = Nave.cargarNave("Anaconda", repo);
		costeOro = NavecuestaRecurso.cargarCosteNave("Oro", "Anaconda", naveCuestaRepo);
		costeMetal = NavecuestaRecurso.cargarCosteNave("Metal", "Anaconda", naveCuestaRepo);
		costePetroleo = NavecuestaRecurso.cargarCosteNave("Petroleo", "Anaconda", naveCuestaRepo);
		
		assertEquals(aux.getNombreNave(), "Anaconda");
		assertEquals(aux.getRutaImagenNave(), "imagenUrl");
		assertEquals(aux.getTipoNavenombreTipoNave().getNombreTipoNave(), "Caza");
		assertEquals(aux.getSegundosConstruccion(), 15, 0.0);
		assertEquals(aux.getAtaqueNave(), 20.3, 0.0001);
		assertEquals(aux.getHullNave(), 10.2, 0.0001);
		assertEquals(aux.getEscudoNave(), 50.4, 0.0001);
		assertEquals(aux.getVelocidadNave(), 22.4, 0.0001);
		assertEquals(aux.getAgilidadNave(), 3.1, 0.0001);
		assertEquals(aux.getCapacidadCarga(), 80.9, 0.0001);
		assertEquals(costeOro, 120);
		assertEquals(costeMetal, 140);
		assertEquals(costePetroleo, 180);
		
	}
	
	@Transactional
	@Test
	public void testCrearNaveNombreVacio()
	{
		String[] probabilidadesDesbloqueo = new String[]{"10","15", "30", "60"};
		
		assertFalse(CrudNave.crearNave("    ", "imagenUrl", "Caza", "15", "20.3", "10.2", "50.4", "22.4", "3.1", "80.9", "120", "140", "180", true, probabilidadesDesbloqueo, repo, tipoNaveRepo, naveCuestaRepo, pirataDesbloqueoRepo).isEmpty());
	}
	
	@Transactional
	@Test
	public void testCrearNaveImagenVacio()
	{
		String[] probabilidadesDesbloqueo = new String[]{"10","15", "30", "60"};
		
		assertFalse(CrudNave.crearNave("Vulture", "   ", "Caza", "15", "20.3", "10.2", "50.4", "22.4", "3.1", "80.9", "120", "140", "180", true, probabilidadesDesbloqueo, repo, tipoNaveRepo, naveCuestaRepo, pirataDesbloqueoRepo).isEmpty());
	}
	
	@Transactional
	@Test
	public void testCrearNaveSegundosIncorrectos()
	{
		String[] probabilidadesDesbloqueo = new String[]{"10","15", "30", "60"};
		
		assertFalse(CrudNave.crearNave("Vulture", "imagenUrl", "Caza", "-15", "20.3", "10.2", "50.4", "22.4", "3.1", "80.9", "120", "140", "180", true, probabilidadesDesbloqueo, repo, tipoNaveRepo, naveCuestaRepo, pirataDesbloqueoRepo).isEmpty());
	}
	
	@Transactional
	@Test
	public void testCrearNaveCaracteristicasIncorrectas()
	{
		String[] probabilidadesDesbloqueo = new String[]{"10","15", "30", "60"};
		
		assertFalse(CrudNave.crearNave("Vulture", "imagenUrl", "Caza", "15", "20.3a", "10.2", "50.4", "22.4", "3.1", "80.9", "120", "140", "180", true, probabilidadesDesbloqueo, repo, tipoNaveRepo, naveCuestaRepo, pirataDesbloqueoRepo).isEmpty());
		assertFalse(CrudNave.crearNave("Vulture", "imagenUrl", "Caza", "15", "20.3", "10.2`", "50.4", "22.4", "3.1", "80.9", "120", "140", "180", true, probabilidadesDesbloqueo, repo, tipoNaveRepo, naveCuestaRepo, pirataDesbloqueoRepo).isEmpty());
		assertFalse(CrudNave.crearNave("Vulture", "imagenUrl", "Caza", "15", "20.3", "10.2", "adwad", "22.4", "3.1", "80.9", "120", "140", "180", true, probabilidadesDesbloqueo, repo, tipoNaveRepo, naveCuestaRepo, pirataDesbloqueoRepo).isEmpty());
		assertFalse(CrudNave.crearNave("Vulture", "imagenUrl", "Caza", "15", "20.3", "10.2", "50.4", "2+2.*4", "3.1", "80.9", "120", "140", "180", true, probabilidadesDesbloqueo, repo, tipoNaveRepo, naveCuestaRepo, pirataDesbloqueoRepo).isEmpty());
		assertFalse(CrudNave.crearNave("Vulture", "imagenUrl", "Caza", "15", "20.3", "10.2", "50.4", "22.4", "  ", "80.9", "120", "140", "180", true, probabilidadesDesbloqueo, repo, tipoNaveRepo, naveCuestaRepo, pirataDesbloqueoRepo).isEmpty());
		assertFalse(CrudNave.crearNave("Vulture", "imagenUrl", "Caza", "15", "20.3", "10.2", "50.4", "22.4", "3.1", "8awd0awd.9", "120", "140", "180", true, probabilidadesDesbloqueo, repo, tipoNaveRepo, naveCuestaRepo, pirataDesbloqueoRepo).isEmpty());
	}
	
	@Transactional
	@Test
	public void testCrearNaveRecursosIncorrectos()
	{
		String[] probabilidadesDesbloqueo = new String[]{"10","15", "30", "60"};
		
		assertFalse(CrudNave.crearNave("Vulture", "imagenUrl", "Caza", "15", "20.3", "10.2", "50.4", "22.4", "3.1", "80.9", " ", "140", "180", true, probabilidadesDesbloqueo, repo, tipoNaveRepo, naveCuestaRepo, pirataDesbloqueoRepo).isEmpty());
		assertFalse(CrudNave.crearNave("Vulture", "imagenUrl", "Caza", "15", "20.3", "10.2", "50.4", "22.4", "3.1", "80.9", "120", "zsfz", "180", true, probabilidadesDesbloqueo, repo, tipoNaveRepo, naveCuestaRepo, pirataDesbloqueoRepo).isEmpty());
		assertFalse(CrudNave.crearNave("Vulture", "imagenUrl", "Caza", "15", "20.3", "10.2", "50.4", "22.4", "3.1", "80.9", "120", "140", "1!80", true, probabilidadesDesbloqueo, repo, tipoNaveRepo, naveCuestaRepo, pirataDesbloqueoRepo).isEmpty());
	}
	
	@Transactional
	@Test
	public void testCrearNaveProbabilidadesIncorrectas()
	{
		String[] probabilidadesDesbloqueo = new String[]{"10","15", "30", "60a"};
		
		assertFalse(CrudNave.crearNave("Vulture", "imagenUrl", "Caza", "15", "20.3", "10.2", "50.4", "22.4", "3.1", "80.9", "120", "140", "180", true, probabilidadesDesbloqueo, repo, tipoNaveRepo, naveCuestaRepo, pirataDesbloqueoRepo).isEmpty());
	}
	
	@Transactional
	@Test
	public void tesCrearNaveExistente()
	{
		String[] probabilidadesDesbloqueo = new String[]{"10","15", "30", "60"};
		
		assertFalse(CrudNave.crearNave("Eagle", "imagenUrl", "Caza", "15", "20.3", "10.2", "50.4", "22.4", "3.1", "80.9", "120", "140", "180", true, probabilidadesDesbloqueo, repo, tipoNaveRepo, naveCuestaRepo, pirataDesbloqueoRepo).isEmpty());
	}

}
