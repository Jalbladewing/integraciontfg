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
public class TestPerfil 
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
	
	@Transactional
	@Test
	public void testModificarPerfilCorrecto()
	{
		Usuario aux;
		
		assertTrue(Perfil.modificarPerfil("juan", "juanito@ual.es", "juan2@gmail.com", "12345", "12345", repo).isEmpty());
		
		aux = Usuario.cargarUsuario("juan", repo);
		
		assertEquals(aux.getEmail(), "juan2@gmail.com");
		assertTrue(CheckPassword.verifyHash("12345", aux.getPassword()));
	}
	
	@Test
	public void testModificarPerfilEmailIncorrecto()
	{
		assertFalse(Perfil.modificarPerfil("juan", "juanito@ual.es", "mail", "1234", "1234", repo).isEmpty());
	}
	
	@Test
	public void testModificarPerfilPasswordIncorrecto()
	{
		assertFalse(Perfil.modificarPerfil("juan", "juanito@ual.es", "juanito@ual.es", "1234", "123", repo).isEmpty());
	}
	
	@Test
	public void testModificarPerfilNoPassword()
	{
		assertTrue(Perfil.modificarPerfil("juan", "juanito@ual.es", "juanito@ual.es", "", "123", repo).isEmpty());
	}
	
	@Test
	public void testModificarPerfilEmailExistente()
	{
		assertFalse(Perfil.modificarPerfil("juan", "juanito@ual.es", "root@ual.es", "1234", "1234", repo).isEmpty());
	}
	
	@Test
	public void testNoModificarPerfil()
	{
		assertTrue(Perfil.modificarPerfil("juan", "juanito@ual.es", "juanito@ual.es", "damn", "damn", repo).isEmpty());
	}
	
	@Transactional
	@Test
	public void testModificarPerfilAdminCorrecto()
	{
		Usuario aux;
		
		assertTrue(Editar_usuario.modificarPerfil("juan", "juanito@ual.es", "juan2@gmail.com", "12345", "12345", "admin", false, repo, rolRepo).isEmpty());
		
		aux = Usuario.cargarUsuario("juan", repo);
		
		assertEquals(aux.getEmail(), "juan2@gmail.com");
		assertTrue(CheckPassword.verifyHash("12345", aux.getPassword()));
	}
	
	@Test
	public void testModificarPerfilAdminEmailIncorrecto()
	{
		assertFalse(Editar_usuario.modificarPerfil("juan", "juanito@ual.es", "mail", "1234", "1234", "Jugador", true, repo, rolRepo).isEmpty());
	}
	
	@Test
	public void testModificarPerfilAdminPasswordIncorrecto()
	{
		assertFalse(Editar_usuario.modificarPerfil("juan", "juanito@ual.es", "juanito@ual.es", "1234", "123", "Jugador", true, repo, rolRepo).isEmpty());
	}
	
	@Test
	public void testModificarPerfilAdminNoPassword()
	{
		assertTrue(Editar_usuario.modificarPerfil("juan", "juanito@ual.es", "juanito@ual.es", "", "123", "Jugador", true, repo, rolRepo).isEmpty());
	}
	
	@Test
	public void testModificarPerfilAdminEmailExistente()
	{
		assertFalse(Editar_usuario.modificarPerfil("juan", "juanito@ual.es", "root@ual.es", "1234", "1234", "Jugador", true, repo, rolRepo).isEmpty());
	}
	
	@Test
	public void testNoModificarPerfilAdmin()
	{
		assertTrue(Editar_usuario.modificarPerfil("juan", "juanito@ual.es", "juanito@ual.es", "damn", "damn", "Jugador", true, repo, rolRepo).isEmpty());
	}
	
	@Test
	public void testBorrarUsuarioUsuario()
	{
		Usuario.borrarUsuario("juan", repo);
		assertNull(Usuario.cargarUsuario("juan", repo));
	}

}