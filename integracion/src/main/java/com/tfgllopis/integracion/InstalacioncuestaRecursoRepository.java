package com.tfgllopis.integracion;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface InstalacioncuestaRecursoRepository extends JpaRepository<InstalacioncuestaRecurso, Integer> 
{

	@Query
	public InstalacioncuestaRecurso findByRecursoInstalacionname(@Param("instalacionname") String nombreInstalacion, @Param("recursoname") String nombreRecurso);
}

