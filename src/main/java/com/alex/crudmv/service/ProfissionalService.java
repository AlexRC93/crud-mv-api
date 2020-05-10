package com.alex.crudmv.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alex.crudmv.entity.Profissional;
import com.alex.crudmv.repository.ProfissionalRepository;

@Service
public class ProfissionalService {
	
	@Autowired
	private ProfissionalRepository profissionalRepository;
	
	
	public List<Profissional> listar() {
		return profissionalRepository.findAll();
	}
	
	public Optional<Profissional> buscarPorId(Long id) {
		return profissionalRepository.findById(id);
	}
	
	public Profissional persistir(Profissional profissional) {
		return profissionalRepository.save(profissional);
	}
	
	public void remover(Long id) {
		profissionalRepository.deleteById(id);
	}
	
	public List<Profissional> buscarPorEstabelecimento(Long id) {
		return profissionalRepository.buscarPorEstabelecimeto(id);
	}

}
