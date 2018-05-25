package com.tfgllopis.integracion;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface TipoMensajeRepository extends JpaRepository<TipoMensaje, Integer> 
{
	public TipoMensaje findByName(@Param("name") String nombreTipo);
}
