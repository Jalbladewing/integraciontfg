package com.tfgllopis.integracion;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PiratahasInstalacionRepository extends JpaRepository<PiratahasInstalacion, Integer> 
{
	@Query
	public List<PiratahasInstalacion> findByPirataidPirata(@Param("pirataidPirata") int idPirata);
	
	@Query
	public PiratahasInstalacion findByPirataidPirata_Instalacionname(@Param("pirataidPirata") int idPirata, @Param("instalacionname") String nombreInstalacion);
}