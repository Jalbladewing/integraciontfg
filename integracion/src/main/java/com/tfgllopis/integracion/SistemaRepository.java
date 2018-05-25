package com.tfgllopis.integracion;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SistemaRepository extends JpaRepository<Sistema, Integer> {
	@Query
	public Planeta findByCoordenadaX(@Param("coordenadaX") int coordenadaX);
}
