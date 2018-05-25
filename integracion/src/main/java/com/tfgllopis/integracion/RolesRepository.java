package com.tfgllopis.integracion;

import java.util.List;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface RolesRepository extends JpaRepository<Rol, Integer> {

	public List<Rol> findByName(@Param("name") String string);

}
