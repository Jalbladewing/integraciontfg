package com.tfgllopis.integracion;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UsuarioHasNaveRepository extends JpaRepository<UsuarioHasNave, Integer> {

	@Query
	public List<UsuarioHasNave> findByNavenombreNaveUsuarioUsername(@Param("navenombreNave") String nombreNave, @Param("usuariousername") String username);
	@Query
	public List<UsuarioHasNave> findByUsuarioUsername(@Param("usuariousername") String usuariousername);
}
