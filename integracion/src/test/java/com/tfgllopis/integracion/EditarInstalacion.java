package com.tfgllopis.integracion;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class) 
@SpringBootTest(classes = ApplicationForTests.class)
public class EditarInstalacion 
{
	@Autowired
	private InstalacionRepository instalacionRepo;
	
	private Instalacion instalacion1, instalacion2, instalacion3;
	
	@Before
	@Transactional
	public void setUp() 
	{		 	
		instalacion1 = new Instalacion("Mina de Metal", 20);
		instalacion2 = new Instalacion("Mina de Oro", 20);
		instalacion3 = new Instalacion("Plataforma Petrolifera", 20);
		
		instalacionRepo.save(instalacion1);
		instalacionRepo.save(instalacion2);
		instalacionRepo.save(instalacion3);
	 }

	@Test
	public void testEditarGeneracionInstalacion() 
	{
		Instalacion auxMetal, auxOro, auxPetroleo;
		
		Instalacion instalacionMetal = Instalacion.cargarInstalacion("Mina de Metal", instalacionRepo);
		instalacionMetal.setGeneracionBase(120);
		
		Instalacion instalacionOro = Instalacion.cargarInstalacion("Mina de Oro", instalacionRepo);
		instalacionOro.setGeneracionBase(90);
		
		Instalacion instalacionPetroleo = Instalacion.cargarInstalacion("Plataforma Petrolifera", instalacionRepo);
		instalacionPetroleo.setGeneracionBase(160);
		
		Recursos_tecnico.updateInstalacion(instalacionMetal, instalacionOro, instalacionPetroleo, "120", "90", "160", instalacionRepo);
		
		auxMetal = Instalacion.cargarInstalacion("Mina de Metal", instalacionRepo);
		auxOro = Instalacion.cargarInstalacion("Mina de Oro", instalacionRepo);
		auxPetroleo = Instalacion.cargarInstalacion("Plataforma Petrolifera", instalacionRepo);
		
		assertEquals(auxMetal.getGeneracionBase(), 120, 0.0);
		assertEquals(auxOro.getGeneracionBase(), 90, 0.0);
		assertEquals(auxPetroleo.getGeneracionBase(), 160, 0.0);
		
	}

}
