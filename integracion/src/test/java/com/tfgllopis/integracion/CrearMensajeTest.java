package com.tfgllopis.integracion;

import static org.junit.Assert.*;

import java.util.Date;

import javax.transaction.Transactional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class) 
@SpringBootTest(classes = ApplicationForTests.class)
public class CrearMensajeTest 
{
	@Autowired
	UsuarioRepository usuarioRepo;
	
	@Autowired
	RolesRepository rolRepo;
	
	@Autowired
	MensajeRepository mensajeRepo;
	
	@Autowired
	UsuariohasMensajeRepository usuarioMensajeRepo;
	
	@Autowired
	TipoMensajeRepository tipoMensajeRepo;
		
	@Before
	@Transactional
	public void setUp() 
	{
		TipoMensaje tipoMensaje = new TipoMensaje("Sistema");
		Rol rol = new Rol("Jugador");
		
		rolRepo.save(rol);
		tipoMensajeRepo.save(tipoMensaje);
	
		Usuario usuario1 = new Usuario("juan@gmail.com", "Juan", "1234", true, new Date(), new Date());
		Usuario usuario2 = new Usuario("pedro@gmail.com", "Pedro", "1234", true, new Date(), new Date());
		Usuario usuario3 = new Usuario("test@gmail.com", "test", "1234", true, new Date(), new Date());

		usuario1.setRolName(rol);
		usuario2.setRolName(rol);
		usuario3.setRolName(rol);
		
		usuarioRepo.save(usuario1);
		usuarioRepo.save(usuario2);
		usuarioRepo.save(usuario3);
	 }
	
	@Transactional
	@Test
	public void testCrearMensajeCorrecto()
	{
		int numeroUsuarios = usuarioRepo.findAll().size();
		usuarioMensajeRepo.deleteAll();
		
		assertTrue(Crear_mensaje.crearMensaje("Mantenimiento", "Mantenimiento a las 15", usuarioRepo, mensajeRepo, tipoMensajeRepo, usuarioMensajeRepo).isEmpty());
		assertEquals(numeroUsuarios, usuarioMensajeRepo.findAll().size());
	}
	
	@Transactional
	@Test
	public void testCrearMensajeSinAsunto()
	{
		assertFalse(Crear_mensaje.crearMensaje("", "Mantenimiento a las 15", usuarioRepo, mensajeRepo, tipoMensajeRepo, usuarioMensajeRepo).isEmpty());
	}
	
	@Transactional
	@Test
	public void testCrearMensajeSinDescripcion()
	{
		assertFalse(Crear_mensaje.crearMensaje("Mantenimiento", " ", usuarioRepo, mensajeRepo, tipoMensajeRepo, usuarioMensajeRepo).isEmpty());
	}

}
