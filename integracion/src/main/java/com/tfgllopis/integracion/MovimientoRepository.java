package com.tfgllopis.integracion;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MovimientoRepository extends JpaRepository<Movimiento, Integer> 
{
	@Query
	public List<Movimiento> findByPlanetaDestino(@Param("planeta") Planeta planeta);
	
	@Query
	public List<Movimiento> findByMovimientosUsuario(@Param("planeta") Planeta planeta, @Param("usuariousername") Usuario usuario);
}
