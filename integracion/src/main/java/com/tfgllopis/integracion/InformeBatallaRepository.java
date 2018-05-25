package com.tfgllopis.integracion;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface InformeBatallaRepository extends JpaRepository<InformeBatalla, Integer> 
{
	@Query
	public InformeBatalla findByIdMovimiento(@Param("movimientoidMovimiento") Movimiento idMovimiento);
}
