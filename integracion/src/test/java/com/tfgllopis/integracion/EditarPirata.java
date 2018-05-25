package com.tfgllopis.integracion;

import static org.junit.Assert.*;

import java.text.DecimalFormatSymbols;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationForTests.class) 
public class EditarPirata 
{
	@Autowired
	private PirataRepository pirataRepo;
	
	@Autowired
	private InstalacionRepository instalacionRepo;
	
	@Autowired
	private TipoNaveRepository tipoNaveRepo;
	
	@Autowired
	private NaveRepository naveRepo;
	
	@Autowired
	private PiratahasInstalacionRepository pirataInstalacionRepo;
	
	@Autowired
	private PiratahasNaveRepository pirataNaveRepo;
	
	private TipoNave tipoNave1;
	private Instalacion instalacion1, instalacion2, instalacion3;
	
	@Before
	@Transactional
	public void setUp() 
	{		 	
		tipoNave1 = new TipoNave("Caza", "testUrl");
	 	
		tipoNaveRepo.save(tipoNave1);
		
		Nave nave1 = new Nave("Eagle", "/WEB-INF/images/image.png", 120, 200, 105, 22, 52, 10, 11, (short) 1);
		nave1.setTipoNavenombreTipoNave(tipoNave1);
		
		Nave nave2 = new Nave("Vulture", "/WEB-INF/images/image.png", 120, 200, 105, 22, 52, 10, 11, (short) 1);
		nave2.setTipoNavenombreTipoNave(tipoNave1);
		
		naveRepo.save(nave1);
		naveRepo.save(nave2);
		
		instalacion1 = new Instalacion("Mina de Metal", 20);
		instalacion2 = new Instalacion("Mina de Oro", 20);
		instalacion3 = new Instalacion("Plataforma Petrolifera", 20);
		
		instalacionRepo.save(instalacion1);
		instalacionRepo.save(instalacion2);
		instalacionRepo.save(instalacion3);
		
		pirataRepo.save(new Pirata(1));
		
		pirataInstalacionRepo.save(new PiratahasInstalacion(1, "Mina de Metal", 3));
		pirataInstalacionRepo.save(new PiratahasInstalacion(1, "Mina de Oro", 9));
		pirataInstalacionRepo.save(new PiratahasInstalacion(1, "Plataforma Petrolifera", 3));

		pirataNaveRepo.save(new PiratahasNave("Eagle", 1, 50));
	 }
	
	@Test
	public void testNumeroEntero()
	{
		String numeroFloat = " 2";
		
		assertTrue(NaveDataValidator.comprobarValorInteger(numeroFloat));
	}
	
	@Test
	public void testNumeroFloat()
	{
		String numeroFloat = " 2,8";
		
		assertFalse(NaveDataValidator.comprobarValorInteger(numeroFloat));
	}
	
	@Test
	public void testNumeroVacio()
	{
		String numeroVacio = "   ";
		
		assertFalse(NaveDataValidator.comprobarValorInteger(numeroVacio));
	}
	
	@Test
	public void testEditarPirataInstalacion() 
	{
		PiratahasInstalacion auxMetal, auxOro, auxPetroleo;
		PiratahasInstalacion instalacionMetal = pirataInstalacionRepo.findByPirataidPirata_Instalacionname(1, "Mina de Metal");
		instalacionMetal.setNivelDefecto(1);		
		
		PiratahasInstalacion instalacionOro = pirataInstalacionRepo.findByPirataidPirata_Instalacionname(1, "Mina de Oro");
		instalacionOro.setNivelDefecto(3);	
		
		PiratahasInstalacion instalacionPetroleo = pirataInstalacionRepo.findByPirataidPirata_Instalacionname(1, "Plataforma Petrolifera");
		instalacionPetroleo.setNivelDefecto(5);	
		
		Editar_pirata.updatePirataInstalacion(instalacionMetal, instalacionOro, instalacionPetroleo, "1", "3", "5", pirataInstalacionRepo);
		
		auxMetal = pirataInstalacionRepo.findByPirataidPirata_Instalacionname(1, "Mina de Metal");
		auxOro = pirataInstalacionRepo.findByPirataidPirata_Instalacionname(1, "Mina de Oro");
		auxPetroleo = pirataInstalacionRepo.findByPirataidPirata_Instalacionname(1, "Plataforma Petrolifera");
		
		assertEquals(auxMetal.getNivelDefecto(), 1);
		assertEquals(auxOro.getNivelDefecto(), 3);
		assertEquals(auxPetroleo.getNivelDefecto(), 5);
		
	}
	
	@Test
	public void testEditarPirataNave() 
	{
		PiratahasNave aux;
		PiratahasNave pirataNave = pirataNaveRepo.findByPirataidPirata_NavenombreNave(1, "Eagle");
		pirataNave.setCantidadDefecto(20);	
		pirataNaveRepo.save(pirataNave);
		
		aux = pirataNaveRepo.findByPirataidPirata_NavenombreNave(1, "Eagle");
		
		assertEquals(aux.getCantidadDefecto(), 20);
		
	}

}
