package com.tfgllopis.integracion;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PlanetahasRecursoRepository extends JpaRepository<PlanetahasRecurso, Integer> {

	@Query
	public PlanetahasRecurso findByPlanetaRecurso(@Param("planetacoordenadaX") int coordenadaX, @Param("planetacoordenadaY") Integer coordenadaY, @Param("planetaSistemanombreSistema") String sistema, @Param("recursoname") String nombreRecurso);
}