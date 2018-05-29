package com.tfgllopis.integracion;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class) 
@SpringBootTest(classes = ApplicationForTests.class)
public class ModuloInicioSesionApplicationTests {

	@Test
	public void testUsuarioFull() 
	{
		Date fechaAhora = new Date();
		Usuario user = new Usuario("juan@gmail.com", "Pepe", "1234", true, fechaAhora, fechaAhora);
		
		assertEquals("juan@gmail.com", user.getEmail());
		assertEquals("Pepe", user.getUsername());
		assertEquals(true, user.getActivo());
		assertEquals(fechaAhora, user.getFechaCreacion());
		assertEquals(fechaAhora, user.getFechaUltimoAcceso());
		assertTrue(CheckPassword.verifyHash("1234", user.getPassword()));
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
	


}
