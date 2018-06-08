package com.tfgllopis.integracion;

import static org.junit.Assert.*;

import java.text.SimpleDateFormat;
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
public class Login 
{

	@Autowired
	private UsuarioRepository repo;
	
	@Autowired
	private RolesRepository rolRepo;
	
	private Usuario usuario1, usuario2, usuario3;
	private Rol rol1, rol2;
	
	@Before
	@Transactional
	public void setUp() 
	{
	       
		 rol1 = new Rol("admin");
		 rol2 = new Rol("Jugador");
		 	
		 	
		 rolRepo.save(rol1);
		 rolRepo.save(rol2);
		 	
		 	
	     usuario1 = new Usuario("root@ual.es", "root","1234",false,new Date(), new Date());
	     usuario1.setRolName(rolRepo.findByName("admin").get(0));
	       
	     usuario2 = new Usuario("juanito@ual.es", "juan","damn",true,new Date(), new Date());
	     usuario2.setRolName(rolRepo.findByName("Jugador").get(0)); 
	     
	     usuario3 = new Usuario("admin@ual.es", "admin","1234",true,new Date(), new Date());
	     usuario3.setRolName(rolRepo.findByName("admin").get(0));
	       
	     repo.save(usuario1);
	     repo.save(usuario2);
	     repo.save(usuario3);
	 
	 }
	
	@Test
	public void testLoginCorrecto()
	{
		String username = "juan";
		String password = "damn";
		
		assertTrue(Iniciar_sesion.login(username, password, repo).isEmpty());	
	}
	
	@Test
	public void testLoginUsuarioIncorrecto()
	{
		String username = "";
		String password = "damn";
		
		assertFalse(Iniciar_sesion.login(username, password, repo).isEmpty());			
	}
	
	@Test
	public void testLoginPasswordIncorrecto()
	{
		String username = "juan";
		String password = "";
		
		assertFalse(Iniciar_sesion.login(username, password, repo).isEmpty());			
	}
	
	@Test
	public void testLoginJugador()
	{
		String username = "juan";
		String password = "damn";
		
		assertTrue(Iniciar_sesion.login(username, password, repo).isEmpty());			
		assertEquals(repo.findByUsername(username).get(0).getRolName().getName(), "Jugador");
	}
	
	@Test
	public void testLoginAdministrador()
	{
		String username = "admin";
		String password = "1234";
		
		assertTrue(Iniciar_sesion.login(username, password, repo).isEmpty());			
		assertEquals(repo.findByUsername(username).get(0).getRolName().getName(), "admin");
	}
	
	@Test
	public void testActivo()
	{
		String username = "juan";
		
		assertTrue(UserDataValidator.comprobarActivo(username, repo));
	}
	
	@Test
	public void testInactivo()
	{
		String username = "root";
		String password = "1234";
		
		assertFalse(Iniciar_sesion.login(username, password, repo).isEmpty());	
	}
	
	@Test
	public void testFechaAcceso()
	{
		Usuario user;
		String username = "juan";
		String password = "damn";
		Date aux = new Date();
		java.sql.Date fecha = new java.sql.Date(aux.getTime());
		
		
		Iniciar_sesion.login(username, password, repo);
		user = Usuario.cargarUsuario(username, repo);
		
		assertEquals(fecha.toString(), user.getFechaUltimoAcceso().toString());
	}

}
