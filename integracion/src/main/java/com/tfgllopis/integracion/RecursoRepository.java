package com.tfgllopis.integracion;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RecursoRepository extends JpaRepository<Recurso, Integer> 
{

	@Query
	public Recurso findByName(@Param("name") String recurso);

}
