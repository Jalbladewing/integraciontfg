package com.tfgllopis.integracion;

import java.util.List;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

	public List<Usuario> findByEmailLikeIgnoreCase(@Param("email") String string);

	public List<Usuario> findByUsername(@Param("username") String string);

}
