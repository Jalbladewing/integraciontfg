package com.tfgllopis.integracion;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface InformeBatallahasNaveAtaqueRepository extends JpaRepository<InformeBatallahasNaveAtaque, Integer>  
{
	@Query
	public List<InformeBatallahasNaveAtaque> findByInformeBatallaidBatalla(@Param("informeBatallaidBatalla") int idInforme); 
}
