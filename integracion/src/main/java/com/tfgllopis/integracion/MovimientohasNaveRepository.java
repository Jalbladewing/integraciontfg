package com.tfgllopis.integracion;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MovimientohasNaveRepository extends JpaRepository<MovimientohasNave, Integer>  
{
	@Query
	public List<MovimientohasNave> findByMovimientoidMovimiento(@Param("movimientoidMovimiento") int idMovimiento);
}
