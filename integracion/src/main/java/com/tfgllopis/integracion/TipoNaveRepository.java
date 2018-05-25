package com.tfgllopis.integracion;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface TipoNaveRepository extends JpaRepository<TipoNave, Integer> {

	public List<TipoNave> findByNombreTipoNave(@Param("nombreTipoNave") String string);

}