package com.tfgllopis.integracion;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PiratahasDesbloqueoNaveRepository extends JpaRepository<PiratahasDesbloqueoNave, Integer> 
{
	@Query
	public List<PiratahasDesbloqueoNave> findByPirataidPirata(@Param("Pirata_idPirata") int idPirata);
	@Query
	public List<PiratahasDesbloqueoNave> findByNavenombreNave(@Param("navenombreNave") String nombreNave);
}
