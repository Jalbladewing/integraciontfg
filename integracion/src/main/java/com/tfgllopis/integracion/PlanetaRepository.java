package com.tfgllopis.integracion;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PlanetaRepository extends JpaRepository<Planeta, Integer> {

	@Query
	public List<Planeta> findByPlanetaLibre();
	@Query
	public Planeta findByUsuarioUsername(@Param("usuariousername") Usuario usuario);
	@Query
	public Planeta findPlaneta(@Param("coordenadaX") Integer coordenadaX, @Param("coordenadaY") Integer coordenadaY, @Param("sistemanombreSistema") String sistema);
	@Query
	public List<Planeta> findBySistemanombreSistema(@Param("sistemanombreSistema") String nombreSistema);
	@Query
	public List<Planeta> findByPirataId(@Param("pirataidPirata") int idPirata);
}

