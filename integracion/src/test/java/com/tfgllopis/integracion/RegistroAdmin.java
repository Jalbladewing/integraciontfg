package com.tfgllopis.integracion;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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
public class RegistroAdmin 
{
	@Autowired
	private UsuarioRepository repo;
	
	@Autowired
	private RolesRepository rolRepo;
	
	private Usuario usuario1, usuario2;
	private Rol rol1, rol2;
	
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
	 
	 }
	
	@Test
	public void testUsuarioCorrecto() 
	{
		String user  = "Pablito";
		assertTrue(UserDataValidator.comprobarUser(user));
	}
	
	@Test
	public void testUsuarioVacio() 
	{
		String user  = "     ";
		assertFalse(UserDataValidator.comprobarUser(user));
	}
	
	@Transactional
	@Test
	public void testRegistroAdminCorrecto()
	{
		Usuario aux;
		
		assertTrue(CrudUsuario.registroAdmin("Pepe", "juan@gmail.com", "1234", "1234", "Jugador", true, repo, rolRepo).isEmpty());
		
		aux = Usuario.cargarUsuario("Pepe", repo);
		
		assertEquals(aux.getEmail(), "juan@gmail.com");
		assertEquals(aux.getUsername(), "Pepe");
		assertEquals(aux.getActivo(), true);
		assertEquals(aux.getRolName().getName(), "Jugador");
		assertTrue(CheckPassword.verifyHash("1234", aux.getPassword()));
	}
	
	@Test
	public void testRegistrAdminoUsuarioIncorrecto()
	{
		assertFalse(CrudUsuario.registroAdmin(" Pepe&Changed", "juan@gmail.com", "1234", "1234", "Jugador", true, repo, rolRepo).isEmpty());
	}
	
	@Test
	public void testRegistroAdminEmailIncorrecto()
	{
		assertFalse(CrudUsuario.registroAdmin("Pepe", "mail", "1234", "1234", "Jugador", true, repo, rolRepo).isEmpty());
	}
	
	@Test
	public void testRegistroAdminPasswordIncorrecto()
	{
		assertFalse(CrudUsuario.registroAdmin("Pepe", "juan@gmail.com", "1234", "123", "Jugador", true, repo, rolRepo).isEmpty());
	}
	
	@Test
	public void testRegistroAdminUsuarioExistente()
	{
		assertFalse(CrudUsuario.registroAdmin("juan", "juan@gmail.com", "1234", "1234", "Jugador", true, repo, rolRepo).isEmpty());
	}
	
	@Test
	public void testRegistroAdminEmailExistente()
	{
		assertFalse(CrudUsuario.registroAdmin("Pepe", "juanito@ual.es", "1234", "1234", "Jugador", true, repo, rolRepo).isEmpty());
	}
}
