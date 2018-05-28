package com.tfgllopis.integracion;

import java.sql.Timestamp;

public class CrudRecurso 
{
	public static String generarRecursos(Planeta planeta, PlanetahasRecursoRepository planetaRecursoRepo, PlanetahasInstalacionRepository planetaInstalacionRepo)
	{
		Timestamp fechaAhora = new Timestamp(System.currentTimeMillis());
		int generacionSegundoMetal, generacionSegundoOro, generacionSegundoPetroleo, metalGenerado, oroGenerado, petroleoGenerado, segundos;
		PlanetahasRecurso metalPlaneta, oroPlaneta, petroleoPlaneta;
		PlanetahasInstalacion instalacionMetal = planetaInstalacionRepo.findByInstalacionnamePlaneta("Mina de Metal", planeta.getCoordenadaX(), planeta.getCoordenadaY(), planeta.getSistemanombreSistema());
		PlanetahasInstalacion instalacionOro = planetaInstalacionRepo.findByInstalacionnamePlaneta("Mina de Oro", planeta.getCoordenadaX(), planeta.getCoordenadaY(), planeta.getSistemanombreSistema());
		PlanetahasInstalacion instalacionPetroleo = planetaInstalacionRepo.findByInstalacionnamePlaneta("Plataforma Petrolifera", planeta.getCoordenadaX(), planeta.getCoordenadaY(), planeta.getSistemanombreSistema());

		generacionSegundoMetal = instalacionMetal.getNivelInstalacion() * (int) instalacionMetal.getInstalacion().getGeneracionBase();
		generacionSegundoOro = instalacionOro.getNivelInstalacion() * (int) instalacionOro.getInstalacion().getGeneracionBase();
		generacionSegundoPetroleo = instalacionPetroleo.getNivelInstalacion() * (int) instalacionPetroleo.getInstalacion().getGeneracionBase();

		segundos =  (int) (fechaAhora.getTime()-instalacionMetal.getUltimaGeneracion().getTime())/1000;

		instalacionMetal.setUltimaGeneracion(fechaAhora);
		instalacionOro.setUltimaGeneracion(fechaAhora);
		instalacionPetroleo.setUltimaGeneracion(fechaAhora);
		
		metalGenerado = generacionSegundoMetal * segundos;
		oroGenerado = generacionSegundoOro * segundos;
		petroleoGenerado = generacionSegundoPetroleo * segundos;
		
		metalPlaneta = planetaRecursoRepo.findByPlanetaRecurso(planeta.getCoordenadaX(), planeta.getCoordenadaY(), planeta.getSistemanombreSistema(), "Metal");
		oroPlaneta = planetaRecursoRepo.findByPlanetaRecurso(planeta.getCoordenadaX(), planeta.getCoordenadaY(), planeta.getSistemanombreSistema(), "Oro");
		petroleoPlaneta = planetaRecursoRepo.findByPlanetaRecurso(planeta.getCoordenadaX(), planeta.getCoordenadaY(), planeta.getSistemanombreSistema(), "Petroleo");
		
		metalPlaneta.setCantidad(metalPlaneta.getCantidad() + metalGenerado);
		oroPlaneta.setCantidad(oroPlaneta.getCantidad() + oroGenerado);
		petroleoPlaneta.setCantidad(petroleoPlaneta.getCantidad() + petroleoGenerado);
		
		planetaRecursoRepo.save(metalPlaneta);
		planetaRecursoRepo.save(oroPlaneta);
		planetaRecursoRepo.save(petroleoPlaneta);
		
		planetaInstalacionRepo.save(instalacionMetal);
		planetaInstalacionRepo.save(instalacionOro);
		planetaInstalacionRepo.save(instalacionPetroleo);

		return "";
	}
	
	public static String subirNivel(Usuario usuario, String nombreInstalacion,PlanetaRepository planetaRepo, PlanetahasInstalacionRepository planetaInstalacionRepo, PlanetahasRecursoRepository planetaRecursoRepo, InstalacioncuestaRecursoRepository instalacionCuestaRepo)
	{
		Planeta planeta = planetaRepo.findByUsuarioUsername(usuario);
		PlanetahasInstalacion instalacion = planetaInstalacionRepo.findByInstalacionnamePlaneta(nombreInstalacion, planeta.getCoordenadaX(), planeta.getCoordenadaY(), planeta.getSistemanombreSistema());
		PlanetahasRecurso recursoMetal = planetaRecursoRepo.findByPlanetaRecurso(planeta.getCoordenadaX(), planeta.getCoordenadaY(), planeta.getSistemanombreSistema(), "Metal");
		PlanetahasRecurso recursoOro = planetaRecursoRepo.findByPlanetaRecurso(planeta.getCoordenadaX(), planeta.getCoordenadaY(), planeta.getSistemanombreSistema(), "Oro");
		PlanetahasRecurso recursoPetroleo = planetaRecursoRepo.findByPlanetaRecurso(planeta.getCoordenadaX(), planeta.getCoordenadaY(), planeta.getSistemanombreSistema(), "Petroleo");
		int costeMetal = instalacionCuestaRepo.findByRecursoInstalacionname(nombreInstalacion, "Metal").getCantidadBase() * instalacion.getNivelInstalacion();
		int costeOro = instalacionCuestaRepo.findByRecursoInstalacionname(nombreInstalacion, "Oro").getCantidadBase() * instalacion.getNivelInstalacion();
		int costePetroleo = instalacionCuestaRepo.findByRecursoInstalacionname(nombreInstalacion, "Petroleo").getCantidadBase() * instalacion.getNivelInstalacion();

		if(costeMetal > recursoMetal.getCantidad()) return "Metal insuficiente";
		if(costeOro > recursoOro.getCantidad()) return "Oro insuficiente";
		if(costePetroleo > recursoPetroleo.getCantidad()) return "Petroleo insuficiente";
		
		
		recursoMetal.setCantidad(recursoMetal.getCantidad()-costeMetal);
		recursoOro.setCantidad(recursoOro.getCantidad()-costeOro);
		recursoPetroleo.setCantidad(recursoPetroleo.getCantidad()-costePetroleo);
		
		instalacion.subirNivelInstalacion();
		planetaInstalacionRepo.save(instalacion);
		
		planetaRecursoRepo.save(recursoMetal);
		planetaRecursoRepo.save(recursoOro);
		planetaRecursoRepo.save(recursoPetroleo);
		
		return "";
	}

}
