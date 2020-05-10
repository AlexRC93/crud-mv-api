package com.alex.crudmv.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alex.crudmv.entity.Estabelecimento;
import com.alex.crudmv.repository.EstabelecimentoRepository;

@Service
public class EstabelecimentoService {
	
	@Autowired
	private EstabelecimentoRepository estabelecimentoRepository;
	
	
	public List<Estabelecimento> listar() {
		return estabelecimentoRepository.findAll();
	}
	
	public Optional<Estabelecimento> buscarPorId(Long id) {
		return estabelecimentoRepository.findById(id);
	}
	
	public Estabelecimento persistir(Estabelecimento estabelecimento) {
		return estabelecimentoRepository.save(estabelecimento);
	}
	
	public void remover(Long id) {
		estabelecimentoRepository.deleteById(id);
	}

}
