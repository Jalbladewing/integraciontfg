package com.tfgllopis.integracion;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PlanetahasInstalacionRepository extends JpaRepository<PlanetahasInstalacion, Integer> 
{
	@Query
	public PlanetahasInstalacion findByInstalacionnamePlaneta(@Param("instalacionname") String nombreInstalacion, @Param("planetacoordenadaX") Integer coordenadaX, @Param("planetacoordenadaY") Integer coordenadaY, @Param("planetaSistemanombreSistema") String sistema);
}
