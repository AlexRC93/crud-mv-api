package com.alex.crudmv.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alex.crudmv.entity.Estabelecimento;

public interface EstabelecimentoRepository extends JpaRepository<Estabelecimento, Long> {
	
}
