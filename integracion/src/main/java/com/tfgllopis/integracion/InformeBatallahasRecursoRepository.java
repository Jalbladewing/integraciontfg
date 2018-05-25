package com.tfgllopis.integracion;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface InformeBatallahasRecursoRepository extends JpaRepository<InformeBatallahasRecurso, Integer>  
{
	@Query
	public List<InformeBatallahasRecurso> findByInformeBatallaidBatalla(@Param("informeBatallaidBatalla") int idInforme); 
}