package com.tfgllopis.integracion;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UsuarioconstruyeNaveRepository extends JpaRepository<UsuarioconstruyeNave, Integer> {

	@Query
	public List<UsuarioconstruyeNave> findByUsuariousername(@Param("usuariousername") String nombreUsuario);
}
