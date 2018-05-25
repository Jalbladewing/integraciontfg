package com.tfgllopis.integracion;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PiratahasNaveRepository extends JpaRepository<PiratahasNave, Integer> 
{
	@Query
	public List<PiratahasNave> findByPirataidPirata(@Param("pirataidPirata") int idPirata);
	@Query
	public PiratahasNave findByPirataidPirata_NavenombreNave(@Param("pirataidPirata") int idPirata, @Param("navenombreNave") String nombreNave);
}