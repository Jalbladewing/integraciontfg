package com.tfgllopis.integracion;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UsuariohasMensajeRepository extends JpaRepository<UsuariohasMensaje, Integer> {

	@Query
	public List<UsuariohasMensaje> findByUsuariousernameNoDescartado(@Param("usuariousername") String username);
	@Query
	public UsuariohasMensaje findByMensajeidMensajeUsuariousernameNoDescartado(@Param("mensajeidMensaje") int mensajeId, @Param("usuariousername") String username);
}
