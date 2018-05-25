package com.tfgllopis.integracion;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ModuloUsuarioApplicationTests {

	@Test
	public void testUsuarioFull() 
	{
		Date fechaAhora = new Date();
		Usuario user = new Usuario("juan@gmail.com", "Pepe", "1234", true, fechaAhora, fechaAhora);
		Rol rol = new Rol("Jugador");
		
		user.setRolName(rol);
		
		assertEquals("juan@gmail.com", user.getEmail());
		assertEquals("Pepe", user.getUsername());
		assertEquals(true, user.getActivo());
		assertEquals(fechaAhora, user.getFechaCreacion());
		assertEquals(fechaAhora, user.getFechaUltimoAcceso());
		assertTrue(CheckPassword.verifyHash("1234", user.getPassword()));
		assertEquals(rol, user.getRolName());
	}
	
	@Test
	public void testUsuarioEmail() 
	{
		Date fechaAhora = new Date();
		Usuario user = new Usuario("juan@gmail.com");
		
		assertEquals("juan@gmail.com", user.getEmail());
	}
	
	@Test
	public void testUsuarioEspacios() 
	{
		Date fechaAhora = new Date();
		Usuario user = new Usuario("juan@ gmail. com", "P ep e", "1 23 4", true, fechaAhora, fechaAhora);
		
		assertEquals("juan@gmail.com", user.getEmail());
		assertEquals("Pepe", user.getUsername());
		assertEquals(true, user.getActivo());
		assertEquals(fechaAhora, user.getFechaCreacion());
		assertEquals(fechaAhora, user.getFechaUltimoAcceso());
		assertTrue(CheckPassword.verifyHash("1234", user.getPassword()));
	}
	
	@Test
	public void testRol()
	{
		Rol rol = new Rol("Administrador");
		
		assertEquals("Administrador", rol.getName());
	}
	
	@Test
	public void testRolListaUsuario()
	{
		Date fechaAhora = new Date();
		Rol rol = new Rol("Jugador");
		
		Usuario user1 = new Usuario("juan@gmail.com", "Pepe", "1234", true, fechaAhora, fechaAhora);
		Usuario user2 = new Usuario("laurencio@gmail.com", "Jeremias", "1234", true, fechaAhora, fechaAhora);
		
		ArrayList<Usuario> listaUsuarios = new ArrayList<>();
		listaUsuarios.add(user1);
		listaUsuarios.add(user2);
		rol.setUsuarioList(listaUsuarios);
		
		
		assertEquals(rol.getUsuarioList().size(), 2);
		assertEquals(rol.getUsuarioList().get(0), user1);
		assertEquals(rol.getUsuarioList().get(1), user2);
	}
	


}
