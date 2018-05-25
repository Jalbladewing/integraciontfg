package com.tfgllopis.integracion;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface NavecuestaRecursoRepository extends JpaRepository<NavecuestaRecurso, Integer> 
{

	@Query
	public NavecuestaRecurso findByNavenombreNave(@Param("Nave_nombreNave") String nave);
	@Query
	public NavecuestaRecurso findByRecursoname_NavenombreNave(@Param("navenombreNave") String nombreNave, @Param("recursoname") String nombreRecurso);
}

