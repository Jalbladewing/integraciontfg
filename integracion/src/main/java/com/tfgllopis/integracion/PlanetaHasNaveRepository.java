package com.tfgllopis.integracion;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PlanetaHasNaveRepository extends JpaRepository<PlanetaHasNave, Integer> {

	@Query
	public List<PlanetaHasNave> findByNavenombreNavePlaneta(@Param("navenombreNave") String nombreNave, @Param("planetacoordenadaX") Integer coordenadaX, @Param("planetacoordenadaY") Integer coordenadaY, @Param("planetaSistemanombreSistema") String sistema);
	
	@Query
	public List<PlanetaHasNave> findByPlaneta(@Param("planetacoordenadaX") Integer coordenadaX, @Param("planetacoordenadaY") Integer coordenadaY, @Param("planetaSistemanombreSistema") String sistema);
}
