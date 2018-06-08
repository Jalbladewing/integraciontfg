package com.tfgllopis.integracion;

import static org.junit.Assert.*;

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
public class Registro 
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
	       
	     repo.save(usuario1);
	     repo.save(usuario2);
	 
	 }

	@Test
	public void testEmailCorrecto() 
	{
		String email = "myName@example.es";
		assertTrue(UserDataValidator.comprobarEmail(email));
	}
	
	@Test
	public void testEmailIncorrecto() 
	{
		String email = "myName";
		assertFalse(UserDataValidator.comprobarEmail(email));
	}
	
	@Test
	public void testEmailVacio() 
	{
		String email = "     ";
		assertFalse(UserDataValidator.comprobarEmail(email));
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
	
	@Test
	public void testPasswordCoincide() 
	{
		String pass1  = "hola123";
		String pass2 = "hola123";
		assertTrue(UserDataValidator.comprobarPassword(pass1, pass2));
	}
	
	@Test
	public void testPasswordNoCoincide() 
	{
		String pass1  = "hola123";
		String pass2 = "hola13";
		assertFalse(UserDataValidator.comprobarPassword(pass1, pass2));
	}
	
	@Test
	public void testPasswordVacio() 
	{
		String pass1  = "  ";
		String pass2 = "    ";
		assertFalse(UserDataValidator.comprobarPassword(pass1, pass2));
	}
	
	@Test
	public void testEmailExistente()
	{
		String email = "juanito@ual.es";
		assertTrue(UserDataValidator.comprobarEmailBD(email, repo));
	}
	
	@Test
	public void testEmailNoExistente()
	{
		String email = "paco@ual.es";
		assertFalse(UserDataValidator.comprobarEmailBD(email, repo));
	}
	
	@Test
	public void testUsuarioExistente()
	{
		String usuario = "juan";
		assertTrue(UserDataValidator.comprobarUsuarioBD(usuario, repo));
	}
	
	@Test
	public void testUsuarioNoExistente()
	{
		String usuario = "zacarias";
		assertFalse(UserDataValidator.comprobarUsuarioBD(usuario, repo));
	}
	
	@Transactional
	@Test
	public void testRegistroCorrecto()
	{
		Usuario aux;
		
		assertTrue(Registrarse.registro("Zacarias", "zacarias@gmail.com", "1234", "1234", repo, rolRepo).isEmpty());
		
		aux = Usuario.cargarUsuario("Zacarias", repo);
		
		assertEquals(aux.getEmail(), "zacarias@gmail.com");
		assertEquals(aux.getUsername(), "Zacarias");
		assertEquals(aux.getActivo(), true);
		assertTrue(CheckPassword.verifyHash("1234", aux.getPassword()));
	}
	
	@Test
	public void testRegistroUsuarioIncorrecto()
	{
		assertFalse(Registrarse.registro(" ", "juan@gmail.com", "1234", "1234", repo, rolRepo).isEmpty());
	}
	
	@Test
	public void testRegistroEmailIncorrecto()
	{
		assertFalse(Registrarse.registro("Pepe", "mail", "1234", "1234", repo, rolRepo).isEmpty());
	}
	
	@Test
	public void testRegistroPasswordIncorrecto()
	{
		assertFalse(Registrarse.registro("Pepe", "juan@gmail.com", "1234", "123", repo, rolRepo).isEmpty());
	}
	
	@Test
	public void testRegistroUsuarioExistente()
	{
		assertFalse(Registrarse.registro("juan", "juan@gmail.com", "1234", "1234", repo, rolRepo).isEmpty());
	}
	
	@Test
	public void testRegistroEmailExistente()
	{
		assertFalse(Registrarse.registro("Pepe", "juanito@ual.es", "1234", "1234", repo, rolRepo).isEmpty());
	}

}