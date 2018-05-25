package com.tfgllopis.integracion;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface InformeBatallahasNaveDefensaRepository extends JpaRepository<InformeBatallahasNaveDefensa, Integer>  
{
	@Query
	public List<InformeBatallahasNaveDefensa> findByInformeBatallaidBatalla(@Param("informeBatallaidBatalla") int idInforme); 
}
