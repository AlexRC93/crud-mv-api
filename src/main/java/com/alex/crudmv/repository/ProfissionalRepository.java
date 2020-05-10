package com.alex.crudmv.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.alex.crudmv.entity.Profissional;

public interface ProfissionalRepository extends JpaRepository<Profissional, Long> {
	
	@Query("SELECT p FROM Profissional p WHERE p.estabelecimento.id = ?1")
	public List<Profissional> buscarPorEstabelecimeto(Long id);

}
