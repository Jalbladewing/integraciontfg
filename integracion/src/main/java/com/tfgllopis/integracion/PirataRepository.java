package com.tfgllopis.integracion;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PirataRepository extends JpaRepository<Pirata, Integer> 
{
	@Query
	public List<Pirata> findByIdPirata(@Param("idPirata") int idPirata);
}
