package com.tfgllopis.integracion;

import java.util.List;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface NaveRepository extends JpaRepository<Nave, Integer> {

	@Query
	public List<Nave> findByNombreTipoNave(@Param("nombreTipoNave") String tipo);
	@Query
	public List<Nave> findByNombreNave(@Param("nombreNave") String nombre);

}