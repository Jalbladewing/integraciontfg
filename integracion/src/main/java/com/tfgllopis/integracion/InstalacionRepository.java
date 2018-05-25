package com.tfgllopis.integracion;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface InstalacionRepository extends JpaRepository<Instalacion, Integer> 
{

	@Query
	public Instalacion findByName(@Param("name") String instalacion); 

}
